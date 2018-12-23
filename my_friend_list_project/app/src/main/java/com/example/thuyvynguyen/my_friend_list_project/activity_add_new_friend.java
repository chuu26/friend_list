package com.example.thuyvynguyen.my_friend_list_project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.midi.MidiDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;

public class activity_add_new_friend extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, Serializable {

    EditText edtFirstName, edtMiddleName, edtLastName;
    TextView textView;
    Button btnDateTime, btnAdd;

    Calendar calendar = Calendar.getInstance();
    int day = 1, month = 1, year = 2000;
    Spinner spinner;
    String sex;//Only have 2 values: male and female


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_friend);

        Radiation();
        putValueToSpinner();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //textView.setText(friend.toString());
                returnToMain();
            }
        });

    }

    public void returnToMain(){
        Intent intentReturn = new Intent();

        intentReturn.putExtra("firstName", edtFirstName.getText().toString());
        intentReturn.putExtra("middleName", edtMiddleName.getText().toString());
        intentReturn.putExtra("lastName", edtLastName.getText().toString());

        intentReturn.putExtra("day", day);
        intentReturn.putExtra("month", month);
        intentReturn.putExtra("year", year);

        intentReturn.putExtra("sex", sex);

        setResult(RESULT_OK, intentReturn);
        finish();
    }


    public void Radiation(){
        edtFirstName  = (EditText) findViewById(R.id.edtFirstName);
        edtMiddleName = (EditText) findViewById(R.id.edtMiddleName);
        edtLastName   = (EditText) findViewById(R.id.edtLastName);
        btnDateTime   = (Button)   findViewById(R.id.btnDateTime);
        btnAdd        = (Button)   findViewById(R.id.btnAdd);
        textView = findViewById(R.id.textView);

        btnDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year  = calendar.get(Calendar.YEAR);

                DatePickerDialog datePicker = new DatePickerDialog(activity_add_new_friend.this,
                        activity_add_new_friend.this, year, month, day);
                datePicker.show();
            }
        });


    }


    public void putValueToSpinner(){
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity_add_new_friend.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.gender));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public String getCurrentDay(){

        //neu muon chuyen ra Saturday, 19 December, 2018 thi DateFormat.FULL
        //..getTimeInstance()..
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        return currentDate; //19.12.2018

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        day = dayOfMonth;
        this.month = month + 1;
        this.year = year;
        btnDateTime.setText(day + "/" + this.month + "/" + this.year);
    }
}
