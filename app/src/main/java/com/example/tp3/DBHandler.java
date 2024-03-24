package com.example.tp3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;// Database version
    private static final String DATABASE_NAME = "dbtp";// Name of the data base
    // the constructor to create the database with a defined version and name
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //In the onCreate method, we create the tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE etudiant(_id INTEGER, matricule TEXT PRIMARY KEY, nom TEXT, prenom TEXT, photo BLOB)";
        db.execSQL(CREATE_TABLE);
    }
    // The onUpgrade method allows you to update the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS etudiant");// Drop older table if existed
        onCreate(db);// Creating tables again
    }

    public void addStudent(Student s){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues val=new ContentValues();
        val.put("matricule",s.matricule);
        val.put("nom",s.nom);
        val.put("prenom",s.prenom);
        val.put("photo",s.photo);
        db.insert("etudiant",null,val);
        db.close();
    }

    public Student getStudent(String matricule){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor= db.rawQuery("Select * from etudiant where matricule=?",new String[]{matricule});
        if (cursor.getCount()==0)
            return null;
        cursor.moveToFirst();
        Student e = new Student(cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getBlob(4));
        cursor.close();
        return e;
    }

    public void updateStudent(Student s){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues val=new ContentValues();
        val.put("nom",s.nom);
        val.put("prenom",s.prenom);
        db.update("etudiant",val,"matricule=?",new String[]{String.valueOf(s.matricule)});
    }

    public void deleteStudent(String matricule){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("etudiant","matricule=?",new String[]{String.valueOf(matricule)});

    }

    public Cursor getAllStudents(){
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM etudiant",null);
    }
}
