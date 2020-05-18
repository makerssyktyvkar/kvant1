package space.firsov.kvantnews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class courseNews {
    long id_course;
    String title;
    String message;
    Bitmap image;
    String additionalInfo;

    courseNews(long id_course, String title, String message, String image_src, String additionalInfo) {
        this.title = title;
        this.id_course = id_course;
        this.message = message;
        if(!image_src.equalsIgnoreCase("")){
            byte[] imageBytes = Base64.decode(image_src, Base64.DEFAULT);
            InputStream is = new ByteArrayInputStream(imageBytes);
            this.image= BitmapFactory.decodeStream(is);
        }else {
            this.image = null;
        }
        this.additionalInfo = additionalInfo;
    }
}
