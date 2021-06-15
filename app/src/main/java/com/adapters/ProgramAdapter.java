package com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.appfmufit.R;


public class ProgramAdapter extends ArrayAdapter {

    private Context contexto;
    int[] img;
    String[] itens;

    public ProgramAdapter(Context contexto, String[] itens, int[] img) {
        super(contexto, R.layout.layout_menu_principal, R.id.title_menu, itens);
        this.contexto = contexto;
        this.itens = itens;
        this.img = img;
    }

    public View getView (int position, View convertView, ViewGroup parent){

        View v = convertView;
        ProgramViewHolder holder = null;

        if(v == null){
            LayoutInflater layoutInflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.layout_menu_principal, parent, false);
            holder = new ProgramViewHolder(v);
            v.setTag(holder);
        }

        else{
            holder = (ProgramViewHolder) v.getTag();
        }

        holder.itemImg.setImageResource(img[position]);
        holder.itemTitulo.setText(itens[position]);

        return v;
    }
}
