package space.firsov.kvantnews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class LoginActivity extends AppCompatActivity {
    static int tp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button registration_btn = findViewById(R.id.registration_btn);
        Button login_btn = findViewById(R.id.btn_come_in);
        final EditText login = findViewById(R.id.login);
        final EditText password = findViewById(R.id.password);
        final TextView tw1 = findViewById(R.id.tw1);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = login.getText().toString();
                String pas = password.getText().toString();
                String type = "pass";
                try {
                    type = new GetTypeOfUser(name,pas).execute().get();
                } catch (Exception e){
                    //
                }
                tp = Integer.valueOf(type);
                if(tp==0) tw1.setText(R.string.NoSuchUsers);
                else{
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}
