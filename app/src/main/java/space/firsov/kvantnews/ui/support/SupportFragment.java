package space.firsov.kvantnews.ui.support;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import space.firsov.kvantnews.R;

public class SupportFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_support, container, false);
        final TextView textView = root.findViewById(R.id.text_support);
        textView.setText(R.string.support);
        return root;
    }
}
