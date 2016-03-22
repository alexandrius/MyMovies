package turtlecat.mymovies.ui.adapters;

import android.content.Context;
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
import turtlecat.mymovies.ui.components.LoadingView;

/**
 * Created by Alex on 3/22/2016.
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {

    private RuntimeCache rc = RuntimeCache.getInstance();
    private Context context;
    public MovieRecyclerAdapter(Context c) {
        this.context = c;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.movie_recycler_item, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        holder.posterView.setTag(holder.loadingView);
        holder.loadingView.setTag(holder.posterView);


        Picasso.with(context).load(getItem(position).getPoster()).into(holder.posterView);

        Picasso.with(context).load(getItem(position).getPoster()).into(holder.posterView, new Callback() {
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
        }
    }
}
