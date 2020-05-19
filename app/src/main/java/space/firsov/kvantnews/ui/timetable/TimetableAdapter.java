package space.firsov.kvantnews.ui.timetable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import space.firsov.kvantnews.R;

public class TimetableAdapter extends ArrayAdapter<Timetable> {

    TimetableAdapter(Context context, Timetable[] arr){
        super(context, R.layout.timetable_adapter, arr);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Timetable timetable = getItem(position);

        assert timetable != null;
        String monday = timetable.monday;
        String tuesday = timetable.tuesday;
        String wednesday = timetable.wednesday;
        String thursday = timetable.thursday;
        String friday = timetable.friday;
        String saturday = timetable.saturday;
        String sunday = timetable.sunday;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.timetable_adapter, null);
        }

        ((TextView)convertView.findViewById(R.id.course_name)).setText(timetable.course);
        ((TextView)convertView.findViewById(R.id.groupp)).setText(timetable.group);
        ((TextView)convertView.findViewById(R.id.monday)).setText(monday.equals("")? null: "Понедельник: "+monday);
        ((TextView)convertView.findViewById(R.id.tuesday)).setText(tuesday.equals("")? null: "Вторник: " + tuesday);
        ((TextView)convertView.findViewById(R.id.wednesday)).setText(wednesday.equals("")? null : "Среда: " + wednesday);
        ((TextView)convertView.findViewById(R.id.thursday)).setText(thursday.equals("")? null: "Четверг: " + thursday);
        ((TextView)convertView.findViewById(R.id.friday)).setText(friday.equals("")? null: "Пятница: " + friday);
        ((TextView)convertView.findViewById(R.id.saturday)).setText(saturday.equals("")? null: "Суббота: " + saturday);
        ((TextView)convertView.findViewById(R.id.sunday)).setText(sunday.equals("")? null: "Воскресенье: " + sunday);

        return convertView;
    }
}
