package com.example.week5;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<CatViewHoolder> {


    private MainActivity context;
    private List<Listdata> dataList;


    public Adapter(MainActivity context, List<Listdata> DataList) {
        this.context = context;
        dataList = DataList;
    }


    @NonNull

    @Override
    public CatViewHoolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_layout, parent, false);

        return new CatViewHoolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHoolder holder, int p) {

        holder.cardview_name.setText(dataList.get(p).getViewName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, catpage_detail.class);
                i.putExtra("cat_name", dataList.get(holder.getAdapterPosition()).getViewName());
                context.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

}

class CatViewHoolder extends RecyclerView.ViewHolder {


    TextView cardview_name;
    CardView cardView;


    public CatViewHoolder(View foodView) {
        super(foodView);
        cardview_name = itemView.findViewById(R.id.cardview_name);
        cardView = itemView.findViewById(R.id.cardview);


    }


}
