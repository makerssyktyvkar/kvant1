package space.firsov.kvantnews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class News {
    String title;
    String message;
    Bitmap image;
    String additionalInfo;

    News(String title, String message, String image_src, String additionalInfo) {
        this.title = title;
        this.message = message;
        byte[] imageBytes= Base64.decode(image_src.substring(24), Base64.DEFAULT);
        InputStream is = new ByteArrayInputStream(imageBytes);
        this.image= BitmapFactory.decodeStream(is);
        this.additionalInfo = additionalInfo;
    }

}
