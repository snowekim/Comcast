package com.xfinity.common.holders;

import android.view.View;
import android.widget.ImageView;

import com.xfinity.common.R;

public class TopicsGridViewHolder extends TopicViewHolder {
    public ImageView mImage;

    public TopicsGridViewHolder(View view) {
        super(view);
        mImage = (ImageView)view.findViewById(R.id.image);
    }

}
