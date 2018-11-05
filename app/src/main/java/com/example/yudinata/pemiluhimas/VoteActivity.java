package com.example.yudinata.pemiluhimas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class VoteActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnCalon1, btnCalon2;
    String USERNAME="";
   final String typececk ="ceck";
   final String  typeupdate ="update";
   public static String ceckdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        Intent intent = getIntent();
        USERNAME = intent.getStringExtra("username");
        btnCalon1 = (Button)findViewById(R.id.calon1);
        btnCalon1.setOnClickListener(this);
        btnCalon2 = (Button)findViewById(R.id.calon2);
        btnCalon2.setOnClickListener(this);

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(typececk,USERNAME);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.calon1:
                if (ceckdata.equals("0")){
                    BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                    backgroundWorker.execute(typeupdate,USERNAME,"1");
                    ceckdata = "sudah";
                }
                else if (ceckdata.equals("sudah")){
                    Toast.makeText(this, "Anda Sudah Vote",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(this, "Dalam Tahap Ceck Data",Toast.LENGTH_LONG).show();
                }

                //something to do
                break;
            case R.id.calon2:
                if (ceckdata.equals("0")){
                    BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                    backgroundWorker.execute(typeupdate,USERNAME,"2");
                    ceckdata = "sudah";
                }
                else if (ceckdata.equals("sudah")){
                    Toast.makeText(this, "Anda Sudah Vote",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(this, "Dalam Tahap Ceck Data",Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    public void setCeckdata(String ceckdata) {
        this.ceckdata = ceckdata;
    }
}
