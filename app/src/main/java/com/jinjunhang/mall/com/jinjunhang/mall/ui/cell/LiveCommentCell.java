package com.jinjunhang.mall.com.jinjunhang.mall.ui.cell;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.jinjunhang.mall.R;
import com.jinjunhang.mall.com.jinjunhang.framework.ui.BaseListViewCell;

public class LiveCommentCell extends BaseListViewCell {

    public LiveCommentCell(Activity activity) {
        super(activity);
    }

    @Override
    public ViewGroup getView(View convertView) {
        View v = mActivity.getLayoutInflater().inflate(R.layout.listitem_comment, null, false);

        return (ViewGroup)v;
    }
}
