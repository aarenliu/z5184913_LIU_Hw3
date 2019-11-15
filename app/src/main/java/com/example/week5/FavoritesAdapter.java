package com.example.week5;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder> {


    public class FavoritesFragment extends Fragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate( savedInstanceState );
            // db = new DatabaseHelper( this );
            catData = db.getdata();

    //        SearchItemsAdapter SearchItemsAdapter = new SearchItemsAdapter( this, catData ); //cant debug here
      //      favRe.setAdapter( SearchItemsAdapter ); //cant debug here


        }
  /*     @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate( R.layout.fragment_favorites, container, false );
        }
*/

        RecyclerView favRe;
        DatabaseHelper db;
        ArrayList<catpage_detail> catData;


    }

    Context context;
    ArrayList<catpage_detail> catData;

    public FavoritesAdapter(Context context, ArrayList<catpage_detail> catData) {
        this.context = context;
        this.catData = catData;
    }

    @NonNull
     @Override
     public FavoritesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_favorites, parent, false);

         FavoritesAdapter.MyViewHolder viewHolder = new FavoritesAdapter.MyViewHolder(view);
         return viewHolder;
     }

    @Override
    public void onBindViewHolder(@NonNull final FavoritesAdapter.MyViewHolder myViewHolder, final int p) {

        myViewHolder.tv_name.setText( catData.get( p ).getName() + "" );

    }

    @Override
    public int getItemCount() {
        return catData.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView( recyclerView );
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;

        MyViewHolder(View view) {
            super( view );


            tv_name = (TextView) view.findViewById( R.id.tv_name );

        }


    }
}

