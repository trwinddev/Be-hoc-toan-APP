package com.example.behoctoan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.behoctoan.activities.SetsActivity;
import com.example.behoctoan.activities.SetsActivityThree;
import com.example.behoctoan.activities.SetsActivityTwo;

public class MainActivity extends AppCompatActivity {
    CardView class_1, class_2, class_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        class_1 = findViewById(R.id.class_1);
        class_2 = findViewById(R.id.class_2);
        class_3 = findViewById(R.id.class_3);

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
                Intent intent = new Intent(MainActivity.this, SetsActivityTwo.class);
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
    }
}