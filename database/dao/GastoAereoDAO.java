package com.example.bancodedados.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import androidx.annotation.NonNull;

import com.example.bancodedados.database.DBOpenHelper;
import com.example.bancodedados.database.model.GastoAereoModel;

import java.util.ArrayList;

public class GastoAereoDAO extends AbstrataDao {

    private final String[] colunas = {
            GastoAereoModel.COLUNA_ID,
            GastoAereoModel.COLUNA_CUSTO_PESSOA,
            GastoAereoModel.COLUNA_ALUGUEL_VEICULO,
            GastoAereoModel.COLUNA_UTILIZADO,
            GastoAereoModel.COLUNA_VIAGEM_ID
    };

    public GastoAereoDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long Insert(GastoAereoModel gastoAereo) {

        long linhasAfetadas = -1;

        try {
            Open();

            ContentValues values = new ContentValues();

            values.put(GastoAereoModel.COLUNA_CUSTO_PESSOA, gastoAereo.getCusto_pessoa());
            values.put(GastoAereoModel.COLUNA_ALUGUEL_VEICULO, gastoAereo.getAluguel_veiculo());
            values.put(GastoAereoModel.COLUNA_UTILIZADO, gastoAereo.getUtilizado());
            values.put(GastoAereoModel.COLUNA_VIAGEM_ID, gastoAereo.getViagem_id());

            try {
                linhasAfetadas = db.insert(GastoAereoModel.TABELA_NOME, null, values);
            } catch(SQLException e){
                System.out.println("ERRO AO INSERIR NO BANCO: " + e);
            }
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long Update(GastoAereoModel gastoAereo) {

        long linhasAfetadas = -1;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(GastoAereoModel.COLUNA_CUSTO_PESSOA, gastoAereo.getCusto_pessoa());
            values.put(GastoAereoModel.COLUNA_ALUGUEL_VEICULO, gastoAereo.getAluguel_veiculo());
            values.put(GastoAereoModel.COLUNA_UTILIZADO, gastoAereo.getUtilizado());
            values.put(GastoAereoModel.COLUNA_VIAGEM_ID, gastoAereo.getViagem_id());

            linhasAfetadas = db.update(
                    GastoAereoModel.TABELA_NOME,
                    values,
                    GastoAereoModel.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(gastoAereo.getId())}
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long Delete(GastoAereoModel gastoAereo) {

        long linhasAfetadas = -1;

        try {
            Open();

            linhasAfetadas = db.delete(
                    GastoAereoModel.TABELA_NOME,
                    GastoAereoModel.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(gastoAereo.getId())}
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public ArrayList<GastoAereoModel> SelectAll() {

        ArrayList<GastoAereoModel> lista = new ArrayList<GastoAereoModel>();
        GastoAereoModel gastoAereo = new GastoAereoModel();

        try {
            Open();

            Cursor cursor = db.query(
                    GastoAereoModel.TABELA_NOME,
                    colunas,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {
                    gastoAereo.setId(cursor.getLong(0));
                    gastoAereo.setCusto_pessoa(cursor.getDouble(1));
                    gastoAereo.setAluguel_veiculo(cursor.getDouble(2));
                    gastoAereo.setUtilizado(cursor.getInt(3));
                    gastoAereo.setViagem_id(cursor.getLong(4));
                    lista.add(gastoAereo);

                    cursor.moveToNext();
                }

            }
            
        } finally {
            Close();
        }

        return lista;
    }

}