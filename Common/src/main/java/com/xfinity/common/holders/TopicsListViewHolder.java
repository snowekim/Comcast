package com.xfinity.common.holders;

import android.view.View;
import android.widget.TextView;

import com.xfinity.common.R;

public class TopicsListViewHolder extends TopicViewHolder {
    public TextView mDescription;

    public TopicsListViewHolder(View view) {
        super(view);
        mDescription = (TextView)view.findViewById(R.id.description);
    }

}
