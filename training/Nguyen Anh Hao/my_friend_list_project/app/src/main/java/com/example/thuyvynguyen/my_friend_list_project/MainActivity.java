package com.example.thuyvynguyen.my_friend_list_project;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    ArrayList<Friend> arrayFriend = new ArrayList<>();
    ArrayList<Friend> arrayMale = new ArrayList<>();
    ArrayList<Friend> arrayFemale = new ArrayList<>();
    ArrayList<String> arrayUpcomingBirthday = new ArrayList<>();

    ListView listFriend;
    Button btnAddNewFriend;
    ArrayAdapter<Friend> adapter;
    static final int MY_REQUEST_CODE = 100;

    Date date;
    Person person;
    Friend friend;

    Spinner spinner_sort;
    ListView listViewUpcomingBirthday;

    String currentDay;//get the current day to know who's birthday in 7 days
    Calendar calendar = Calendar.getInstance();//Import to use when ever need Calendar
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listFriend = (ListView) findViewById(R.id.listFriend);


        //handle event btn Click and intent to activity add new friend
        btnClickAndIntent();

        putValueToSpinner();

        dialog = new Dialog(MainActivity.this);
    }

    //When button click is clicked
    //Open activity add new friend
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
    String middleName, firstName, lastName, sex;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Success get data", Toast.LENGTH_SHORT).show();

            day = data.getIntExtra("day", 1);
            month = data.getIntExtra("month", 1);
            year = data.getIntExtra("year", 2000);
            date =  new Date(day  +0, month + 1, year + 0);//Month start from 0(January = 0)

            firstName   = data.getStringExtra("firstName");
            middleName  = data.getStringExtra("middleName");
            lastName    = data.getStringExtra("lastName");
            person      = new Person(firstName, middleName, lastName);

            sex        = data.getStringExtra("sex");

            friend = new Friend(person, date, sex);

            //Add friend follow their sex
            if ("Male".contains(sex)){
                arrayMale.add(friend);
            }
            if ("Female".contains(sex)){
                arrayFemale.add(friend);
            }

            arrayFriend.add(friend);

            Collections.sort(arrayFriend,
                    new Comparator<Friend>() {
                @Override
                public int compare(Friend o1, Friend o2) {
                    return o1.getPerson().getFirstName().compareToIgnoreCase(o2.getPerson().getFirstName());
                }
            });

            //To easy put data to ListView
            setAdapter(arrayFriend);

            int tempDay = calendar.get(Calendar.DAY_OF_MONTH);
            int tempMonth = calendar.get(Calendar.MONTH) + 1;

            //Test is my code is true =))
            if (arrayFriend.get(0).getDate().getDay() == tempDay &&
                    arrayFriend.get(0).getDate().getMonth() == tempMonth){
                Toast.makeText(this, "Today is " + arrayFriend.get(0).getPerson().getFirstName() + "'s birthday", Toast.LENGTH_SHORT).show();
            }
            /*for(int i=0; i < arrayFriend.size(); i++){

                if (arrayFriend.get(i).getDate().getDay() == tempDay &&
                        arrayFriend.get(i).getDate().getMonth() == tempMonth){
                    arrayUpcomingBirthday.add("Today is " + arrayFriend.get(i).getPerson().getFirstName() + "'s birthday");
                } else {

                    for(int j=1; j <= 7; j++){   //Check whether anyone have birthday in next 7 days

                        if (arrayFriend.get(i).getDate().getDay() == tempDay + j &&
                                arrayFriend.get(i).getDate().getMonth() == calendar.get(Calendar.MONTH) + 1){
                            arrayUpcomingBirthday.add( arrayFriend.get(i).getPerson().getFirstName() +
                                    "'s birthday coming in" + j + "days" );
                        }

                    }
                }

            }

            dialog.setContentView(R.layout.dialog_notification);
            dialog.setTitle("Notification");
            listViewUpcomingBirthday = (ListView) dialog.findViewById(R.id.listViewUpcomingBirthday);
            ArrayAdapter<String> adapterDialog = new ArrayAdapter<>(MainActivity.this,
                    R.layout.dialog_notification, arrayUpcomingBirthday );
            listViewUpcomingBirthday.setAdapter(adapterDialog);
            dialog.show();
            */


        }

    }

    //Set the listview follow kind of friend: male, female, abc
    public void setAdapter(ArrayList<Friend> arrFriend){
        adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, arrFriend);
        listFriend.setAdapter(adapter);
    }

    //Put data to spinner
    String key;
    public void putValueToSpinner(){
        spinner_sort = findViewById(R.id.spinner_sort);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.key_sort));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sort.setAdapter(adapter);

        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                key = parent.getItemAtPosition(position).toString();

                if (key.contains("Male")){
                    setAdapter(arrayMale);
                }

                if (key.contains("Female")){
                    setAdapter(arrayFemale);
                }

              

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void showDialogEvent(){
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_notification);
        dialog.setTitle("Notification");
        listViewUpcomingBirthday = (ListView) dialog.findViewById(R.id.listViewUpcomingBirthday);
        dialog.show();

    }



}

