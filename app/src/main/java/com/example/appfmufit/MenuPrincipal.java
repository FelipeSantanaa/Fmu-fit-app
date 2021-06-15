package com.example.appfmufit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.adapters.ProgramAdapter;

import java.util.ArrayList;

public class MenuPrincipal extends AppCompatActivity {

    private ArrayList<String> itens = preencherLista();
    private ArrayAdapter<String> adapter;
    private ListView lista;
    private int id_usuario;

    private String[] lista_itens = new String[]{"Meu Perfil\n", "Reservar Treino\n", "Meus Treinos\n", "Planos\n", "Notificações\n", "Sair\n"};
    private int [] lista_img = new int[]{R.drawable.icon_perfil, R.drawable.icon_marcar_treinos, R.drawable.icon_treinos,
            R.drawable.icon_planos, R.drawable.icon_notificacao, R.drawable.icon_logout};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Mostrar o botão
        getSupportActionBar().setTitle("Menu");     // Título para ser exibido na sua Action Bar em frente à seta

        // código para pegar o id do usuário logado
        Intent it = getIntent();
        Bundle b = it.getExtras();
        if(b != null){
            id_usuario = b.getInt("cd_usuario");
        }

        exibirMenu();

    }

    private void exibirMenu(){

        lista = findViewById(R.id.lvwMenu);
        ProgramAdapter adapter = new ProgramAdapter(this, lista_itens, lista_img);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                switch (position){
                    case 0: chamarTelaAlteraCadastro();
                        break;

                    case 1: acessarReservasTreinos();
                        break;

                    case 2: visualizarMeusTreinos();
                        break;

                    case 3: visualizarPlanos();
                        break;

                    case 4: exibirNotificacoes();
                        break;

                    case 5: finish();
                        break;
                }
            }
        });

    }

    private ArrayList<String> preencherLista(){

        ArrayList<String> dados = new ArrayList<>();

        dados.add("MEU PERFIL");
        dados.add("RESERVAR TREINO");
        dados.add("MEUS TREINOS");
        dados.add("PLANOS");
        dados.add("NOTIFICAÇÕES");
        dados.add("SAIR");

        return dados;

    }

    private void chamarMenu(){

        lista = findViewById(R.id.lvwMenu);

        adapter = new ArrayAdapter<String>(MenuPrincipal.this, R.layout.layout_menu_principal, itens);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                switch (position){
                    case 0: chamarTelaAlteraCadastro();
                        break;

                    case 1: acessarReservasTreinos();
                        break;

                    case 2: visualizarMeusTreinos();
                        break;

                    case 3: visualizarPlanos();
                        break;

                    case 4: exibirNotificacoes();
                        break;

                    case 5: finish();
                        break;
                }
            }
        });
    }

    private void exibirNotificacoes(){
        Intent it = new Intent(this, Notificacoes.class);

        Bundle b = new Bundle();
        b.putInt("cd_usuario", id_usuario);
        it.putExtras(b);

        startActivity(it);
        finishAffinity();
    }

    private void visualizarPlanos(){
        Intent it = new Intent(MenuPrincipal.this, Planos.class);

        Bundle b = new Bundle();
        b.putInt("cd_usuario", id_usuario);
        it.putExtras(b);

        startActivity(it);
        finishAffinity();
    }

    private void visualizarMeusTreinos(){
        Intent it = new Intent(MenuPrincipal.this, MeusTreinos.class);

        Bundle b = new Bundle();
        b.putInt("cd_usuario", id_usuario);
        it.putExtras(b);

        startActivity(it);
        finishAffinity();
    }

    private void acessarReservasTreinos(){
        Intent it = new Intent(MenuPrincipal.this, ReservasTreinos.class);

        Bundle b = new Bundle();
        b.putInt("cd_usuario", id_usuario);
        it.putExtras(b);

        startActivity(it);
        finishAffinity();
    }

    private void chamarTelaAlteraCadastro(){
        Intent it = new Intent(MenuPrincipal.this, AlteraUsuario.class);

        Bundle b = new Bundle();
        b.putInt("cd_usuario", id_usuario);
        it.putExtras(b);

        startActivity(it);
        finishAffinity();
    }

    /*private void sair(){
        System.exit(0);
    }*/

}