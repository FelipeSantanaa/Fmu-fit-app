package com.example.appfmufit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.MaskFormatter;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.pattern.MaskPattern;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.ArrayList;

public class CadastroTreino extends AppCompatActivity {

    private Treino treino;
    private Conexao con;
    private EditText data, hora;
    private Spinner lista_mod;
    private ImageView imgModalidade;
    private Button btnSalvar;
    private CalendarView calendario;

    private int id_usuario;
    private int[] v = {R.drawable.musculacao_2, R.drawable.jump, R.drawable.crossfit, R.drawable.spinning,
            R.drawable.zumba, R.drawable.muay_thai, R.drawable.boxe,
            R.drawable.judo, R.drawable.pilates, R.drawable.yoga};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_treino);

        Intent it = getIntent();
        Bundle b = it.getExtras();
        if(b != null){
            id_usuario = b.getInt("cd_usuario");
        }

        getSupportActionBar().setTitle("Agendar Treino");

        lista_mod = (Spinner) findViewById(R.id.listaMod);
        data = (EditText) findViewById(R.id.txtDataTreino);
        hora = (EditText) findViewById(R.id.txtHoraTreino);
        imgModalidade = (ImageView) findViewById(R.id.imgModalidade);
        btnSalvar = (Button) findViewById(R.id.btnSalvarTreino);

        // personaliza cor do Spinner
        lista_mod.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);

        //alterar cor do botão
        btnSalvar.setBackgroundResource(R.color.blue_button);

        preencherModalidade();
        mascararCampos();
        salvarTreino();

    }

    public void salvarTreino(){

        try {
            con = new Conexao(this);

            btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    treino = new Treino();

                    treino.setCd_modalidade(Integer.parseInt(String.valueOf(lista_mod.getSelectedItemId() + 1)));
                    treino.setCd_usuario(id_usuario);
                    treino.setData(data.getText().toString());
                    treino.setHora(hora.getText().toString());

                    boolean result = con.registrarTreino(treino);

                    if (result == true) {
                        Toast t = Toast.makeText(CadastroTreino.this, "TREINO AGENDADO PARA " + data.getText().toString() + " às " + hora.getText().toString(), Toast.LENGTH_SHORT);
                        t.show();

                        Intent it = new Intent(CadastroTreino.this, MeusTreinos.class);

                        Bundle b = new Bundle();
                        b.putInt("cd_usuario", id_usuario);
                        it.putExtras(b);
                        startActivity(it);

                        finishAffinity();
                    } else {
                        Toast t = Toast.makeText(CadastroTreino.this, "ERRO AO TENTAR AGENDAR TREINO", Toast.LENGTH_SHORT);
                        t.show();
                    }
                }
            });

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    public ArrayList<String> buscarModalidade(){

        ArrayList<String> mod = null;

        try {
            con = new Conexao(this);

            SQLiteDatabase db = con.getWritableDatabase();
            mod = new ArrayList<String>();

            Cursor c = db.rawQuery("SELECT * FROM modalidade", null);

            if (c.moveToFirst()) {
                do {
                    mod.add(c.getString(c.getColumnIndex("ds_modalidade")));
                } while (c.moveToNext());
            } else {
                mod = null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        finally {
            con.close();
        }

        return mod;
    }

    public void preencherModalidade(){

        ArrayList<String> mod = buscarModalidade();

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item, mod);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lista_mod.setAdapter(adapter);

        lista_mod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imgModalidade.setImageResource(v[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    private void mascararCampos(){

        // MÁSCARA PARA O CAMPO RG
        SimpleMaskFormatter masc_rg = new SimpleMaskFormatter("NN:NN");
        MaskTextWatcher txt_masc_rg = new MaskTextWatcher(hora, masc_rg);
        hora.addTextChangedListener(txt_masc_rg);

        // MÁSCARA PARA O CAMPO DATA
        MaskPattern mp03 = new MaskPattern("[0-3]");
        MaskPattern mp09 = new MaskPattern("[0-9]");
        MaskPattern mp01 = new MaskPattern("[0-1]");

        MaskFormatter masc_dt_nasc = new MaskFormatter("[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]");
        masc_dt_nasc.registerPattern(mp01);
        masc_dt_nasc.registerPattern(mp03);
        masc_dt_nasc.registerPattern(mp09);

        MaskTextWatcher txt_masc_dt_nasc = new MaskTextWatcher(data, masc_dt_nasc);
        data.addTextChangedListener(txt_masc_dt_nasc);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão

    }

    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar

                Intent it = new Intent(CadastroTreino.this,  MeusTreinos.class);

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