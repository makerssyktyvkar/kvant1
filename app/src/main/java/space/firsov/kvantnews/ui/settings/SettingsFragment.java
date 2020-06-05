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

import space.firsov.kvantnews.InfoActivity;
import space.firsov.kvantnews.IsNotification;
import space.firsov.kvantnews.QAndAActivity;
import space.firsov.kvantnews.R;

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
                notificated = !notificated;
                if(!notificated){
                    OneSignal.setSubscription(false);
                }else{
                    OneSignal.setSubscription(true);
                }
                new IsNotification(getContext()).change(notificated ? 1:0);
                Toast.makeText(getContext(),"Настройки уведомлений успешно изменены", Toast.LENGTH_SHORT).show();
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
}
