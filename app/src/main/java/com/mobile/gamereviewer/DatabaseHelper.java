package com.mobile.gamereviewer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobile.gamereviewer.model.GameReview;

/**
 * Created by Rushan on 3/27/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GameReviewer.db";



    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(GameReview.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GameReview.TABLE_NAME);
        // Create tables again
        onCreate(db);


    }

    public long insertGameReview(String gamereview) {


        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(GameReview.COLUMN_NAME, gamereview);
        values.put(GameReview.COLUMN_DESC, gamereview);
        values.put(GameReview.COLUMN_IMG, gamereview);
        values.put(GameReview.COLUMN_GENRE, gamereview);
        values.put(GameReview.COLUMN_ADDED_USER, gamereview);

        // insert row
        long id = db.insert(GameReview.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public  GameReview getGameReviewList(long id){

        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =db.query(GameReview.TABLE_NAME,new String[]{GameReview.COLUMN_ID,GameReview.COLUMN_NAME,
                        GameReview.COLUMN_DESC,GameReview.COLUMN_IMG,GameReview.COLUMN_ADDED_USER,
                        GameReview.COLUMN_ADDED_DATE},null,
                new String[]{String.valueOf(id)},null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare GameReview object

        GameReview gameReview= new GameReview(cursor.getInt(cursor.getColumnIndex(GameReview.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_DESC)),
                cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_IMG)),
                cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_GENRE)),
                cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_ADDED_DATE)),
                cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_ADDED_USER)));

        // close the db connection
        cursor.close();

        return gameReview;
    }

    public  GameReview getGameReviewbyID(long id){

        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =db.query(GameReview.TABLE_NAME,new String[]{GameReview.COLUMN_ID,GameReview.COLUMN_NAME,
                                GameReview.COLUMN_DESC,GameReview.COLUMN_IMG,GameReview.COLUMN_ADDED_USER,
                                GameReview.COLUMN_ADDED_DATE},GameReview.COLUMN_ID+"=?",
                                new String[]{String.valueOf(id)},null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare GameReview object

        GameReview gameReview= new GameReview(cursor.getInt(cursor.getColumnIndex(GameReview.COLUMN_ID)),
                                            cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_NAME)),
                                            cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_DESC)),
                                            cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_IMG)),
                                            cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_GENRE)),
                                            cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_ADDED_DATE)),
                                            cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_ADDED_USER)));

        // close the db connection
        cursor.close();

        return gameReview;
    }
    public  GameReview getGameReviewbyGenre(String genre){

        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =db.query(GameReview.TABLE_NAME,new String[]{GameReview.COLUMN_ID,GameReview.COLUMN_NAME,
                        GameReview.COLUMN_DESC,GameReview.COLUMN_IMG,GameReview.COLUMN_ADDED_USER,
                        GameReview.COLUMN_ADDED_DATE},GameReview.COLUMN_GENRE+"=?",
                new String[]{String.valueOf(genre)},null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare GameReview object

        GameReview gameReview= new GameReview(cursor.getInt(cursor.getColumnIndex(GameReview.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_DESC)),
                cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_IMG)),
                cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_GENRE)),
                cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_ADDED_DATE)),
                cursor.getString(cursor.getColumnIndex(GameReview.COLUMN_ADDED_USER)));

        // close the db connection
        cursor.close();

        return gameReview;
    }
}
