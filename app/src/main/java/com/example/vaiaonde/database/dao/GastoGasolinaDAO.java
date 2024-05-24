package com.example.vaiaonde.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vaiaonde.database.DBOpenHelper;
import com.example.vaiaonde.database.model.GastoGasolinaModel;
import com.example.vaiaonde.database.model.ViagensModel;

import java.util.ArrayList;


public class GastoGasolinaDAO extends AbstrataDao {

    private final String[] colunas = {
            GastoGasolinaModel.COLUNA_ID,
            GastoGasolinaModel.COLUNA_VIAGEM_ID,
            GastoGasolinaModel.COLUNA_KM,
            GastoGasolinaModel.COLUNA_KM_LITRO,
            GastoGasolinaModel.COLUNA_CUSTO_LITRO,
            GastoGasolinaModel.COLUNA_TOTAL_VEICULOS,
            GastoGasolinaModel.COLUNA_UTILIZADO
    };
    private Context context;

    public GastoGasolinaDAO(Context context) {
        this.context = context;
        db_helper = new DBOpenHelper(context);
    }

    public long Insert(GastoGasolinaModel gastoGasolina) {

        long linhasAfetadas = -1;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(GastoGasolinaModel.COLUNA_VIAGEM_ID, gastoGasolina.getViagem().getId());
            values.put(GastoGasolinaModel.COLUNA_KM, gastoGasolina.getKm());
            values.put(GastoGasolinaModel.COLUNA_KM_LITRO, gastoGasolina.getKm_litro());
            values.put(GastoGasolinaModel.COLUNA_CUSTO_LITRO, gastoGasolina.getCusto_litro());
            values.put(GastoGasolinaModel.COLUNA_TOTAL_VEICULOS, gastoGasolina.getTotal_veiculos());
            values.put(GastoGasolinaModel.COLUNA_UTILIZADO, gastoGasolina.getUtilizado());

            linhasAfetadas = db.insert(
                    GastoGasolinaModel.TABELA_NOME,
                    null,
                    values
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long Update(GastoGasolinaModel gastoGasolina) {

        long linhasAfetadas = -1;

        try {
            Open();

            ContentValues values = new ContentValues();

            values.put(GastoGasolinaModel.COLUNA_VIAGEM_ID, gastoGasolina.getViagem().getId());
            values.put(GastoGasolinaModel.COLUNA_KM, gastoGasolina.getKm());
            values.put(GastoGasolinaModel.COLUNA_KM_LITRO, gastoGasolina.getKm_litro());
            values.put(GastoGasolinaModel.COLUNA_CUSTO_LITRO, gastoGasolina.getCusto_litro());
            values.put(GastoGasolinaModel.COLUNA_TOTAL_VEICULOS, gastoGasolina.getTotal_veiculos());
            values.put(GastoGasolinaModel.COLUNA_UTILIZADO, gastoGasolina.getUtilizado());

            // Atualiza por Id
            linhasAfetadas = db.update(
                    GastoGasolinaModel.TABELA_NOME,
                    values,
                    GastoGasolinaModel.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(gastoGasolina.getId())}
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long Delete(GastoGasolinaModel gastoGasolina) {

        long linhasAfetadas = -1;

        try {
            Open();

            // Deleta por Id
            linhasAfetadas = db.delete(
                    GastoGasolinaModel.TABELA_NOME,
                    GastoGasolinaModel.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(gastoGasolina.getId())}
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public GastoGasolinaModel SelectByViagem(ViagensModel viagensModel){
        GastoGasolinaModel gasto = new GastoGasolinaModel();
        gasto.setViagem(viagensModel);
        gasto.setId(0);
        gasto.setKm(0);
        gasto.setCusto_litro(0);
        gasto.setKm_litro(0);
        gasto.setTotal_veiculos(0);
        try {
            Open();
            Cursor cursor = db.query(
                    GastoGasolinaModel.TABELA_NOME,
                    colunas,
                    GastoGasolinaModel.COLUNA_VIAGEM_ID + " = ?",
                    new String[]{String.valueOf(viagensModel.getId())},
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                GastoGasolinaModel gastoGasolina = new GastoGasolinaModel();
                gastoGasolina.setId(cursor.getLong(0));
                gastoGasolina.setViagem(viagensModel);
                gastoGasolina.setKm(cursor.getInt(2));
                gastoGasolina.setKm_litro(cursor.getInt(3));
                gastoGasolina.setCusto_litro(cursor.getDouble(4));
                gastoGasolina.setTotal_veiculos(cursor.getInt(5));
                gastoGasolina.setUtilizado(cursor.getInt(6));
                return gastoGasolina;
            }
        } finally {
            Close();
        }
        return gasto;
    }

    public ArrayList<GastoGasolinaModel> SelectAll() {

        ArrayList<GastoGasolinaModel> lista = new ArrayList<>();
        GastoGasolinaModel gastoGasolina = new GastoGasolinaModel();

        try {
            Open();
            Cursor cursor = db.query(
                    GastoGasolinaModel.TABELA_NOME,
                    colunas,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {
                    gastoGasolina.setId(cursor.getLong(0));
                    gastoGasolina.setViagem(new ViagensDAO(context).selectById(cursor.getLong(1)));
                    gastoGasolina.setKm(cursor.getInt(2));
                    gastoGasolina.setKm_litro(cursor.getInt(3));
                    gastoGasolina.setCusto_litro(cursor.getDouble(4));
                    gastoGasolina.setTotal_veiculos(cursor.getInt(5));
                    gastoGasolina.setUtilizado(cursor.getInt(6));
                    lista.add(gastoGasolina);

                    cursor.moveToNext();
                }

            }
        } finally {
            Close();
        }

        return lista;
    }
}
