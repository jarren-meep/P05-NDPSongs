package com.myapplicationdev.android.p05_ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    Button btnFilter;
    Spinner spin;
    ArrayList<Song> songs;
    SongAdapter listAdapter;
    ArrayList<Integer> years;
    ArrayAdapter<Integer> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lv = findViewById(R.id.lv);
        btnFilter = findViewById(R.id.button);
        spin = findViewById(R.id.spin);

        DBHelper db = new DBHelper(SecondActivity.this);
        songs = db.getAllSongs();


        years = new ArrayList<Integer>();

        listAdapter = new SongAdapter(this, R.layout.row, songs);
        lv.setAdapter(listAdapter);

        spinnerAdapter  = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, years);
        spin.setAdapter(spinnerAdapter);

        for (Song i: songs) {
            if (!years.contains(i.getYear()))
                years.add(i.getYear());
        }
        db.close();

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer year = years.get(position);
                DBHelper db = new DBHelper(SecondActivity.this);
                songs.clear();
                songs.addAll(db.SortByYear(year));
                db.close();
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song target = songs.get(position);
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("yes", target);
                startActivityForResult(i, 9);
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(SecondActivity.this);
                songs.clear();
                songs.addAll(db.getAllStars());
                db.close();
                listAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 9) {
            DBHelper db = new DBHelper(SecondActivity.this);
            songs.clear();
            songs.addAll(db.getAllSongs());
            db.close();
            listAdapter.notifyDataSetChanged();
            refresh();
        }
    }

    public void refresh() {
        years.clear();
        for (Song i:songs) {
            if (!years.contains(i.getYear()))
                years.add(i.getYear());
        }
        spinnerAdapter.notifyDataSetChanged();
    }}
