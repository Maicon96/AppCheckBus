package com.example.maicon.checkbus.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maicon.checkbus.models.Carteira;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maico on 22/10/2017.
 */

public class ServiceSendCarteira extends IntentService {
    private RequestQueue requestQueue;
    public static final String URL = "http://192.168.0.105:8080/ConexaoCarteira/webresources/carteira.carteira/create";
    public static final String BROADCAST_FILTER = "the_service_broadcast";
    public static final String PARAMETER_EXTRA_KEY = "the_service_key";

    public ServiceSendCarteira() {
        super("serviceSendCarteira");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        synchronized (this) {
            ArrayList<Carteira> carteiras = new ArrayList<Carteira>();
            Carteira carteira = new Carteira("jose", 66666, "SI", 36475656);
            carteiras.add(carteira);
            final String json = new Gson().toJson(carteiras);

            requestQueue = Volley.newRequestQueue(this);
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("maiconLog", "erro:" + error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    System.out.println("entro aqui?" + json);
                    params.put("", json);
                    params.put("method", "send-json");

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }

            };
            requestQueue.add(stringRequest);

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
}
