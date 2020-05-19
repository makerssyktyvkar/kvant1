package space.firsov.kvantnews.ui.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import java.io.IOException;
import java.util.ArrayList;

import space.firsov.kvantnews.CoursesNewsOfUserDB;
import space.firsov.kvantnews.GetStudentCourseNews;
import space.firsov.kvantnews.R;
import space.firsov.kvantnews.User;
import space.firsov.kvantnews.courseNews;

public class PostsFragment extends Fragment  implements View.OnClickListener {
    private ArrayList<courseNews> listNews = new ArrayList<>();
    private CoursesNewsOfUserDB newsBD;
    private MyCourseNewsAdapter adapter;
    private ListView lv;
    private String login;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_posts, container, false);
        login = new User(getActivity()).getLogin();
        lv =  (ListView) root.findViewById(R.id.list_container);
        newsBD = new CoursesNewsOfUserDB(root.getContext());
        listNews = newsBD.selectAll();
        if(listNews.size()!=0){
            adapter = new MyCourseNewsAdapter(getContext(), drawThreadNews());
            lv.setAdapter(adapter);
        }else{
            reloadPressed();
        }
        ImageButton reload_btn = (ImageButton)root.findViewById(R.id.reload_btn);
        reload_btn.setOnClickListener(this);

        return root;
    }

    private courseNews[] drawThreadNews (){
        courseNews[] news = new courseNews[listNews.size()];
        for(int i=0;i<listNews.size();i++){
            news[i] = listNews.get(i);
        }
        return news;
    }

    private void reloadPressed() {
        if(isOnline()) {
            Toast.makeText(getContext(), R.string.please_wait, Toast.LENGTH_SHORT).show();
            try {
                new GetStudentCourseNews(login, getContext()).execute();
            }catch (Exception e) {
                //
            }
            listNews = newsBD.selectAll();
            adapter = new MyCourseNewsAdapter(getContext(), drawThreadNews());
            lv.setAdapter(adapter);
            Toast.makeText(getContext(), R.string.is_ready, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reload_btn:
                reloadPressed();
        }
    }
}
