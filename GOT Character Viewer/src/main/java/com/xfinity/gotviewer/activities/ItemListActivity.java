package com.xfinity.gotviewer.activities;

import com.xfinity.common.activities.BaseItemListActivity;
import com.xfinity.gotviewer.R;

public class ItemListActivity extends BaseItemListActivity {

    @Override
    public void setDataConfig() {
        detailsActivity = ItemDetailActivity.class;
        mQuery = getString(R.string.query);
    }

}
