package com.project.yoavr.countries;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements AdpterListContry.OnItemClickListener {

    public static final String ENGLISHNAME="englishname";
    public static final String NATIVEHNAME="nativename";

    private TextView title;
    private Double areasize=0.0;
    private RecyclerView mRecycleview;
    private AdpterListContry mAdpterList;
    private ArrayList<Item> itemArrayList;
    private ArrayList<Item> sizearaylist;
    private RequestQueue mRequestQueue;
    private Button alpa;
    private Button size;
    private int temp=1,sizecounter=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mRecycleview =findViewById(R.id.recycler);
        mRecycleview.setHasFixedSize(true);
        mRecycleview.setLayoutManager(new LinearLayoutManager(this));
        itemArrayList=new ArrayList<>();
        sizearaylist=new ArrayList<>();
        mRequestQueue =Volley.newRequestQueue(this);
        title=findViewById(R.id.titlemain);
        alpa=findViewById(R.id.Z_a);
        size=findViewById(R.id.size);
       // AskJason();
        Ask(temp);
        alpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp++;
                Ask(temp);

            }
        });
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizecounter++;
                Order(sizecounter);
            }
        });

    }

    private void AskJason(){
        String url="https://restcountries.eu/rest/v2/all";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("");

                            for (int i = 0; i <jsonArray.length() ; i++) {

                                    JSONObject jasonname=jsonArray.getJSONObject(i);

                                    String Englishname=jasonname.getString("name");
                                    String NativeName=jasonname.getString("NativeName");

                                    itemArrayList.add(new Item(Englishname,NativeName));

                                mAdpterList=new AdpterListContry(MainActivity.this,itemArrayList);
                                mRecycleview.setAdapter(mAdpterList);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    private void Ask(final int click){
        itemArrayList.clear();
        String url="https://restcountries.eu/rest/v2/all";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if ((click % 2) == 0) {
                            title.setText("countries in order Z-A");
                            for (int i = response.length(); i > 0; i--) {
                                try {
                                    JSONObject jasonname = (JSONObject) response.get(i);

                                    String Englishname = jasonname.getString("name");
                                    String NativeName = jasonname.getString("nativeName");
                                    itemArrayList.add(new Item(Englishname, NativeName));
                                    mAdpterList = new AdpterListContry(MainActivity.this, itemArrayList);
                                    mRecycleview.setAdapter(mAdpterList);
                                    mAdpterList.setItemClickListener(MainActivity.this);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            title.setText("countries in order A-Z");
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jasonname = (JSONObject) response.get(i);

                                    String Englishname = jasonname.getString("name");
                                    String NativeName = jasonname.getString("nativeName");
                                    itemArrayList.add(new Item(Englishname, NativeName));

                                    mAdpterList = new AdpterListContry(MainActivity.this, itemArrayList);
                                    mRecycleview.setAdapter(mAdpterList);
                                    mAdpterList.setItemClickListener(MainActivity.this);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(jsonArrayRequest);
    }

    private void Order(final int counter){
        sizearaylist.clear();
        String url="https://restcountries.eu/rest/v2/all";
        JsonArrayRequest jsonObjectRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jasonname = (JSONObject) response.get(i);

                                String Englishname = jasonname.getString("name");
                                String NativeName = jasonname.getString("nativeName");
                                if(jasonname.isNull("area")){
                                     areasize = 0.0;
                                }else {
                                    areasize = jasonname.getDouble("area");
                                }
                                sizearaylist.add(new Item(Englishname, NativeName, areasize));
                            }
                            if(counter%2==0) {
                                title.setText("countries in order Small-Big");
                                for (int i = 0; i < sizearaylist.size(); i++) {
                                    Collections.sort(sizearaylist, new Comparator<Item>() {
                                        @Override
                                        public int compare(Item o1, Item o2) {
                                            mAdpterList = new AdpterListContry(MainActivity.this, sizearaylist);
                                            mRecycleview.setAdapter(mAdpterList);
                                            mAdpterList.setItemClickListener(MainActivity.this);
                                            return o1.getSize().compareTo(o2.getSize());

                                        }
                                    });

                                }
                            }else {
                                title.setText("countries in order Big-small");
                                for (int i = 0; i < sizearaylist.size(); i++) {
                                    Collections.sort(sizearaylist, new Comparator<Item>() {
                                        @Override
                                        public int compare(Item o1, Item o2) {
                                            mAdpterList = new AdpterListContry(MainActivity.this, sizearaylist);
                                            mRecycleview.setAdapter(mAdpterList);
                                            mAdpterList.setItemClickListener(MainActivity.this);
                                            return o2.getSize().compareTo(o1.getSize());

                                        }
                                    });

                                }
                            }
                        }

                           catch(JSONException e){
                                e.printStackTrace();

                            }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }


    @Override
    public void OnItenClick(int position) {
        Intent intent=new Intent(MainActivity.this,ChoosenCountry.class);
        Item clickcountry= itemArrayList.get(position);
        intent.putExtra(ENGLISHNAME,clickcountry.getEname());
        intent.putExtra(NATIVEHNAME,clickcountry.getEnativeName());
        startActivity(intent);
    }

}
