package com.example.vaiaonde.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vaiaonde.database.DBOpenHelper;
import com.example.vaiaonde.database.model.GastoDiversosModel;
import com.example.vaiaonde.database.model.ViagensModel;

import java.util.ArrayList;

public class GastoDiversosDAO extends AbstrataDao {

    private final String[] colunas = {
            GastoDiversosModel.COLUNA_ID,
            GastoDiversosModel.COLUNA_VIAGEM_ID,
            GastoDiversosModel.COLUNA_DESCRICAO,
            GastoDiversosModel.COLUNA_VALOR,
            GastoDiversosModel.COLUNA_UTILIZADO
    };
    private Context context;
    public GastoDiversosDAO(Context context) {
        db_helper = new DBOpenHelper(context);
        this.context = context;
    }

    public long Insert(GastoDiversosModel gastoDiversos) {

        long linhasAfetadas = -1;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(GastoDiversosModel.COLUNA_VIAGEM_ID, gastoDiversos.getViagemId());
            values.put(GastoDiversosModel.COLUNA_DESCRICAO, gastoDiversos.getDescricao());
            values.put(GastoDiversosModel.COLUNA_VALOR, gastoDiversos.getValor());
            values.put(GastoDiversosModel.COLUNA_UTILIZADO, gastoDiversos.getUtilizado());

            linhasAfetadas = db.insert(
                    GastoDiversosModel.TABELA_NOME,
                    null,
                    values
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long Update(GastoDiversosModel gastoDiversos) {

        long linhasAfetadas = -1;

        try {
            Open();

            ContentValues values = new ContentValues();

            values.put(GastoDiversosModel.COLUNA_VIAGEM_ID, gastoDiversos.getViagemId());
            values.put(GastoDiversosModel.COLUNA_DESCRICAO, gastoDiversos.getDescricao());
            values.put(GastoDiversosModel.COLUNA_VALOR, gastoDiversos.getValor());
            values.put(GastoDiversosModel.COLUNA_UTILIZADO, gastoDiversos.getUtilizado());

            // Atualiza por Id
            linhasAfetadas = db.update(
                    GastoDiversosModel.TABELA_NOME,
                    values,
                    GastoDiversosModel.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(gastoDiversos.getId())}
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long Delete(GastoDiversosModel gastoDiversos) {

        long linhasAfetadas = -1;

        try {
            Open();

            // Deleta por Id
            linhasAfetadas = db.delete(
                    GastoDiversosModel.TABELA_NOME,
                    GastoDiversosModel.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(gastoDiversos.getId())}
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public ArrayList<GastoDiversosModel> SelectAll(ViagensModel viagem) {

        ArrayList<GastoDiversosModel> lista = new ArrayList<>();

        try {
            Open();
            Cursor cursor = db.query(
                    GastoDiversosModel.TABELA_NOME,
                    colunas,
                    GastoDiversosModel.COLUNA_VIAGEM_ID + " = ?",
                    new String[]{String.valueOf(viagem.getId())},
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {
                    GastoDiversosModel gastoDiversos = new GastoDiversosModel();
                    gastoDiversos.setViagemId(new ViagensDAO(context).selectById(cursor.getLong(1)).getId());
                    gastoDiversos.setDescricao(cursor.getString(2));
                    gastoDiversos.setValor(cursor.getDouble(3));
                    gastoDiversos.setUtilizado(cursor.getInt(4));
                    lista.add(gastoDiversos);

                    cursor.moveToNext();
                }

            }
        } finally {
            Close();
        }

        return lista;
    }
}

