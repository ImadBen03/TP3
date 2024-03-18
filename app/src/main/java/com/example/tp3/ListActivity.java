package com.example.tp3;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ListActivity extends AppCompatActivity {

    ListView lv;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        lv=findViewById(R.id.lv);
        dbHandler=new DBHandler(this);
        Cursor cursor=dbHandler.getAllStudents();
        MyCursorAdapter cursorAdapter=new MyCursorAdapter(this,cursor);
        lv.setAdapter(cursorAdapter);
    }
}