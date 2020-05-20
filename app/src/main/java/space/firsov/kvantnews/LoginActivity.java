package space.firsov.kvantnews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

import space.firsov.kvantnews.ui.achievements.GetUserAchievements;
import space.firsov.kvantnews.ui.news.GetNews;
import space.firsov.kvantnews.ui.posts.GetStudentCourseNews;
import space.firsov.kvantnews.ui.support.GetUserSupports;
import space.firsov.kvantnews.ui.timetable.GetUserTimetable;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button registration_btn;
    Button login_btn;
    EditText login_et;
    EditText password_et;
    TextView tw1;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registration_btn = findViewById(R.id.registration_btn);
        login_btn = findViewById(R.id.btn_come_in);
        login_et = findViewById(R.id.login);
        password_et = findViewById(R.id.password);
        user = new User(this);
        if(getIntent().getIntExtra("type",0)==1){
            user.deleteAll();
        }
        ArrayList<Pair<String, Integer>> now = user.selectAll();
        if(now.size()!=0){
            startMain(now.get(0));
        }
        tw1 = findViewById(R.id.tw1);
        login_btn.setOnClickListener(this);
        registration_btn.setOnClickListener(this);
    }

    public void startMain(Pair<String, Integer> userData){
        Toast.makeText(getApplicationContext(), R.string.please_wait, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra("type", userData.second);
        intent.putExtra("login", userData.first);
        startActivity(intent);
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

    @Override
    public void onClick(View v){
        String login = login_et.getText().toString();
        String password = password_et.getText().toString();

        switch (v.getId()){
            case R.id.registration_btn:
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_come_in:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                if(isOnline()) {
                    String type = "pass";
                    try {
                        type = new GetTypeOfUser(login, password).execute().get();
                    } catch (Exception e) {
                        //
                    }
                    int tp = Integer.parseInt(type);
                    if (tp == 0) tw1.setText(R.string.NoSuchUsers);
                    else {
                        user.insert(login, password, tp);
                        switch(tp){
                            case 2:
                                findAllAboutStudent(login);
                                break;
                            /*
                            case 3:
                                findAllAboutParent(login);
                                break;
                            case 4:
                                findAllAboutTeacher(login);
                                break;*/
                        }
                        startMain(new Pair<>(login, tp));
                    }
                }else{
                    tw1.setText(R.string.no_internet_connection);
                }
                break;
        }
    }
    private void findAllAboutStudent(String login){
        Toast.makeText(this, R.string.please_wait, Toast.LENGTH_LONG).show();
        try {
            new GetNews(this).execute().get();
        } catch (Exception e){
            //
        }
        try {
            new GetStudentCourses(login, this).execute().get();
        } catch (Exception e){
            //
        }
        try {
            new GetStudentCourseNews(login, this).execute().get();
        } catch (Exception e){
            //
        }
        try {
            new GetUserSupports(login, this).execute().get();
        } catch (Exception e){
            //
        }
        try {
            new GetUserAchievements(login, this).execute().get();
        } catch (Exception e){
            //
        }
        try {
            new GetUserTimetable(login, this).execute().get();
        } catch (Exception e){
            //
        }
    }
}
