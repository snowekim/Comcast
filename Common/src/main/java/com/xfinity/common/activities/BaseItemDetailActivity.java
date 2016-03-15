package com.xfinity.common.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.xfinity.common.R;
import com.xfinity.common.beans.Topic;
import com.xfinity.common.configs.Definitions;
import com.xfinity.common.fragments.ItemDetailFragment;

/**
 * @author Mike Amigon
 * This is the base class to be used by the client apps, where is presented
 * the details of a particular item selected in the previous screen, this screen will run
 * only if you are in portrait & landscape modes for phones & portrait for tablets with
 * a width >= 900dp (Tablets of 7 & 10 inches)
 */
public abstract class BaseItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        setData();

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Topic topic = getIntent().getParcelableExtra(Definitions.KEY_TOPIC);
            Bundle arguments = new Bundle();
            arguments.putParcelable(Definitions.KEY_TOPIC, topic);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    public void setTitle (String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public abstract void setData();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
