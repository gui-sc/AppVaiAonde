package com.example.vaiaonde.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vaiaonde.R;

import java.util.ArrayList;

public class TravelAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<TravelModel> travelList;

    public TravelAdapter(Activity activity){
        this.activity = activity;
    }

    public void setItems(ArrayList<TravelModel> travelList){
        this.travelList = travelList;
    }

    @Override
    public int getCount() {
        return this.travelList != null ? this.travelList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return this.travelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.item_listview, parent, false);
        }

        TravelModel travel = travelList.get(position);

        TextView destino = convertView.findViewById(R.id.itemText);
        destino.setText(travel.getDestino());

        return convertView;
    }
}
