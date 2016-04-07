package turtlecat.mymovies.ui.components;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;

import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;


import turtlecat.mymovies.R;
import turtlecat.mymovies.utils.Tools;

/**
 * Created by Alex on 3/22/2016.
 */

public class LoadingView extends FrameLayout {

    private View loadingAlphaView, loadingAlphaBackground;
    private static final long ANIM_DURATION = 100;
    private static final long REVEAL_ANIM_DURATION = 500;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.loading_layout, this);
        loadingAlphaView = findViewById(R.id.loadingAlphaView);
        loadingAlphaBackground = findViewById(R.id.loadingAlphaBackground);
        loadingAlphaView.setTag(0f);
        loadingAlphaView.animate().setDuration(ANIM_DURATION).setListener(animListener).alpha(0f);
    }

    public void imageLoaded() {
        ImageView imageView = (ImageView) getTag();
        int itemHeight = getContext().getResources().getDimensionPixelSize(R.dimen.recycler_item_height);
        int itemWidth = Tools.getScreenWidth(getContext()) / 2;
        int centerY = itemHeight / 2;
        int centerX = itemWidth / 2;
        imageView.setVisibility(VISIBLE);
        try {
            Animator animator = ViewAnimationUtils.createCircularReveal(imageView, centerX, centerY, 0, (float) Math.hypot(itemHeight, itemWidth));
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(REVEAL_ANIM_DURATION);
            animator.start();
        } catch (IllegalStateException e) {
        }
        cancel();
    }

    public void cancel() {
        loadingAlphaView.animate().cancel();
        setVisibility(GONE);
    }

    Animator.AnimatorListener animListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            float reqAlpha = 1 - (float) loadingAlphaView.getTag();
            loadingAlphaView.setTag(reqAlpha);
            loadingAlphaView.animate().setDuration(ANIM_DURATION).setListener(this).alpha(reqAlpha);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };


}
