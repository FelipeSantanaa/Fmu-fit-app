package com.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appfmufit.R;
import com.example.appfmufit.Treino;

import java.util.List;

public class AdapterListaTreinos extends BaseAdapter {

    private Context context;
    private List<Treino> treinoList;

    public AdapterListaTreinos(Context context, List<Treino> treinoList) {
        this.context = context;
        this.treinoList = treinoList;
    }

    @Override
    public int getCount() {
        return this.treinoList.size();
    }

    public void removerTreinoDaLista(int posicao){
        this.treinoList.remove(posicao);
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int posicao) {
        return this.treinoList.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {

        View v = View.inflate(this.context, R.layout.layout_lvw_treino, null);

        TextView title_treino = (TextView) v.findViewById(R.id.title_treino);
        TextView desc_treino = (TextView) v.findViewById(R.id.desc_treino);

        title_treino.setText(treinoList.get(posicao).getDs_modalidade());
        desc_treino.setText("Data: " + treinoList.get(posicao).getData() + " - Horário: " + treinoList.get(posicao).getHora() + "\n");

        return v;
    }
}
