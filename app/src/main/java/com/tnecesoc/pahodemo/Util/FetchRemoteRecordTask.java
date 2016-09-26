package com.tnecesoc.pahodemo.Util;

import android.app.Activity;
import android.os.AsyncTask;
import com.tnecesoc.pahodemo.Bean.MessageBean;
import com.tnecesoc.pahodemo.IMAdapter;
import com.tnecesoc.pahodemo.RepositoryController;

import java.util.List;

/**
 * Created by Tnecesoc on 2016/9/9.
 * FetchRemoteRecordTask
 * 就是个 RepositoryController.refreshMessagesByTheTopicOf(context, topic) 罢了
 */
public class FetchRemoteRecordTask extends AsyncTask<String, Integer, List<MessageBean>> {

    Activity context;
    IMAdapter adapter;

    public FetchRemoteRecordTask(Activity context, IMAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected List<MessageBean> doInBackground(String... params) {

        return RepositoryController.refreshMessagesByTheTopicOf(context, params[0]);

    }

    @Override
    protected void onPostExecute(List<MessageBean> messageList) {

        adapter.addAll(messageList);

    }
}
