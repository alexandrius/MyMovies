package turtlecat.mymovies.ui.activities;

import android.animation.Animator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.ViewById;

import turtlecat.mymovies.R;
import turtlecat.mymovies.api.ServiceInvoker;
import turtlecat.mymovies.ui.adapters.MovieRecyclerAdapter;
import turtlecat.mymovies.utils.K;
import turtlecat.mymovies.utils.Tools;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @Bean
    protected ServiceInvoker si;
    @ViewById
    protected RecyclerView movieRecyclerView;
    @ViewById
    protected Toolbar toolbar;

    @ViewById
    protected LinearLayout toolbarSearchLayout;

    @ViewById
    protected ProgressBar movieProgressBar;

    @ViewById
    protected EditText toolbarSearchET;

    private int currentPagingIndex = 1;

    private MovieRecyclerAdapter adapter;

    @Click
    protected void toolbarSearchButton() {
        showSearch(false);

    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @AfterViews
    protected void initUI() {
        setSupportActionBar(toolbar);
        movieProgressBar.setVisibility(View.GONE);
        movieProgressBar.getIndeterminateDrawable().setColorFilter(Tools.getColor(this, R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

        if (Tools.getAndroidVersion() >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setPadding(0, Tools.getStatusBarHeight(this), 0, 0);
        }
        adapter = new MovieRecyclerAdapter(this);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        movieRecyclerView.setAdapter(adapter);

        toolbarSearchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                movieProgressBar.setVisibility(View.VISIBLE);
                si.searchMovie(toolbarSearchET.getText().toString(), currentPagingIndex);
                return false;
            }
        });
    }


    @Receiver(actions = K.MOVIES_LOADED)
    protected void onMoviesLoaded() {
        movieProgressBar.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (toolbarSearchLayout.getVisibility() != View.VISIBLE) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showSearch(true);
        invalidateOptionsMenu();
        return true;
    }

    private void showSearch(final boolean shouldShow) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (shouldShow) {
            toolbarSearchLayout.setVisibility(View.VISIBLE);
            toolbarSearchET.requestFocus();
            imm.showSoftInput(toolbarSearchET, InputMethodManager.SHOW_IMPLICIT);
        } else {
            imm.hideSoftInputFromWindow(toolbarSearchET.getWindowToken(), 0);
        }

        if (Tools.isLollipopOrNewer()) {
            float hypot = (float) Math.hypot(toolbar.getHeight(), toolbar.getWidth());
            float startRadius = shouldShow ? 0 : hypot;
            float endRadius = shouldShow ? hypot : 0;
            final Animator animator = ViewAnimationUtils.createCircularReveal(toolbarSearchLayout,
                    toolbar.getRight(), toolbar.getTop() - (toolbar.getHeight() / 2), startRadius,
                    endRadius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(200);

            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    if (!shouldShow)
                        toolbarSearchLayout.setVisibility(View.GONE);

                    invalidateOptionsMenu();
                    animator.removeAllListeners();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            animator.start();
        } else if (!shouldShow) {
            toolbarSearchLayout.setVisibility(View.GONE);
        }
    }
}

