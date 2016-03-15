package com.xfinity.common.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.xfinity.common.R;
import com.xfinity.common.adapters.TopicsAdapter;
import com.xfinity.common.beans.Topic;
import com.xfinity.common.beans.Topics;
import com.xfinity.common.common.Utility;
import com.xfinity.common.configs.ComcastController;
import com.xfinity.common.configs.Definitions;
import com.xfinity.common.fragments.ItemDetailFragment;
import com.xfinity.common.fragments.TopicsFragment;
import com.xfinity.common.interfaces.ComcastService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * @author Mike Amigon
 * This is the main base class to be used by the client apps, here are performed
 * all the common operations, like download the topic list and setup all the stuff needed
 * for the entire nativagion.
 */
public abstract class BaseItemListActivity extends AppCompatActivity {

    // Query string to be used for the service
    protected String mQuery;

    // Progress Bar
    protected ProgressBar mBar;

    // Topics List
    protected ArrayList<Topic> mTopics = new ArrayList<>();

    // List type
    private int mListType = TopicsAdapter.LIST_VIEW;

    // Details activity
    protected  Class<? extends Activity> detailsActivity;

    // Current Fragment
    protected TopicsFragment mTopicsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        setDataConfig();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        mBar = (ProgressBar)findViewById(R.id.bar);

        if (savedInstanceState != null) {
            List<Topic> topics = savedInstanceState.getParcelableArrayList(Definitions.KEY_LIST);
            mTopics.addAll(topics);
            mListType = savedInstanceState.getInt(Definitions.KEY_LIST_TYPE, TopicsAdapter
                    .LIST_VIEW);
            mTopicsFragment = (TopicsFragment)getSupportFragmentManager().findFragmentByTag("frag");
            addFirstTopic();
        } else {
            addFragment(R.layout.topics_list);
            loadTopics();
        }

    }

    private void addFragment(int layoutId) {
        mTopicsFragment = TopicsFragment.build(layoutId, mTopics);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.topics_container, mTopicsFragment, "frag")
                .commit();
    }

    public Class<? extends Activity> getDetailsActivity() {
        return detailsActivity;
    }

    public ArrayList<Topic> getTopics() {
        return mTopics;
    }

    public int getListType() {
        return mListType;
    }

    /**
     * Store some properties in case of getting notification changes
     */
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList(Definitions.KEY_LIST, mTopics);
        bundle.putInt(Definitions.KEY_LIST_TYPE, mListType);
        super.onSaveInstanceState(bundle);
    }

    /**
     * Load data for each app
     */
    public abstract void setDataConfig();

    protected void loadTopics() {

        if (!Utility.haveNetworkConnection()) {
            Utility.showMessage(ComcastController.getApp().getString(R.string.network_error));
            openBar(false);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Definitions.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ComcastService forecastService = retrofit.create(ComcastService.class);
        Call<Topics> call = forecastService.getTopics(mQuery);

        call.enqueue(new Callback<Topics>() {
            @Override
            public void onResponse(Response<Topics> response, Retrofit retrofit) {
                // Close progress bar
                openBar(false);

                // Process the response
                Topics topics = response.body();

                // Update the UI if the context is valid
                if (isFinishing()) return;

                // Update the UI
                mTopics.addAll(topics.getRelatedTopics());
                mTopicsFragment.setTopics(mTopics);
                mTopicsFragment.refresh();
                addFirstTopic();
            }

            @Override
            public void onFailure(Throwable t) {

                // Close progress bar
                openBar(false);

                // Error message
                Utility.showMessage(ComcastController.getApp().getString(R.string.network_error));
            }

        });

        openBar(true);

    }

    // Adds the first item when data comes back from web service
    private void addFirstTopic() {
        if (mTopics.size() == 0) return;
        if (findViewById(R.id.item_detail_container) == null) return;

        Bundle arguments = new Bundle();
        arguments.putParcelable(Definitions.KEY_TOPIC, mTopics.get(0));
        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container,
                fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.base_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if (mListType == TopicsAdapter.LIST_VIEW) {
            menu.findItem(R.id.action_list_view).setVisible(false);
            menu.findItem(R.id.action_grid_view).setVisible(true);
        } else {
            menu.findItem(R.id.action_list_view).setVisible(true);
            menu.findItem(R.id.action_grid_view).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_list_view) {
            mListType = TopicsAdapter.LIST_VIEW;
            addFragment(R.layout.topics_list);
        } else if (id == R.id.action_grid_view) {
            mListType = TopicsAdapter.GRID_VIEW;
            addFragment(R.layout.topics_grid);
        }

        if (id == R.id.action_list_view || id == R.id.action_grid_view) {
            invalidateOptionsMenu();
        }

        return super.onOptionsItemSelected(item);
    }

    public void openBar(boolean open) {
        if (open) {
            mBar.setVisibility(View.VISIBLE);
        } else {
            mBar.setVisibility(View.GONE);
        }
    }

}
