package app.newsup.com;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import app.newsup.com.newsupapp.R;

/**
 * Created by manshu on 4/29/15.
 */
public class TopTrendingNewsFragment extends Fragment implements ListView.OnItemClickListener {

    private ListView topNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_top_news, container, false);
        topNews = (ListView) rootView.findViewById(R.id.topNewsListView);
        topNews.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.list_item, R.id.listTextview, new String[]{"goku", "vegeta", "gohan"}));
        topNews.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
