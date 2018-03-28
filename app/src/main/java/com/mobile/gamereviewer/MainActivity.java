package com.mobile.gamereviewer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar= (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Gaming TechNote");
        toolbar.setTitleTextColor(Color.parseColor("#8c5ca4"));

        mainGrid=(GridLayout)findViewById(R.id.mainGrid);

        //set event
//        setSingleEvent(mainGrid);

        setToogleEvent(mainGrid);


    }

    private void setToogleEvent(GridLayout mainGrid) {

        //loop all childitem of main grid

        for (int i=0;i<mainGrid.getChildCount();i++){

            final CardView cardView=(CardView)mainGrid.getChildAt(i);
            final int finalI = i;

            final String genrevalue = String.valueOf(cardView.getTag());

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this,"Moving..",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),AddGameActivity.class);
                    intent.putExtra("genre",genrevalue);
                    startActivity(intent);


                }
            });
        }


    }

    private void setSingleEvent(GridLayout mainGrid) {

        //loop all childitem of main grid
        for (int i=0;i<mainGrid.getChildCount();i++){

            CardView cardView=(CardView)mainGrid.getChildAt(i);
            final int finalI= i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(MainActivity.this,"Clicked Index"+finalI,Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}
