package com.tnecesoc.pahodemo;

import android.content.Context;
import com.tnecesoc.pahodemo.Bean.MessageBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Tnecesoc on 2016/9/9.
 */
public class RepositoryController {

    public static List<MessageBean> getLocalRecords(Context context, String topic) {
        LocalRepository localMessages = new LocalRepository(context);

        return localMessages.findMessages();

    }

    public static List<MessageBean> refreshMessagesByTheTopicOf(Context context, String topic) {

        LocalRepository localMessages = new LocalRepository(context);

        List<MessageBean> localRecords = localMessages.findMessages();

        List<MessageBean> newMessages;

        if (localRecords.isEmpty()) {

            newMessages = RemoteRepository.getAllMessage(topic);

        } else {

            Date date = localRecords.get(localRecords.size() - 1).toNativeDate();

            newMessages = RemoteRepository.getMessageLaterThan(topic, date);

        }

        if (!newMessages.isEmpty()) {
            for (MessageBean o: newMessages) {
                localMessages.insertNewMessage(o);
            }
        }

        localMessages.close();

        return newMessages;

    }

}
