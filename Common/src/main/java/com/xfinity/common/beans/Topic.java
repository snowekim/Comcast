package com.xfinity.common.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Topic implements Parcelable {

    @SerializedName("Result")
    private String mResult;

    @SerializedName("Icon")
    private Icon mIcon;

    @SerializedName("FirstURL")
    private String mFirstURL;

    @SerializedName("Text")
    private String mText;

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        this.mResult = result;
    }

    public Icon getIcon() {
        return mIcon;
    }

    public void setIcon(Icon icon) {
        this.mIcon = icon;
    }

    public String getFirstURL() {
        return mFirstURL;
    }

    public void setFirstURL(String firstURL) {
        this.mFirstURL = firstURL;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    /**
     * Retrieving Topic data from Parcel object
     **/
    private Topic(Parcel in) {
        this.mResult = in.readString();
        this.mIcon = in.readParcelable(Icon.class.getClassLoader());
        this.mFirstURL = in.readString();
        this.mText = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Storing the Topic data to Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mResult);
        dest.writeParcelable(mIcon, flags);
        dest.writeString(mFirstURL);
        dest.writeString(mText);
    }

    // Magic happens here
    public static final Parcelable.Creator<Topic> CREATOR = new Parcelable.Creator<Topic>() {

        @Override
        public Topic createFromParcel(Parcel source) {
            return new Topic(source);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

}
