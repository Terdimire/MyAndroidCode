package com.tryndamere.lzm.kuaikanmanhua.http;

import android.os.AsyncTask;
import android.text.TextUtils;


import com.tryndamere.lzm.kuaikanmanhua.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by muhanxi on 17/4/24.
 */

public class IAsyncTask extends AsyncTask<String, Void, String> {


    private URL url;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String result = "";

        String path = params[0];
        InputStream inputStream = null;
        if (TextUtils.isEmpty(path)) {
            return result;
        }


        try {
            URL url = new URL(path);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                result = StringUtils.inputStreamToString(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }
}
