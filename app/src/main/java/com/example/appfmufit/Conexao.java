package com.example.appfmufit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;

public class Conexao extends SQLiteOpenHelper {

    private static final String nm_bd = "dbafmu";
    private static final int versao = 6;

    public Conexao(Context c) {
        super(c, nm_bd, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS usuario (\n" +
                "    cd_usuario INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    nm_usuario TEXT NOT NULL,\n" +
                "    dt_nascimento TEXT,\n" +
                "    rg TEXT NOT NULL,\n" +
                "    cpf INTEGER NOT NULL,\n" +
                "    sexo TEXT,\n" +
                "    cel INTEGER NOT NULL,\n" +
                "    tel_com INTEGER,\n" +
                "    email TEXT NOT NULL,\n" +
                "    senha TEXT NOT NULL,\n" +
                "    UNIQUE(rg),\n" +
                "    UNIQUE(cpf),\n" +
                "    UNIQUE(email),\n" +
                "    CHECK(sexo in ('M', 'F'))\n" +
                ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS modalidade(" +
                "cd_modalidade INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ds_modalidade TEXT NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS treino(" +
                "cd_treino INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cd_modalidade INTEGER NOT NULL, " +
                "cd_usuario INTEGER NOT NULL, " +
                "ds_treino TEXT," +
                "dt_treino TEXT NOT NULL, " +
                "hr_treino TEXT NOT NULL,\n" +
                "CONSTRAINT fk_modalidade FOREIGN KEY(cd_modalidade) REFERENCES modalidade(cd_modalidade)," +
                "CONSTRAINT fk_usuario FOREIGN KEY(cd_usuario) REFERENCES usuario(cd_usuario));");

        db.execSQL("insert into modalidade(ds_modalidade) values('Musculação'), " +
                "('Jump'), " +
                "('Crossfit'), " +
                "('Spinning'), " +
                "('Zumba'), " +
                "('Muay Thai'), " +
                "('Boxe'), " +
                "('Judô')," +
                "('Pilates')," +
                "('Yoga');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS usuario;");
        db.execSQL("DROP TABLE IF EXISTS modalidade;");
        db.execSQL("DROP TABLE IF EXISTS treino;");
        onCreate(db);
    }

    public boolean registrarTreino(Treino t){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("cd_modalidade", t.getCd_modalidade());
        valores.put("cd_usuario", t.getCd_usuario());
        valores.put("dt_treino", t.getData());
        valores.put("hr_treino", t.getHora());

        long resultado = db.insert("treino", null, valores);
        db.close();

        if (resultado == -1) {
            return false;
        } else return true;

    }

    public boolean excluirAgendamentoTreino(long idTreino){

        SQLiteDatabase db = null;

        try{
            db = this.getWritableDatabase();

            db.delete("treino",
                    "cd_treino = ?",
                    new String[]{String.valueOf(idTreino)});


        } catch (Exception e){
            Log.d("MeusTreinos.class", "Não foi possível deletar o produto");
            return  false;

        } finally {
            db.close();
        }

        return true;
    }

    public boolean inserirUsuario(Usuario u) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("nm_usuario", u.getNome());
        valores.put("dt_nascimento", u.getDt_nascimento());
        valores.put("rg", u.getRg());
        valores.put("cpf", u.getCpf());
        valores.put("sexo", u.getSexo());
        valores.put("cel", u.getCel());
        valores.put("tel_com", u.getTel_com());
        valores.put("email", u.getEmail());
        valores.put("senha", u.getSenha());

        long resultado = db.insert("usuario", null, valores);
        db.close();

        if (resultado == -1) {
            return false;
        } else return true;

    }

    public boolean alterarUsuario(Usuario u, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("nm_usuario", u.getNome());
        valores.put("dt_nascimento", u.getDt_nascimento());
        valores.put("rg", u.getRg());
        valores.put("cpf", u.getCpf());
        valores.put("sexo", u.getSexo());
        valores.put("cel", u.getCel());
        valores.put("tel_com", u.getTel_com());
        valores.put("email", u.getEmail());
        valores.put("senha", u.getSenha());

        db.update("usuario",  valores,"cd_usuario = ?", new String[]{String.valueOf(id)});
        db.close();
        return true;

    }

    public Integer deletarTreinos(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("treino", "cd_usuario = ?", new String[]{String.valueOf(id)});
        db.close();
        return  result;
    }

    public Integer deletarUsuario(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("usuario", "cd_usuario = ?", new String[]{String.valueOf(id)});
        db.close();
        return  result;
    }
}