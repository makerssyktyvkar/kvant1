package space.firsov.kvantnews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


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
        tw1 = findViewById(R.id.tw1);
        user = new User(this);
        if(getIntent().getIntExtra("type",0)==1){
            user.deleteAll();
        }
        ArrayList<Pair<String, Integer>> now = user.selectAll();
        if(now.size()!=0){
            startMain(now.get(0));
        }
        login_btn.setOnClickListener(this);
        registration_btn.setOnClickListener(this);
    }

    public void startMain(Pair<String, Integer> userData){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra("type", userData.second);
        intent.putExtra("login", userData.first);
        startActivity(intent);
    }

    @Override
    public void onClick(View v){
        String login = login_et.getText().toString();
        String password = password_et.getText().toString();

        switch (v.getId()){
            case R.id.registration_btn:
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            case R.id.btn_come_in:
                String type = "pass";
                try {
                    type = new GetTypeOfUser(login, password).execute().get();
                } catch (Exception e){
                    //
                }
                int tp = Integer.valueOf(type);
                if(tp==0) tw1.setText(R.string.NoSuchUsers);
                else{
                    user.insert(login,password, tp);
                    startMain(new Pair<>(login,tp));
                }
        }
    }
}
