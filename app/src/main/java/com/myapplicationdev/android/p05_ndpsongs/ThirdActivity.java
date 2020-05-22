package com.myapplicationdev.android.p05_ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ThirdActivity extends AppCompatActivity {

    TextView tvID;
    EditText etTitle, etName, etYear;
    RadioGroup rg2;
    RadioButton rb1, rb2, rb3, rb4, rb5;
    RadioButton rbstars;
    Button btnUpdate, btnDelete, btnCancel;
    Song data;
    int stars = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        tvID = findViewById(R.id.tvId);
        etTitle = findViewById(R.id.etTitle);
        etName = findViewById(R.id.etName);
        etYear = findViewById(R.id.etYear);
        rg2 = findViewById(R.id.rg2);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        ArrayList<RadioButton> rbList = new ArrayList<>(Arrays.asList(rb1, rb2, rb3, rb4, rb5));

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");
        tvID.setText(data.getId() + "");
        etTitle.setText(data.getTitle());
        etName.setText(data.getSingers());
        etYear.setText(data.getYear() + "");

        rbList.get(data.getStars()-1).setChecked(true);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                String title = etTitle.getText().toString();
                String name = etName.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());
                stars = rg2.getCheckedRadioButtonId();
                rbstars = (RadioButton) findViewById(stars);
                int stars = Integer.parseInt(rbstars.getText().toString());

                Song newSong = new Song(data.getId(), title, name, year, stars);

                dbh.updateSong(newSong);
                dbh.close();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                dbh.deleteSong(data.getId());
                dbh.close();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}