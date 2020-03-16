package space.firsov.kvantnews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StudentActivity extends FragmentActivity implements View.OnClickListener{
    private ArrayList<News> listNews = new ArrayList<>();
    private NewsDB newsBD;
    private MyNewsAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        TextView tw_username = findViewById(R.id.tw_username);
        ImageButton btn_selector = findViewById(R.id.selector);
        ImageButton btn_reload = findViewById(R.id.reload_button);
        lv =  (ListView) findViewById(R.id.list_container);

        String username = getIntent().getStringExtra("student_name");
        tw_username.setText(username);

        newsBD = new NewsDB(this);
        listNews = newsBD.selectAll();

        btn_selector.setOnClickListener(this);
        btn_reload.setOnClickListener(this);

        if(listNews.size()!=0){
            adapter = new MyNewsAdapter(this, drawThreadNews());
            lv.setAdapter(adapter);
        }else{
            try{
                new GetNews().execute().get();
            }catch(Exception e){
                //
            }
        }

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
            adapter = new MyNewsAdapter(getApplicationContext(), drawThreadNews());
            lv.setAdapter(adapter);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }


    private void startLoginActivity(){
        Intent intent = new Intent(StudentActivity.this, LoginActivity.class);
        intent.putExtra("type",1);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selector:
                showPopupMenu(v);
            case R.id.reload_button:
                new GetNews().execute();
        }
    }


    @SuppressLint("ResourceType")
    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.inflate(R.layout.popup_menu2);
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.exit:
                                startLoginActivity();
                                return true;
                            case R.id.settings:
                                //startSettingsActivity();
                                return true;
                            case R.id.reload:
                                new GetNews().execute();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
        popupMenu.show();
    }
}
