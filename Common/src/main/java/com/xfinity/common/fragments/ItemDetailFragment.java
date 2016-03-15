package com.xfinity.common.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xfinity.common.R;
import com.xfinity.common.activities.BaseItemDetailActivity;
import com.xfinity.common.beans.Topic;
import com.xfinity.common.common.Utility;
import com.xfinity.common.configs.Definitions;

public class ItemDetailFragment extends Fragment {

    // Current topic
    private Topic mTopic;

    public ItemDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_detail, container, false);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        // Get the item
        if (bundle == null) {
            mTopic = getArguments().getParcelable(Definitions.KEY_TOPIC);
        } else {
            mTopic = bundle.getParcelable(Definitions.KEY_TOPIC);
        }

        // System is recycling this fragment, it no longer exist!
        if (mTopic == null) return;

        // Fill all the fields with data
        View view = getView();

        // Get the Title & Description
        String[] titleAndDescription = Utility.getTitleAndDescription(mTopic.getText());

        // Set Title
        TextView tv = (TextView)view.findViewById(R.id.title);
        tv.setText(titleAndDescription[0]);

        // Set Description
        tv = (TextView)view.findViewById(R.id.description);
        tv.setText(titleAndDescription[1]);

        // Set the Image
        ImageView img = (ImageView)view.findViewById(R.id.image);
        if (Utility.notNullNorEmpty(mTopic.getIcon().getUrl())) {
            Picasso.with(getActivity())
                    .load(mTopic.getIcon().getUrl())
                    .placeholder(R.drawable.loading_image)
                    .error(R.drawable.image_not_available)
                    .into(img);
        }  else {
            img.setImageResource(R.drawable.image_not_available);
        }

        if (getActivity() instanceof BaseItemDetailActivity) {
            ((BaseItemDetailActivity)getActivity()).setTitle(titleAndDescription[0]);
        }
    }

    /**
     * Store some properties in case of getting notification changes
     */
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable(Definitions.KEY_TOPIC, mTopic);
        super.onSaveInstanceState(bundle);
    }

}
