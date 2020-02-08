package space.firsov.kvantnews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView today_text = findViewById(R.id.today);
        int tp = LoginActivity.tp;
        if(tp==1){
            today_text.setText("Вы вошли как администратор");
        }else if(tp==2){
            today_text.setText("Вы вошли как ученик");
        }else if(tp==3){
            today_text.setText("Вы вошли как родитель");
        }else{
            today_text.setText("Вы вошли как учитель");
        }
    }
}
