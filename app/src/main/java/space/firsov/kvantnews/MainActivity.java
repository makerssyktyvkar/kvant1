package space.firsov.kvantnews;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity{
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("login");
        int type = getIntent().getIntExtra("type", 0);
        switch(type){
            case 2:
                startStudentActivity();
                break;
            /*case 1:
                startAdminActivity();
            case 3:
                startParentActivity();
            case 4:
                startTeacherActivity();*/
        }
    }
    private void startStudentActivity(){
        Intent intent = new Intent(MainActivity.this, StudentActivity.class);
        intent.putExtra("student_name",username);
        startActivity(intent);
    }
    /*private void startAdminActivity(){
        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
        startActivity(intent);
    }
    private void startParentActivity(){
        Intent intent = new Intent(MainActivity.this, ParentActivity.class);
        intent.putExtra("parent_name",username);
        startActivity(intent);
    }
    private void startTeacherActivity(){
        Intent intent = new Intent(MainActivity.this, TeacherActivity.class);
        intent.putExtra("teacher_name",username);
        startActivity(intent);
    }*/

}