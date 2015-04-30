package app.newsup.com.newsupapp.tryandroid;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import app.newsup.com.newsupapp.R;

/**
 * Created by manshu on 4/28/15.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;
    public static LinkedBlockingQueue<String> list = new LinkedBlockingQueue<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        DatabaseHandler db = new DatabaseHandler(this);

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        initVars();

        loginButton.setOnClickListener(this);
    }

    private void initVars() {
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(NewsService.DATA);
                int resultCode = bundle.getInt(NewsService.RESULT);
                if (resultCode == RESULT_OK) {
                    Toast.makeText(LoginActivity.this,
                            "Download complete. Download URI: " + string,
                            Toast.LENGTH_LONG).show();
                    for (String s : list) {
                        System.out.println(s);
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Download failed", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(NewsService.NOTIFICATION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                System.out.println(emailEditText.getText() + " " + passwordEditText.getText());
                loginButton.setText("LoggedIn");

                Intent intent = new Intent(this, NewsService.class);
                intent.putExtra(NewsService.STIME, new Date().toString());
                startService(intent);
                Toast.makeText(this, "News are being updated", Toast.LENGTH_LONG).show();
        }
    }
}
