package org.qii.weiciyuan.bean;

import android.text.SpannableString;

import java.io.Serializable;

/**
 * User: qii
 * Date: 12-9-6
 */
public abstract class ItemBean implements Serializable {
    public abstract SpannableString getListViewSpannableString();

    public abstract String getListviewItemShowTime();

    public abstract String getText();

    public abstract String getCreated_at();

    public abstract void setMills(long mills);

    public abstract long getMills();

    public abstract String getId();

    public abstract UserBean getUser();

}
