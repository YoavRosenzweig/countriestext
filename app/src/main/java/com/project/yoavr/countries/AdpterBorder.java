package com.project.yoavr.countries;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdpterBorder extends RecyclerView.Adapter<AdpterBorder.AdpterViewHolder> {

    private Context mcontext;
    private ArrayList<BorderCountry> countryArrayList;

    public  AdpterBorder(Context context, ArrayList<BorderCountry> borderCountries) {
        mcontext = context;
        countryArrayList = borderCountries;
    }

    @Override
    public AdpterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(mcontext).inflate(R.layout.borderscountries,parent,false);
        return new AdpterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdpterViewHolder holder, int position) {
        BorderCountry choosetItem=countryArrayList.get(position);
        String choosename=choosetItem.getCountry();

        holder.countryname.setText(choosename);
    }

    @Override
    public int getItemCount() {
        return countryArrayList.size();
    }

    public class AdpterViewHolder extends RecyclerView.ViewHolder {

        public TextView countryname;

        public AdpterViewHolder(View itemView) {
            super(itemView);
            countryname = itemView.findViewById(R.id.bordername);
        }
    }

}
