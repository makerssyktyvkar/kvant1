package space.firsov.kvantnews.ui.exit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import space.firsov.kvantnews.CoursesNewsOfUserDB;
import space.firsov.kvantnews.LoginActivity;
import space.firsov.kvantnews.R;
import space.firsov.kvantnews.StudentNavActivity;
import space.firsov.kvantnews.SupportsDB;
import space.firsov.kvantnews.User;

public class ExitFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_achievements, container, false);
        CoursesNewsOfUserDB coursesNewsOfUserDB = new CoursesNewsOfUserDB(getContext());
        coursesNewsOfUserDB.deleteAll();
        SupportsDB supportsDB = new SupportsDB(getContext());
        supportsDB.deleteAll();
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
