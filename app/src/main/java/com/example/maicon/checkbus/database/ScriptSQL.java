package com.example.maicon.checkbus.database;

/**
 * Created by maicon
 */

public class ScriptSQL {

    public static String getCreateLogin(){

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS LOGIN ( ");
        sqlBuilder.append("_id                INTEGER       NOT NULL ");
        sqlBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("USUARIO              VARCHAR (50), ");
        sqlBuilder.append("SENHA                VARCHAR (50) ");
        sqlBuilder.append("); ");

        return sqlBuilder.toString();

    }

    public static String getCreateCarteira(){

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS CARTEIRA ( ");
        sqlBuilder.append("_id                INTEGER       NOT NULL ");
        sqlBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("NOME                 VARCHAR (255), ");
        sqlBuilder.append("RG                   INTEGER, ");
        sqlBuilder.append("CURSO                VARCHAR (255), ");
        sqlBuilder.append("SALA                 VARCHAR (255), ");
        sqlBuilder.append("TELEFONE             INTEGER, ");
        sqlBuilder.append("DETER                INTEGER, ");
        sqlBuilder.append("FOTO                BLOB, ");
        sqlBuilder.append("FRETAMENTO           INTEGER ");
        sqlBuilder.append("); ");

        return sqlBuilder.toString();

    }

}