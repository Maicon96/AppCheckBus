package com.example.maicon.checkbus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maicon.checkbus.database.DataBase;
import com.example.maicon.checkbus.models.Bus;
import com.example.maicon.checkbus.models.Carteira;
import com.example.maicon.checkbus.models.CarteiraService;
import com.example.maicon.checkbus.models.ListAdapterCarteira;
import com.example.maicon.checkbus.service.ServiceList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunosActivity extends AppCompatActivity {

    public static final String BROADCAST_FILTER = "the_service_broadcast";
    public static final String PARAMETER_EXTRA_KEY = "the_service_key";
    public static ArrayList<Carteira> carteiras = new ArrayList<Carteira>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);
        Intent intent = new Intent(this, ServiceList.class);
        startService(intent);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ListAdapterCarteira  adapterCarteira = new ListAdapterCarteira(AlunosActivity.this, carteiras);

            ListView listView = (ListView) findViewById(R.id.ivAlunos);
            listView.setAdapter(adapterCarteira);
        }

    };

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(ServiceList.BROADCAST_FILTER));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }
}
