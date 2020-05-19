package space.firsov.kvantnews.ui.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import space.firsov.kvantnews.R;

public class TimetableFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timetable, container, false);
        final TextView textView = root.findViewById(R.id.text_timetable);
        textView.setText(R.string.timetable);
        return root;
    }
}
