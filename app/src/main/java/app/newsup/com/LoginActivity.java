package app.newsup.com;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import app.newsup.com.newsupapp.R;

/**
 * Created by manshu on 4/28/15.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;
    private NewsDownloadService newsDownloadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initVars();
        loginButton.setOnClickListener(this);
        Intent intent = new Intent(this, NewsDownloadService.class);
        intent.putExtra(NewsDownloadService.LAST_SYNC_TIME, new Date().toString());
        startService(intent);
    }

    private void initVars() {
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Intent intent = new Intent(this, MainNewsContainer.class);
                startActivity(intent);
//                Toast.makeText(this, "You are being logged in", Toast.LENGTH_LONG).show();

//                Intent intent = new Intent(this, NewsService.class);
//                intent.putExtra(NewsService.STIME, new Date().toString());
//                startService(intent);
//                Toast.makeText(this, "News are being updated", Toast.LENGTH_LONG).show();

//                if (email.equalsIgnoreCase("newsup@retina.com")) {
//                    if (password.equalsIgnoreCase("newsup")) {
//                        Intent intent = new Intent(this, MainNewsContainer.class);
//                        startActivity(intent);
//                        Toast.makeText(this, "You are being logged in", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(this, "Incorrect password", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(this, "Incorrect username", Toast.LENGTH_LONG).show();
//                }
        }
    }
}
