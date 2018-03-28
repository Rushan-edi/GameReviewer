package com.mobile.gamereviewer;

import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.mobile.gamereviewer.model.GameReview;
import com.mobile.gamereviewer.model.GameReviewModel;

import java.util.ArrayList;


public class ReviewListActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<GameReviewModel>list;
    GameReviewListAdapter adapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);


        String genre =getIntent().getStringExtra("genre");
//        Toast.makeText(ReviewListActivity.this,"Recived"+genre,Toast.LENGTH_SHORT).show();

        gridView =(GridView) findViewById(R.id.review_list);
        list = new ArrayList<>();
        adapter=new GameReviewListAdapter(this,R.layout.item,list);
        gridView.setAdapter(adapter);

        DatabaseHelper databaseHelper=new DatabaseHelper(this,DatabaseHelper.DATABASE_NAME,null,DatabaseHelper.DATABASE_VERSION);
        GameReview gameReview= databaseHelper.getGameReviewbyGenre(genre);
        list.clear();


    }
}
