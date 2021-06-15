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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.rtoshiro.util.format.MaskFormatter;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.pattern.MaskPattern;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class AlteraUsuario extends AppCompatActivity {

    private EditText nome, rg, cpf, dt_nascimento, cel, tel_com, email, senha_atual, nova_senha, confirma_senha, cd_usuario;
    private Button btnAtualizar, btnDeletar;
    private RadioButton rbFem, rbMasc;
    private RadioGroup rdgSexo;
    private Usuario u;
    Conexao myDb;
    private int id_usuario;
    private String senha1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera_usuario);

        //código para pegar o id do usuário logado
        Intent it = getIntent();
        Bundle b = it.getExtras();
        if(b != null){
            id_usuario = b.getInt("cd_usuario");
        }

        myDb = new Conexao(this);
        getSupportActionBar().setTitle("Perfil");

        cd_usuario = findViewById(R.id.txtID);
        nome = findViewById(R.id.txtNome);
        rg = findViewById(R.id.txtRG);
        cpf = findViewById(R.id.txtCPF);
        dt_nascimento = findViewById(R.id.txtDataNasc);
        cel = findViewById(R.id.txtCelular);
        tel_com = findViewById(R.id.txtTelComercial);
        email = findViewById(R.id.txtEmail);
        senha_atual = findViewById(R.id.txtSenhaAtual);
        nova_senha = findViewById(R.id.txtNovaSenha);
        confirma_senha = findViewById(R.id.txtConfirmaNovaSenha);
        rbFem = findViewById(R.id.rbFem);
        rbMasc = findViewById(R.id.rbMasc);
        rdgSexo = findViewById(R.id.rdgSexo);
        btnAtualizar = findViewById(R.id.btnAlterar);
        btnDeletar = findViewById(R.id.btnDeletar);

        // alterar cor do botão
        btnAtualizar.setBackgroundResource(R.color.blue_button);
        btnDeletar.setBackgroundResource(R.color.blue_button);

        mascararCampos();
        trazerDadosUsuario();
        deletarUsuario();
    }

    public void trazerDadosUsuario(){
        SQLiteDatabase db = myDb.getWritableDatabase();
        Cursor c = db.rawQuery("select * from usuario where cd_usuario = " + id_usuario, null);

        while (c.moveToNext()){
            cd_usuario.setText(c.getString(0));
            nome.setText(c.getString(1));
            dt_nascimento.setText(c.getString(2));
            rg.setText(c.getString(3));
            cpf.setText(c.getString(4));

            if(c.getString(5).equals("M")){
                rbMasc.setChecked(true);
            }
            else if(c.getString(5).equals("F")){
                rbFem.setChecked(true);
            }

            cel.setText(c.getString(6));
            tel_com.setText(c.getString(7));
            email.setText(c.getString(8));
            senha1 = c.getString(9);
        }

        btnAtualizar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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

                        if (!senha_atual.getText().toString().equals(senha1)){
                            Toast t = Toast.makeText(AlteraUsuario.this, "A SENHA DIGITADA NÃO CONFERE COM A SENHA ATUAL!", Toast.LENGTH_SHORT);
                            t.show();
                        }

                        else if (!confirma_senha.getText().toString().equals(nova_senha.getText().toString())){
                            Toast t = Toast.makeText(AlteraUsuario.this, "SENHAS NÃO CONFEREM", Toast.LENGTH_SHORT);
                            t.show();
                        }

                        else if ((!senha_atual.getText().toString().equals(null)) && (!nova_senha.getText().toString().equals(null)) && (!confirma_senha.getText().toString().equals(null))){

                            if((senha_atual.getText().toString().equals(senha1)) && confirma_senha.getText().toString().equals(nova_senha.getText().toString())){
                                u.setSenha(nova_senha.getText().toString());

                                boolean r = myDb.alterarUsuario(u, id_usuario);

                                if(r == true){
                                    Toast t = Toast.makeText(AlteraUsuario.this, "DADOS ATUALIZADOS!", Toast.LENGTH_SHORT);
                                    t.show();
                                    limpar();

                                    Intent it = new Intent(AlteraUsuario.this, MenuPrincipal.class);
                                    Bundle b = new Bundle();
                                    b.putInt("cd_usuario", id_usuario);
                                    it.putExtras(b);
                                    startActivity(it);

                                }

                                else {
                                    Toast t = Toast.makeText(AlteraUsuario.this, "NÃO FOI POSSÍVEL ATUALIZAR OS DADOS!", Toast.LENGTH_SHORT);
                                    t.show();
                                }
                            }
                        }

                        else if(senha_atual.getText().toString() != null && senha_atual.getText().toString().equals(senha1)){
                            u.setSenha(senha_atual.getText().toString());

                            boolean r = myDb.alterarUsuario(u, id_usuario);
                            if(r = true){
                                Toast t = Toast.makeText(AlteraUsuario.this, "DADOS ATUALIZADOS!", Toast.LENGTH_SHORT);
                                t.show();
                                limpar();

                                Intent it = new Intent(AlteraUsuario.this, MenuPrincipal.class);
                                Bundle b = new Bundle();
                                b.putInt("cd_usuario", id_usuario);
                                it.putExtras(b);
                                startActivity(it);
                            }
                            else {
                                Toast t = Toast.makeText(AlteraUsuario.this, "NÃO FOI POSSÍVEL ATUALIZAR OS DADOS!", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }

                    }
                }

        );


    }

    public void deletarUsuario(){
        btnDeletar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer linhaDeletada = myDb.deletarUsuario(id_usuario);
                        if(linhaDeletada > 0){
                            myDb.deletarTreinos(id_usuario);
                            Toast t = Toast.makeText(AlteraUsuario.this, "REGISTRO EXCLUÍDO!", Toast.LENGTH_SHORT);
                            t.show();
                            limpar();
                            Intent it = new Intent(AlteraUsuario.this, Login.class);
                            startActivity(it);
                            finishAffinity();
                        }

                        else{
                            Toast t = Toast.makeText(AlteraUsuario.this, "ERRO AO ATUALIZAR O REGISTRO!", Toast.LENGTH_SHORT);
                            t.show();
                        }

                    }
                }

        );
    }

    public  void limpar(){
        cd_usuario.setText("");
        nome.setText("");
        rg.setText("");
        cpf.setText("");
        dt_nascimento.setText("");
        rbFem.setChecked(false);
        rbMasc.setChecked(false);
        cel.setText("");
        tel_com.setText("");
        email.setText("");
        senha_atual.setText("");
        nova_senha.setText("");
        confirma_senha.setText("");
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

    public void showMessage(String titulo, String msg){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(false);
        b.setTitle(titulo);
        b.setMessage(msg);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                limpar();
            }
        });
        AlertDialog alerta = b.create();
        alerta.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar

                Intent it = new Intent(AlteraUsuario.this, MenuPrincipal.class);
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