package app.newsup.com;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by manshu on 4/30/15.
 */
public class NewsDownloadService extends Service {
    private final IBinder mBinder = new MyBinder();
    private List<NewsObject> newsList = new ArrayList<>();
    protected static final String LAST_SYNC_TIME = "last_sync_time";
    protected static final String RESULT = "result_code";
    protected static final String NEWS_NOTIFICATION = "com.newsup.app.news_receiver";
    private int result = Activity.RESULT_CANCELED;

    private Date last_updated_time;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Date synced_data = new Date(intent.getStringExtra(LAST_SYNC_TIME));
        System.out.println("Service got synced_data = " + synced_data.toString());
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute("http://172.17.193.163/mongotest/index_new.php");
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        NewsDownloadService getService() {
            return NewsDownloadService.this;
        }
    }

    public List<NewsObject> getNews() {
        return newsList;
    }

    private void publishResult(int result_code) {
        Intent intent = new Intent(NEWS_NOTIFICATION);
        intent.putExtra(RESULT, result_code);
        sendBroadcast(intent);
        //LoginActivity.list.add(data);
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            System.out.println("Downloading news in long running thread.");
            // Do your long operations here and return the result
            makePostRequest(params[0]);
            return resp;
        }

        @Override
        protected void onPostExecute(String res) {
            // execution of result of Long time consuming operation
            publishResult(result);
        }

        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            result = Activity.RESULT_CANCELED;
        }

        @Override
        protected void onProgressUpdate(String... text) {
            // Things to be done while execution of long running operation is in
            // progress.
        }
    }

    private void makePostRequest(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost(url);

        //Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("username", "admin"));
        //nameValuePair.add(new BasicNameValuePair("type", "news"));
        nameValuePair.add(new BasicNameValuePair("type", "count"));
        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        //making POST request.
        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            if (response != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                StringBuilder builder = new StringBuilder();
                for (String line = null; (line = reader.readLine()) != null;) {
                    builder.append(line).append("\n");
                }
                JSONTokener tokener = new JSONTokener(builder.toString());
                JSONArray finalResult = new JSONArray(tokener);
                for (int i = 0; i < finalResult.length(); i++){
                    JSONObject jsonObject = finalResult.getJSONObject(i);
                    try {
                        Long tweet_id = jsonObject.getLong("tweet_id");
                        String tweet = jsonObject.getString("tweet_content");
                        String tweet_url = jsonObject.getString("tweet_urls");
                        String summary = jsonObject.getString("summary");
                        if (summary.equalsIgnoreCase("")) continue;
                        String source = jsonObject.getString("source");
                        String language = jsonObject.getString("language");
                        String time_posted = jsonObject.getString("time_posted");
                        String title = "";
                        String images = "";
                        String hash_tags = "";
                        Double longitude = 0.0, latitude = 0.0;

                        Integer sentiment = Integer.MIN_VALUE;
                        try {
                            sentiment = jsonObject.getInt("sentiment-score");
                        } catch (JSONException e) {;}

                        Integer emoticon = Integer.MIN_VALUE;
                        try {
                            jsonObject.getInt("emoticon-score");
                        } catch (JSONException e) {;}

                        Integer favorites = 0;
                        try {
                            jsonObject.getInt("favorite-count");
                        } catch (JSONException e) {;}

                        Integer retweets = 0;
                        try {
                            jsonObject.getInt("retweet-count");
                        } catch (JSONException e) {;}

                        JSONArray jsonArray = jsonObject.getJSONArray("comments-score");
                        List<CommentsObject> commentsObjects = new ArrayList<>(jsonArray.length());
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject jsonCommentObject = jsonArray.getJSONObject(j);
                            int comment_id = jsonCommentObject.getInt("id");
                            String comment = jsonCommentObject.getString("comment");

                            int comment_sentiment = Integer.MIN_VALUE;
                            try {
                                jsonCommentObject.getInt("sentiment");
                            } catch (JSONException e) {;}
                            int comment_emoticon = Integer.MIN_VALUE;
                            try {
                                jsonCommentObject.getInt("emoticon");
                            } catch (JSONException e) {;}
                            CommentsObject commentsObject = new CommentsObject(comment_id, comment,
                                    comment_sentiment, comment_emoticon);
                            //System.out.println(commentsObject);
                            commentsObjects.add(commentsObject);
                        }

                        NewsObject newsObject = new NewsObject(tweet_id, title, tweet, tweet_url,
                                summary, sentiment, emoticon, source, favorites, retweets, images,
                                hash_tags, longitude, latitude, language, commentsObjects);
                        newsList.add(newsObject);

                        System.out.println(newsObject.toString());
                    } catch (Exception e) {
                        continue;
                    }
                }
                result = Activity.RESULT_OK;
            }
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
