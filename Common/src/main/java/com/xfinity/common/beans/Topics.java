package com.xfinity.common.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Topics {

    @SerializedName("Heading")
    private String mHeading;

    @SerializedName("RelatedTopics")
    private List<Topic> mRelatedTopics;

    public String getHeading() {
        return mHeading;
    }

    public void setHeading(String heading) {
        this.mHeading = heading;
    }

    public List<Topic> getRelatedTopics() {
        return mRelatedTopics;
    }

    public void setRelatedTopics(List<Topic> relatedTopics) {
        this.mRelatedTopics = relatedTopics;
    }

}
