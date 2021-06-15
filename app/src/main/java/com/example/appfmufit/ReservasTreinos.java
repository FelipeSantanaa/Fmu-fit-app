package com.example.appfmufit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ReservasTreinos extends AppCompatActivity {

    private Conexao con;
    private int id_usuario;
    private ListView listaTreinos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_treinos);

        //código para pegar o id do usuário logado
        Intent it = getIntent();
        Bundle b = it.getExtras();
        if (b != null) {
            id_usuario = b.getInt("cd_usuario");
        }

        // Personalizar cor do botão
        Button btnCadastrarTreino = (Button) findViewById(R.id.btnCadastrarTreino);
        btnCadastrarTreino.setBackgroundResource(R.color.blue_button);

        // Açõs na ActionBar
        getSupportActionBar().setTitle("Reservas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      // Ativar o botão
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

    public void chamarCadastroTreino(View v){
        Intent it = new Intent(this, CadastroTreino.class);

        Bundle b = new Bundle();
        b.putInt("cd_usuario", id_usuario);
        it.putExtras(b);
        startActivity(it);
        finishAffinity();

    }

    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar

                Intent it = new Intent(ReservasTreinos.this, MenuPrincipal.class);

                Bundle b = new Bundle();
                b.putInt("cd_usuario", id_usuario);
                it.putExtras(b);
                startActivity(it);

                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }
}