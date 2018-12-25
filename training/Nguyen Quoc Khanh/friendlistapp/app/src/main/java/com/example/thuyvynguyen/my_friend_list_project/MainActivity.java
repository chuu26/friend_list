package com.example.thuyvynguyen.my_friend_list_project;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ListView listFriend;
    ArrayList<Friend> arrayFriend;
    Button btnAddNewFriend;
    ArrayAdapter<Friend> adapter;
    static final int MY_REQUEST_CODE = 100;

    Date date;
    Person person;
    Friend friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listFriend = (ListView) findViewById(R.id.listFriend);
        arrayFriend = new ArrayList<>();

        //handle event btn Click and intent to activity add new friend
        btnClickAndIntent();
    }

    public void btnClickAndIntent(){

        btnAddNewFriend = (Button) findViewById(R.id.btnAddNewFriend);

        btnAddNewFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, activity_add_new_friend.class);
                startActivityForResult(intent, MY_REQUEST_CODE);
            }
        });
    }

    //Take data like day, month, year... to add into object Friend
    int day, month, year;
    String middleName, firstName, lastName, male;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Success get data", Toast.LENGTH_SHORT).show();

            day = data.getIntExtra("day", 1);
            month = data.getIntExtra("month", 1);
            year = data.getIntExtra("year", 2000);
            date =  new Date(day, month, year);

            firstName   = data.getStringExtra("firstName");
            middleName  = data.getStringExtra("middleName");
            lastName    = data.getStringExtra("lastName");
            person      = new Person(firstName, middleName, lastName);

            male        = data.getStringExtra("male");

            friend = new Friend(person, date, male);

            arrayFriend.add(friend);

            adapter = new ArrayAdapter<>(MainActivity.this,
                    android.R.layout.simple_list_item_1, arrayFriend);
            listFriend.setAdapter(adapter);
        }

    }
}
