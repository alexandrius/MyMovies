package turtlecat.mymovies.api;

import java.util.ArrayList;

import turtlecat.mymovies.bean.SearchResultItem;

/**
 * Created by Alex on 3/22/2016.
 */
public class RuntimeCache {
    private static RuntimeCache instance;
    private ArrayList<SearchResultItem> searchedMovies;
    private int searchResultCount;

    public static void setInstance(RuntimeCache instance) {
        RuntimeCache.instance = instance;
    }

    public int getSearchResultCount() {
        return searchResultCount;
    }

    public void setSearchResultCount(int searchResultCount) {
        this.searchResultCount = searchResultCount;
    }

    public ArrayList<SearchResultItem> getSearchedMovies() {
        return searchedMovies;
    }

    public void setSearchedMovies(ArrayList<SearchResultItem> searchedMovies) {
        this.searchedMovies = searchedMovies;
    }

    public void addSearchedMovies(ArrayList<SearchResultItem> movies) {
        searchedMovies.addAll(movies);
    }


    public static RuntimeCache getInstance() {
        if (instance == null) instance = new RuntimeCache();
        return instance;
    }

    private RuntimeCache() {
    }
}
