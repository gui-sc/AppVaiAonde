package com.example.vaiaonde.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vaiaonde.database.DBOpenHelper;
import com.example.vaiaonde.database.model.GastoHospedagemModel;
import com.example.vaiaonde.database.model.ViagensModel;

import java.util.ArrayList;

public class GastoHospedagemDAO extends AbstrataDao {

    private final String[] colunas = {
            GastoHospedagemModel.COLUNA_ID,
            GastoHospedagemModel.COLUNA_VIAGEM_ID,
            GastoHospedagemModel.COLUNA_CUSTO_NOITE,
            GastoHospedagemModel.COLUNA_NOITES,
            GastoHospedagemModel.COLUNA_QUARTOS,
            GastoHospedagemModel.COLUNA_UTILIZADO
    };
    private Context context;
    public GastoHospedagemDAO(Context context) {
        this.context = context;
        db_helper = new DBOpenHelper(context);
    }

    public long Insert(GastoHospedagemModel gastoHospedagem) {

        long linhasAfetadas = -1;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(GastoHospedagemModel.COLUNA_VIAGEM_ID, gastoHospedagem.getViagem().getId());
            values.put(GastoHospedagemModel.COLUNA_CUSTO_NOITE, gastoHospedagem.getCusto_noite());
            values.put(GastoHospedagemModel.COLUNA_NOITES, gastoHospedagem.getNoites());
            values.put(GastoHospedagemModel.COLUNA_QUARTOS, gastoHospedagem.getQuartos());
            values.put(GastoHospedagemModel.COLUNA_UTILIZADO, gastoHospedagem.getUtilizado());

            linhasAfetadas = db.insert(
                    GastoHospedagemModel.TABELA_NOME,
                    null,
                    values
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long Update(GastoHospedagemModel gastoHospedagem) {

        long linhasAfetadas = -1;

        try {
            Open();

            ContentValues values = new ContentValues();

            values.put(GastoHospedagemModel.COLUNA_VIAGEM_ID, gastoHospedagem.getViagem().getId());
            values.put(GastoHospedagemModel.COLUNA_CUSTO_NOITE, gastoHospedagem.getCusto_noite());
            values.put(GastoHospedagemModel.COLUNA_NOITES, gastoHospedagem.getNoites());
            values.put(GastoHospedagemModel.COLUNA_QUARTOS, gastoHospedagem.getQuartos());
            values.put(GastoHospedagemModel.COLUNA_UTILIZADO, gastoHospedagem.getUtilizado());

            // Atualiza por Id
            linhasAfetadas = db.update(
                    GastoHospedagemModel.TABELA_NOME,
                    values,
                    GastoHospedagemModel.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(gastoHospedagem.getId())}
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long Delete(GastoHospedagemModel gastoHospedagem) {

        long linhasAfetadas = -1;

        try {
            Open();

            // Deleta por Id
            linhasAfetadas = db.delete(
                    GastoHospedagemModel.TABELA_NOME,
                    GastoHospedagemModel.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(gastoHospedagem.getId())}
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public GastoHospedagemModel SelectByViagem(ViagensModel viagem){
        GastoHospedagemModel gasto = new GastoHospedagemModel();
        gasto.setId(0);
        gasto.setViagem(viagem);
        gasto.setNoites(0);
        gasto.setQuartos(0);
        gasto.setCusto_noite(0);
        try {
            Open();
            Cursor cursor = db.query(
                    GastoHospedagemModel.TABELA_NOME,
                    colunas,
                    GastoHospedagemModel.COLUNA_VIAGEM_ID + " = ?",
                    new String[]{String.valueOf(viagem.getId())},
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                GastoHospedagemModel gastoHospedagem = new GastoHospedagemModel();
                gastoHospedagem.setId(cursor.getLong(0));
                gastoHospedagem.setViagem(viagem);
                gastoHospedagem.setCusto_noite(cursor.getDouble(2));
                gastoHospedagem.setNoites(cursor.getInt(3));
                gastoHospedagem.setQuartos(cursor.getInt(4));
                gastoHospedagem.setUtilizado(cursor.getInt(5));

                return gastoHospedagem;
            }
        } finally {
            Close();
        }
        return gasto;
    }

    public ArrayList<GastoHospedagemModel> SelectAll() {

        ArrayList<GastoHospedagemModel> lista = new ArrayList<GastoHospedagemModel>();
        GastoHospedagemModel gastoHospedagem = new GastoHospedagemModel();

        try {
            Open();
            Cursor cursor = db.query(
                    GastoHospedagemModel.TABELA_NOME,
                    colunas,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {
                    gastoHospedagem.setId(cursor.getLong(0));
                    gastoHospedagem.setViagem(new ViagensDAO(context).selectById(cursor.getLong(1)));
                    gastoHospedagem.setCusto_noite(cursor.getDouble(2));
                    gastoHospedagem.setNoites(cursor.getInt(3));
                    gastoHospedagem.setQuartos(cursor.getInt(4));
                    gastoHospedagem.setUtilizado(cursor.getInt(5));
                    lista.add(gastoHospedagem);

                    cursor.moveToNext();
                }

            }
        } finally {
            Close();
        }

        return lista;
    }
}
