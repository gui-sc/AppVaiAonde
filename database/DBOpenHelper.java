package com.example.bancodedados.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bancodedados.database.model.ProdutoModel;
import com.example.bancodedados.database.model.UsuariosModel;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String BANCO_NOME = "unesc.db";
    private static final int VERSAO_BANCO = 1;

    public DBOpenHelper(Context context) {
        super(context, BANCO_NOME, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsuariosModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsuariosModel.DROP_TABLE);
        db.execSQL(UsuariosModel.CREATE_TABLE);
    }
}
