package turtlecat.mymovies.api;

import android.content.Intent;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import turtlecat.mymovies.MyApp;
import turtlecat.mymovies.bean.SearchResultHolder;
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
                if (pageIndex == 1) {
                    RuntimeCache.getInstance().setSearchResultCount(response.body().getTotalResults());
                    RuntimeCache.getInstance().setSearchedMovies(response.body().getSearch());
                } else {
                    RuntimeCache.getInstance().addSearchedMovies(response.body().getSearch());
                }

                Intent intent = new Intent(K.MOVIES_LOADED);
                app.sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<SearchResultHolder> call, Throwable t) {
                Tools.showToast(app, "Error happened");
            }
        });
    }
}
