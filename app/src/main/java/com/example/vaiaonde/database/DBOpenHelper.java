package com.example.vaiaonde.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vaiaonde.database.model.GastoAereoModel;
import com.example.vaiaonde.database.model.GastoDiversosModel;
import com.example.vaiaonde.database.model.GastoGasolinaModel;
import com.example.vaiaonde.database.model.GastoHospedagemModel;
import com.example.vaiaonde.database.model.GastoRefeicoesModel;
import com.example.vaiaonde.database.model.UsuariosModel;
import com.example.vaiaonde.database.model.ViagensModel;


public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String BANCO_NOME = "unesc.db";
    private static final int VERSAO_BANCO = 1;

    public DBOpenHelper(Context context) {
        super(context, BANCO_NOME, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(UsuariosModel.CREATE_TABLE);
        db.execSQL(ViagensModel.CREATE_TABLE);
        db.execSQL(GastoAereoModel.CREATE_TABLE);
        db.execSQL(GastoDiversosModel.CREATE_TABLE);
        db.execSQL(GastoGasolinaModel.CREATE_TABLE);
        db.execSQL(GastoGasolinaModel.CREATE_TABLE);
        db.execSQL(GastoRefeicoesModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsuariosModel.DROP_TABLE);
        db.execSQL(ViagensModel.DROP_TABLE);
        db.execSQL(GastoAereoModel.DROP_TABLE);
        db.execSQL(GastoDiversosModel.DROP_TABLE);
        db.execSQL(GastoGasolinaModel.DROP_TABLE);
        db.execSQL(GastoGasolinaModel.DROP_TABLE);
        db.execSQL(GastoRefeicoesModel.DROP_TABLE);

        db.execSQL(UsuariosModel.CREATE_TABLE);
        db.execSQL(ViagensModel.CREATE_TABLE);
        db.execSQL(GastoAereoModel.CREATE_TABLE);
        db.execSQL(GastoDiversosModel.CREATE_TABLE);
        db.execSQL(GastoGasolinaModel.CREATE_TABLE);
        db.execSQL(GastoGasolinaModel.CREATE_TABLE);
        db.execSQL(GastoRefeicoesModel.CREATE_TABLE);
    }
}
