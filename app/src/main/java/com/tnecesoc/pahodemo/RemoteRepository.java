package com.tnecesoc.pahodemo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tnecesoc.pahodemo.Bean.MessageBean;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Tnecesoc on 2016/9/7.
 */
public class RemoteRepository {

    private static String serverUrl;

    public static void setServerUrl(String host) {
        serverUrl =  "http://" + host + ":8080";
    }

    public static List<MessageBean> getAllMessage(String topic) {
        List<MessageBean> ans = new ArrayList<>();

        try {
            URL url = new URL(serverUrl + "/get-message");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(4000);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            out.write("topic=" + topic);
            out.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStreamReader in = new InputStreamReader(connection.getInputStream(), "UTF-8");

                ans = new Gson().fromJson(in, new TypeToken<List<MessageBean>>(){}.getType());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ans;
    }

    public static List<MessageBean> getMessageLaterThan(String topic, Date date) {

        List<MessageBean> ans = new ArrayList<>();

        String formattedDate = new SimpleDateFormat("y-M-d-H-m-s", Locale.getDefault()).format(date);

        try {

            URL url = new URL(serverUrl + "/get-message");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(4000);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));
            out.write("topic=" + topic);
            out.write("&later-than" + formattedDate);
            out.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStreamReader in = new InputStreamReader(connection.getInputStream(), "utf-8");

                ans = new Gson().fromJson(in, new TypeToken<List<MessageBean>>(){}.getType());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ans;


    }

}
