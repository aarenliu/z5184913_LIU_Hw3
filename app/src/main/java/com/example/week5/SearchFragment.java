package com.example.week5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    RecyclerView serachRe;
    DatabaseHelper db;
    ArrayList<catpage_detail> catData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_search, container, false );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
         db = new DatabaseHelper(getContext()); //cant debug here
         catData = db.getdata();
        SearchItemsAdapter SearchItemsAdapter = new SearchItemsAdapter(getContext(), catData ); //cant debug here
        serachRe.setAdapter( SearchItemsAdapter ); //cant debug here


    }
}

