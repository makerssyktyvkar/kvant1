package space.firsov.kvantnews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetAnswersAndQuestions extends AsyncTask<String, Void, String> {
    private QuestionsAnswersDB questionsAnswersDB;

    public GetAnswersAndQuestions(Context context) {
        questionsAnswersDB = new QuestionsAnswersDB(context);
    }

    @Override
    protected String doInBackground(String... args) {
        try {
            String url = "https://kvantfp.000webhostapp.com/ReturnFamousAnswer.php";
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
}