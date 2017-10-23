package com.example.maicon.checkbus;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maicon.checkbus.database.DataBase;

import java.security.Principal;

public class CarteiraActivity extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtRG;
    private EditText edtCurso;
    private EditText edtSala;
    private EditText edtTelefone;
    private EditText edtFretamento;
    private DataBase dataBase;
    private SQLiteDatabase conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carteira);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtRG = (EditText) findViewById(R.id.edtRG);
        edtCurso = (EditText) findViewById(R.id.edtCurso);
        edtSala = (EditText) findViewById(R.id.edtSala);
        edtTelefone = (EditText) findViewById(R.id.edtTelefone);
        edtFretamento = (EditText) findViewById(R.id.edtFretamento);

        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();

    }

    public void confirmar(View view) {
        ContentValues values = new ContentValues();
        try {
            values.put("NOME", edtNome.getText().toString());
            values.put("RG", edtRG.getText().toString());
            values.put("CURSO", edtCurso.getText().toString());
            values.put("SALA", edtSala.getText().toString());
            values.put("TELEFONE", edtTelefone.getText().toString());
            values.put("FRETAMENTO", edtFretamento.getText().toString());
            conn.insertOrThrow("CARTEIRA", null, values);
            Toast.makeText(this, "Carteira Cadastrada com Sucesso!", Toast.LENGTH_SHORT).show();



        } catch (Exception e) {
            Log.d("maicon", "erro ao inserir carteira - " + e.getMessage());
        }
        finally {
            Intent in = new Intent(this, PrincipalActivity.class);
            startActivity(in);
        }
    }

    public void pegaFoto(View v) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);

    }
}
