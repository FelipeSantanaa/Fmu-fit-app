package com.example.appfmufit;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.adapters.AdapterListaTreinos;

import java.util.ArrayList;
import java.util.List;


public class MeusTreinos extends AppCompatActivity {

    private Conexao con;
    private int id_usuario;

    private ListView listaTreinos;
    private List<Treino> treinoList;
    private AdapterListaTreinos adapterListaTreinos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treinos);

        // Guarda o user da tela anterior
        Intent it = getIntent();
        Bundle b = it.getExtras();
        if (b != null) {
            id_usuario = b.getInt("cd_usuario");
        }

        // Ações na Action Bar
        getSupportActionBar().setTitle("Meus Treinos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      // Ativar o botão

        // buscar produtos do banco e preenche listView
        carregarTreinos();

    }

    public void carregarTreinos(){

            this.listaTreinos = (ListView) findViewById(R.id.lvwMeusTreinos);
            treinoList = getListaTreinos();

            this.adapterListaTreinos = new AdapterListaTreinos(MeusTreinos.this, this.treinoList);

            if(treinoList == null){
                Toast.makeText(this, "NÃO HÁ TREINOS AGENDADOS", Toast.LENGTH_SHORT).show();
            }

            else{
                this.listaTreinos.setAdapter(adapterListaTreinos);
                escolherAcaoAoClicarItem();
            }



    }

    public List<Treino> getListaTreinos(){

        List<Treino> treinoList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        String sql = "select t.cd_treino, m.ds_modalidade, m.cd_modalidade, t.dt_treino, t.hr_treino" +
                " from treino t, modalidade m" +
                " where t.cd_modalidade = m.cd_modalidade" +
                " and t.cd_usuario = " + id_usuario +
                " order by t.cd_treino";

        try{

            con = new Conexao(this);
            db = con.getReadableDatabase();
            cursor = db.rawQuery(sql, null);

            if(cursor.moveToFirst()){

                Treino treino = null;

                do{
                    treino = new Treino();
                    treino = new Treino(cursor.getInt(0), null, cursor.getInt(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(1));

                    treinoList.add(treino);

                } while (cursor.moveToNext());

            }

            else{
                treinoList = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }

        return treinoList;

    }

    public void escolherAcaoAoClicarItem(){

        Treino treinoSelecionado = null;

        this.listaTreinos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int posicao, long id) {

                Treino treinoSelecionado = (Treino) adapterListaTreinos.getItem(posicao);

                AlertDialog.Builder alerta = new AlertDialog.Builder(MeusTreinos.this, R.style.AlertDialogTheme);

                alerta.setTitle("Excluir Agendamento");
                alerta.setMessage("Deseja excluir o agendamento selecionado?");

                alerta.setNeutralButton("Cancelar", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                });

                alerta.setNegativeButton("Não", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                });

                alerta.setPositiveButton("Sim", (dialogInterface, i) -> {
                    boolean excluiuTreino = con.excluirAgendamentoTreino(treinoSelecionado.getCd_treino());
                    dialogInterface.cancel();

                    if(excluiuTreino){
                        Toast.makeText(MeusTreinos.this, "AGENDAMENTO EXCLUÍDO COM SUCESSO", Toast.LENGTH_SHORT).show();
                        adapterListaTreinos.removerTreinoDaLista(posicao);
                    }
                    else{
                        Toast.makeText(MeusTreinos.this, "ERRO AO EXCLUIR O AGENDAMENTO", Toast.LENGTH_SHORT).show();
                    }
                });

                alerta.create().show();

            }
        });

    }


    public void showMessage(String titulo, String msg){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(true);
        b.setTitle(titulo);
        b.setMessage(msg);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alerta = b.create();
        alerta.show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        // Botão adicional na ToolBar

        switch (item.getItemId()) {
            case android.R.id.home:  // ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar

                Intent it = new Intent(MeusTreinos.this,  MenuPrincipal.class);

                Bundle b = new Bundle();
                b.putInt("cd_usuario", id_usuario);
                it.putExtras(b);
                startActivity(it);

                finishAffinity();  // Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

            default:break;
        }
        return true;
    }

 }