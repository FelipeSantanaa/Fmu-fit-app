package com.example.appfmufit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.rtoshiro.util.format.MaskFormatter;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.pattern.MaskPattern;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class TelaCadastro extends AppCompatActivity {

    private EditText nome, rg, cpf, dt_nascimento, cel, tel_com, email, senha, c_senha;
    private Button btnSalvar, btnVisualizar;
    private RadioGroup rdgSexo;
    private Usuario u;
    Conexao myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);
        myDb = new Conexao(this);

        getSupportActionBar().setTitle("Perfil");

        nome = findViewById(R.id.txtNome);
        rg = findViewById(R.id.txtRG);
        cpf = findViewById(R.id.txtCPF);
        dt_nascimento = findViewById(R.id.txtDataNasc);
        cel = findViewById(R.id.txtCelular);
        tel_com = findViewById(R.id.txtTelComercial);
        email = findViewById(R.id.txtEmail);
        senha = findViewById(R.id.txtSenha);
        c_senha = findViewById(R.id.txtConfirmaSenha);
        rdgSexo = findViewById(R.id.rdgSexo);
        btnSalvar = findViewById(R.id.btnCadastrar);
        //btnVisualizar = findViewById(R.id.btnVisualizar);

        // personaliza cor do botão
        btnSalvar.setBackgroundResource(R.color.blue_button);
        //btnVisualizar.setBackgroundResource(R.color.blue_button);

        mascararCampos();
        salvarUsuario();
        //visualizarUsuarios();
    }

    private void mascararCampos(){

        // MÁSCARA PARA O CAMPO RG
        SimpleMaskFormatter masc_rg = new SimpleMaskFormatter("NN.NNN.NNN-N");
        MaskTextWatcher txt_masc_rg = new MaskTextWatcher(rg, masc_rg);
        rg.addTextChangedListener(txt_masc_rg);

        // MÁSCARA PARA O CAMPO DATA DE NASCIMENTO
        MaskPattern mp03 = new MaskPattern("[0-3]");
        MaskPattern mp09 = new MaskPattern("[0-9]");
        MaskPattern mp01 = new MaskPattern("[0-1]");

        MaskFormatter masc_dt_nasc = new MaskFormatter("[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]");
        masc_dt_nasc.registerPattern(mp01);
        masc_dt_nasc.registerPattern(mp03);
        masc_dt_nasc.registerPattern(mp09);

        MaskTextWatcher txt_masc_dt_nasc = new MaskTextWatcher(dt_nascimento, masc_dt_nasc);
        dt_nascimento.addTextChangedListener(txt_masc_dt_nasc);

        // MÁSCARA PARA O CAMPO CPF
        SimpleMaskFormatter masc_cpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher txt_masc_cpf = new MaskTextWatcher(cpf, masc_cpf);
        cpf.addTextChangedListener(txt_masc_cpf);

        // MÁSCARA PARA O CAMPO CELULAR
        SimpleMaskFormatter masc_cel = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher txt_masc_cel = new MaskTextWatcher(cel, masc_cel);
        cel.addTextChangedListener(txt_masc_cel);

        // MÁSCARA PARA O CAMPO TEL COMERCIAL
        SimpleMaskFormatter masc_tel_com = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher txt_masc_tel_com = new MaskTextWatcher(tel_com, masc_tel_com);
        tel_com.addTextChangedListener(txt_masc_tel_com);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão

    }

    public void salvarUsuario(){

        btnSalvar.setOnClickListener(
            new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    u = new Usuario();
                    int op = rdgSexo.getCheckedRadioButtonId();

                    u.setNome(nome.getText().toString());
                    u.setDt_nascimento(dt_nascimento.getText().toString());
                    u.setRg(rg.getText().toString());
                    u.setCpf(cpf.getText().toString());

                    if(op == R.id.rbMasc){
                        u.setSexo("M");
                    }
                    else if(op == R.id.rbFem){
                        u.setSexo("F");
                    }

                    u.setCel(cel.getText().toString());
                    u.setTel_com(tel_com.getText().toString());
                    u.setEmail(email.getText().toString());
                    u.setSenha(senha.getText().toString());

                    if(!senha.getText().toString().equals("") && !c_senha.getText().toString().equals("") && c_senha.getText().toString().equals(senha.getText().toString()) && !email.getText().toString().equals(null)){

                        boolean r = myDb.inserirUsuario(u);

                        if(r == true){
                            Toast t = Toast.makeText(TelaCadastro.this, "USUÁRIO CADASTRADO", Toast.LENGTH_SHORT);
                            t.show();
                            Intent it = new Intent(TelaCadastro.this, Login.class);
                            startActivity(it);
                            finishAffinity();
                        }
                        else {
                            Toast t = Toast.makeText(TelaCadastro.this, "ERRO", Toast.LENGTH_SHORT);
                            t.show();
                        }

                    }

                    else if(c_senha.getText().toString().equals("") || senha.getText().toString().equals("") || email.getText().toString().equals("")){
                        Toast t = Toast.makeText(TelaCadastro.this, "E-MAIL E SENHA NÃO PODEM SER NULOS!", Toast.LENGTH_SHORT);
                        t.show();
                    }

                    else if(!c_senha.getText().toString().equals(senha)){
                        Toast t = Toast.makeText(TelaCadastro.this, "SENHAS NÃO CONFEREM!", Toast.LENGTH_SHORT);
                        t.show();
                    }
                }

            }
        );

    }

    public void visualizarUsuarios(){
        btnVisualizar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase db = myDb.getWritableDatabase();
                        Cursor c = db.rawQuery("select * from usuario", null);
                        if(c.getCount() == 0){
                            Toast t = Toast.makeText(TelaCadastro.this, "NÃO HÁ DADOS PARA VISUALIZAR", Toast.LENGTH_SHORT);
                            t.show();
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while(c.moveToNext()){
                            if(c.isFirst()) {
                                buffer.append("ID: " + c.getString(0) + " - " + c.getString(1) + "\n" +
                                        "Data de Nascimento: " + c.getString(2) + "\n" +
                                        "RG: " + c.getString(3) + "\n" +
                                        "CPF: " + c.getString(4) + "\n" +
                                        "Sexo: " + c.getString(5) + "\n" +
                                        "Celular: " + c.getString(6) + "\n" +
                                        "E-mail: " + c.getString(8));
                            }

                            else{
                                buffer.append("\n\nID: " + c.getString(0) + " - " +  c.getString(1) + "\n" +
                                        "Data de Nascimento: " + c.getString(2) + "\n" +
                                        "RG: " + c.getString(3) + "\n" +
                                        "CPF: " + c.getString(4) + "\n" +
                                        "Sexo: " + c.getString(5) + "\n" +
                                        "Celular: " + c.getString(6) + "\n" +
                                        "E-mail: " + c.getString(8));
                            }
                        }

                        showMessage("USUÁRIO", buffer.toString());
                    }
                }


        );
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Login.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }
}