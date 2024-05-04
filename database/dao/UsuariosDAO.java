package com.example.bancodedados.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.bancodedados.database.DBOpenHelper;
import com.example.bancodedados.database.model.ProdutoModel;
import com.example.bancodedados.database.model.UsuariosModel;

import java.util.ArrayList;

public class UsuariosDAO extends AbstrataDao {

    private final String[] colunas = {
            UsuariosModel.COLUNA_ID,
            UsuariosModel.COLUNA_EMAIL,
            UsuariosModel.COLUNA_SENHA,
    };

    public UsuariosDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long Insert(UsuariosModel usuario) {

        long linhasAfetadas = -1;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(UsuariosModel.COLUNA_EMAIL, usuario.getEmail());
            values.put(UsuariosModel.COLUNA_SENHA, usuario.getSenha());

            linhasAfetadas = db.insert(
                    UsuariosModel.TABELA_NOME,
                    null,
                    values
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long Update(UsuariosModel usuario) {

        long linhasAfetadas = -1;

        try {
            Open();

            ContentValues values = new ContentValues();

            values.put(UsuariosModel.COLUNA_EMAIL, usuario.getEmail());
            values.put(UsuariosModel.COLUNA_SENHA, usuario.getSenha());

            //Atualiza por Id
            linhasAfetadas = db.update(
                    UsuariosModel.TABELA_NOME,
                    values,
                    ProdutoModel.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(usuario.getId())}
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long Delete(UsuariosModel usuario) {

        long linhasAfetadas = -1;

        try {
            Open();

            //Deleta por Id
            linhasAfetadas = db.delete(
                    UsuariosModel.TABELA_NOME,
                    UsuariosModel.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(usuario.getId())}
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public ArrayList<UsuariosModel> SelectAll() {

        ArrayList<UsuariosModel> lista = new ArrayList<UsuariosModel>();
        UsuariosModel usuario = new UsuariosModel();

        try {
            Open();
            Cursor cursor = db.query(
                    UsuariosModel.TABELA_NOME,
                    colunas,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {
                    usuario.setId(cursor.getLong(0));
                    usuario.setEmail(cursor.getString(1));
                    usuario.setSenha(cursor.getString(2));
                    lista.add(usuario);

                    cursor.moveToNext();
                }

            }
        } finally {
            Close();
        }

        return lista;
    }
}
