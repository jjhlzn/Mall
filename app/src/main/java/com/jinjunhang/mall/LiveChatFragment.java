package com.jinjunhang.mall;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jinjunhang.mall.com.jinjunhang.framework.ui.ListViewCell;
import com.jinjunhang.mall.com.jinjunhang.mall.ui.cell.LiveCommentCell;

import java.util.ArrayList;
import java.util.List;


public class LiveChatFragment extends Fragment {

    private CommentsListAdapter mCommentsListAdapter;
    private List<ListViewCell> mCommentsCells;

    private ListView mCommentsListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shoplive_chat, null);
        mCommentsListView = v.findViewById(R.id.commentsList);
        mCommentsListView.setDividerHeight(0);
        mCommentsListView.setVerticalScrollBarEnabled(false); //隐藏scrollbar

        makeCommentCells();
        mCommentsListAdapter = new CommentsListAdapter(getActivity(), mCommentsCells);

        mCommentsListView.setAdapter(mCommentsListAdapter);
        return v;
    }

    private  void makeCommentCells() {
        mCommentsCells = new ArrayList<>();
        for(int i = 0; i < 15; i++) {
            mCommentsCells.add(new LiveCommentCell(getActivity()));
        }
    }




    private class CommentsListAdapter extends ArrayAdapter<ListViewCell> {
        private List<ListViewCell> mViewCells;

        public CommentsListAdapter(Activity activity, List<ListViewCell> cells) {
            super(activity, 0, cells);
            mViewCells = cells;
        }

        @Override
        public int getCount() {
            return mViewCells.size();
        }

        @Override
        public ListViewCell getItem(int position) {
            return mViewCells.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewCell item = getItem(position);
            return item.getView(convertView);
        }


    }
}
