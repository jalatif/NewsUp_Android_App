package app.newsup.com;

/**
 * Created by manshu on 4/29/15.
 */
public class CommentsObject {
    private int comment_id;
    private String comment;
    private int sentiment;
    private int emoticon;

    public CommentsObject(int comment_id, String comment, int sentiment, int emoticon) {
        this.comment_id = comment_id;
        this.comment = comment;
        this.sentiment = sentiment;
        this.emoticon = emoticon;
    }

    @Override
    public String toString() {
        return "CommentsObject{" +
                "comment_id=" + comment_id +
                ", comment='" + comment + '\'' +
                ", sentiment=" + sentiment +
                ", emoticon=" + emoticon +
                '}';
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
}
