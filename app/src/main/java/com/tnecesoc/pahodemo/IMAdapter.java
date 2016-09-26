package com.tnecesoc.pahodemo;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.tnecesoc.pahodemo.Bean.MessageBean;

import java.util.List;

/**
 * Created by Tnecesoc on 2016/9/9.
 */
public class IMAdapter extends ArrayAdapter<MessageBean> {

    int layout;
    LayoutInflater inflater;

    boolean showPublishDateTrigger = false;



    public IMAdapter(Activity context, @LayoutRes int layout, @NonNull List<MessageBean> objects) {
        super(context, layout, objects);
        this.layout = layout;
        inflater = context.getLayoutInflater();
    }


    public void togglePublishTime() {

        showPublishDateTrigger = !showPublishDateTrigger;

        notifyDataSetChanged();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        MessageBean message = getItem(position);

        TextView tv_author = (TextView) convertView.findViewById(R.id.author);
        TextView tv_content = (TextView) convertView.findViewById(R.id.content);
        TextView tv_publish_time = (TextView) convertView.findViewById(R.id.publish_time);

        tv_author.setText(message.getAuthor());
        tv_content.setText(message.getContent());
        tv_publish_time.setText(message.getPublishTime());

        if (showPublishDateTrigger) {
            tv_publish_time.setVisibility(View.VISIBLE);
        } else {
            tv_publish_time.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
