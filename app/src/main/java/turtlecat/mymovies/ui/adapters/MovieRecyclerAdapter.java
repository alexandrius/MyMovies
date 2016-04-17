package turtlecat.mymovies.ui.adapters;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import turtlecat.mymovies.R;
import turtlecat.mymovies.api.RuntimeCache;
import turtlecat.mymovies.bean.SearchResultItem;
import turtlecat.mymovies.ui.activities.MainActivity;
import turtlecat.mymovies.ui.activities.MovieDetailedActivity_;
import turtlecat.mymovies.ui.components.LoadingView;
import turtlecat.mymovies.utils.Tools;

/**
 * Created by Alex on 3/22/2016.
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private RuntimeCache rc = RuntimeCache.getInstance();
    private MainActivity activity;
    public static final int TYPE_PROGRESS_BAR = 1;
    public static final int TYPE_MOVIE_ITEM = 2;
    private boolean hasProgress = true;


    public boolean getHasProgress() {
        return hasProgress;
    }

    public MovieRecyclerAdapter(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_MOVIE_ITEM) {
            v = LayoutInflater.from(activity).inflate(R.layout.movie_recycler_item, parent, false);
            return new MovieViewHolder(v);
        }

        v = LayoutInflater.from(activity).inflate(R.layout.progress_layout, parent, false);
        return new ProgressViewHolder(v);

    }

    public void notifyDataChanged() {
        if (rc.getSearchedMovies() != null)
            if (rc.getSearchedMovies().size() == rc.getSearchResultCount())
                hasProgress = false;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        if (getItemViewType(position) == TYPE_MOVIE_ITEM) {
            final MovieViewHolder holder = (MovieViewHolder) vh;
            holder.loadingView.prepare();
            holder.posterView.setTag(holder.loadingView);
            holder.loadingView.setTag(holder.posterView);
            if (!getItem(position).getPoster().equals("N/A")) {
                Picasso.with(activity)
                        .load(getItem(position).getPoster())
                        .error(R.mipmap.no_photo)
                        .into(holder.posterView, new Callback() {
                            @Override
                            public void onSuccess() {
                                LoadingView view = (LoadingView) holder.posterView.getTag();
                                view.imageLoaded();
                            }

                            @Override
                            public void onError() {
                                LoadingView view = (LoadingView) holder.posterView.getTag();
                                view.cancel();
                            }
                        });
            } else {
                holder.posterView.setImageResource(R.mipmap.no_photo);
                LoadingView view = (LoadingView) holder.posterView.getTag();
                view.imageLoaded();
            }

        }
    }


    @Override
    public int getItemViewType(int position) {
        if (hasProgress && rc.getSearchedMovies().size() == position)
            return TYPE_PROGRESS_BAR;
        return TYPE_MOVIE_ITEM;
    }

    @Override
    public int getItemCount() {
        if (rc.getSearchedMovies().size() > 0)
            return hasProgress ? rc.getSearchedMovies().size() + 1 : rc.getSearchedMovies().size();
        return 0;
    }

    public SearchResultItem getItem(int position) {
        return RuntimeCache.getInstance().getSearchedMovies().get(position);
    }

    class ProgressViewHolder extends ViewHolder {
        ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            progressBar.getIndeterminateDrawable().setColorFilter(Tools.getColor(progressBar.getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class MovieViewHolder extends ViewHolder {

        ImageView posterView;
        LoadingView loadingView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            loadingView = (LoadingView) itemView.findViewById(R.id.movie_recycler_item_loading);
            posterView = (ImageView) itemView.findViewById(R.id.movie_recycler_item_image);

            posterView.setOnClickListener(onClickListener);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, MovieDetailedActivity_.class);
                i.putExtra("poster", getItem(getAdapterPosition()).getPoster());
                if (Tools.isLollipopOrNewer()) {
                    Pair<View, String> pair1 = Pair.create((View) posterView, posterView.getTransitionName());
                    Pair<View, String> pair2 = Pair.create((View) activity.getToolbar(), activity.getToolbar().getTransitionName());
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair1, pair2);
                    activity.startActivity(i, options.toBundle());
                } else {
                    activity.startActivity(i);
                }
            }
        };
    }
}
