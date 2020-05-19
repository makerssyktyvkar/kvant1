package space.firsov.kvantnews.ui.posts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import space.firsov.kvantnews.R;
import space.firsov.kvantnews.courseNews;

public class MyCourseNewsAdapter extends ArrayAdapter<courseNews> {

    public MyCourseNewsAdapter(Context context, courseNews[] arr) {
        super(context, R.layout.news_adapter, arr);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final courseNews news = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_news_adapter, null);
        }

        assert news != null;
        ((TextView) convertView.findViewById(R.id.course_name)).setText(news.courseName);
        ((TextView) convertView.findViewById(R.id.title)).setText(news.title);
        ((TextView) convertView.findViewById(R.id.message)).setText(String.valueOf(news.message));
        ((TextView) convertView.findViewById(R.id.date)).setText(String.valueOf(news.additionalInfo));
        if(news.image != null) ((ImageView) convertView.findViewById(R.id.news_image)).setImageBitmap(news.image);

        return convertView;
    }
}