package turtlecat.mymovies.bean;


/**
 * Created by Alex on 3/19/2016.
 */

public class SearchResultItem {
    private String Title;
    private String Year;
    private String imdbID;
    private String Poster;
    private String type;
    private String Rated;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Actors;
    private String Plot;
    private String Language;
    private String County;
    private String Awards;
    private String Metascore;
    private String imdbRating;
    private String imdbVotes;
    private String Type;
    private String Response;


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SearchResultItem{" +
                "Title='" + Title + '\'' +
                ", Year='" + Year + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", Poster='" + Poster + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
