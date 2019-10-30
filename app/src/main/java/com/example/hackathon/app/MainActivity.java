package com.example.hackathon.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hackathon.R;
import com.example.hackathon.app.model.Tile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tile tile = findViewById(R.id.imageButtonx);
        
    }
}
