package space.firsov.kvantnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView today_text = findViewById(R.id.today);
        Button tmp_btn = findViewById(R.id.btn_tmp);
        String login = getIntent().getStringExtra("login");
        today_text.setText(login);
        int type = getIntent().getIntExtra("type",0);
        if(type==1){
            today_text.setText("Вы вошли как администратор");
        }else if(type==2){
            today_text.setText("Вы вошли как ученик");
        }else if(type==3){
            today_text.setText("Вы вошли как родитель");
        }else{
            today_text.setText("Вы вошли как учитель");
        }
        tmp_btn.setOnClickListener(this);
    }
    private void startLoginActivity(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra("type",1);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tmp:
                startLoginActivity();
        }
    }
}