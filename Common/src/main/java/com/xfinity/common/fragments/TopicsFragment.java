package com.xfinity.common.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xfinity.common.R;
import com.xfinity.common.activities.BaseItemListActivity;
import com.xfinity.common.adapters.TopicsAdapter;
import com.xfinity.common.beans.Topic;

import java.util.ArrayList;
import java.util.List;

public class TopicsFragment extends Fragment {

    // Topics List
    protected ArrayList<Topic> mTopics = new ArrayList<>();

    // Recycler View
    protected RecyclerView mRecyclerView;

    // Adapter for the Recycler View
    protected TopicsAdapter mAdapter;

    // Flag set two panes support for List & Details in the same activity when running
    // on devices with a width >= 900dp
    protected boolean mTwoPane;

    private int mLayoutId;

    public static TopicsFragment build(int layoutId) {
        TopicsFragment topicsFragment = new TopicsFragment();
        topicsFragment.mLayoutId = layoutId;
        return topicsFragment;
    }

    public static TopicsFragment build(int layoutId, ArrayList<Topic> topics) {

        TopicsFragment topicsListFragment = build(layoutId);
        topicsListFragment.mTopics.addAll(topics);

        return topicsListFragment;
    }

    public TopicsFragment() {
    }

    public boolean isTwoPane() {
        mTwoPane = getActivity().findViewById(R.id.item_detail_container) != null;
        return  mTwoPane;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mLayoutId = savedInstanceState.getInt("mLayoutId");
        }
        return inflater.inflate(mLayoutId, container, false);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        // Fill all the fields with data
        View view = getView();

        mRecyclerView = (RecyclerView)view.findViewById(R.id.item_list);

        // Get Data from Main Activity
        mTopics.addAll(((BaseItemListActivity)getActivity()).getTopics());

        // Build the Adapter
        mAdapter = new TopicsAdapter(getActivity().getSupportFragmentManager(), mTopics,
                isTwoPane(), ((BaseItemListActivity)getActivity()).getListType());

        // Set the Adapter
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setDetailsActivity(((BaseItemListActivity) getActivity()).getDetailsActivity());
    }

    public void setTopics(List<Topic> topics) {
        mTopics.addAll(topics);
    }

    public void refresh() {
        if (isResumed() || mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Store some properties in case of getting notification changes
     */
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("mLayoutId", mLayoutId);
        super.onSaveInstanceState(bundle);
    }

}
