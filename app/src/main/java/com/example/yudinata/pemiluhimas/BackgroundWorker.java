package com.example.yudinata.pemiluhimas;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    String NAME= "", STATUS= "", USERNAME="";
    String TYPE="";

    AlertDialog alertDialog ;
    Context context ;

    BackgroundWorker(Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        TYPE = params[0];
        Log.d("param0",params.toString());
        String login_url = "http://exanatha.000webhostapp.com/login.php";
        String ceck_url = "http://exanatha.000webhostapp.com/ceckdata.php";
        String pilih_url = "http://exanatha.000webhostapp.com/pungutsuara.php";

        if (TYPE.equals("login")){
            try {
                String username = params[1];
                Log.d("param1", username);
                String password = params[2];
                Log.d("params2",password);

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                                    +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                 bufferedWriter.write(post_data);
                 bufferedWriter.flush();
                 bufferedWriter.close();
                 outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result ="";
                String line = "";
                int tmp;
                while ((tmp = inputStream.read())!=-1){
                    Log.d("tmp", String.valueOf((char)tmp));
                    result += (char)tmp;
                }

                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("result",result);

                return  result;
            }catch (MalformedURLException e ){
                e.printStackTrace();

            }catch (IOException e){
                e.printStackTrace();
            }

        }
        else if (TYPE.equals("ceck")){
            try {
                String username = params[1];

                URL url = new URL(ceck_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result ="";
                String line = "";
//                int tmp;
//                while ((tmp = inputStream.read())!=-1){
//                    Log.d("tmp", String.valueOf((char)tmp));
//                    result += (char)tmp;
//                }

//                Log.d("buffered",bufferedReader.readLine().toString());
                while ((line = bufferedReader.readLine())!=null){
                    result  += line;
//                    Log.d("resultLine",line);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("result",result);

                return  result;
            }catch (MalformedURLException e ){
                e.printStackTrace();

            }catch (IOException e){
                e.printStackTrace();
            }

        }
        else if (TYPE.equals("update")){
            try {
                String suara = params[2];
                String username = params[1];
                URL url = new URL(pilih_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("suara","UTF-8")+"="+URLEncoder.encode(suara,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result ="";
                String line = "";
//                int tmp;
//                while ((tmp = inputStream.read())!=-1){
//                    Log.d("tmp", String.valueOf((char)tmp));
//                    result += (char)tmp;
//                }

//                Log.d("buffered",bufferedReader.readLine().toString());
                while ((line = bufferedReader.readLine())!=null){
                    result  += line;
//                    Log.d("resultLine",line);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("result",result);

                return  result;
            }catch (MalformedURLException e ){
                e.printStackTrace();

            }catch (IOException e){
                e.printStackTrace();
            }

        }

        return "Anda Offline";
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Status");
    }

    @Override
    protected void onPostExecute(String s) {
        String trr= null;
        Log.d("iniVoid", s);
        if (TYPE.equals("login")){
            try {
                JSONObject root = new JSONObject(s);
                JSONObject user_data = root.getJSONObject("user_data");
                Log.d("user_data",user_data.toString());
                NAME = user_data.getString("name");
                STATUS = user_data.getString("status");
                USERNAME = user_data.getString("username");
                Log.d("STATUS",STATUS);

            } catch (JSONException e) {
                e.printStackTrace();
                trr = "Exception: "+e.getMessage();
            }


            if (STATUS.equals("0")){
                Intent Myintent  = new Intent(context,VoteActivity.class);
                Myintent.putExtra("username",USERNAME);
                context.startActivity(Myintent);
            }
            else {
                alertDialog.setMessage(s);
                alertDialog.show();
            }

        }

        if (TYPE.equals("ceck")){
            VoteActivity voteActivity = new VoteActivity();
            voteActivity.setCeckdata(s);
        }
        else {
            alertDialog.setMessage(s);
            alertDialog.show();
        }



    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
