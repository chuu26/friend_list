package com.example.thuyvynguyen.my_friend_list_project;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ArrayList<Friend> arrayFriend = new ArrayList<>();
    ArrayList<Friend> arrayMale = new ArrayList<>();
    ArrayList<Friend> arrayFemale = new ArrayList<>();

    ListView listFriend;
    Button btnAddNewFriend;
    ArrayAdapter<Friend> adapter;
    static final int MY_REQUEST_CODE = 100;

    Date date;
    Person person;
    Friend friend;

    Spinner spinner_sort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listFriend = (ListView) findViewById(R.id.listFriend);


        //handle event btn Click and intent to activity add new friend
        btnClickAndIntent();


        putValueToSpinner();




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
    String middleName, firstName, lastName, sex;
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

            sex        = data.getStringExtra("sex");

            friend = new Friend(person, date, sex);

            //Add friend vao tung array male va female tuong ung
            if ("Male".contains(sex)){
                arrayMale.add(friend);
            }
            if ("Female".contains(sex)){
                arrayFemale.add(friend);
            }

            arrayFriend.add(friend);

            Collections.sort(arrayFriend, new Comparator<Friend>() {
                @Override
                public int compare(Friend o1, Friend o2) {
                    return o1.getPerson().getFirstName().compareToIgnoreCase(o2.getPerson().getFirstName());
                }
            });

            //De de dang tuy chinh arrayList vao adapter
            setAdapter(arrayFriend);
        }

    }

    public void setAdapter(ArrayList<Friend> arrFriend){
        adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, arrFriend);
        listFriend.setAdapter(adapter);
    }

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

                if (key.contains("ABC")){
                    setAdapter(arrayFriend);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.options_long_click, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.option_delete:
                arrayFriend.remove(1);
                break;

            default: return super.onContextItemSelected(item);
        }
    }*/

    /*public void notification(){

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (getApplicationContext()).setContentTitle(tittle).setContentText(body).
                setContentTitle(subject).setSmallIcon(R.drawable.ic_launcher_foreground.build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);

    }*/

}

