package com.example.contactpro;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private List<Contact> contacts;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_contact) {
            startActivity(new Intent(this, AddContactActivity.class));
            return true;
        } else if (id == R.id.action_show_favoris) {
            AppDatabase db = AppDatabase.getInstance(this);
            contacts.clear();
            contacts.addAll(db.contactDao().getFavoris());
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FloatingActionButton fab = findViewById(R.id.fabAddContact);
        fab.setOnClickListener(v -> {
            Toast.makeText(this, "FAB cliqué", Toast.LENGTH_SHORT).show();
            try {
                startActivity(new Intent(this, AddContactActivity.class));
            } catch (Exception e) {
                Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db = AppDatabase.getInstance(this);
        contacts = db.contactDao().getAll();
        adapter = new ContactAdapter(contacts);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh list when returning from AddContactActivity
        AppDatabase db = AppDatabase.getInstance(this);
        contacts.clear();
        contacts.addAll(db.contactDao().getAll());
        adapter.notifyDataSetChanged();
    }
}