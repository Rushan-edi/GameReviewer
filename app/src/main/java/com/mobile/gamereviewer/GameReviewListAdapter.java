package com.mobile.gamereviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.gamereviewer.model.GameReviewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rushan on 3/28/2018.
 */

public class GameReviewListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<GameReviewModel> itemlist;

    public GameReviewListAdapter(Context context, int layout, ArrayList<GameReviewModel> itemlist) {
        this.context = context;
        this.layout = layout;
        this.itemlist = itemlist;
    }

    @Override
    public int getCount() {
        return itemlist.size();
    }

    @Override
    public Object getItem(int position) {
        return itemlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private  class  ViewHolder{

            ImageView item_img;
            TextView item_txt;

    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder =new ViewHolder();

        if(row == null){

            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(layout,null);


            holder.item_txt =(TextView)row.findViewById(R.id.item_txt);
            holder.item_img =(ImageView) row.findViewById(R.id.item_img);
            row.setTag(holder);
        }
        else {


            holder=(ViewHolder)row.getTag();
        }
        GameReviewModel gameReview= itemlist.get(position);

        holder.item_txt.setText(gameReview.getGame_name());

//        byte[] gameImage=gameReview.getGame_img();
//
//        Bitmap bitmap= BitmapFactory.decodeByteArray(gameImage,0,gameImage.length);
//        Log.e("IMAGE REV",bitmap.toString());
//        holder.item_img.setImageBitmap(bitmap);
        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(holder.item_img);

        return row;
    }
}
