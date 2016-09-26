package com.tnecesoc.pahodemo.activity;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import com.tnecesoc.pahodemo.*;
import com.tnecesoc.pahodemo.Bean.MessageBean;
import com.tnecesoc.pahodemo.Util.FetchRemoteRecordTask;
import com.tnecesoc.pahodemo.Util.TogglePublishTimeTask;

import java.util.Date;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private String username;
    private String topic;
    private static final String USER_INFO = "USER_INFO";
    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_TOPIC = "TOPIC";
    private static final String KEY_BROKER_HOST = "BROKER_HOST";
    private static final String KEY_DATABASE_HOST = "DATABASE_HOST";

    public static final String TAG = "lo";


    private BrokerConnector broker;
    private ListView messageList;
    private EditText messageInput;

    private IMAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageInput = (EditText) findViewById(R.id.editText);
        messageList = (ListView) findViewById(R.id.listView);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setCustomView(R.layout.actionbar);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }

        initUserAttrs();

        initHosts();

        adapter = new IMAdapter(this, R.layout.chat_bubble, RepositoryController.getLocalRecords(this, topic));

        new FetchRemoteRecordTask(this, adapter).execute(topic);

        messageList.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                ((ListView) parent).setSelection(((ListView) parent).getCount() - 1);
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
                ((ListView) parent).setSelection(((ListView) parent).getCount() - 1);
            }
        });

        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                if (messageList != null) {
                    messageList.setSelection(messageList.getCount() - 1);
                }
            }
        });

        messageList.setAdapter(adapter);

        messageList.setOnTouchListener((v, event) -> {

            if (TogglePublishTimeTask.isTriggered()) {
                adapter.togglePublishTime();
                new Timer().schedule(new TogglePublishTimeTask(adapter), 5000);
            }

            return false;
        });



        findViewById(R.id.button).setOnClickListener(v -> {
            broker.send(new MessageBean(topic, new Date(), username, messageInput.getText().toString()));
            messageInput.setText("");
        });
    }

    private void initHosts() {
        SharedPreferences preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);

        String brokerHost = preferences.getString(KEY_BROKER_HOST, "localhost");
        broker = new BrokerConnector(brokerHost, message -> {
            adapter.add(message);
        });

        String databaseHost = preferences.getString(KEY_DATABASE_HOST, "localhost");
        RemoteRepository.setServerUrl(databaseHost);
    }



    private void initUserAttrs() {

        SharedPreferences preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        username = preferences.getString(KEY_USERNAME, "Unnamed");
        topic = preferences.getString(KEY_TOPIC, "lo");
        setTitle(topic);

    }
}
