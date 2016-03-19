package turtlecat.mymovies.bean;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by Alex on 3/19/2016.
 */

@RealmClass
public class SearchResultHolder extends RealmObject{
    private List<SearchResultItem> Search;
    private int totalResults;
    private String response;

    public List<SearchResultItem> getSearch() {
        return Search;
    }

    public void setSearch(List<SearchResultItem> search) {
        Search = search;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "SearchResultHolder{" +
                "Search=" + Search +
                ", totalResults=" + totalResults +
                ", response='" + response + '\'' +
                '}';
    }
}