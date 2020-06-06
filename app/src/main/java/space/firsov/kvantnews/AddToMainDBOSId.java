package space.firsov.kvantnews;

import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AddToMainDBOSId extends AsyncTask<String,Void,String> {
    private String login, Id;
    private Context context;

    public AddToMainDBOSId(String login, String Id, Context context){
        this.login = login;
        this.Id = Id;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String res = "ok";
        try{
            String url = context.getResources().getString(R.string.main_host_dns) + "AddOSId.php";
            Document document = Jsoup.connect(url).
                    data("id", Id).
                    data("login", login).post();
        }catch (Exception e){
            res = "fail";
        }
        return res;
    }
}
