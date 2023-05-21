package com.example.behoctoan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.behoctoan.Adapters.SetAdapter;
import com.example.behoctoan.Models.SetModel;
import com.example.behoctoan.R;
import com.example.behoctoan.databinding.ActivitySetsBinding;
import com.example.behoctoan.databinding.ActivitySetsFourBinding;

import java.util.ArrayList;

public class SetsActivityFour extends AppCompatActivity {

    ActivitySetsFourBinding binding;
    ArrayList<SetModel>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetsFourBinding.inflate(getLayoutInflater());
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



        SetAdapter adapter = new SetAdapter(this, list);
        binding.setsRecy.setAdapter(adapter);
    }
}