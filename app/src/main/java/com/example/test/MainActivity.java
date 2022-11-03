package com.example.test;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.test.databinding.ActivityMainBinding;
import com.example.test.response.Datum;
import com.example.test.viewmodel.MyViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    ActivityMainBinding binding;
    int userUniqueId = 0;
    MyViewModel myViewModel;
    List<Datum> list;
    List<String> name;
    List<String> cityNameList;
    String selectedCountry, selectedCity, selectedLanguage  ;
    private static final int PICK_RESUME = 200;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 300;
    File myFile;
    public String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        if (userList() != null) {
            binding.userList.setVisibility(View.VISIBLE);
        }

        binding.userList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });
        countryList();

        binding.save.setOnClickListener(view -> {
            if (binding.name.getText().toString().isEmpty()) {
                binding.name.setError("Please enter your name");
                binding.name.requestFocus();
                return;
            }
            myDatabaseHelper.insertData(uniqueId(), binding.name.getText().toString(), selectedCountry, selectedCity, selectedLanguage, binding.resumeFiletv.getText().toString(), binding.dateOfBirth.getText().toString());
            startActivity(new Intent(MainActivity.this, ListActivity.class));

        });
        binding.dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(MainActivity.this);
            }
        });
        binding.resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(checkStoragePermission())) {
                    requestStoragePermission(STORAGE_PERMISSION_REQUEST_CODE);
                } else {
                    getLogoImageFromFile(getApplication(), PICK_RESUME);
                }
            }
        });

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
                //   Toast.makeText(MainActivity.this, "" + selectedCity, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.language.setItem(languageList());
        binding.language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int possition, long l) {
                selectedLanguage = languageList().get(possition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
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


        binding.city.setItem(cityNameList);

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
            binding.country.setItem(name);
        });

    }

    private String uniqueId() {

        if (userList() == null) {
            return "0";
        } else {
            for (int i = 0; i < userList().size(); i++) {
                userUniqueId = Integer.parseInt(userList().get(i).getId());
            }
            userUniqueId = userUniqueId + 1;

            return String.valueOf(userUniqueId);
        }
    }


    public void getLogoImageFromFile(Application application, int PICK_RESUME) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");

        startActivityForResult(Intent.createChooser(intent, "Select File"),
                PICK_RESUME);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        binding.dateOfBirth.setText("" + dateSelect(year, monthOfYear, dayOfMonth));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_RESUME) {
            if (data != null) {
                Toast.makeText(this, "file selected", Toast.LENGTH_LONG).show();
                // result=data.getStringExtra("data");
                Uri uri = data.getData();
                //  String uriString = uri.toString();
                myFile = new File(uri.toString());
                path = myFile.getAbsolutePath();

                binding.resumeFiletv.setText("" + data.getData());

                File f = new File(path);
                FileInputStream fin = null;
                FileChannel ch = null;
                try {
                    fin = new FileInputStream(f);
                    ch = fin.getChannel();
                    int size = (int) ch.size();
                    MappedByteBuffer buf = ch.map(FileChannel.MapMode.READ_ONLY, 0, size);
                    byte[] bytes = new byte[size];
                    buf.get(bytes);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    try {
                        if (fin != null) {
                            fin.close();
                        }
                        if (ch != null) {
                            ch.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                // result=data.toString();
                // URI uri=data.getParcelableExtra(path);
                // path=fileUri.toString();
                //  this.render();
            }

        }
    }
}