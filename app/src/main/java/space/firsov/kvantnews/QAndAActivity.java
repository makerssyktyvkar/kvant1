package space.firsov.kvantnews;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class QAndAActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Pair<String, String>> listOfAnswers;
    private QuestionsAnswersDB questionsAnswersDB;
    private QuestionsAndAnswersAdapter questionsAndAnswersAdapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        lv = findViewById(R.id.questions);
        questionsAnswersDB = new QuestionsAnswersDB(this);
        listOfAnswers = questionsAnswersDB.selectAll();
        questionsAndAnswersAdapter = new QuestionsAndAnswersAdapter(this,listOfAnswers);
        lv.setAdapter(questionsAndAnswersAdapter);
        if(listOfAnswers.size() == 0){
            reloadPressed();
        }
        ((ImageButton)findViewById(R.id.reload_btn)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reload_btn:
                reloadPressed();
                break;
        }
    }

    class GetAnswersAndQuestions extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... args) {
            try {
                String url = getString(R.string.main_host_dns) + "ReturnFamousAnswer.php";
                Document document = Jsoup.connect(url).get();
                Elements element = document.select("li[class=answer-item]");
                questionsAnswersDB.deleteAll();
                for (int i = 0; i < element.size(); i++) {
                    String question = element.eq(i).select("p[class=question]").eq(0).text();
                    String answer = element.eq(i).select("p[class=answer]").eq(0).text();
                    questionsAnswersDB.insert(question, answer);
                }
            } catch (Exception e) {
                //
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            listOfAnswers = questionsAnswersDB.selectAll();
            questionsAndAnswersAdapter = new QuestionsAndAnswersAdapter(getApplicationContext(),listOfAnswers);
            lv.setAdapter(questionsAndAnswersAdapter);
            Toast.makeText(getApplicationContext(),R.string.is_ready, Toast.LENGTH_SHORT).show();
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

    void reloadPressed(){
        if(isOnline()){
            Toast.makeText(this,R.string.please_wait, Toast.LENGTH_SHORT).show();
            new GetAnswersAndQuestions().execute();
        }else{
            Toast.makeText(this,R.string.no_internet_connection,Toast.LENGTH_SHORT).show();
        }
    }
}
