package app.newsup.com.newsupapp.tryandroid;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

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
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsService extends IntentService {

    private int result = Activity.RESULT_CANCELED;
    public static final String DATA = "count";
    public static final String STIME = "last_sync_time";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.newsup.app.receiver";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param // name Used to name the worker thread, important only for debugging.
     */

    public NewsService() {
        super("NewsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Date synced_data = new Date(intent.getStringExtra(STIME));
        System.out.println("Service got synced_data = " + synced_data.toString());
        result = Activity.RESULT_OK;
        makePostRequest("http://76.10.1.40/mongotest/index_new.php"); //172.17.193.163
        publishResult("ListService " + synced_data.toString(), result);
        result = Activity.RESULT_CANCELED;
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
                Log.d("Http Post Response:", response.toString());
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

    private void publishResult(String data, int result_code) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(DATA, data);
        intent.putExtra(RESULT, result_code);
        sendBroadcast(intent);
        LoginActivity.list.add(data);
    }
}
