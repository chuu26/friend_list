package com.example.fengtimo.app2;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.TimePickerDialog;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity{


    TextView txtday,txttime;
    EditText etxt,e2;
    Button btnday,btntime,btnadd;
    ListView lv;

    //lấy lịch
    Calendar cal;
    //tạo adapter để hiển thị listview
    ArrayList<dateday>arr= new ArrayList<dateday>();
    ArrayAdapter<dateday>adapter=null;

    Date dateFinish;
    Date hourFinish;


    //khai báo firebase ra ngoài toàn cục
    Firebase fb;



    //báo thức
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Firebase.setAndroidContext(this);
        fb = new Firebase("https://save-list.firebaseio.com/");



        txtday = findViewById(R.id.txtday);
        txttime = findViewById(R.id.txttime);
        etxt = findViewById(R.id.etxt);
        e2 = findViewById(R.id.e2);
        btnadd = findViewById(R.id.btnadd);
        btnday = findViewById(R.id.btnday);
        btntime = findViewById(R.id.btntime);
        lv = findViewById(R.id.lv);


        alarmManager =(AlarmManager)getSystemService(ALARM_SERVICE);
        final Intent id =new Intent(MainActivity.this,Alarm.class);



        //chèn vào adapter
        adapter = new ArrayAdapter<dateday>(this, android.R.layout.simple_list_item_1,arr);
        //gán adapter vô list
        lv.setAdapter(adapter);




        ////////////////////////
        //Lấy lịch từ hệ thống
        cal = Calendar.getInstance();
        //simpledateformat là hàm dùng để tạo đối tượng
        SimpleDateFormat aa=null;
        //định dạng kiểu ngày
        aa=new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        String date= aa.format(cal.getTime());
        txtday.setText(date);


        //kiểu giờ
        //hh viết thường là định dạng 12 giờ
        //a đại diện cho AM or PM
        //Muốn cho nó sang 24h thì HH
        aa=new SimpleDateFormat("hh:mm a",Locale.getDefault());
        String time= aa.format(cal.getTime());
        txttime.setText(time);

        ///////////////
        txttime.setTag(aa.format(cal.getTime()));
        //////////////////



        etxt.requestFocus();
        dateFinish=cal.getTime();
        hourFinish=cal.getTime();




        //set lệnh  cho nút
        btnday.setOnClickListener(new Mybtn());
        btntime.setOnClickListener(new Mybtn());
        lv.setOnItemClickListener(new Mylist());
        lv.setOnItemLongClickListener(new Mylist());




        ////////////////////////////////
        ///////////////////////////////
        //Thêm dữ liệu vô rồi update data từ firebase về.
        //////////////////////////////
        fb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arr.add(dataSnapshot.getValue(dateday.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)  {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });







        //////////////////////////////////////////////////
        ////////////////////////////////
        //hàm này xử lý khi đưa vào list view
        ////////////////////////////////////

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sc=etxt.getText()+"";
                String ss=e2.getText()+"";
                final dateday sd=new dateday(ss,sc,dateFinish,hourFinish);
                //hàm push này đẩy data lên firebase
                fb.push().setValue(sd);
                e2.setText("");
                etxt.setText("");
                id.putExtra("extra","on");
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,id,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
            }
        });


    }



////////////////////////////////////////
    //ấn giữ nó tự xóa nhờ LongClick đó
///////////////
    ///////////////////////////////////
    private class Mylist implements android.widget.AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent m2 =new Intent(MainActivity.this,Main2.class);
            m2.putExtra("abc",arr.get(position).getcontent());
            startActivity(m2);
        }


        ////cái này nè..giử nó tầm 2s nó tự xóa
        ///xịn sò ghê
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
           arr.remove(position);
            //Xóa xong cập nhật lại ngay
           adapter.notifyDataSetChanged();
            return false;
        }

    }






///////////////////////////////////

    private class Mybtn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //nó dùng như java cho mình nhiều sự lựa chọn
            switch (v.getId()) {
                case R.id.btnday:
                    showDate();
                    break;

                case R.id.btntime:
                    showTime();
                    break;


            }
        }


    }

    //////
    //cái này implement nó là private
    //nhưng nó bị lỗi gì đó
    //tao đổi qua public luôn
    ////////





    //////////////////
    //Set thời gian đây
    public void showTime() {
        final int Time=cal.get(Calendar.HOUR_OF_DAY);
        int minute =cal.get(Calendar.MINUTE);
        TimePickerDialog yes=new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String a=hourOfDay +":"+minute;
                int val= hourOfDay;
                if(val > 12)
                    val = val - 12;
                txttime.setText(val+":"+minute);
                txttime.setTag(a);

                //Lưu giờ kết thúc
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                cal.set(Calendar.MINUTE,minute);
                hourFinish=cal.getTime();
            }
        },Time,minute,false);
        yes.show();
    }






    /////////////////////
    //Set ngày tháng năm đây
    public void showDate() {
        /////////
        //Tao tạo hàm anounymous
        DatePickerDialog.OnDateSetListener fun= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                txtday.setText((day)+"/"+(month+1)+"/"+year);
                cal.set(year,month,day);
                dateFinish=cal.getTime();
            }
        };

        //này là hàm DatePickerDialog
        String s=txtday.getText()+"";
        String strArrtmp[]=s.split("/");
        int d=Integer.parseInt(strArrtmp[0]);
        int m=Integer.parseInt(strArrtmp[1])-1;
        int y=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                MainActivity.this,
                fun, y, m, d);
        pic.show();
    }


}
