package com.example.contactpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {
    private EditText editTextNom, editTextPrenom, editTextSociete, editTextAdresse, editTextTel, editTextEmail;
    private Spinner spinnerSecteur;
    private Button buttonAjouter, buttonAnnuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        editTextNom = findViewById(R.id.editTextNom);
        editTextPrenom = findViewById(R.id.editTextPrenom);
        editTextSociete = findViewById(R.id.editTextSociete);
        editTextAdresse = findViewById(R.id.editTextAdresse);
        editTextTel = findViewById(R.id.editTextTel);
        editTextEmail = findViewById(R.id.editTextEmail);
        spinnerSecteur = findViewById(R.id.spinnerSecteur);
        buttonAjouter = findViewById(R.id.buttonAjouter);
        buttonAnnuler = findViewById(R.id.buttonAnnuler);

        String[] secteurs = {"Industrie", "Informatique", "Santé", "Autre"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, secteurs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSecteur.setAdapter(adapter);

        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterContact();
            }
        });

        buttonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void ajouterContact() {
        String nom = editTextNom.getText().toString().trim();
        String prenom = editTextPrenom.getText().toString().trim();
        String societe = editTextSociete.getText().toString().trim();
        String adresse = editTextAdresse.getText().toString().trim();
        String tel = editTextTel.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String secteur = spinnerSecteur.getSelectedItem().toString();

        if (nom.isEmpty() || prenom.isEmpty() || societe.isEmpty() || adresse.isEmpty() || tel.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Contact contact = new Contact(nom, prenom, societe, adresse, tel, email, secteur, 0);
        AppDatabase.getInstance(this).contactDao().insert(contact);
        finish();
    }
}
