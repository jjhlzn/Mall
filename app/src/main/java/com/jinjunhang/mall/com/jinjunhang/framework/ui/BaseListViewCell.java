package com.jinjunhang.mall.com.jinjunhang.framework.ui;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.jinjunhang.mall.com.jinjunhang.framework.lib.LogHelper;

public abstract class BaseListViewCell implements ListViewCell {
    private  static final  String TAG = LogHelper.makeLogTag(BaseListViewCell.class);

    public final static int OTHER_CELL = 0;

    protected Activity mActivity;

    public BaseListViewCell(Activity activity) {
        mActivity = activity;
    }

    private BaseListViewCell() {}

    @Override
    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void release() {
    }

    @Override
    public Object getContent() {
        return null;
    }

    @Override
    public int getItemViewType() {
        return 0;
    }
}
