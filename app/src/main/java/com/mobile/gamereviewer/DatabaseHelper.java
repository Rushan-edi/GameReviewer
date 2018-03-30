package com.mobile.gamereviewer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobile.gamereviewer.model.GameReview;
import com.mobile.gamereviewer.model.GameReviewModel;

/**
 * Created by Rushan on 3/27/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GamereviewGG.db";


    public static final String TABLE_NAME = "game_review";
    public static final String TABLE_NAME_2 = "game_review_rate";

    public static final String COLUMN_ID = "game_id";
    public static final String COLUMN_NAME = "game_name";
    public static final String COLUMN_DESC = "game_desc";
    public static final String COLUMN_IMG = "game_img";
    public static final String COLUMN_GENRE = "game_genre";
    public static final String COLUMN_ADDED_DATE = "added_date";
    public static final String COLUMN_ADDED_USER = "added_user";
    public static final String COLUMN_RATE= "game_rate";




    public static  String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("
            +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COLUMN_NAME+" TEXT,"
            +COLUMN_DESC+" TEXT,"
            +COLUMN_IMG+" TEXT,"
            +COLUMN_GENRE+" TEXT,"
            +COLUMN_ADDED_DATE+" DATETIME DEFAULT CURRENT_TIMESTAMP,"
            +COLUMN_ADDED_USER+" TEXT)";



    public DatabaseHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);

        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        db.execSQL("DROP DATABASE IF EXISTS " + TABLE_NAME);

        db.execSQL("CREATE TABLE "+ TABLE_NAME + "("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +COLUMN_NAME+" TEXT," +COLUMN_DESC+" TEXT," +COLUMN_IMG+" BLOB," +COLUMN_GENRE+" TEXT," +COLUMN_ADDED_DATE+" DATETIME DEFAULT CURRENT_TIMESTAMP," +COLUMN_ADDED_USER+" TEXT)");
        db.execSQL("CREATE TABLE "+ TABLE_NAME_2 + "("+COLUMN_NAME+" TEXT," +COLUMN_RATE+" INTEGER,"  +COLUMN_ADDED_USER+ " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        // Create tables again
//        onCreate(db);


    }


    public boolean insertGameReview(String name,String desc,String genre,byte[] image,String user) {


        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_DESC,desc);
        values.put(COLUMN_IMG, image);
        values.put(COLUMN_GENRE, genre);
        values.put(COLUMN_ADDED_USER,user);

        // insert row
        long id = db.insert(TABLE_NAME, null, values);

        // close db connection
        db.close();

        if (id == -1){

            return false;
        }
        else {
            return true;
        }

//        // return newly inserted row id
//        return id;
    }
    public void insertGameReviewRate(String name,int rate,String user) {


        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME,name);
        values.put(COLUMN_RATE,rate);
        values.put(COLUMN_ADDED_USER,user);

        // insert row
        db.insert(TABLE_NAME_2, null, values);

        // close db connection
        db.close();


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
    public  Cursor getGameReviewbyGenre(String genre){

        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        String[] params = new String[]{ genre };
        String sql="SELECT * FROM "+ TABLE_NAME ;
//        String sql="SELECT * FROM "+ TABLE_NAME +" WHERE " +COLUMN_GENRE+"= ?";
        return db.rawQuery(sql,null);


    }
}
