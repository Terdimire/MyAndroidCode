package com.tryndamere.lzm.httpclient;

import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpClientGetAsync extends AsyncTask<Object, Void, String> {


    // 开始准备工作
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    // 耗时操作
    @Override
    protected String doInBackground(Object... params) {
        String url = (String) params[0];

        try {
            //创建HttpClient对象
            HttpClient httpClient = new DefaultHttpClient();
            //httpget对象
            HttpGet httpGet = new HttpGet(url);
            //httpResponse
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = httpResponse.getEntity();
                String response = EntityUtils.toString(entity, "utf-8");//将entity当中的数据转换为字符串
                return  response;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    // 得到子线程返回结果 ， 更新ui
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("onPostExecute s = " + s);
    }
}
