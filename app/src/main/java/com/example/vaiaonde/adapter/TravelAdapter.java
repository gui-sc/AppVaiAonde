package com.example.vaiaonde.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaiaonde.R;
import com.example.vaiaonde.ResumeActivity;
import com.example.vaiaonde.TravelActivity;
import com.example.vaiaonde.database.model.ViagensModel;

import java.util.ArrayList;

public class TravelAdapter extends BaseAdapter {

    private Activity activity;
    private Context context;
    private ArrayList<ViagensModel> travelList;

    public TravelAdapter(Activity activity, Context context){
        this.activity = activity;
        this.context = context;
    }

    public void setItems(ArrayList<ViagensModel> travelList){
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

        ViagensModel travel = travelList.get(position);
        System.out.println("testelog");
        TextView destino = convertView.findViewById(R.id.itemText);
        destino.setText(travel.getDestino());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(travel.getAtiva()){
                    intent = new Intent(context, TravelActivity.class);
                }else{
                    intent = new Intent(context, ResumeActivity.class);
                }
                intent.putExtra("travel", travel.getId());
                startActivity(context, intent, null);
                activity.finish();
            }
        });

        return convertView;
    }
}
