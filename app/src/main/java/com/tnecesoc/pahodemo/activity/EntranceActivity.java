package com.tnecesoc.pahodemo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import com.tnecesoc.pahodemo.R;

public class EntranceActivity extends AppCompatActivity {

    private static final String USER_INFO = "USER_INFO";
    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_TOPIC = "TOPIC";
    private static final String KEY_BROKER_HOST = "BROKER_HOST";
    private static final String KEY_DATABASE_HOST = "DATABASE_HOST";

    private TextInputLayout usernameInputArea;
    private TextInputLayout topicInputArea;

    private TextInputLayout brokerHostInputArea;
    private TextInputLayout serverHostInputArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

        usernameInputArea = (TextInputLayout) findViewById(R.id.username_input_area);
        topicInputArea = (TextInputLayout) findViewById(R.id.topic_input_area);
        brokerHostInputArea = (TextInputLayout) findViewById(R.id.broker_url_input_area);
        serverHostInputArea = (TextInputLayout) findViewById(R.id.database_url_input_area);

        SharedPreferences preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);

        String username = preferences.getString(KEY_USERNAME, "");
        String topic = preferences.getString(KEY_TOPIC, "");
        String brokerHost = preferences.getString(KEY_BROKER_HOST, "");
        String databaseHost = preferences.getString(KEY_DATABASE_HOST, "");

        //noinspection ConstantConditions
        usernameInputArea.getEditText().setText(username);
        //noinspection ConstantConditions
        topicInputArea.getEditText().setText(topic);
        //noinspection ConstantConditions
        brokerHostInputArea.getEditText().setText(brokerHost);
        //noinspection ConstantConditions
        serverHostInputArea.getEditText().setText(databaseHost);

        findViewById(R.id.confirm).setOnClickListener(v -> {

            //noinspection ConstantConditions
            String newUsername = usernameInputArea.getEditText().getText().toString();

            //noinspection ConstantConditions
            String newTopic = topicInputArea.getEditText().getText().toString();

            //noinspection ConstantConditions
            String newBrokerHost = brokerHostInputArea.getEditText().getText().toString();

            //noinspection ConstantConditions
            String newDatabaseHost = serverHostInputArea.getEditText().getText().toString();

            boolean isInputValid = true;

            if (username.isEmpty() && newUsername.isEmpty()) {
                usernameInputArea.setError("Please input an username");
                usernameInputArea.setErrorEnabled(true);
                isInputValid = false;
            }

            if (topic.isEmpty() && newUsername.isEmpty()) {
                topicInputArea.setError("Please input a topic");
                topicInputArea.setErrorEnabled(true);
                isInputValid = false;
            }

            if (brokerHost.isEmpty() && newBrokerHost.isEmpty()) {
                brokerHostInputArea.setError("Please input the broker host");
                brokerHostInputArea.setErrorEnabled(true);
                isInputValid = false;
            }

            if (databaseHost.isEmpty() && newDatabaseHost.isEmpty()) {
                serverHostInputArea.setError("Please input the database host");
                serverHostInputArea.setErrorEnabled(true);
                isInputValid = false;
            }

            if (isInputValid) {
                newUsername = newUsername.isEmpty() ? username : newUsername;
                newTopic = newTopic.isEmpty() ? topic : newTopic;
                newBrokerHost = newBrokerHost.isEmpty() ? brokerHost : newBrokerHost;
                newDatabaseHost = newDatabaseHost.isEmpty() ? databaseHost : newDatabaseHost;
                preferences.edit().putString(KEY_USERNAME, newUsername).apply();
                preferences.edit().putString(KEY_TOPIC, newTopic).apply();
                preferences.edit().putString(KEY_BROKER_HOST, newBrokerHost).apply();
                preferences.edit().putString(KEY_DATABASE_HOST, newDatabaseHost).apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }

        });

    }
}
