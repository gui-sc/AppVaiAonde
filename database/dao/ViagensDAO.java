package com.example.bancodedados.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Path;

import com.example.bancodedados.database.DBOpenHelper;
import com.example.bancodedados.database.model.ViagensModel;

import java.util.ArrayList;

public class ViagensDAO extends AbstrataDao {

    private final String[] colunas = {
            ViagensModel.COLUNA_ID,
            ViagensModel.COLUNA_DESTINO,
            ViagensModel.COLUNA_PESSOAS,
            ViagensModel.COLUNA_DIAS,
            ViagensModel.COLUNA_ATIVA,
            ViagensModel.COLUNA_USUARIO_ID
    };

    public ViagensDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long Insert(ViagensModel viagem) {

        long linhasAfetadas = -1;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(ViagensModel.COLUNA_DESTINO, viagem.getDestino());
            values.put(ViagensModel.COLUNA_PESSOAS, viagem.getPessoas());
            values.put(ViagensModel.COLUNA_DIAS, viagem.getDias());
            values.put(ViagensModel.COLUNA_ATIVA, viagem.getAtiva());
            values.put(ViagensModel.COLUNA_USUARIO_ID, viagem.getUsuario_id());

            linhasAfetadas = db.insert(
                    ViagensModel.TABELA_NOME,
                    null,
                    values
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long Update(ViagensModel viagem) {

        long linhasAfetadas = -1;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(ViagensModel.COLUNA_DESTINO, viagem.getDestino());
            values.put(ViagensModel.COLUNA_PESSOAS, viagem.getPessoas());
            values.put(ViagensModel.COLUNA_DIAS, viagem.getDias());
            values.put(ViagensModel.COLUNA_ATIVA, viagem.getAtiva());
            values.put(ViagensModel.COLUNA_USUARIO_ID, viagem.getUsuario_id());

            linhasAfetadas = db.update(
                    ViagensModel.TABELA_NOME,
                    values,
                    ViagensModel.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(viagem.getId())}
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long Delete(ViagensModel viagem) {

        long linhasAfetadas = -1;

        try{
            Open();

            linhasAfetadas = db.delete(
                    ViagensModel.TABELA_NOME,
                    ViagensModel.COLUNA_ID + " = ?",
                    new String[] {String.valueOf(viagem.getId())}
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public ArrayList<ViagensModel> SelectAll() {

        ViagensModel viagem = new ViagensModel();
        ArrayList<ViagensModel> lista = new ArrayList<ViagensModel>();

        try{
            Open();

            Cursor cursor = db.query(
                    ViagensModel.TABELA_NOME,
                    colunas,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                while(!cursor.isAfterLast()) {
                    viagem.setId(cursor.getLong(0));
                    viagem.setDestino(cursor.getString(1));
                    viagem.setPessoas(cursor.getInt(2));
                    viagem.setDias(cursor.getInt(3));
                    viagem.setAtiva(cursor.getInt(4));
                    viagem.setUsuario_id(cursor.getLong(5));
                    lista.add(viagem);

                    cursor.moveToNext();
                }

            }
        } finally {
            Close();
        }

        return lista;
    }
}
