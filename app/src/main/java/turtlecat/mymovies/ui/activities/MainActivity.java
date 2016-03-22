package turtlecat.mymovies.ui.activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

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
    ServiceInvoker si;
    @ViewById
    protected RecyclerView movieRecyclerView;
    @ViewById
    protected Toolbar toolbar;

    @ViewById
    protected LinearLayout toolbarSearchLayout;

    @ViewById
    protected EditText toolbarSearchET;

    private int currentPagingIndex = 1;

    private MovieRecyclerAdapter adapter;

    @Click
    protected void toolbarSearchButton() {
        si.searchMovie(toolbarSearchET.getText().toString(), currentPagingIndex);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @AfterViews
    protected void initUI() {
        setSupportActionBar(toolbar);
        if (Tools.getAndroidVersion() >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setPadding(0, Tools.getStatusBarHeight(this), 0, 0);
        }
        adapter = new MovieRecyclerAdapter(this);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        movieRecyclerView.setAdapter(adapter);
        toolbarSearchLayout.setVisibility(View.VISIBLE);
    }


    @Receiver(actions = K.MOVIES_LOADED)
    protected void onMoviesLoaded() {
        adapter.notifyDataSetChanged();
    }
}
