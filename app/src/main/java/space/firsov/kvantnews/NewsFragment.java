package space.firsov.kvantnews;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class NewsFragment extends Fragment {

	String title,message,date;
	Bitmap image;
	TextView title_tv,message_tv,date_tv;
	ImageView image_iv;

	NewsFragment(String title, String message, String date, Bitmap image){
		this.title=title;
		this.message=message;
		this.date=date;
		this.image=image;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


		View view = inflater.inflate(R.layout.frag_layout, container, false);

		message_tv = view.findViewById(R.id.message);
		title_tv = view.findViewById(R.id.title);
		date_tv = view.findViewById(R.id.date);
		image_iv = view.findViewById(R.id.image);

		message_tv.setText(message);
		title_tv.setText(title);
		date_tv.setText(date);
		//image_iv.setImageBitmap(image);


		return view;
	}

	public void Change(String title, String message, String date, Bitmap image){
		this.title=title;
		this.message=message;
		this.date=date;
		this.image=image;

		message_tv.setText(message);
		title_tv.setText(title);
		date_tv.setText(date);
		image_iv.setImageBitmap(image);
	}
}
