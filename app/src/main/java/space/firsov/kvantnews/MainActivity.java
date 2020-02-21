package space.firsov.kvantnews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView today_text = findViewById(R.id.today);
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
    }
}