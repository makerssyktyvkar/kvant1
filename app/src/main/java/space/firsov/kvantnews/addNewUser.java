package space.firsov.kvantnews;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class addNewUser extends AsyncTask<String,Void, Boolean> {
    private String login, password, person;
    private Context context;

    addNewUser(String login, String password, String person, Context context) {
        this.password = password;
        this.login = login;
        this.person = person;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... arg0) {
        try {
            String link = context.getResources().getString(R.string.main_host_dns) + "addNewUser.php";
            String data = "login" + "=" +
                    login + "&password=" + password + "&type=" + person;
            URL url = new URL(link);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return Integer.valueOf(sb.toString())==1;
        } catch (Exception e) {
            return false;
        }
    }
}
