package space.firsov.kvantnews;

import android.os.Bundle;
import android.util.Pair;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QAndAActivity extends AppCompatActivity {
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
    }
}
