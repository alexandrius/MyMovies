package turtlecat.mymovies.ui.adapters;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {

    private RuntimeCache rc = RuntimeCache.getInstance();
    private MainActivity activity;

    public MovieRecyclerAdapter(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.movie_recycler_item, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        holder.posterView.setTag(holder.loadingView);
        holder.loadingView.setTag(holder.posterView);


        Picasso.with(activity).load(getItem(position).getPoster()).into(holder.posterView);

        Picasso.with(activity).load(getItem(position).getPoster()).into(holder.posterView, new Callback() {
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

    }

    @Override
    public int getItemCount() {
        if (rc.getSearchedMovies() != null)
            return rc.getSearchedMovies().size();
        return 0;
    }

    public SearchResultItem getItem(int position) {
        return RuntimeCache.getInstance().getSearchedMovies().get(position);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView posterView;
        LoadingView loadingView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            loadingView = (LoadingView) itemView.findViewById(R.id.movie_recycler_item_loading);
            posterView = (ImageView) itemView.findViewById(R.id.movie_recycler_item_image);

            posterView.setOnClickListener(onClickListener);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, MovieDetailedActivity_.class);
                i.putExtra("poster", getItem(getAdapterPosition()).getPoster());

                if (Tools.isLollipopOrNewer()) {
                    Pair<View, String> pair1 = Pair.create((View) posterView, posterView.getTransitionName());
                    Pair<View, String> pair2 = Pair.create((View) activity.getToolbar(), activity.getToolbar().getTransitionName());

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair1, pair2);
                    ActivityOptions transitionActivityOptions =
                            ActivityOptions.makeSceneTransitionAnimation(activity, posterView, activity.getString(R.string.movie_transition));
                    activity.startActivity(i, options.toBundle());
                } else {
                    activity.startActivity(i);
                }
            }
        };
    }
}
