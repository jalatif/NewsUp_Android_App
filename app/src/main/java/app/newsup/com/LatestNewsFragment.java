package app.newsup.com;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.newsup.com.newsupapp.R;

/**
 * Created by manshu on 4/29/15.
 */
public class LatestNewsFragment extends Fragment implements ListView.OnItemClickListener {

    private ListView latestNews;
    private List<String> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_latest_news, container, false);
        latestNews = (ListView) rootView.findViewById(R.id.latestNewsListView);
//
//        list = new ArrayList<>(MainNewsContainer.newsList.size());
//        for (NewsObject newsObject : MainNewsContainer.newsList) {
//            list.add(newsObject.getTweet());
//        }
        String[] tweets = new String[MainNewsContainer.newsList.size()];
        int i = 0;
        for (NewsObject newsObject : MainNewsContainer.newsList) {
            tweets[i++] = newsObject.getTweet();
        }
        latestNews.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, tweets));//new String[]{"abhinav", "saikat", "shyam"}));
        latestNews.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity().getApplicationContext(),
                NewsDetailsActivity.class);

        intent.putExtra("ID", position);
        startActivity(intent);
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
