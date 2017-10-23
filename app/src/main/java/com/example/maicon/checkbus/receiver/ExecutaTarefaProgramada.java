package com.example.maicon.checkbus.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.maicon.checkbus.service.ServiceList;

/**
 * Created by maicon on 22/10/2017.
 */

public class ExecutaTarefaProgramada extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("maiconLog", "entrou no receiver");

        Intent intent1 = new Intent(context, ServiceList.class);
        context.startService(intent1);
    }
}
