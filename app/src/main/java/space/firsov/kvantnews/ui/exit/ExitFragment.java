package space.firsov.kvantnews.ui.exit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import space.firsov.kvantnews.ui.achievements.AchievementsDB;
import space.firsov.kvantnews.ui.posts.CoursesNewsOfUserDB;
import space.firsov.kvantnews.LoginActivity;
import space.firsov.kvantnews.R;
import space.firsov.kvantnews.ui.support.SupportsDB;
import space.firsov.kvantnews.User;
import space.firsov.kvantnews.ui.timetable.TimetableDB;

public class ExitFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_achievements, container, false);
        CoursesNewsOfUserDB coursesNewsOfUserDB = new CoursesNewsOfUserDB(getContext());
        coursesNewsOfUserDB.deleteAll();
        SupportsDB supportsDB = new SupportsDB(getContext());
        supportsDB.deleteAll();
        TimetableDB timetableDB = new TimetableDB(getContext());
        timetableDB.deleteAll();
        AchievementsDB achievementsDB = new AchievementsDB(getContext());
        achievementsDB.deleteAll();
        User user = new User(getContext());
        user.deleteAll();
        startLoginActivity();
        return root;
    }
    private void startLoginActivity(){
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra("type",1);
        startActivity(intent);
    }
}
