package com.example.vaiaonde.database.dao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vaiaonde.database.DBOpenHelper;
import com.example.vaiaonde.database.model.GastoRefeicoesModel;

import java.util.ArrayList;

public class GastoRefeicoesDAO extends AbstrataDao {

    private final String[] colunas = {
            GastoRefeicoesModel.COLUNA_ID,
            GastoRefeicoesModel.COLUNA_VIAGEM_ID,
            GastoRefeicoesModel.COLUNA_CUSTO_REFEICAO,
            GastoRefeicoesModel.COLUNA_REFEICOES_DIA,
            GastoRefeicoesModel.COLUNA_UTILIZADO
    };
    private Context context;
    public GastoRefeicoesDAO(Context context) {
        this.context = context;
        db_helper = new DBOpenHelper(context);
    }

    public long Insert(GastoRefeicoesModel gastoRefeicao) {

        long linhasAfetadas = -1;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(GastoRefeicoesModel.COLUNA_VIAGEM_ID, gastoRefeicao.getViagem().getId());
            values.put(GastoRefeicoesModel.COLUNA_CUSTO_REFEICAO, gastoRefeicao.getCusto_refeicao());
            values.put(GastoRefeicoesModel.COLUNA_REFEICOES_DIA, gastoRefeicao.getRefeicoes_dia());
            values.put(GastoRefeicoesModel.COLUNA_UTILIZADO, gastoRefeicao.getUtilizado());

            linhasAfetadas = db.insert(
                    GastoRefeicoesModel.TABELA_NOME,
                    null,
                    values
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long Update(GastoRefeicoesModel gastoRefeicao) {

        long linhasAfetadas = -1;

        try {
            Open();

            ContentValues values = new ContentValues();

            values.put(GastoRefeicoesModel.COLUNA_VIAGEM_ID, gastoRefeicao.getViagem().getId());
            values.put(GastoRefeicoesModel.COLUNA_CUSTO_REFEICAO, gastoRefeicao.getCusto_refeicao());
            values.put(GastoRefeicoesModel.COLUNA_REFEICOES_DIA, gastoRefeicao.getRefeicoes_dia());
            values.put(GastoRefeicoesModel.COLUNA_UTILIZADO, gastoRefeicao.getUtilizado());

            // Atualiza por Id
            linhasAfetadas = db.update(
                    GastoRefeicoesModel.TABELA_NOME,
                    values,
                    GastoRefeicoesModel.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(gastoRefeicao.getId())}
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public long Delete(GastoRefeicoesModel gastoRefeicao) {

        long linhasAfetadas = -1;

        try {
            Open();

            // Deleta por Id
            linhasAfetadas = db.delete(
                    GastoRefeicoesModel.TABELA_NOME,
                    GastoRefeicoesModel.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(gastoRefeicao.getId())}
            );
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public ArrayList<GastoRefeicoesModel> SelectAll() {

        ArrayList<GastoRefeicoesModel> lista = new ArrayList<>();
        GastoRefeicoesModel gastoRefeicao = new GastoRefeicoesModel();

        try {
            Open();
            Cursor cursor = db.query(
                    GastoRefeicoesModel.TABELA_NOME,
                    colunas,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {
                    gastoRefeicao.setId(cursor.getLong(0));
                    gastoRefeicao.setViagem(new ViagensDAO(context).selectById(cursor.getLong(1)));;
                    gastoRefeicao.setCusto_refeicao(cursor.getDouble(2));
                    gastoRefeicao.setRefeicoes_dia(cursor.getInt(3));
                    gastoRefeicao.setUtilizado(cursor.getInt(4));
                    lista.add(gastoRefeicao);

                    cursor.moveToNext();
                }

            }
        } finally {
            Close();
        }

        return lista;
    }
}
