package com.xfinity.common.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.xfinity.common.R;
import com.xfinity.common.beans.Topic;
import com.xfinity.common.common.Utility;
import com.xfinity.common.configs.Definitions;
import com.xfinity.common.fragments.ItemDetailFragment;
import com.xfinity.common.holders.TopicViewHolder;
import com.xfinity.common.holders.TopicsGridViewHolder;
import com.xfinity.common.holders.TopicsListViewHolder;

import java.util.List;

public class TopicsAdapter
        extends RecyclerView.Adapter<TopicViewHolder> implements View.OnClickListener {
    public static final int LIST_VIEW = 0;
    public static final int GRID_VIEW = 1;

    private int mListType;
    private boolean mTwoPane;
    private FragmentManager mManager;
    private final List<Topic> mValues;
    private Class<? extends Activity> mDetailsActivity;

    public TopicsAdapter(FragmentManager manager, List<Topic> items, boolean twoPane) {
        mValues = items;
        mTwoPane = twoPane;
        mManager = manager;
        mListType = LIST_VIEW;
    }

    public TopicsAdapter(FragmentManager manager, List<Topic> items, boolean twoPane, int type) {
        this(manager, items, twoPane);
        mListType = type;
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case GRID_VIEW:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.grid_item, parent, false);
                return new TopicsGridViewHolder(view);
            case LIST_VIEW:
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                return new TopicsListViewHolder(view);
        }
    }

    public void setItems(List<Topic> topics) {
        this.mValues.clear();
        this.mValues.addAll(topics);
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        Topic topic = mValues.get(position);

        holder.mTopic = topic;

        // Set data to fields
        String[] titleAndText = Utility.getTitleAndDescription(topic.getText());

        // Title
        holder.mTitle.setText(titleAndText[0]);

        if (mListType == LIST_VIEW) {
            ((TopicsListViewHolder)holder).mDescription.setText(titleAndText[1]);
        } else {
            if (Utility.notNullNorEmpty(topic.getIcon().getUrl())) {
                Picasso.with(holder.mTitle.getContext())
                        .load(topic.getIcon().getUrl())
                        .placeholder(R.drawable.loading_image)
                        .error(R.drawable.image_not_available)
                        .into(((TopicsGridViewHolder) holder).mImage);
            }  else {
                ((TopicsGridViewHolder)holder).mImage.setImageResource(R.drawable.image_not_available);
            }
        }

        // Bind data
        holder.mView.setTag(holder.mTopic);

        // Set click event
        holder.mView.setOnClickListener(this);
    }

    @Override
    public int getItemViewType(int position) {
        return mListType;
    }

    public int getListType() {
        return mListType;
    }

    public void setListType(int listType) {
        this.mListType = listType;
        this.notifyDataSetChanged();
    }

    public void setDetailsActivity(Class<? extends Activity> detailsActivity) {
        this.mDetailsActivity = detailsActivity;
    }

    public boolean isTwoPane() {
        return mTwoPane;
    }

    public void setTwoPane(boolean twoPane) {
        this.mTwoPane = twoPane;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void onClick(View v) {
        if (!(v.getTag() instanceof Topic)) return;
        Topic topic = (Topic)v.getTag();
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(Definitions.KEY_TOPIC, topic);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            FragmentTransaction fragmentTransaction = mManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_from_right);
            fragmentTransaction.replace(R.id.item_detail_container, fragment);
            fragmentTransaction.commit();
        } else if (mDetailsActivity != null) {
            Context context = v.getContext();
            Intent intent = new Intent(context, mDetailsActivity);
            intent.putExtra(Definitions.KEY_TOPIC, topic);
            context.startActivity(intent);
        }
    }

}
