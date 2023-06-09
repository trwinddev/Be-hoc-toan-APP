package com.example.behoctoan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.behoctoan.Adapters.SetAdapter;
import com.example.behoctoan.Adapters.SetAdapterFive;
import com.example.behoctoan.Models.SetModel;
import com.example.behoctoan.R;
import com.example.behoctoan.databinding.ActivitySetFiveBinding;
import com.example.behoctoan.databinding.ActivitySetsBinding;

import java.util.ArrayList;

public class SetActivityFive extends AppCompatActivity {

    ActivitySetFiveBinding binding;
    ArrayList<SetModel>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetFiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        list = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.setsRecy.setLayoutManager(linearLayoutManager);

        list.add(new SetModel("BÀI LUYỆN TẬP 1"));
        list.add(new SetModel("BÀI LUYỆN TẬP 2"));
        list.add(new SetModel("BÀI LUYỆN TẬP 3"));
        list.add(new SetModel("BÀI LUYỆN TẬP 4"));
        list.add(new SetModel("BÀI LUYỆN TẬP 5"));
        list.add(new SetModel("BÀI LUYỆN TẬP 6"));
        list.add(new SetModel("BÀI LUYỆN TẬP 7"));
        list.add(new SetModel("BÀI LUYỆN TẬP 8"));
        list.add(new SetModel("BÀI LUYỆN TẬP 9"));
        list.add(new SetModel("BÀI LUYỆN TẬP 10"));

        SetAdapterFive adapter = new SetAdapterFive(this, list);
        binding.setsRecy.setAdapter(adapter);

        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}