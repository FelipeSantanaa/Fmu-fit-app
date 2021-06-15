package com.example.appfmufit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Notificacoes extends AppCompatActivity {

    private int id_usuario;
    private ArrayList<HashMap<String, String>> itens;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes);

        getSupportActionBar().setTitle("Notificações");
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

        for(int i = 0; i < 2; i++){

            HashMap<String, String> lista_itens = new HashMap<String, String>();

            switch (i){
                case 0:
                    lista_itens.put("titulo", "AVISO");
                    lista_itens.put("desc", "Prezado aluno,\n\nFavor entrar em contato com seu instrutor referente ao agendamento de Cross Fit em 22/06/2021 às 08h00.\n");
                    break;

                case 1:
                    lista_itens.put("titulo", "DESCONTO IMPERDÍVEL!!!");
                    lista_itens.put("desc", "Já sabe da novidade de fim de ano?\n\nEntre em contato conosco no 0800-46780\n" +
                            "E realize o pagamento da rematrícula 2022 com até 45% de desconto!\n\nMas somente até 30/11!\n");
                    break;
            }

            lista.add(lista_itens);

        }

        return lista;

    }

    private void exibirPlanosNaLista(){

        itens = new ArrayList<HashMap<String, String>>();
        itens = carregaItens();

        listView = (ListView) findViewById(R.id.lsvNotificacoes);
        String[] from = new String[]{"titulo", "desc"};
        int[] to = new int[]{R.id.title_notification, R.id.desc_notification};

        listView.setAdapter(new SimpleAdapter(this, itens, R.layout.layout_lvw_notificacoes, from, to));
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