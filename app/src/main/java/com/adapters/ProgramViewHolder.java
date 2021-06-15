package com.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appfmufit.R;

public class ProgramViewHolder {

    public ImageView itemImg;
    public TextView itemTitulo;

    ProgramViewHolder(View v){

        itemImg = v.findViewById(R.id.icons_menu);
        itemTitulo = v.findViewById(R.id.title_menu);

    }

}
