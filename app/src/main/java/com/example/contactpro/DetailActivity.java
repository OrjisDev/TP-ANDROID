package com.example.contactpro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private Contact contact;
    private TextView nomPrenom, societe, adresse, tel, email, secteur;
    private Button buttonAppeler, buttonSms, buttonEmail, buttonLocaliser, buttonFavori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nomPrenom = findViewById(R.id.textViewNomPrenom);
        societe = findViewById(R.id.textViewSociete);
        adresse = findViewById(R.id.textViewAdresse);
        tel = findViewById(R.id.textViewTel);
        email = findViewById(R.id.textViewEmail);
        secteur = findViewById(R.id.textViewSecteur);
        buttonAppeler = findViewById(R.id.buttonAppeler);
        buttonSms = findViewById(R.id.buttonSms);
        buttonEmail = findViewById(R.id.buttonEmail);
        buttonLocaliser = findViewById(R.id.buttonLocaliser);
        buttonFavori = findViewById(R.id.buttonFavori);
        buttonEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + contact.getEmail()));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{contact.getEmail()});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Contact Pro");
            startActivity(intent);
        });

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
        afficherContact();

        buttonAppeler.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.getTelephone()));
            startActivity(intent);
        });
        buttonSms.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + contact.getTelephone()));
            startActivity(intent);
        });
        buttonLocaliser.setOnClickListener(v -> {
            String uri = "geo:0,0?q=" + Uri.encode(contact.getAdresse());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        });
        buttonFavori.setOnClickListener(v -> {
            contact.setFavori(contact.getFavori() == 1 ? 0 : 1);
            AppDatabase.getInstance(this).contactDao().update(contact);
            afficherContact();
        });
    }

    private void afficherContact() {
        nomPrenom.setText(contact.getNom() + " " + contact.getPrenom());
        societe.setText("Société : " + contact.getSociete());
        adresse.setText("Adresse : " + contact.getAdresse());
        tel.setText("Téléphone : " + contact.getTelephone());
        email.setText("Email : " + contact.getEmail());
        secteur.setText("Secteur : " + contact.getSecteur());
        buttonFavori.setText(contact.getFavori() == 1 ? "Retirer des favoris" : "Ajouter aux favoris");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            AppDatabase.getInstance(this).contactDao().delete(contact);
            finish();
            return true;
        } else if (id == R.id.action_update) {
            Intent intent = new Intent(this, UpdateContactActivity.class);
            intent.putExtra("contact_id", contact.getId());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
