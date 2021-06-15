package com.example.appfmufit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText txtLogin, txtSenha;
    Button btnEntrar;
    Conexao myDb;
    private String email, senha;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDb = new Conexao(this);

        getSupportActionBar().setTitle("FMU Fit");

        txtLogin = findViewById(R.id.txtLogin);
        txtSenha = findViewById(R.id.txtChave);
        btnEntrar = findViewById(R.id.btnEntrar);

        // personaliza cor do botão
        btnEntrar.setBackgroundResource(R.color.blue_button);

        chamarMenu();
    }

    public void chamarMenu() {
        btnEntrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SQLiteDatabase db = myDb.getWritableDatabase();

                        Cursor c = db.rawQuery("select * from usuario where email = '" + txtLogin.getText().toString() + "'", null);

                        while (c.moveToNext()){
                            id = Integer.parseInt(c.getString(0));
                            email = c.getString(8);
                            senha = c.getString(9);
                        }

                        if (c.getCount() == 0) {
                            Toast t = Toast.makeText(Login.this, "USUÁRIO NÃO CADASTRADO!", Toast.LENGTH_SHORT);
                            t.show();
                        }

                        else if (!txtLogin.getText().toString().equals(email) || !txtSenha.getText().toString().equals(senha)) {
                            Toast t = Toast.makeText(Login.this, "USUÁRIO OU SENHA NÃO CONFEREM!", Toast.LENGTH_SHORT);
                            t.show();
                        }

                        else if(txtLogin.getText().toString().equals(email) && txtSenha.getText().toString().equals(senha)){

                            Intent it = new Intent(Login.this, MenuPrincipal.class);
                            Bundle b = new Bundle();
                            b.putInt("cd_usuario", id);
                            it.putExtras(b);
                            myDb.close();
                            startActivity(it);
                            finishAffinity();

                        }

                    }
                }

        );
    }

    public void chamarTelaCadastro(View v){
        Intent it = new Intent(this, TelaCadastro.class);
        startActivity(it);
        finishAffinity();
    }
}