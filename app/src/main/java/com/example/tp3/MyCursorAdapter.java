package com.example.tp3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

public class MyCursorAdapter extends CursorAdapter {
    public MyCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.itemlist, parent,
                false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtmat = (TextView) view.findViewById(R.id.matricule);
        TextView txtnom = (TextView) view.findViewById(R.id.nom);
        TextView txtprenom = (TextView) view.findViewById(R.id.prenom);
        String mat = String.valueOf(cursor.getInt(1));
        String nom = cursor.getString(2);
        String prenom = cursor.getString(3);
        txtmat.setText(mat);
        txtnom.setText(nom);
        txtprenom.setText(prenom);
    }
}

