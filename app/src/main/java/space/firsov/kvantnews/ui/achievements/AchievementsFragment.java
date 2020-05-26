package space.firsov.kvantnews.ui.achievements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;

import space.firsov.kvantnews.R;
import space.firsov.kvantnews.User;
import space.firsov.kvantnews.ui.timetable.ChildrenDB;

public class AchievementsFragment extends Fragment  implements View.OnClickListener {
    private ArrayList<Achievement> listAchievements = new ArrayList<>();
    private AchievementsDB achievementsDB;
    private AchievementAdapter adapter;
    private ListView lv;
    private String login;
    private Button change_childName_btn;
    private String mainChild;
    private int type;
    private ArrayList<String> children;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_achievements, container, false);
        login = new User(getActivity()).getLogin();
        type = new User(getActivity()).getType();
        lv =  (ListView) root.findViewById(R.id.list_container);
        ImageButton reload_btn = (ImageButton)root.findViewById(R.id.reload_btn);
        change_childName_btn = (Button)root.findViewById(R.id.main_child_name);
        achievementsDB = new AchievementsDB(root.getContext());
        if(type == 2){
            mainChild = login;
            change_childName_btn.getLayoutParams().height = 0;
        }else if(type == 3 || type == 4){
            children = achievementsDB.selectUniqueChildren();
            if(children.size()==0){
                change_childName_btn.setText("Нет достижений");
            }else{
                mainChild = children.get(0);
                change_childName_btn.setText(mainChild);
            }
            change_childName_btn.setOnClickListener(this);
        }
        listAchievements = achievementsDB.selectAll(mainChild);
        if(listAchievements.size()!=0){
            adapter = new AchievementAdapter(getContext(), drawThreadNews());
            lv.setAdapter(adapter);
        }else{
            reloadPressed();
        }
        reload_btn.setOnClickListener(this);
        return root;
    }

    private Achievement[] drawThreadNews (){
        Achievement[] achievements = new Achievement[listAchievements.size()];
        for(int i=0;i<listAchievements.size();i++){
            achievements[i] = listAchievements.get(i);
        }
        return achievements;
    }

    private void reloadPressed() {
        if(isOnline()) {
            try {
                new GetUserAchievements(login, getContext()).execute().get();
            }catch (Exception e) {
                //
            }
            listAchievements = achievementsDB.selectAll(mainChild);
            adapter = new AchievementAdapter(getContext(), drawThreadNews());
            lv.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
        }
    }

    boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException | InterruptedException e)          { e.printStackTrace(); }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reload_btn:
                reloadPressed();
                break;
            case R.id.main_child_name:
                PopupMenu popupMenu = new PopupMenu(getContext(),v);
                for(int i=0;i<children.size();i++) {
                    popupMenu.getMenu().add(0,i,0,children.get(i));
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mainChild = children.get(item.getItemId());
                        change_childName_btn.setText(mainChild);
                        listAchievements = achievementsDB.selectAll(mainChild);
                        if(listAchievements.size() == 0){
                            reloadPressed();
                            return false;
                        }
                        adapter = new AchievementAdapter(getContext(), drawThreadNews());
                        lv.setAdapter(adapter);
                        return false;
                    }
                });
                popupMenu.show();
        }
    }
}
