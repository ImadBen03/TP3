package com.example.tp3;

public class Student {
    byte[] photo;
    String matricule,nom,prenom;

    public Student(String matricule, String nom, String prenom,byte[] photo){
        this.matricule=matricule;
        this.nom=nom;
        this.prenom=prenom;
        this.photo=photo;
    }
    public Student(String matricule, String nom, String prenom){
        this.matricule=matricule;
        this.nom=nom;
        this.prenom=prenom;
    }

}
