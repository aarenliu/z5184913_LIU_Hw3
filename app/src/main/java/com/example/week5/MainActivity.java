package com.example.week5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase DB;
    DatabaseHelper DatabaseHelper1;
    EditText et_search;
    RecyclerView rv_searchItems;
    ProgressBar pb_progressBar;
    SearchItemsAdapter searchItemsAdapter;
    catpage_detail catDetails;
    ArrayList<catpage_detail> catData;
    String imageURL="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper1 = new DatabaseHelper(this);
        setContentView(R.layout.activity_main);
        getViews();
        setListeners();

        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_na);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });

//      bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

//        SearchFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new SearchFragment()).commit();

//        SearchFragment
          BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;

                        switch (item.getItemId()){
                            case R.id.nav_favorites:
                                selectedFragment = new FavoritesFragment();
                                break;
                            case R.id.nav_search:
                                selectedFragment = new SearchFragment();
                                break;
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

                        return true;

                    }
                };
    }



     public void getViews()
     {
         et_search=(EditText)findViewById(R.id.et_search);
         rv_searchItems=(RecyclerView)findViewById(R.id.rv_searchItems);


         catData=new ArrayList<>();

         searchItemsAdapter=new SearchItemsAdapter(this, catData);
         rv_searchItems.setLayoutManager(new LinearLayoutManager(this));
         rv_searchItems.addItemDecoration(new DividerItemDecoration(rv_searchItems.getContext(), DividerItemDecoration.VERTICAL));
         rv_searchItems.setAdapter(searchItemsAdapter);
     }

    public void setListeners()
    {
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(checkInternetConnection(MainActivity.this))
                {
                    if(et_search.getText().toString().length()>0)
                    {
                        getCatDetails(et_search.getText().toString());
                        pb_progressBar.setVisibility( View.VISIBLE);
                    }
                }
                else
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }

    private void getCatDetails(String data)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, " https://api.thecatapi.com/v1/breeds/search?q="+data,null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    catDetails=new catpage_detail();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        catDetails.setName(jsonObject.getString("name"));
                        catDetails.setImage(jsonObject.getString("id"));
                        catDetails.setWeight(jsonObject.getString("weight"));
                        catDetails.setTemperament(jsonObject.getString("temperament"));
                        catDetails.setOrigin(jsonObject.getString("origin"));
                        catDetails.setLifeSpan(jsonObject.getString("life_span"));
                        catDetails.setWikiLink(jsonObject.getString("wikipedia_url"));
                        catDetails.setDogFriendliness(jsonObject.getString("dog_friendly"));
                        catDetails.setDescription(jsonObject.getString("description"));

                        catData.add(catDetails);



                        searchItemsAdapter.notifyDataSetChanged();
                        pb_progressBar.setVisibility(View.INVISIBLE);
                    }


                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }


        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                if(error instanceof NoConnectionError)
                {
                    //error msg - internet problem
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map getHeaders() throws AuthFailureError
            {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("x-api-key", "d0bec58d-a2e6-4dca-846a-32eb96c68541");
                return headers;
            }

        };

  /*      StringRequest stringRequest = new StringRequest(); // can't debug here as well
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }
            @Override
            public int getCurrentRetryCount() {
                return 30000;
            }
            @Override
            public void retry(VolleyError error) throws VolleyError { }
        });*/
     //   Volley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest); //cant debug here
    }

    public static boolean checkInternetConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connMgr != null;
        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }

    public String getImageURL(String data)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.thecatapi.com/v1/images/search?breed_ids="+data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
                    Log.i("responseGetImage", response);

                    JSONArray jsonArray=new JSONArray(response);
                    if(jsonArray.length()>0)
                    {
                        imageURL=jsonArray.getJSONObject(0).getString("url");
                    }

                } catch (Exception e)
                {
                    Log.i("responseError", e.toString());
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                if(error instanceof NoConnectionError)
                {
                    //error msg - internet problem
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }){

            @Override
            public Map getHeaders() throws AuthFailureError
            {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("x-api-key", "d0bec58d-a2e6-4dca-846a-32eb96c68541");
                return headers;
            }

        };

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }
            @Override
            public int getCurrentRetryCount() {
                return 30000;
            }
            @Override
            public void retry(VolleyError error) throws VolleyError { }
        });
//        Volley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        return imageURL;
    }



    }

