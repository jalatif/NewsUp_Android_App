package app.newsup.com;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import app.newsup.com.newsupapp.R;

/**
 * Created by manshu on 4/29/15.
 */
public class NewsDetailsActivity extends Activity {

    private WebView webView;
    private TextView summaryTextView;
    private TextView titleTextView;

    private Map<String, String> color_map = new HashMap<>();

    private class ViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        Bundle bundle = getIntent().getExtras();
        int id = 0;
        try {
            id = bundle.getInt("ID");
        } catch (NullPointerException npe) {
            System.out.println("Null Pointer Exception in sending intent");
        }

        NewsObject newsObject = MainNewsContainer.newsList.get(id);

        initVars();

        titleTextView.setText(newsObject.getTweet());
        summaryTextView.setText(newsObject.getSummary());

        summaryTextView.setTextColor(Color.BLACK);

        if (newsObject.getSentiment() == Integer.MIN_VALUE)
            summaryTextView.setBackgroundColor(Color.parseColor("ffffffff"));
        else if (newsObject.getSentiment() > 2)
            summaryTextView.setBackgroundColor(Color.parseColor(color_map.get("POSITIVE")));
        else if (newsObject.getSentiment() < 2)
            summaryTextView.setBackgroundColor(Color.parseColor(color_map.get("NEGATIVE")));
        else
            summaryTextView.setBackgroundColor(Color.parseColor(color_map.get("NEUTRAL")));

        webView.loadUrl(newsObject.getUrl());
    }

    private void initVars() {
        color_map.put("POSITIVE", "#b4e89e");
        color_map.put("NEUTRAL", "#eceeff");
        color_map.put("NEGATIVE", "#ffc0cb");

        titleTextView = (TextView) findViewById(R.id.headlineTextView);
        summaryTextView = (TextView) findViewById(R.id.summaryTextView);
        webView = (WebView) findViewById(R.id.newsWebView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);

        webView.setWebViewClient(new ViewClient());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
