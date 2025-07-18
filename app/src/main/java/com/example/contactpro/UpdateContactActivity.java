package com.example.contactpro;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateContactActivity extends AppCompatActivity {
    private EditText editTextNom, editTextPrenom, editTextSociete, editTextAdresse, editTextTel, editTextEmail;
    private Spinner spinnerSecteur;
    private Button buttonUpdate, buttonAnnuler;
    private Contact contact;

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
        buttonUpdate = findViewById(R.id.buttonAjouter);
        buttonAnnuler = findViewById(R.id.buttonAnnuler);

        String[] secteurs = {"Industrie", "Informatique", "Santé", "Autre"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, secteurs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSecteur.setAdapter(adapter);

        long contactId = getIntent().getLongExtra("contact_id", -1);
        if (contactId == -1) {
            finish();
            return;
        }
        contact = AppDatabase.getInstance(this).contactDao().getById(contactId);
        if (contact == null) {
            finish();
            return;
        }
        remplirFormulaire();

        buttonUpdate.setText("Mettre à jour");
        buttonUpdate.setOnClickListener(v -> updateContact());
        buttonAnnuler.setOnClickListener(v -> finish());
    }

    private void remplirFormulaire() {
        editTextNom.setText(contact.getNom());
        editTextPrenom.setText(contact.getPrenom());
        editTextSociete.setText(contact.getSociete());
        editTextAdresse.setText(contact.getAdresse());
        editTextTel.setText(contact.getTelephone());
        editTextEmail.setText(contact.getEmail());
        String[] secteurs = {"Industrie", "Informatique", "Santé", "Autre"};
        for (int i = 0; i < secteurs.length; i++) {
            if (secteurs[i].equals(contact.getSecteur())) {
                spinnerSecteur.setSelection(i);
                break;
            }
        }
    }

    private void updateContact() {
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

        contact = new Contact(nom, prenom, societe, adresse, tel, email, secteur, contact.getFavori());
        contact.setId(getIntent().getLongExtra("contact_id", -1));
        AppDatabase.getInstance(this).contactDao().update(contact);
        finish();
    }
}
