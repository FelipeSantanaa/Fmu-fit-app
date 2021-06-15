package com.example.appfmufit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Planos extends AppCompatActivity {

    private int id_usuario;
    private ArrayList<HashMap<String, String>> itens;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planos);

        getSupportActionBar().setTitle("Planos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão

        //código para pegar o id do usuário logado
        Intent it = getIntent();
        Bundle b = it.getExtras();
        if(b != null){
            id_usuario = b.getInt("cd_usuario");
        }

        exibirPlanosNaLista();
    }

    private ArrayList<HashMap<String, String>> carregaItens(){

        ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();

        for(int i = 0; i < 5; i++){

            HashMap<String, String> lista_itens = new HashMap<String, String>();

            switch (i){
                case 0:
                    lista_itens.put("titulo", "Plano Anual");
                    lista_itens.put("desc", "R$ 99,00 / mês\nR$ 1188,00 em até 12x sem juros\n");
                    break;

                case 1:
                    lista_itens.put("titulo", "Plano Semestral");
                    lista_itens.put("desc", "R$ 120,00 / mês\nR$ 720,00 em até 6x sem juros\n");
                    break;

                case 2:
                    lista_itens.put("titulo", "Plano Quadrimestral");
                    lista_itens.put("desc", "R$ 140,00 / mês\nR$ 560,00 em até 4x sem juros\n");
                    break;

                case 3:
                    lista_itens.put("titulo", "Plano Bimestral");
                    lista_itens.put("desc", "R$ 170,00 / mês\nR$ 340,00 em até 2x sem juros\n");
                    break;

                case 4:
                    lista_itens.put("titulo", "Plano Mensal");
                    lista_itens.put("desc", "R$ 200,00 / mês\n");
                    break;

            }

            lista.add(lista_itens);

        }

        return lista;

    }

    private void exibirPlanosNaLista(){

        itens = new ArrayList<HashMap<String, String>>();
        itens = carregaItens();

        listView = (ListView) findViewById(R.id.lsvPlanos);
        String[] from = new String[]{"titulo", "desc"};
        int[] to = new int[]{R.id.head_title, R.id.description};

        listView.setAdapter(new SimpleAdapter(this, itens, R.layout.layout_lvw_planos, from, to));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent it = new Intent(this, MenuPrincipal.class);

                Bundle b = new Bundle();
                b.putInt("cd_usuario", id_usuario);
                it.putExtras(b);

                startActivity(it);

                 //O efeito ao ser pressionado do botão (no caso abre a activity)



                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

}