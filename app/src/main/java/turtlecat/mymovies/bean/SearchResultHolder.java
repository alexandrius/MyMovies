package turtlecat.mymovies.bean;

import java.util.ArrayList;


/**
 * Created by Alex on 3/19/2016.
 */

public class SearchResultHolder{
    private ArrayList<SearchResultItem> Search;
    private int totalResults;
    private String response;


    public ArrayList<SearchResultItem> getSearch() {
        return Search;
    }

    public void setSearch(ArrayList<SearchResultItem> search) {
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
