package com.example.vaiaonde.database.model;

public class ViagensModel {

    public static final String TABELA_NOME = "tb_viagens";
    public static final String
        COLUNA_ID = "_id",
        COLUNA_DIAS = "dias",
        COLUNA_PESSOAS = "pessoas",
        COLUNA_DESTINO = "destino",
        COLUNA_ATIVA = "ativa",
        COLUNA_USUARIO_ID = "usuario_id";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABELA_NOME +
                    " ( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_DIAS + " INTEGER NOT NULL, "
                    + COLUNA_PESSOAS + " INTEGER NOT NULL, "
                    + COLUNA_DESTINO + " TEXT NOT NULL, "
                    + COLUNA_ATIVA + " INTEGER DEFAULT 0, "
                    + COLUNA_USUARIO_ID + " INTEGER NOT NULL, "
                    + " FOREIGN KEY (" + COLUNA_USUARIO_ID + ") REFERENCES " + UsuariosModel.TABELA_NOME + "(_id) "
                    + " );";

    public static  final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABELA_NOME;

    private long id;
    private int dias;
    private int pessoas;
    private String destino;
    private boolean ativa;
    private UsuariosModel usuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getPessoas() {
        return pessoas;
    }

    public void setPessoas(int pessoas) {
        this.pessoas = pessoas;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public UsuariosModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosModel usuario_id) {
        this.usuario = usuario_id;
    }

    @Override
    public String toString() {
        return "tabela: " + this.TABELA_NOME +
                "id: " + this.id +
                "dias: " + this.dias +
                "pessoas: " + this.pessoas +
                "destino: " + this.destino +
                "ativa: " + this.ativa +
                "usuario_id: " + this.usuario.getId();
    }

}
