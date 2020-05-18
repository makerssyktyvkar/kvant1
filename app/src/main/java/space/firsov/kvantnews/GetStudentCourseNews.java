package space.firsov.kvantnews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

class GetStudentCourseNews extends AsyncTask<String, Void, String> {
    private String login;
    private CoursesNewsOfUserDB coursesNewsOfUserDB;

    GetStudentCourseNews(String login, Context context) {
        this.login = login;
        coursesNewsOfUserDB = new CoursesNewsOfUserDB(context);
    }

    @SuppressLint("WrongThread")
    @Override
    protected String doInBackground(String... args) {
        try {
            String url = "https://kvantfp.000webhostapp.com/ReturnCoursesNewsForStudent.php?login=" + login;
            Document document = Jsoup.connect(url).get();
            Elements element = document.select("li[class=course-item]");
            coursesNewsOfUserDB.deleteAll();
            for (int i = 0; i < element.size(); i++) {
                String name = element.eq(i).select("h1[class=name_course]").eq(0).text();
                long id = Integer.parseInt(element.eq(i).select("h1[class=id_course]").eq(0).text());
                Elements element2 = element.eq(i).select("li[class=news-item]");
                for(int j = 0; j < element2.size(); j++){
                    String title = element2.eq(j).select("h2[class=title]").eq(0).text();
                    String desc = element2.eq(j).select("p[class=message]").eq(0).text();
                    String time = element2.eq(j).select("p[class=time]").eq(0).text();
                    String linkImage;
                    try{
                        linkImage = element2.eq(j).select("img").eq(0).attr("src").substring(24);
                    }catch (Exception e){
                        linkImage = "";
                    }
                    coursesNewsOfUserDB.insert(id, title,desc,linkImage,time);
                }
            }
        } catch (Exception e) {
            //
        }
        return "";
    }
}