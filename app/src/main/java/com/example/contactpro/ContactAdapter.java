package com.example.contactpro;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> contacts;

    public ContactAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.nomPrenom.setText(contact.getNom() + " " + contact.getPrenom());
        holder.telephone.setText(contact.getTelephone());
        holder.email.setText(contact.getEmail());
        if (contact.getFavori() == 1) {
            holder.favori.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            holder.favori.setImageResource(android.R.drawable.btn_star_big_off);
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("contact_id", contact.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView nomPrenom, telephone, email;
        ImageView favori;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            nomPrenom = itemView.findViewById(R.id.textViewNomPrenom);
            telephone = itemView.findViewById(R.id.textViewTelephone);
            email = itemView.findViewById(R.id.textViewEmail);
            favori = itemView.findViewById(R.id.imageViewFavori);
        }
    }
}
