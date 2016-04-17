package turtlecat.mymovies.api;

import android.content.Intent;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import turtlecat.mymovies.MyApp;
import turtlecat.mymovies.bean.SearchResultHolder;
import turtlecat.mymovies.bean.SearchResultItem;
import turtlecat.mymovies.utils.K;
import turtlecat.mymovies.utils.Tools;

/**
 * Created by Alex on 3/22/2016.
 */
@EBean
public class ServiceInvoker {

    @App
    MyApp app;

    public void searchMovie(String name, final int pageIndex) {
        Call<SearchResultHolder> call = app.getOmdbService().searchMovie(name, pageIndex);
        call.enqueue(new Callback<SearchResultHolder>() {
            @Override
            public void onResponse(Call<SearchResultHolder> call, Response<SearchResultHolder> response) {
                boolean isEmpty = response.body().getSearch() == null;
                if (!isEmpty)
                    if (pageIndex == 1) {
                        RuntimeCache.getInstance().setSearchResultCount(response.body().getTotalResults());
                        RuntimeCache.getInstance().setSearchedMovies(response.body().getSearch());
                    } else {
                        RuntimeCache.getInstance().addSearchedMovies(response.body().getSearch());
                    }

                Intent intent = new Intent(K.MOVIES_LOADED);
                intent.putExtra(K.MOVIES_EMPTY, isEmpty);
                app.sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<SearchResultHolder> call, Throwable t) {
                Tools.showToast(app, "Error happened");
            }
        });
    }

    public void getMovieDetailed(String id, String plotType) {
        Call<SearchResultItem> call = app.getOmdbService().getMovieDetailed(id, plotType);
        call.enqueue(new Callback<SearchResultItem>() {
            @Override
            public void onResponse(Call<SearchResultItem> call, Response<SearchResultItem> response) {

            }

            @Override
            public void onFailure(Call<SearchResultItem> call, Throwable t) {

            }
        });
    }
}
