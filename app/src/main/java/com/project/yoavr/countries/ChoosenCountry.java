package com.project.yoavr.countries;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import static com.project.yoavr.countries.MainActivity.ENGLISHNAME;
import static com.project.yoavr.countries.MainActivity.NATIVEHNAME;

public class ChoosenCountry extends AppCompatActivity {

    public  String name;

    private RecyclerView mRecycleview;
    private AdpterListContry mAdpterList;
    private ArrayList<Item> borderArrayList;
    private RequestQueue mRequestQueue;
    private  ArrayList<String> listcountries;
    private AdpterListContry mAdpterList1;
    private ArrayList<Item> itemArrayList;
    String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosen_country);


        Intent intent=getIntent();
        name=intent.getStringExtra(ENGLISHNAME);
        String nativename=intent.getStringExtra(NATIVEHNAME);

        TextView englisgname=findViewById(R.id.chosenenglishgname);
        TextView nativchonenename=findViewById(R.id.chosnativegname);
        englisgname.setText(name);
        nativchonenename.setText(nativename);
        listcountries=new ArrayList<>();
        mRecycleview =findViewById(R.id.chosanrecycler);
        mRecycleview.setHasFixedSize(true);
        mRecycleview.setLayoutManager(new LinearLayoutManager(this));
        borderArrayList=new ArrayList<>();
        mRequestQueue =Volley.newRequestQueue(this);
        AskApi();

    }
    private void AskApi(){
        String url="https://restcountries.eu/rest/v2/name/"+name;
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i <response.length() ; i++) {
                            try {
                                JSONObject jsonname = (JSONObject) response.get(i);
                                JSONArray borders = jsonname.getJSONArray("borders");

                                for (int j = 0; j <borders.length()+1 ; j++) {
                                    if(j==borders.length()){
                                            Ask();
                                    }
                                        country = borders.getString(j);
                                        listcountries.add(country);
//                                        borderArrayList.add(new BorderCountry(country));
//                                        mAdpterList = new AdpterBorder(ChoosenCountry.this, borderArrayList);
//                                        mRecycleview.setAdapter(mAdpterList);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
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

    private void Ask(){
        for (int i = 0; i <listcountries.size() ; i++) {
            String url="http://restcountries.eu/rest/v2/alpha/"+listcountries.get(i);
            JsonObjectRequest requestbordercountry=new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String Englishname = response.getString("name");
                                String NativeName = response.getString("nativeName");

                                borderArrayList.add(new Item(Englishname,NativeName));
                                mAdpterList = new AdpterListContry(ChoosenCountry.this, borderArrayList);
                                mRecycleview.setAdapter(mAdpterList);

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
            mRequestQueue.add(requestbordercountry);
        }


    }

}
