package com.example.maicon.checkbus.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLOutput;

/**
 * Created by maicon on 16/09/2017.
 */

public class DataBase extends SQLiteOpenHelper {

    Context context;
    public DataBase(Context context){
        super(context, "checkBus", null, 1);
        Log.d("maicon", "criou o banco de dados");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateCarteira());
        db.execSQL(ScriptSQL.getCreateLogin());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
