package space.firsov.kvantnews;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AddToMainDBOSId extends AsyncTask<String,Void,String> {
    String login;
    String Id;

    public AddToMainDBOSId(String login, String Id){
        this.login = login;
        this.Id = Id;
    }

    @Override
    protected String doInBackground(String... strings) {
        String res = "ok";
        try{
            String url = "https://kvantfp.000webhostapp.com/AddOSId.php";
            Document document = Jsoup.connect(url).
                    data("id", Id).
                    data("login", login).post();
        }catch (Exception e){
            res = "fail";
        }
        return res;
    }
}
