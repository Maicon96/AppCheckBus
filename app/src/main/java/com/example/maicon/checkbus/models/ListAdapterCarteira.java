package com.example.maicon.checkbus.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maicon.checkbus.R;

import java.util.ArrayList;

/**
 * Created by maico on 21/10/2017.
 */

public class ListAdapterCarteira extends ArrayAdapter<Carteira> {
    private Context context;
    private ArrayList<Carteira> carteiras;


    public ListAdapterCarteira(Context context, ArrayList<Carteira> carteiras){
        super(context, 0, carteiras);
        this.context = context;
        this.carteiras = carteiras;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//vai devolver nossa view personalizada
        Carteira carteiraPosicao = this.carteiras.get(position);//vou pegar o item da lista na pos passada

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item, null);

        TextView txtAluno = (TextView) convertView.findViewById(R.id.txtNome);
        txtAluno.setText(carteiraPosicao.getNome());

        TextView txtCurso = (TextView) convertView.findViewById(R.id.txtCurso);
        txtCurso.setText(carteiraPosicao.getCurso());

        TextView txtTelefone = (TextView) convertView.findViewById(R.id.txtTelefone);
        txtTelefone.setText(carteiraPosicao.getTelefone().toString());

        TextView txtSituacao = (TextView) convertView.findViewById(R.id.txtSituacao);
        if (carteiraPosicao.getSituacao() == 1) {
            txtSituacao.setText("ESPERAR!");
            convertView.setBackgroundResource(android.R.color.holo_orange_light);
        } else if(carteiraPosicao.getSituacao() == 2){
            txtSituacao.setText("CHEGOU!");
            convertView.setBackgroundResource(android.R.color.holo_green_light);
        } else if(carteiraPosicao.getSituacao() == 3){
            txtSituacao.setText("NÃO VOLTA!");
            convertView.setBackgroundResource(android.R.color.holo_red_light);
        }

        return  convertView;
    }
}
