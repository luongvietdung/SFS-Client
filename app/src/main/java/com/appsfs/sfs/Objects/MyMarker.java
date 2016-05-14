package com.appsfs.sfs.Objects;

/**
 * Created by longdv on 4/29/16.
 */
public class MyMarker {
    private String mLabelName;
    private String mLabelPhone;
    private String mIcon;
    private double mLatitude;
    private double mLongitude;

    public MyMarker()
    {
        super();
    }

    public MyMarker(String name, String icon, String phone)
    {
        this.mLabelName = name;
        this.mLabelPhone = phone;
        this.mIcon = icon;
    }

    public String getLabelName()
    {
        return mLabelName;
    }

    public void setLabelName(String mLabel)
    {
        this.mLabelName = mLabel;
    }

    public String getLabelPhone()
    {
        return mLabelPhone;
    }

    public void setLabelPhone(String mLabel)
    {
        this.mLabelPhone = mLabel;
    }

    public String getIcon()
    {
        return mIcon;
    }

    public void setIcon(String icon)
    {
        this.mIcon = icon;
    }

    public Double getLatitude()
    {
        return mLatitude;
    }

    public void setLatitude(Double mLatitude)
    {
        this.mLatitude = mLatitude;
    }

    public Double getLongitude()
    {
        return mLongitude;
    }

    public void setLongitude(Double mLongitude)
    {
        this.mLongitude = mLongitude;
    }
}
