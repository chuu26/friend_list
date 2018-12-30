package com.example.fengtimo.app2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2 extends AppCompatActivity {
    TextView tv2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ///có thể tạo actionbar nhưng nó không giống nút back ảo
        tv2 = findViewById(R.id.tv2);



        String abc = getIntent().getStringExtra("abc");
        tv2.setText(abc);
    }
}
