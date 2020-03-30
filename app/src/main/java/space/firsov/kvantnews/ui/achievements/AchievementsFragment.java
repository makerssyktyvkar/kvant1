package space.firsov.kvantnews.ui.achievements;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import space.firsov.kvantnews.R;

public class AchievementsFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_achievements, container, false);
        final TextView textView = root.findViewById(R.id.text_achievements);
        textView.setText(R.string.achievements);
        return root;
    }
}
