package com.ntl.udacity.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable
{
    public static final Creator<Step> CREATOR = new Creator<Step>()
    {
        @Override
        public Step createFromParcel(Parcel in)
        {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size)
        {
            return new Step[size];
        }
    };
    private String id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public Step(Parcel in)
    {
        id = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    public String getId()
    {
        return id;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public String getDescription()
    {
        return description;
    }

    public String getVideoURL()
    {
        return videoURL;
    }

    public String getThumbnailURL()
    {
        return thumbnailURL;
    }
}
