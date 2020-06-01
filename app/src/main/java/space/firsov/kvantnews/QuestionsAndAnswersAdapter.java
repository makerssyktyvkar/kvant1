package space.firsov.kvantnews;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionsAndAnswersAdapter extends ArrayAdapter<Pair<String, String>> {
    private LayoutInflater inflater;
    private Context context;

    public QuestionsAndAnswersAdapter(Context context, ArrayList<Pair<String, String>> questions) {
        super(context, R.layout.question_adapter, questions);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Pair<String,String> question = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.question_adapter, null);
        }
        assert question != null;
        ((TextView) convertView.findViewById(R.id.answer)).setText(question.first);
        ((TextView) convertView.findViewById(R.id.question)).setText(question.second);

        return convertView;
    }
}