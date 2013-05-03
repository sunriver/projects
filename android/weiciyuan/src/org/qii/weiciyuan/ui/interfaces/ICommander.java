package org.qii.weiciyuan.ui.interfaces;

import android.widget.ImageView;
import android.widget.ListView;
import org.qii.weiciyuan.support.file.FileLocationMethod;

public interface ICommander {


    public void downloadAvatar(ImageView view, String url, int position, ListView listView, boolean isFling);

    public void downContentPic(ImageView view, String url, int position, ListView listView, FileLocationMethod method,boolean isScroll);

    public void totalStopLoadPicture();
}