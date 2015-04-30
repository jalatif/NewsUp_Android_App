package app.newsup.com;

import java.util.List;

/**
 * Created by manshu on 4/29/15.
 */
public class NewsObject {
    private Long tweet_id;
    private String title;
    private String tweet;
    private String url;
    private String summary;
    private int sentiment;
    private int emoticon;
    private String source;
    private int favorites;
    private int retweets;
    private String images;
    private String hash_tags;
    private Double longitude, latitude;
    private String language;
    private List<CommentsObject> comments;
    //private String[] images;

    public NewsObject(Long tweet_id, String title, String tweet, String url, String summary,
                      int sentiment, int emoticon, String source, int favorites, int retweets,
                      String images, String hash_tags, Double longitude, Double latitude,
                      String language, List<CommentsObject> comments) {
        this.tweet_id = tweet_id;
        this.title = title;
        this.tweet = tweet;
        this.url = url;
        this.summary = summary;
        this.sentiment = sentiment;
        this.emoticon = emoticon;
        this.source = source;
        this.favorites = favorites;
        this.retweets = retweets;
        this.images = images;
        this.hash_tags = hash_tags;
        this.longitude = longitude;
        this.latitude = latitude;
        this.language = language;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "NewsObject{" +
                "tweet_id=" + tweet_id +
                ", title='" + title + '\'' +
                ", tweet='" + tweet + '\'' +
                ", url='" + url + '\'' +
                ", summary='" + summary + '\'' +
                ", sentiment=" + sentiment +
                ", emoticon=" + emoticon +
                ", source='" + source + '\'' +
                ", favorites=" + favorites +
                ", retweets=" + retweets +
                ", images='" + images + '\'' +
                ", hash_tags='" + hash_tags + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", language='" + language + '\'' +
                ", comments=" + comments +
                '}';
    }

    public Long getTweet_id() {
        return tweet_id;
    }

    public void setTweet_id(Long tweet_id) {
        this.tweet_id = tweet_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getSentiment() {
        return sentiment;
    }

    public void setSentiment(int sentiment) {
        this.sentiment = sentiment;
    }

    public int getEmoticon() {
        return emoticon;
    }

    public void setEmoticon(int emoticon) {
        this.emoticon = emoticon;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getHash_tags() {
        return hash_tags;
    }

    public void setHash_tags(String hash_tags) {
        this.hash_tags = hash_tags;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<CommentsObject> getComments() {
        return comments;
    }

    public void setComments(List<CommentsObject> comments) {
        this.comments = comments;
    }
}
