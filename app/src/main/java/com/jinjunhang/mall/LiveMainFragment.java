package com.jinjunhang.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.spherical.SphericalSurfaceView;
import com.google.android.exoplayer2.util.ErrorMessageProvider;

import java.util.ArrayList;

public class LiveMainFragment extends Fragment {

    private PlayerView playerView;
    private LinearLayout debugRootView;
    private TextView debugTextView;
    private ViewPager mViewPager;

    private Fragment[] mFragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shoplive_main, null);

        mViewPager = v.findViewById(R.id.viewPager);

        mViewPager.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return false;
            }
        });
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


        playerView = v.findViewById(R.id.player_view);
        debugRootView = v.findViewById(R.id.controls_root);
        debugTextView = v.findViewById(R.id.debug_text_view);

        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        //playerView.setControllerVisibilityListener(this);
        playerView.setErrorMessageProvider(new PlayerErrorMessageProvider());
        playerView.requestFocus();

        setViewPager();

        return v;
    }



    public void setPlayerView() {

        PlayerActivity activity = (PlayerActivity)getActivity();
        if (activity == null)
            return;
        activity.setPlayerView(playerView);
        activity.setDebugRootView(debugRootView);
        activity.setDebugTextView(debugTextView);
    }

    public void play() {
        PlayerActivity activity = (PlayerActivity)getActivity();
        if (activity == null)
            return;
        ((PlayerActivity)getActivity()).changePlayerSource();
    }

    private void setViewPager() {
        mFragments = new Fragment[3];
        mFragments[0] = new LiveIntroFragment();
        mFragments[1] = new LiveChatFragment();
        mFragments[2] = new LiveEmptyFragment();

        ShopLivePageAdapter adapter = new ShopLivePageAdapter(getChildFragmentManager(), mFragments);
        mViewPager.setAdapter(adapter);

        mViewPager.setCurrentItem(1);

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

    private class PlayerErrorMessageProvider implements ErrorMessageProvider<ExoPlaybackException> {

        @Override
        public Pair<Integer, String> getErrorMessage(ExoPlaybackException e) {
            String errorString = getString(R.string.error_generic);
            if (e.type == ExoPlaybackException.TYPE_RENDERER) {
                Exception cause = e.getRendererException();
                if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                    // Special case for decoder initialization failures.
                    MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                            (MediaCodecRenderer.DecoderInitializationException) cause;
                    if (decoderInitializationException.decoderName == null) {
                        if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
                            errorString = getString(R.string.error_querying_decoders);
                        } else if (decoderInitializationException.secureDecoderRequired) {
                            errorString =
                                    getString(
                                            R.string.error_no_secure_decoder, decoderInitializationException.mimeType);
                        } else {
                            errorString =
                                    getString(R.string.error_no_decoder, decoderInitializationException.mimeType);
                        }
                    } else {
                        errorString =
                                getString(
                                        R.string.error_instantiating_decoder,
                                        decoderInitializationException.decoderName);
                    }
                }
            }
            return Pair.create(0, errorString);
        }
    }
}
