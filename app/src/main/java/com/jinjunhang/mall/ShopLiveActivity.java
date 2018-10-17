package com.jinjunhang.mall;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.jinjunhang.mall.com.jinjunhang.framework.lib.LogHelper;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.UltraViewPagerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopLiveActivity extends PlayerActivity{

    private static  final String TAG = LogHelper.makeLogTag(ShopLiveActivity.class);

    private ImmersionBar mImmersionBar;
    private LiveMainFragment[] mFragments;
    //private UltraViewPagerView ultraViewPager;

    @BindView(R.id.ultra_viewpager) UltraViewPager mUltraViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setUltraViewPager();

        findViewById(R.id.root).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mImmersionBar = ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
    }

    @Override
    public void onResume() {
        super.onResume();
        mUltraViewPager.setCurrentItem(1);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogHelper.d(TAG, event);
        mUltraViewPager.getViewPager().onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //将左右滑动的事件从UltraViewPager传递到ViewPager
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        super.dispatchTouchEvent(ev);
        return false;
    }

    private void setUltraViewPager() {

        mUltraViewPager.setScrollMode(UltraViewPager.ScrollMode.VERTICAL);
        //initialize UltraPagerAdapter，and add child view to UltraViewPager

        LiveMainFragment[] mFragments = new LiveMainFragment[3];
        mFragments[0] = new LiveMainFragment();
        mFragments[1] = new LiveMainFragment();
        mFragments[2] = new LiveMainFragment();
        PagerAdapter adapter = new ShopLivePageAdapter(getSupportFragmentManager(), mFragments);
        mUltraViewPager.setAdapter(adapter);

        //set an infinite loop

        mUltraViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                LogHelper.d(TAG, "i = " + i);
                int index = i % 3;
                mFragments[index].setPlayerView();
                mFragments[index].play();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mUltraViewPager.setInfiniteLoop(true);

    }

    public class ShopLivePageAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        public ShopLivePageAdapter(FragmentManager fm, Fragment[] fragments) {
            super(fm);
            ArrayList<Fragment> list = new ArrayList<>();
            for(int i = 0; i < fragments.length; i++) {
                list.add(fragments[i]);
            }
            this.fragments = list;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }

}
