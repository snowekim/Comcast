package com.xfinity.common.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Icon implements Parcelable {

    @SerializedName("URL")
    private String mUrl;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    /**
     * Retrieving Icon data from Parcel object
     **/
    private Icon(Parcel in) {
        this.mUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Storing the Icon data to Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
    }

    // Magic happens here
    public static final Parcelable.Creator<Icon> CREATOR = new Parcelable.Creator<Icon>() {

        @Override
        public Icon createFromParcel(Parcel source) {
            return new Icon(source);
        }

        @Override
        public Icon[] newArray(int size) {
            return new Icon[size];
        }
    };

}
