package com.example.contactpro;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nom;
    private String prenom;
    private String societe;
    private String adresse;
    private String telephone;
    private String email;
    private String secteur;
    private int favori;

    public Contact(String nom, String prenom, String societe, String adresse, String telephone, String email, String secteur, int favori) {
        this.nom = nom;
        this.prenom = prenom;
        this.societe = societe;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.secteur = secteur;
        this.favori = favori;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getSociete() { return societe; }
    public String getAdresse() { return adresse; }
    public String getTelephone() { return telephone; }
    public String getEmail() { return email; }
    public String getSecteur() { return secteur; }
    public int getFavori() { return favori; }
    public void setFavori(int favori) { this.favori = favori; }
}
