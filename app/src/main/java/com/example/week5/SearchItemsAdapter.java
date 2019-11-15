package com.example.week5;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class SearchItemsAdapter extends RecyclerView.Adapter<SearchItemsAdapter.MyViewHolder>
{

    protected void onCreate(Bundle savedInstanceState) {
  //      super.onCreateViewHolder( savedInstanceState ); //cant debug here


    }

    catpage_detail catpage_detail;
    Context context;
    ArrayList<catpage_detail> catData;

    public SearchItemsAdapter(Context context, ArrayList<catpage_detail> catData)
    {
        this.context=context;
        this.catData=catData;
    }



    @NonNull
    @Override
    public SearchItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catpage_detail, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int p) {



        Log.i("responseURL",catData.get(p).getImage());
        if(!catData.get(p).getImage().isEmpty())
        {

            Picasso.get()
                    .load(catData.get(p).getImage())
                    .into(myViewHolder.iv_image, new Callback() {
                        @Override
                        public void onSuccess() {
                            myViewHolder.pb_image.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
        }



        myViewHolder.cardview.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( context,MainActivity.class );
                i.putExtra( "image", catData.get(myViewHolder.getAdapterPosition() ).getImage());
                i.putExtra( "origin", catData.get(myViewHolder.getAdapterPosition()).getOrigin());
                i.putExtra( "lifespan", catData.get(myViewHolder.getAdapterPosition()).getLifeSpan());
                i.putExtra( "temperament", catData.get(myViewHolder.getAdapterPosition()).getTemperament());
                i.putExtra( "description", catData.get(myViewHolder.getAdapterPosition()).getDescription());
                i.putExtra( "wikipedia_url", catData.get(myViewHolder.getAdapterPosition()).getWikiLink());
                i.putExtra( "dog_friendly", catData.get(myViewHolder.getAdapterPosition()).getDogFriendliness());
                i.putExtra( "weight", catData.get(myViewHolder.getAdapterPosition()).getWeight());
                i.putExtra( "name", catData.get(myViewHolder.getAdapterPosition()).getName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return catData.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        View cardview;
        ImageView iv_image;
        SQLiteDatabase DB;
        DatabaseHelper db;
        ProgressBar pb_image;
        TextView tv_name, tv_origin, tv_weight, tv_lifeSpan, tv_temperament, tv_dogFriendly, tv_wikiLink, tv_description;

        MyViewHolder(View view)
        {
            super(view);

            iv_image = (ImageView) view.findViewById(R.id.iv_image);
            tv_name = (TextView)view.findViewById(R.id.tv_name);
            tv_origin = (TextView)view.findViewById(R.id.tv_origin);
            tv_weight = (TextView)view.findViewById(R.id.tv_weight);
            tv_lifeSpan = (TextView)view.findViewById(R.id.tv_lifeSpan);
            tv_temperament = (TextView)view.findViewById(R.id.tv_temperament);
            tv_dogFriendly = (TextView)view.findViewById(R.id.tv_dogFriendliness);
            tv_wikiLink = (TextView)view.findViewById(R.id.tv_wikiLink);
            tv_description = (TextView)view.findViewById(R.id.tv_description);
         //   db = new DatabaseHelper(this);
        }

        //press the Addtofavbtn, add the cat detail to fav. page
        public void Addtofavbtn(View view) {

            db.addData(tv_name.getText().toString());
           // Toast.makeText(catData.this,"Added!",Toast.LENGTH_SHORT).show();
        }
    }





}
