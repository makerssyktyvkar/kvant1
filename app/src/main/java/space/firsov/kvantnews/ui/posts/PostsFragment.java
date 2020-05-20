package space.firsov.kvantnews.ui.posts;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import space.firsov.kvantnews.R;
import space.firsov.kvantnews.User;

public class PostsFragment extends Fragment  implements View.OnClickListener {
    private ArrayList<courseNews> listNews = new ArrayList<>();
    private CoursesNewsOfUserDB newsBD;
    private MyCourseNewsAdapter adapter;
    private ListView lv;
    private String login;
    private Integer type;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_posts, container, false);
        login = new User(getActivity()).getLogin();
        type = new User(getContext()).getType();
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
            new GetCourseNews().execute();
        }else{
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
        }
    }
    private class GetCourseNews extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... args) {
            try {
                String url = "https://kvantfp.000webhostapp.com/ReturnCoursesNews.php?login=" + login;
                Document document = Jsoup.connect(url).get();
                Elements element = document.select("li[class=news-item]");
                newsBD.deleteAll();
                for (int i = 0; i < element.size(); i++) {
                    String name = element.eq(i).select("p[class=course_name]").eq(0).text();
                    String title = element.eq(i).select("h2[class=title]").eq(0).text();
                    String message = element.eq(i).select("p[class=message]").eq(0).text();
                    String time = element.eq(i).select("p[class=time]").eq(0).text();
                    String linkImage;
                    try{
                        linkImage = element.eq(i).select("img").eq(0).attr("src").substring(24);
                    }catch (Exception e){
                        linkImage = "";
                    }
                    newsBD.insert(name, title,message,linkImage,time);
                }
            } catch (Exception e) {
                //
            }
            return 1;
        }
        @Override
        public void onPostExecute(Integer s){
            super.onPostExecute(s);
            listNews = newsBD.selectAll();
            adapter = new MyCourseNewsAdapter(getContext(), drawThreadNews());
            lv.setAdapter(adapter);
            Toast.makeText(getContext(), R.string.is_ready, Toast.LENGTH_SHORT).show();
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
