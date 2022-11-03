package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.database.User;
import com.example.test.databinding.ItemLayoutBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.Myholder> {
    List<User> list;
    ListActivity context;

    public UserListAdapter(List<User> list, ListActivity context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public UserListAdapter.Myholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(parent.getContext()), R.layout.item_layout, parent, false);
        return new Myholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserListAdapter.Myholder holder, int position) {
        User currentPossition = list.get(position);
        holder.binding.name.setText(":  " + currentPossition.getName());
        holder.binding.country.setText(":  " + currentPossition.getCountry());
        holder.binding.city.setText(":  " + currentPossition.getCity());
        holder.binding.language.setText(": " + currentPossition.getLanguage());

        holder.binding.teOfBirth.setText(": " + currentPossition.getDateOfBirth());


        holder.binding.edit.setOnClickListener(view -> context.update(currentPossition.getId(),
                currentPossition.getName(),
                currentPossition.getCountry(),
                currentPossition.getCity(),
                currentPossition.getLanguage(),
                currentPossition.getResume(),
                currentPossition.getDateOfBirth()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        private ItemLayoutBinding binding;

        public Myholder(@NonNull @NotNull ItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
