package com.tnecesoc.pahodemo;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.tnecesoc.pahodemo.Bean.MessageBean;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.*;

/**
 * Created by Tnecesoc on 2016/9/5.
 */
public class BrokerConnector implements IBrokerConnector {

//    private static final String BASE_URL = "tcp://10.52.26.33:1883";

//    private static final String BASE_URL = "tcp://172.22.26.0:1883";

    private static final String CLIENT_ID = "poi";

    private static final String SUBSCRIBE_TOPIC = "lo";

    private static final String PUBLISH_TOPIC = "lo";

    private static final String TAG = "lo";

    private static final Gson gson = new Gson();

    private MqttAndroidClient client;

    private static Context usedContext;

    public static void setAndroidContext(Context context) {
        usedContext = context;
    }

    @Override
    public boolean send(MessageBean newMessage) {

        boolean flag = client.isConnected();

        publishMessage(gson.toJson(newMessage));

        return flag;
    }

    private void publishMessage(String newMessage) {
        MqttMessage message = new MqttMessage();

        message.setPayload(newMessage.getBytes());

        try {
            client.publish(PUBLISH_TOPIC, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void subscribeToDefaultTopic() {

        try {
            client.subscribe(SUBSCRIBE_TOPIC, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i(TAG, "onSuccess: Subscribe successful");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i(TAG, "onFailure: Subscribe failed");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public BrokerConnector(String host, IBrokerConnector.OnRepositoryCallbackListener listener) {

        String baseUrl = "tcp://" + host + ":1883";

        this.client = new MqttAndroidClient(usedContext, baseUrl, CLIENT_ID);

        this.client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.i(TAG, "connectComplete: Connected to " + serverURI + ".");

                if (reconnect) {
                    subscribeToDefaultTopic();
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.i(TAG, "connectionLost: Connection lost.");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                listener.onReceive(gson.fromJson(new String(message.getPayload(), "UTF-8"), MessageBean.class));
                Log.i(TAG, "messageArrived: message received");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.i(TAG, "deliveryComplete: delivery compeleted.");
            }
        });

        MqttConnectOptions options = new MqttConnectOptions();

        options.setAutomaticReconnect(true);
        options.setCleanSession(false);


        Log.i(TAG, "BrokerConnector: connecting to the server...");
        try {
            this.client.connect(options, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    client.setBufferOpts(disconnectedBufferOptions);
                    Log.i(TAG, "onSuccess: Connected to " + baseUrl);
                    subscribeToDefaultTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i(TAG, "onFailure: Connection failed");
                    try {
                        throw exception;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
