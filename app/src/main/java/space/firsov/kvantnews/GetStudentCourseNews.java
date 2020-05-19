package space.firsov.kvantnews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetStudentCourseNews extends AsyncTask<String, Void, Integer> {
    private String login;
    private CoursesNewsOfUserDB coursesNewsOfUserDB;

    public GetStudentCourseNews(String login, Context context) {
        this.login = login;
        coursesNewsOfUserDB = new CoursesNewsOfUserDB(context);
    }

    @Override
    protected Integer doInBackground(String... args) {
        try {
            String url = "https://kvantfp.000webhostapp.com/ReturnCoursesNewsForStudent.php?login=" + login;
            Document document = Jsoup.connect(url).get();
            Elements element = document.select("li[class=news-item]");
            coursesNewsOfUserDB.deleteAll();
            for (int i = 0; i < element.size(); i++) {
                String name = element.eq(i).select("p[class=course_name]").eq(0).text();
                String title = element.eq(i).select("h2[class=title]").eq(0).text();
                String message = element.eq(i).select("p[class=message]").eq(0).text();
                String time = element.eq(i).select("p[class=time]").eq(0).text();
                String linkImage;
                try{
                    linkImage = element.eq(i).select("img").eq(0).attr("src").substring(24);
                }catch (Exception e){
                    linkImage = "";
                }
                coursesNewsOfUserDB.insert(name, title,message,linkImage,time);
            }
        } catch (Exception e) {
            //
        }
        return 1;
    }
}