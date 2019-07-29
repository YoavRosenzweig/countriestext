package com.project.yoavr.countries;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdpterListContry extends RecyclerView.Adapter<AdpterListContry.AdpterViewHolder> {

    private Context mContext;
    private ArrayList<Item> contryArrayList;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        void OnItenClick(int position);
    }
    public void setItemClickListener(OnItemClickListener listener){
        itemClickListener=listener;
    }

    public  AdpterListContry (Context context,ArrayList<Item> arrayList ){
        mContext=context;
        contryArrayList=arrayList;
    }

    @Override
    public AdpterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(mContext).inflate(R.layout.listcountry,parent,false);
        return new AdpterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdpterViewHolder holder, int position) {
        Item choosetItem=contryArrayList.get(position);
        String choosename=choosetItem.getEname();
        String chosenative=choosetItem.getEnativeName();

        holder.Textname.setText(choosename);
        holder.TextNative.setText(chosenative);

    }

    @Override
    public int getItemCount() {
        return contryArrayList.size();
    }

    public class AdpterViewHolder extends RecyclerView.ViewHolder {

        public TextView Textname;
        public TextView TextNative;

        public AdpterViewHolder(View itemView) {
            super(itemView);
            Textname = itemView.findViewById(R.id.name);
            TextNative = itemView.findViewById(R.id.native_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener !=null)
                    {
                        int position =getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            itemClickListener.OnItenClick(position);
                        }
                    }
                }
            });
        }
    }
}
