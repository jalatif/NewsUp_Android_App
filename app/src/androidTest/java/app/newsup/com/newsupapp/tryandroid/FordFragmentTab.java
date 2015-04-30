package app.newsup.com.newsupapp.tryandroid;

/**
 * Created by manshu on 4/28/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.newsup.com.newsupapp.R;


public class FordFragmentTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ford_layout, container, false);
        return rootView;
    }
}
