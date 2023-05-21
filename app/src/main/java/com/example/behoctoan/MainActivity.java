package com.example.behoctoan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.behoctoan.activities.SetActivityFive;
import com.example.behoctoan.activities.SetsActivity;
import com.example.behoctoan.activities.SetsActivityFour;
import com.example.behoctoan.activities.SetsActivityThree;
import com.example.behoctoan.activities.SetsTwoActivity;

public class MainActivity extends AppCompatActivity {
    CardView class_1, class_2, class_3, class_4, class_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        class_1 = findViewById(R.id.class_1);
        class_2 = findViewById(R.id.class_2);
        class_3 = findViewById(R.id.class_3);
        class_4 = findViewById(R.id.class_4);
        class_5 = findViewById(R.id.class_5);

        class_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetsActivity.class);
                startActivity(intent);
            }
        });
        class_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetsTwoActivity.class);
                startActivity(intent);
            }
        });

        class_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetsActivityThree.class);
                startActivity(intent);
            }
        });
        class_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetsActivityFour.class);
                startActivity(intent);
            }
        });
        class_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetActivityFive.class);
                startActivity(intent);
            }
        });
    }
}