package turtlecat.mymovies.ui.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
    @ViewById
    TextView detailedPlotTextView;

    private Bitmap detailedBlurredBitmap;

    @AfterViews
    void initUI() {
        setSupportActionBar(toolbar);
        if (Tools.getAndroidVersion() >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setPadding(0, Tools.getStatusBarHeight(this), 0, 0);
        }
        loadData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        detailedBlurredBitmap.recycle();
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            detailedBlurredBitmap = Tools.fastBlur(bitmap, 9);
            movieDetailedBackground.setImageBitmap(detailedBlurredBitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    private void loadData() {
        Picasso.with(this).load(poster).into(detailedImageView);
        Picasso.with(this).load(poster).into(target);
    }
}
