package com.shtainyky.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

public class SunsetFragment extends Fragment {

    private View mSceneView;
    private View mSunView;
    private View mSunWaterView;
    private View mSkyView;
    private View mWaterView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    private boolean isSunRize = true;

    public static SunsetFragment newInstance()
    {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);

        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);
        mSunWaterView = view.findViewById(R.id.water_sun);
        mWaterView = view.findViewById(R.id.water);

        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);

        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSunRize) {
                    isSunRize = false;
                    startAnimation1();
                }
                else
                {
                    isSunRize=true;
                    startAnimation2();
                }
            }
        });
        return view;
    }

    private void startAnimation2() {
        float sunYStart = mWaterView.getTop();
        float sunYEnd = mSunView.getTop();
        float sunWaterYEnd = mSunWaterView.getTop();

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mSunView, "y",sunYStart, sunYEnd)
                .setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator waterAnimator = ObjectAnimator
                .ofFloat(mSunWaterView, "y",-sunYStart, sunWaterYEnd)
                .setDuration(2900);
        waterAnimator.setInterpolator(new AccelerateInterpolator());


        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor,mBlueSkyColor )
                .setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(sunsetSkyAnimator)
                .with(waterAnimator);
        animatorSet.start();
    }

    private void startAnimation1() {
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();
        float sunWaterYStart = mSunWaterView.getTop();

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mSunView, "y",sunYStart, sunYEnd)
                .setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator waterAnimator = ObjectAnimator
                .ofFloat(mSunWaterView, "y",sunWaterYStart, -sunYEnd)
                .setDuration(4500);
        waterAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor, mNightSkyColor)
                .setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(sunsetSkyAnimator)
                .with(waterAnimator)
                .before(nightSkyAnimator);
        animatorSet.start();

    }
}
