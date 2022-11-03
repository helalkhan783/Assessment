package com.example.test;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.database.MyDatabaseHelper;
import com.example.test.database.User;
import com.example.test.response.CountryResponse;
import com.example.test.response.Datum;
import com.example.test.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

public class BaseActivity extends AppCompatActivity {
    MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(BaseActivity.this);
    Cursor cursor;

    public List<User> userList() {
        cursor = myDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            return null;
        }
        List<User> users = new ArrayList<>();
        users.clear();
        while (cursor.moveToNext()) {
            User item = new User(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            users.add(item);
        }
        return users;
    }

    public <T> void openDatePicker(T typee) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog((Context) typee, (DatePickerDialog.OnDateSetListener) typee,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
        //   dpd.show(activity.getSupportFragmentManager(), "Datepickerdialog");
    }

    public String dateSelect(int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear;
        if (month == 12) {
            month = 1;
        } else {
            month = monthOfYear + 1;
        }

        String mainMonth, mainDay;


        if (month <= 9) {
            mainMonth = "0" + month;
        } else {
            mainMonth = String.valueOf(month);
        }
        if (dayOfMonth <= 9) {
            mainDay = "0" + dayOfMonth;
        } else {
            mainDay = String.valueOf(dayOfMonth);
        }
        String selectedDate = year + "-" + mainMonth + "-" + mainDay;//set the selected date

        return selectedDate;
    }


    public boolean checkStoragePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestStoragePermission(int STORAGE_PERMISSION_REQUEST_CODE) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    public List<String> languageList() {
        List<String> lan = new ArrayList<>();
        lan.add("Bengali");
        lan.add("English");
        lan.add("Tamil");
       return  lan;
    }
}
