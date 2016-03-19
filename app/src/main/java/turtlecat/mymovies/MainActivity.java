package turtlecat.mymovies;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.ViewById;

import turtlecat.mymovies.utils.K;
import turtlecat.mymovies.utils.Tools;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    protected RecyclerView movieRecyclerView;

    @ViewById
    protected Toolbar toolbar;

    @Click
    protected void toolbarSearchButton() {

    }

    @AfterViews
    protected void initUI() {
        if (toolbar != null)
            setSupportActionBar();
    }


    protected void setSupportActionBar() {
        setSupportActionBar(toolbar);
        if (Tools.getAndroidVersion() >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setPadding(0, Tools.getStatusBarHeight(this), 0, 0);
        }
    }

    @Receiver(actions = K.MOVIES_LOADED)
    protected void onMoviesLoaded() {
        //TODO: test with broadcasts
        Tools.log("Movies Loaded");
    }
}
