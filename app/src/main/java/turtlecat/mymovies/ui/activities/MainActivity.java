package turtlecat.mymovies.ui.activities;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.graphics.PorterDuff;
import android.widget.LinearLayout;
import android.animation.Animator;
import android.widget.ProgressBar;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.os.Build;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.AfterViews;

import turtlecat.mymovies.R;
import turtlecat.mymovies.api.RuntimeCache;
import turtlecat.mymovies.utils.K;
import turtlecat.mymovies.utils.Tools;
import turtlecat.mymovies.api.ServiceInvoker;
import turtlecat.mymovies.ui.adapters.MovieRecyclerAdapter;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements ViewTreeObserver.OnScrollChangedListener {
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
    private String searchedString;

    private GridLayoutManager gridLayoutManager;
    private MovieRecyclerAdapter adapter;

    @Click
    protected void toolbarSearchButton() {
        showSearch(false);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }


    private GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            switch (adapter.getItemViewType(position)) {
                case MovieRecyclerAdapter.TYPE_MOVIE_ITEM:
                    return 1; //FIXME:  REF: -> TODO #1
                case MovieRecyclerAdapter.TYPE_PROGRESS_BAR:
                    return 2;
                default:
                    return -1;
            }
        }
    };

    @AfterViews
    protected void initUI() {
        setSupportActionBar(toolbar);
        movieProgressBar.setVisibility(View.GONE);
        movieProgressBar.getIndeterminateDrawable().setColorFilter(Tools.getColor(this, R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

        if (Tools.getAndroidVersion() >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setPadding(0, Tools.getStatusBarHeight(this), 0, 0);
        }
        adapter = new MovieRecyclerAdapter(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanCount(2);
        gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
        movieRecyclerView.setLayoutManager(gridLayoutManager); //TODO #1: adapt to tablets and orientation changes
        movieRecyclerView.setAdapter(adapter);
        movieProgressBar.getViewTreeObserver().addOnScrollChangedListener(this);

        toolbarSearchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                movieProgressBar.setVisibility(View.VISIBLE);
                currentPagingIndex = 1;
                searchedString = toolbarSearchET.getText().toString();
                getMovieList();
                return false;
            }
        });
    }

    private void getIncrementedPageMovieList() {
        currentPagingIndex++;
        getMovieList();
    }

    private void getMovieList() {
        si.searchMovie(searchedString, currentPagingIndex);
    }

    @Receiver(actions = K.MOVIES_LOADED)
    protected void onMoviesLoaded() {
        if (movieProgressBar.getVisibility() == View.VISIBLE)
            movieProgressBar.setVisibility(View.GONE);

        adapter.notifyDataChanged();
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

    @Override
    public void onScrollChanged() {
        if (adapter.getHasProgress()) {
            int lastItem = gridLayoutManager.findLastVisibleItemPosition();
            if (lastItem == RuntimeCache.getInstance().getSearchedMovies().size()) {
                getIncrementedPageMovieList();
            }
        }
    }
}

