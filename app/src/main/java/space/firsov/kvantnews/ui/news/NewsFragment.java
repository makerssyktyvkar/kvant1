package space.firsov.kvantnews.ui.news;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import space.firsov.kvantnews.R;

public class NewsFragment extends Fragment  implements View.OnClickListener {

    private ArrayList<News> listNews = new ArrayList<>();
    private static long back_pressed = -1;
    private NewsDB newsBD;
    private MyNewsAdapter adapter;
    private ListView lv;
    private boolean is_thread = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_news, container, false);
        lv =  (ListView) root.findViewById(R.id.list_container);

        newsBD = new NewsDB(root.getContext());
        listNews = newsBD.selectAll();

        if(listNews.size()!=0){
            adapter = new MyNewsAdapter(getContext(), drawThreadNews());
            lv.setAdapter(adapter);
        }else{
            reloadPressed();
        }
        Button reload_btn = (Button)root.findViewById(R.id.reload_btn);
        reload_btn.setOnClickListener(this);

        return root;
    }
    private News[] drawThreadNews (){
        News[] news = new News[listNews.size()];
        for(int i=0;i<listNews.size();i++){
            news[i] = listNews.get(i);
        }
        return news;
    }
    @SuppressLint("StaticFieldLeak")
    private class GetNews extends AsyncTask<String, Void, String> {


        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... args) {
            try {
                String url = "https://kvantfp.000webhostapp.com/ReturnNews.php";
                Document document = Jsoup.connect(url).get();
                Elements element = document.select("li[class=news-item]");
                newsBD.deleteAll();
                for(int i=0;i<element.size();i++){
                    String title = element.select("h2[class=title]").eq(i).text();
                    String desc = element.select("p[class=message]").eq(i).text();
                    String time = element.select("p[class=time]").eq(i).text();
                    String linkImage = element.select("img").eq(i).attr("src").substring(24);
                    listNews.add(new News(title, desc, linkImage, time));
                    newsBD.insert(title,desc,linkImage,time);
                }
            } catch (Exception e) {
                //
            }
            adapter = new MyNewsAdapter(getContext(), drawThreadNews());
            lv.setAdapter(adapter);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            is_thread = false;
        }
    }
    private void reloadPressed() {
        if(!is_thread){
            is_thread = true;
            new GetNews().execute();
            Toast.makeText(getContext(),"Wait, please.",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reload_btn:
                reloadPressed();
        }
    }
}
