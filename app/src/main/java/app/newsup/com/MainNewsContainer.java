package app.newsup.com;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.newsup.com.newsupapp.R;

/**
 * Created by manshu on 4/29/15.
 */
public class MainNewsContainer extends ActionBarActivity {

    private NewsDownloadService newsDownloadService = null;
    protected static List<NewsObject> newsList = new ArrayList<>();

    ActionBar.Tab latestTab, topTab;
    Fragment latestNews = new LatestNewsFragment();
    Fragment topNews = new TopTrendingNewsFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_news_container);

        // Asking for the default ActionBar element that a platform supports.
        ActionBar actionBar = this.getSupportActionBar();

        // Screen handling while hiding ActionBar icon.
        //actionBar.setDisplayShowHomeEnabled(false);

        // Screen handling while hiding Actionbar title.
        //actionBar.setDisplayShowTitleEnabled(false);

        // Creating ActionBar tabs.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Setting custom tab icons.
        latestTab = actionBar.newTab().setText("Latest News");
        topTab = actionBar.newTab().setText("Top News");

        // Setting tab listeners.
        latestTab.setTabListener(new TabListener(latestNews));
        topTab.setTabListener(new TabListener(topNews));

        // Adding tabs to the ActionBar.
        actionBar.addTab(latestTab);
        actionBar.addTab(topTab);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_container, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConnection);
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent= new Intent(this, NewsDownloadService.class);
        bindService(intent, mConnection,
                Context.BIND_AUTO_CREATE);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NewsDownloadService.NEWS_NOTIFICATION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        /** Receives the broadcast that has been fired */
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equalsIgnoreCase(NewsDownloadService.NEWS_NOTIFICATION)) {
                //HERE YOU WILL GET VALUES FROM BROADCAST THROUGH INTENT EDIT YOUR TEXTVIEW///////////
                int resultCode = intent.getIntExtra(NewsDownloadService.RESULT,
                        Activity.RESULT_CANCELED);
                if (resultCode == Activity.RESULT_OK) {
                    newsList = newsDownloadService.getNews();
                    System.out.println("NEWS: Got News " + newsList.size());
                }

            }
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className,
                                       IBinder binder) {
            NewsDownloadService.MyBinder b = (NewsDownloadService.MyBinder) binder;
            newsDownloadService = b.getService();
            newsList = newsDownloadService.getNews();
            System.out.println("NEWS: Got News " + newsList.size());
            Toast.makeText(MainNewsContainer.this, "Connected", Toast.LENGTH_SHORT)
                    .show();
        }

        public void onServiceDisconnected(ComponentName className) {
            newsDownloadService = null;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refreshButton) {
            Toast.makeText(getApplicationContext(), "News are being downloaded", Toast.LENGTH_LONG)
            .show();
            Intent intent = new Intent(this, NewsDownloadService.class);
            intent.putExtra(NewsDownloadService.LAST_SYNC_TIME, new Date().toString());
            startService(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
