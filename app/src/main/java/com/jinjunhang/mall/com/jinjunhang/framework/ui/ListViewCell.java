package com.jinjunhang.mall.com.jinjunhang.framework.ui;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

public interface ListViewCell  {
    Object getContent();

    ViewGroup getView( View convertView);

    void setActivity(Activity activity);

    void release();

    int getItemViewType();
}
