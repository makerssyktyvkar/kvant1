package space.firsov.kvantnews;

import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetAnswersAndQuestions extends AsyncTask<String, Void, String> {
    private QuestionsAnswersDB questionsAnswersDB;
    private Context context;

    public GetAnswersAndQuestions(Context context) {
        questionsAnswersDB = new QuestionsAnswersDB(context);
        this.context = context;
    }

    @Override
    protected String doInBackground(String... args) {
        try {
            String url = context.getResources().getString(R.string.main_host_dns) + "ReturnFamousAnswer.php";
            Document document = Jsoup.connect(url).maxBodySize(0).get();
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