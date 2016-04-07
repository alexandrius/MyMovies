package turtlecat.mymovies.ui.activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import turtlecat.mymovies.R;
import turtlecat.mymovies.utils.Tools;

/**
 * Created by Alex on 3/22/2016.
 */

@EActivity(R.layout.movie_detailed_activity)
public class MovieDetailedActivity extends AppCompatActivity {
    @ViewById
    ImageView detailedImageView;
    @ViewById
    Toolbar toolbar;
    @Extra
    String poster;
    @ViewById
    ImageView movieDetailedBackground;

    @AfterViews
    void initUI() {
        setSupportActionBar(toolbar);
        if (Tools.getAndroidVersion() >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setPadding(0, Tools.getStatusBarHeight(this), 0, 0);
        }
        Tools.log(poster);
        Picasso.with(this).load(poster).into(detailedImageView);
    }

}
