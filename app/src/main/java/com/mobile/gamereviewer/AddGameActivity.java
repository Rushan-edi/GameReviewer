package com.mobile.gamereviewer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddGameActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName,editDesc;
    Spinner spinnerGenre;
    ImageView imageView;
    RatingBar ratingGame;
    Button  btn_add;
    Button  btn_choose;

    final int REQUEST_CODE_GALLERY=999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        Toolbar toolbar= (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        myDb=new DatabaseHelper(this);

        getSupportActionBar().setTitle("Gaming TechNote");
        toolbar.setTitleTextColor(Color.parseColor("#8c5ca4"));


       editName = (EditText)findViewById(R.id.game_name);
       editDesc = (EditText)findViewById(R.id.game_review);
       ratingGame = (RatingBar) findViewById(R.id.game_rate);
       spinnerGenre=(Spinner)findViewById(R.id.game_genre);
       btn_add=(Button)findViewById(R.id.btn_submit);
       btn_choose=(Button)findViewById(R.id.btn_choose);
       imageView= (ImageView)findViewById(R.id.upload_image);

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.requestPermissions(

                        AddGameActivity.this,
                        new String []{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY

                );
            }
        });

        addGameReview();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode ==REQUEST_CODE_GALLERY){

            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else {

                Toast.makeText(AddGameActivity.this,"You don't have permissions to access file location ",Toast.LENGTH_LONG).show();
            }
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==REQUEST_CODE_GALLERY && resultCode== RESULT_OK && data !=null){
            Uri uri =data.getData();

            try {

                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            }catch (FileNotFoundException e){

                    e.printStackTrace();
            }
        }

    }

    public void addGameReview(){


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    boolean result=  myDb.insertGameReview(editName.getText().toString(),editDesc.getText().toString(),
                            spinnerGenre.getSelectedItem().toString(),imageViewToByte(imageView),"Rushan ");

                    myDb.insertGameReviewRate(editName.getText().toString(),ratingGame.getNumStars(),"Rushan");

                        if(result == true){


                            Toast.makeText(AddGameActivity.this,"Game review Successfully Saved",Toast.LENGTH_LONG).show();

                        }
                        else {

                            Toast.makeText(AddGameActivity.this,"Game review Can not be saved, Try again",Toast.LENGTH_LONG).show();
                        }

                }catch (Exception e){
                    e.printStackTrace();
                }




            }
        });


    }
    protected byte[] imageViewToByte(ImageView image){


        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray=stream.toByteArray();
        return byteArray;


    }

}
