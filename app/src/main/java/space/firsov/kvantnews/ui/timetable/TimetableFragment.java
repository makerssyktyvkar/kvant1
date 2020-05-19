package space.firsov.kvantnews.ui.timetable;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import space.firsov.kvantnews.R;
import space.firsov.kvantnews.User;

public class TimetableFragment extends Fragment {
    public String login;
    private ArrayList<Timetable> TimetableList = new ArrayList<>();
    private TimetableDB timetableDB;
    private TimetableAdapter adapter;
    private ListView lv;
    private boolean is_thread = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timetable, container, false);
        lv = root.findViewById(R.id.timetable);
        timetableDB = new TimetableDB(root.getContext());
        TimetableList = timetableDB.selectAll();
        User user = new User(getContext());
        login = user.getLogin();

        if (TimetableList.size()!=0){
            adapter = new TimetableAdapter(getContext(), drawThreadTimetable());
            lv.setAdapter(adapter);
        }else{
            loader();
        }
        return root;
    }

    private Timetable[] drawThreadTimetable(){
        Timetable[] timetable = new Timetable[TimetableList.size()];
        for(int i=0;i<TimetableList.size();i++){
            timetable[i] = TimetableList.get(i);
        }
        return timetable;
    }

    @SuppressLint("StaticFieldLeak")
    private class GetTimetable extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            String url = "http://kvantfp.000webhostapp.com/ReturnTimetableForStudent.php?login="+login;
            Document document = null;
            try {
                document = Jsoup.connect(url).get();
                Elements element = document.select("li[class=timetable-item]");
                timetableDB.deleteAll();
                TimetableList.clear();
                for(int i=0;i<element.size();i++){
                    String course = element.eq(i).select("p[class=name_course]").eq(0).text();
                    String group = element.eq(i).select("p[class=name_group]").eq(0).text();
                    String monday = element.eq(i).select("p[class=monday]").eq(0).text();
                    String tuesday = element.eq(i).select("p[class=tuesday]").eq(0).text();
                    String wednesday = element.eq(i).select("p[class=wednesday]").eq(0).text();
                    String thursday = element.eq(i).select("p[class=thursday]").eq(0).text();
                    String friday = element.eq(i).select("p[class=friday]").eq(0).text();
                    String saturday = element.eq(i).select("p[class=saturday]").eq(0).text();
                    String sunday = element.eq(i).select("p[class=sunday]").eq(0).text();
                    TimetableList.add(new Timetable(course, group, monday, tuesday, wednesday, thursday, friday, saturday, sunday));
                    timetableDB.insert(course, group, monday, tuesday, wednesday, thursday, friday, saturday, sunday);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return login;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter = new TimetableAdapter(getContext(), drawThreadTimetable());
            lv.setAdapter(adapter);
            is_thread = false;
            Toast.makeText(getContext(), R.string.is_ready,Toast.LENGTH_LONG).show();
        }

    }

    private void loader() {
        if (isOnline()) {
            if (!is_thread) {
                is_thread=true;
                new GetTimetable().execute();
                Toast.makeText(getContext(), R.string.please_wait, Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getContext(),R.string.no_internet_connection,Toast.LENGTH_LONG).show();
        }
    }




    private boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException | InterruptedException e)          { e.printStackTrace(); }
        return false;
    }
}
