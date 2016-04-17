package turtlecat.mymovies.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import turtlecat.mymovies.bean.SearchResultHolder;
import turtlecat.mymovies.bean.SearchResultItem;
import turtlecat.mymovies.utils.K;

/**
 * Created by Alex on 3/19/2016.
 */
public interface OMDbService {
    @GET("/")
    Call<SearchResultHolder> searchMovie(@Query(K.SEARCH_PARAM) String searchQuery, @Query(K.PAGE_PARAM) int pageIndex);

    @GET("/")
    Call<SearchResultItem> getMovieDetailed(@Query(K.ID_PARAM) String id, @Query(K.PLOT_PARAM) String plotType);
}
