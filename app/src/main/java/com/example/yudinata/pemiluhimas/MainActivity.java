package com.example.yudinata.pemiluhimas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText EtUserName;
    private  EditText EtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EtPassword = (EditText) findViewById(R.id.et_password);
        EtUserName = (EditText) findViewById(R.id.et_username);

    }

    public void CobaLogin(View view) {

        String username = EtUserName.getText().toString();
        String password = EtPassword.getText().toString();
        String type = "login";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username,password);
    }
}
