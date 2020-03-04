package space.firsov.kvantnews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class StudentActivity extends FragmentActivity implements View.OnClickListener{
    private LinearLayout mainContainer;
    private ArrayList<News> listNews = new ArrayList<>();
    private ArrayList<LinearLayout> listLinearNews = new ArrayList<>();
    private NewsDB newsBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        TextView tw_username = findViewById(R.id.tw_username);
        ImageButton btn_selector = findViewById(R.id.selector);
        mainContainer = findViewById(R.id.main_container);

        String username = getIntent().getStringExtra("student_name");
        tw_username.setText(username);

        newsBD = new NewsDB(this);
        listNews = newsBD.selectAll();

        btn_selector.setOnClickListener(this);

        if(listNews.size()!=0){
            try{
                new DrawThreadNews().execute().get();
            }catch (Exception e){
                //
            }
        }else{
            try{
                new GetNews().execute().get();
            }catch(Exception e){
                //
            }
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class DrawThreadNews extends AsyncTask<String, Void, Void> {
        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(String... args) {
            int i = 0;
            for (News news : listNews) {
                drawNews(i, news.title, news.message, news.image, news.additionalInfo);
                i++;
            }

            return null;
        }
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
                for(View view: listLinearNews){
                    mainContainer.removeView(view);
                }
                listLinearNews.clear();
                //newsBD.deleteAll();
                for(int i=0;i<element.size();i++){
                    String title = element.select("h2[class=title]").eq(i).text();
                    String desc = element.select("p[class=message]").eq(i).text();
                    String time = element.select("p[class=time]").eq(i).text();
                    String linkImage = /*document.baseUri() + */element.select("img").eq(i).attr("src").substring(24);
                    listNews.add(new News(title, desc, linkImage, time));
                    newsBD.insert(title,desc,linkImage,time);
                }
            } catch (Exception e) {
                //
            }
            int i=0;
            for(News news : listNews){
                drawNews(i,news.title,news.message,news.image, news.additionalInfo);
                i++;
            }
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
        }
    }

    @SuppressLint("SetTextI18n")
    private void drawNews(int id, String title, String message, Bitmap image, String time){
        listLinearNews.add(new LinearLayout(getApplicationContext()));
        TextView tw_title = new TextView(getApplicationContext());
        TextView tw_message = new TextView(getApplicationContext());
        ImageView iw_img = new ImageView(getApplicationContext());
        TextView tw_time = new TextView(getApplicationContext());

        listLinearNews.get(id).setId(id);
        listLinearNews.get(id).setOrientation(LinearLayout.VERTICAL);
        listLinearNews.get(id).setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
        );

        /*linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainContainer.removeView(v);
            }
        });*/

        tw_title.setText(title);
        tw_title.setTextSize(35);
        tw_title.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
        );

        tw_message.setText(message);
        tw_message.setTextSize(25);
        tw_message.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
        );

        //Picasso.get().load(src_img).transform(new CropSquareTransformation()).into(iw_img);
        iw_img.setImageBitmap(image);
        iw_img.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT)
        );

        tw_time.setText(time);
        tw_time.setTextSize(15);
        tw_time.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
        );

        listLinearNews.get(id).addView(tw_title);
        listLinearNews.get(id).addView(tw_message);
        listLinearNews.get(id).addView(tw_time);
        listLinearNews.get(id).addView(iw_img);
        mainContainer.addView(listLinearNews.get(id));
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
