package turtlecat.mymovies.api;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import turtlecat.mymovies.MyApp;
import turtlecat.mymovies.bean.SearchResultHolder;
import turtlecat.mymovies.utils.Tools;

/**
 * Created by Alex on 3/19/2016.
 */
@EBean
public class RI {

    @App
    MyApp app;

    public void searchMovie(String name, int pageIndex) {
        Call<SearchResultHolder> call = app.getOmdbService().searchMovie(name, pageIndex);
        call.enqueue(new Callback<SearchResultHolder>() {
            @Override
            public void onResponse(Call<SearchResultHolder> call, Response<SearchResultHolder> response) {
                Tools.log(response.body());
            }

            @Override
            public void onFailure(Call<SearchResultHolder> call, Throwable t) {
                Tools.showToast(app, "Error happened");
            }
        });
    }
}
