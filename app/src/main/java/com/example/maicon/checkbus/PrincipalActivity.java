package com.example.maicon.checkbus;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.maicon.checkbus.database.DataBase;
import com.example.maicon.checkbus.models.Bus;
import com.example.maicon.checkbus.models.Carteira;
import com.example.maicon.checkbus.models.CarteiraService;
import com.example.maicon.checkbus.receiver.ExecutaTarefaProgramada;
import com.example.maicon.checkbus.service.ServiceList;
import com.example.maicon.checkbus.service.ServiceSendCarteira;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.maicon.checkbus.models.CarteiraService.retrofit;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DataBase dataBase;
    private SQLiteDatabase conn;
    public static Boolean ESPERAR = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //responsavel pelo abrir e fechar do menu
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try{
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();
            Toast.makeText(this, "banco criado com sucesso", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Log.d("maicon", "erro ao criar branco " + e.getMessage());
        }
        verificaCarteira();
        alarme();
    }

    //responsavel por rolar por cima das outras activitys. ele é o abre e fecha
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    //responsavel pelos icones e tal no menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lista) {
            Intent intent = new Intent(this, AlunosActivity.class);
            startActivity(intent);

        }
        /* else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void verificaCarteira(){

        Cursor cursor = conn.query("CARTEIRA", null, null, null, null, null, null);

        if (cursor.getCount() == 0) {
            Intent intent = new Intent(this, CarteiraActivity.class);
            startActivity(intent);
        }
    }

    public void esperar(View view){
//        if (ESPERAR) {
//            Toast.makeText(this, "Seu nome já está na lista de espera!", Toast.LENGTH_SHORT).show();
//        } else {

//        Cursor cursor = conn.query("CARTEIRA", null, null, null, null, null, null);
//
//        if (cursor.getCount() > 0) {
//
//        } else {
//            Intent intent = new Intent(this, CarteiraActivity.class);
//            startActivity(intent);
//        }

            try {
//                String sql = "SELECT _id, NOME, RG, CURSO, TELEFONE FROM CARTEIRA";
//
//                Cursor cursor = conn.rawQuery(sql, null);
//                cursor.moveToFirst();
//
//                String nome = cursor.getString(cursor.getColumnIndex("NOME"));
//                String rg = cursor.getString(cursor.getColumnIndex("RG"));
//                Integer rgaux = Integer.parseInt(rg);
//                String curso = cursor.getString(cursor.getColumnIndex("CURSO"));
//                String telefone = cursor.getString(cursor.getColumnIndex("TELEFONE"));
//                Integer telefoneaux = Integer.parseInt(telefone);

                Intent intent = new Intent(this, ServiceSendCarteira.class);
                startService(intent);

            } catch (Exception e){
                Log.d("maicon", "erro - " + e.getMessage());
            }
//        }
    }

    public void alarme() {

        //verifica se o alarma ja esta ativo;
        boolean alarmAtivo = (PendingIntent.getBroadcast(this, 1234, new Intent(this, ExecutaTarefaProgramada.class), PendingIntent.FLAG_NO_CREATE) == null);

        if(alarmAtivo) {
            Log.d("maiconLog", "alarme ativo");
            Calendar calendar = Calendar.getInstance();

            //Definir intervalo de 1m
            long intervalo = 60000; //1 minuto em milissegundos
            Intent tarefaIntent = new Intent(this, ExecutaTarefaProgramada.class);
            PendingIntent tarefaPendingIntent = PendingIntent.getBroadcast(this, 1234, tarefaIntent, 0);

            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

            //Definir o alarme para ser ativado no android
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    intervalo, tarefaPendingIntent);
        }else {
            Log.d("maiconLog", "alarme ativado");
        }

    }

}
