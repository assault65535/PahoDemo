package com.tnecesoc.pahodemo;

import com.tnecesoc.pahodemo.Bean.MessageBean;

/**
 * Created by Tnecesoc on 2016/9/5.
 */
public interface IBrokerConnector {

    boolean send(MessageBean newMessage);

    interface OnRepositoryCallbackListener {

        void onReceive(MessageBean message);

    }


}
