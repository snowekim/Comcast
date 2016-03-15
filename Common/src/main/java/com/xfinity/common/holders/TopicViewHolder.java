package com.xfinity.common.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xfinity.common.R;
import com.xfinity.common.beans.Topic;

public class TopicViewHolder extends RecyclerView.ViewHolder {
    public View mView;
    public TextView mTitle;
    public Topic mTopic;

    public TopicViewHolder(View view) {
        super(view);
        mView = view;
        mTitle = (TextView)view.findViewById(R.id.title);
    }
}
