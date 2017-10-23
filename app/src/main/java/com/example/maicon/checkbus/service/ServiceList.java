package com.example.maicon.checkbus.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.maicon.checkbus.AlunosActivity;
import com.example.maicon.checkbus.LoginActivity;
import com.example.maicon.checkbus.models.Carteira;
import com.example.maicon.checkbus.models.CarteiraService;

import org.json.JSONArray;

/**
 * Created by maicon on 22/10/2017.
 */

public class ServiceList extends IntentService {

    private RequestQueue requestQueue;
    public static final String BASE_URL = "http://192.168.0.105:8080/ConexaoCarteira/webresources/carteira.carteira/";
    public static final String BROADCAST_FILTER = "the_service_broadcast";
    public static final String PARAMETER_EXTRA_KEY = "the_service_key";


    public ServiceList() {
        super("serviceList");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        synchronized (this) {
            requestQueue = Volley.newRequestQueue(this);

            JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, CarteiraService.BASE_URL + "find/all",
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (response.length() > 0) {
                       try {
                           JSONArray jsonArray = new JSONArray();

                           for(int i=0; i < response.length(); i++) {
                                Carteira carteira = new Carteira();
                                carteira.setNome(response.getJSONObject(i).getString("nome"));
                                carteira.setCurso(response.getJSONObject(i).getString("curso"));
                                carteira.setTelefone(response.getJSONObject(i).getInt("telefone"));
                                carteira.setSituacao(response.getJSONObject(i).getInt("situaco"));
                                Log.d("maiconLog", "nome: " + carteira.getNome());
                                AlunosActivity.carteiras.add(carteira);
                           }


                       } catch (Exception e) {
                           Log.d("maiconLog", "erro: " + e.getMessage());
                       }
                       notificacao();
                       broadcast(1);

                    } else {
                        Log.d("maicon", "sem informacoes");
                    }
                }
            },

            new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("maicon", "erro");
                }
            }

            );
            requestQueue.add(arrayRequest);
        }
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    void broadcast(int success){
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BROADCAST_FILTER).putExtra(PARAMETER_EXTRA_KEY, success));
    }

    public void notificacao(){
      NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_notify_sync)
                        .setContentTitle("Check Bus")
                        .setContentText("Alteração na Lista de Alunos!");

        Intent resultIntent = new Intent(this, LoginActivity.class);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(LoginActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1222, mBuilder.build());
    }
}
