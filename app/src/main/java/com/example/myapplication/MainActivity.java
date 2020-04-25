package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.binarysimple.edittextpuzzle.EditTextPuzzle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        EditTextPuzzle editTextPuzzle = findViewById(R.id.puzzle);
        editTextPuzzle.setText("TE$TABLE");
    }
}
