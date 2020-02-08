package space.firsov.kvantnews;

import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class GetData extends AsyncTask<String,Void,String> {
    private TextView text;
    private int id = 1;
    GetData(TextView text, int id){
        this.text = text;
        this.id = id;
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String link = "https://kvantfp.000webhostapp.com/getFirstText.php";
            String data = URLEncoder.encode("id","UTF-8") + "=" + id;
            URL url = new URL(link);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = reader.readLine())!=null){
                sb.append(line);
                break;
            }
            return sb.toString();
        }catch (Exception e){
            return "Exception: " + e.getMessage()+e.getStackTrace().toString();
        }
    }
    @Override
    protected void onPostExecute(String result){
        this.text.setText(Html.fromHtml(result));
    }
}
