package space.firsov.kvantnews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import space.firsov.kvantnews.fragment.NewsFragment;

public class StudentActivity extends FragmentActivity implements View.OnClickListener{
    Button btn_to_login;
    TextView tw_username;
    private ArrayList<NewsFragment> newsFragments = new ArrayList<>();
    private FragmentManager manager;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        tw_username = findViewById(R.id.tw_username);
        btn_to_login = findViewById(R.id.btn_to_login);

        String username = getIntent().getStringExtra("student_name");
        tw_username.setText(username);

        btn_to_login.setOnClickListener(this);

        manager = getSupportFragmentManager();
        for(int i=0;i<1;i++){
            drawNews();
        }

    }
    private void startLoginActivity(){
        Intent intent = new Intent(StudentActivity.this, LoginActivity.class);
        intent.putExtra("type",1);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_to_login:
                startLoginActivity();
        }
    }

    private void drawNews(){
        transaction = manager.beginTransaction();
        /*newsFragments.add(new NewsFragment());*/
        NewsFragment newsFragment = new NewsFragment();
        transaction.add(R.id.main_container, newsFragment);
        transaction.commit();
        /*newsFragments.get(i).setMessage(i +"message");
        newsFragments.get(i).setTitle(i +"title");*/
    }
}
