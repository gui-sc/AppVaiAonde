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
import com.example.vaiaonde.TravelActivity;
import com.example.vaiaonde.database.model.GastoDiversosModel;
import com.example.vaiaonde.database.model.ViagensModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.function.Function;

public class OtherItemAdapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private ArrayList<GastoDiversosModel> otherItemsList;
    private Function<GastoDiversosModel, GastoDiversosModel> function;

    public OtherItemAdapter(Activity activity, Context context, Function<GastoDiversosModel, GastoDiversosModel> function){
        this.activity = activity;
        this.context = context;
        this.function = function;
    }

    public void setItems(ArrayList<GastoDiversosModel> otherItemsList){
        this.otherItemsList = otherItemsList;
    }

    @Override
    public int getCount() {
        return this.otherItemsList != null ? this.otherItemsList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return this.otherItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.otherItemsList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.others_item_listview, parent, false);
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        GastoDiversosModel gasto = otherItemsList.get(position);
        TextView txtName = convertView.findViewById(R.id.txtName);
        txtName.setText(gasto.getDescricao());
        TextView txtValue = convertView.findViewById(R.id.txtValue);
        txtValue.setText(decimalFormat.format(gasto.getValor()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                function.apply(gasto);
            }
        });

        return convertView;
    }
}
