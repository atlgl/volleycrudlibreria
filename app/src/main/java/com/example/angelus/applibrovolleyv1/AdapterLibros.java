package com.example.angelus.applibrovolleyv1;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Angelus on 11/07/2017.
 */

public class AdapterLibros extends ArrayAdapter<Libro> {
    private Context ctx;
    private List<Libro> libroList;

    public AdapterLibros(Context context, List<Libro> libroList) {
        super(context, R.layout.item_libro, libroList);
        this.ctx=context;
        this.libroList=libroList;
    }

    private TextView txtid,txtnombre,txtdesc;
    private ImageView imgview;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= LayoutInflater.from(ctx).inflate(R.layout.item_libro,parent,false);

        txtid=(TextView) v.findViewById(R.id.item_txtid);
        txtnombre=(TextView) v.findViewById(R.id.item_txtnombre);
        txtdesc=(TextView) v.findViewById(R.id.item_txtdesc);
        imgview=(ImageView) v.findViewById(R.id.item_imagen);

        txtid.setText("ID"+libroList.get(position).getIdlibros());
        txtnombre.setText(libroList.get(position).getNombre());
        txtdesc.setText(libroList.get(position).getDescripcion());
        return v;
    }
}
