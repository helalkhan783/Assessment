package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.test.databinding.ActivityListBinding;

public class ListActivity extends BaseActivity implements ForUpdate {
    ActivityListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        binding.access.back.setVisibility(View.VISIBLE);
        binding.access.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.access.title.setText("Profile List");

        getList();


    }

    private void getList() {
        if (userList() != null) {
            UserListAdapter userListAdapter = new UserListAdapter(userList(), ListActivity.this);
            binding.rv.setLayoutManager(new LinearLayoutManager(this));
            binding.rv.setAdapter(userListAdapter);
        }
    }

    @Override
    public void update(String id, String name, String country, String city, String language, String resume, String dateOfbirth) {


        Intent intent = new Intent(ListActivity.this, EditActivity.class);

        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("country", country);
        intent.putExtra("city", city);
        intent.putExtra("language", language);
        intent.putExtra("resume", resume);
        intent.putExtra("dateOfbirth", dateOfbirth);
        startActivity(intent);

        //   getList();
    }
}