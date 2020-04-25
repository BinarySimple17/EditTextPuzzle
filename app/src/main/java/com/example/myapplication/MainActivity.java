package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import ru.binarysimple.edittextpuzzle.EditTextPuzzle;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

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
        editTextPuzzle.setSecretText("testable");
        editTextPuzzle.setText("TE$TABLE");

        findViewById(R.id.button).setOnClickListener(v -> {
            String result = "Wrong!";
            if (editTextPuzzle.checkInput()) {
                result = "Correct!";
            }
            Snackbar.make(findViewById(R.id.button), result, LENGTH_SHORT).show();
        });
    }
}
