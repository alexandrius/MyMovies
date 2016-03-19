package turtlecat.mymovies;

import android.app.Application;
import android.content.Intent;

import org.androidannotations.annotations.EApplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import turtlecat.mymovies.api.OMDbService;
import turtlecat.mymovies.utils.K;

/**
 * Created by Alex on 3/19/2016.
 */
@EApplication
public class App extends Application {
    private OMDbService omdbService;

    public OMDbService getOmdbService() {
        return omdbService;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(K.OMDB_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        omdbService = retrofit.create(OMDbService.class);
    }

//    public void sendBroadcast(String action) {
//        Intent i = new Intent(K.MOVIES_LOADED);
//        sendBroadcast(i);
//    }
}
