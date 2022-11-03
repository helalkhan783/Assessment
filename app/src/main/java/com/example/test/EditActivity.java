package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.test.databinding.ActivityEditBinding;
import com.example.test.response.Datum;
import com.example.test.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EditActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    ActivityEditBinding binding;
    MyViewModel myViewModel;
    List<Datum> list;
    List<String> name;
    List<String> cityNameList;
    String selectedCountry, selectedCity, selectedLanguage  ;
    String country, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);

        binding.toolbarAccess.back.setVisibility(View.VISIBLE);
        binding.toolbarAccess.title.setText("Update Profile");
        binding.toolbarAccess.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name1 = intent.getStringExtra("name");
        country = intent.getStringExtra("country");
        city = intent.getStringExtra("city");
        String language = intent.getStringExtra("language");
        String resume = intent.getStringExtra("resume");
        String dateOfbirth = intent.getStringExtra("dateOfbirth");

        binding.name.setText("" + name1);
        binding.resumeFiletv.setText("" + resume);
        binding.dateOfBirth.setText("" + dateOfbirth);
        binding.dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(EditActivity.this);
            }
        });
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        countryList();

        binding.country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCountry = name.get(position);
                setCity(selectedCountry);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int possition, long l) {
                selectedCity = cityNameList.get(possition);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.language.setItem(languageList());
        for (int i = 0; i < languageList().size(); i++) {
            if (language.equals(languageList().get(i))){
                binding.language.setSelection(i);
            }
        }


        binding.language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int possition, long l) {
                selectedLanguage = languageList().get(possition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedCountry == null) {
                    selectedCountry = country;
                }
                if (selectedCity == null) {
                    selectedCity = city;
                }
                try {
                    Boolean isUpdated = myDatabaseHelper
                            .updateData(id, binding.name.getText().toString(), selectedCountry, selectedCity, selectedLanguage, resume, binding.dateOfBirth.getText().toString());

                    Intent intent1 = new Intent(EditActivity.this, ListActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                } catch (Exception e) {
                }
            }
        });

    }


    private void countryList() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        list = new ArrayList<>();
        myViewModel.getCountries().observe(this, countryResponse -> {
            progressDialog.dismiss();
            list.clear();
            list.addAll(countryResponse.getData());
            HashSet<String> nam = new HashSet<>();
            nam.clear();
            for (int i = 0; i < list.size(); i++) {

                nam.add(list.get(i).getCountry());
            }

            name = new ArrayList<>();
            name.clear();
            name.addAll(nam);

            for (int i = 0; i < name.size(); i++) {
                if (country.equals(name.get(i))) {
                    binding.country.setSelection(i);
                }
            }
            binding.country.setItem(name);

            setCity(selectedCountry);

        });

    }

    private void setCity(String selectedCountry) {
        cityNameList = new ArrayList<>();
        cityNameList.clear();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCountry().equals(selectedCountry)) {
                cityNameList.add(list.get(i).getCity());
            }
        }
        for (int i = 0; i < cityNameList.size(); i++) {
            if (city.equals(cityNameList.get(i))){
                binding.city.setSelection(i);
            }
        }
        binding.city.setItem(cityNameList);

    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        binding.dateOfBirth.setText("" + dateSelect(year, monthOfYear, dayOfMonth));

    }
}