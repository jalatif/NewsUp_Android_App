package app.newsup.com;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by manshu on 4/29/15.
 */
public class NewsDatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "NewsUpDB";

    // Tables related to tweets
    private static final String TABLE_TWEETS = "TweetStore";
    private static final String TABLE_TWEET_COMMENTS = "TweetComments";

    public NewsDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TWEETS_TABLE = "CREATE TABLE " + TABLE_TWEETS +
                "(tweet_id INTEGER PRIMARY KEY, title TEXT, tweet TEXT, url TEXT, summary TEXT," +
                " sentiment INTEGER, emoticon INTEGER, source TEXT, favorite " +
                "INTEGER, retweet INTEGER, images TEXT, hash_tags TEXT, longitude REAL, " +
                "latitude REAL)";

        String CREATE_TWEET_COMMENTS_TABLE = "CREATE TABLE " + TABLE_TWEET_COMMENTS +
                "(comment_id INTEGER, comment TEXT, sentiment INTEGER, emoticon INTEGER, " +
                "tweet_id INTEGER)";

        db.execSQL(CREATE_TWEETS_TABLE);
        db.execSQL(CREATE_TWEET_COMMENTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWEETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWEETS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

//    // Adding new contact
//    void addContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName()); // Contact Name
//        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
//
//        // Inserting Row
//        db.insert(TABLE_CONTACTS, null, values);
//        db.close(); // Closing database connection
//    }

}
