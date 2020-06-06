package space.firsov.kvantnews;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class GetTypeOfUser extends AsyncTask<String,Void,String> {
    private String login, password;
    private Context context;

    GetTypeOfUser(String login, String password, Context context) {
        this.password = password;
        this.login = login;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            String link = context.getResources().getString(R.string.main_host_dns) + "getTypeOfUser.php";
            String data = "login" + "=" + login + "&password=" + password;
            URL url = new URL(link);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(data);
            //wr.write(data2);
            wr.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();//return Html.fromHtml(sb.toString()).toString();
        } catch (Exception e) {
            return "0";
        }
    }
}