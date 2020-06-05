package space.firsov.kvantnews.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.onesignal.OneSignal;

import java.io.IOException;

import space.firsov.kvantnews.AddToMainDBOSId;
import space.firsov.kvantnews.InfoActivity;
import space.firsov.kvantnews.IsNotification;
import space.firsov.kvantnews.QAndAActivity;
import space.firsov.kvantnews.R;
import space.firsov.kvantnews.User;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private CheckBox is_notifications_btn;
    private Button questions_and_answers_btn;
    private Button info_btn;
    private boolean notificated;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        questions_and_answers_btn = root.findViewById(R.id.button_q_and_a);
        info_btn = root.findViewById(R.id.button_info);
        is_notifications_btn = root.findViewById(R.id.checkbox_notification);
        if(new IsNotification(getContext()).select() == 0){
            notificated = false;
        }else{
            notificated = true;
            is_notifications_btn.setChecked(true);
        }
        is_notifications_btn.setOnClickListener(this);
        info_btn.setOnClickListener(this);
        questions_and_answers_btn.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_q_and_a:
                startQAndA();
                break;
            case R.id.button_info:
                startInfoActivity();
                break;
            case R.id.checkbox_notification:
                if(isOnline()) {
                    notificated = !notificated;
                    if (!notificated) {
                        OneSignal.sendTag("sub","false");
                        new AddToMainDBOSId(new User(getContext()).getLogin(), "").execute();
                    } else {
                        OneSignal.sendTag("sub","true");
                        new AddToMainDBOSId(new User(getContext()).getLogin(), User.UniqueID).execute();
                    }
                    new IsNotification(getContext()).change(notificated ? 1 : 0);
                    Toast.makeText(getContext(), "Настройки уведомлений успешно изменены", Toast.LENGTH_SHORT).show();
                }else{
                    is_notifications_btn.setChecked(notificated);
                    Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void startQAndA(){
        Intent intent = new Intent(getActivity(), QAndAActivity.class);
        startActivity(intent);

    }

    private void startInfoActivity(){
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        startActivity(intent);
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException | InterruptedException e)          { e.printStackTrace(); }
        return false;
    }
}
