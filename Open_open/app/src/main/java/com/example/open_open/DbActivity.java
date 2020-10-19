package com.example.open_open;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DbActivity extends AppCompatActivity {
    private EditText nrp, nama;
    private Button simpan, ambildata;
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper Opendb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        nrp = findViewById(R.id.nrp);
        nama = findViewById(R.id.nama);
        simpan = findViewById(R.id.simpan);
        ambildata = findViewById(R.id.ambildata);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });

        ambildata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambildata();
            }
        });

        Opendb = new SQLiteOpenHelper(this, "db.sql", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };

        dbku = Opendb.getWritableDatabase();
        dbku.execSQL("create table if not exists mhs(nrp TEXT, nama TEXT);");
    }

    @Override
    protected void onStop() {
        dbku.close();
        Opendb.close();
        super.onStop();
    }

    private void simpan(){
        ContentValues dataku = new ContentValues();
        dataku.put("nrp", nrp.getText().toString());
        dataku.put("nama", nama.getText().toString());
        dbku.insert("mhs", null, dataku);
        Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_LONG).show();
    }

    private void ambildata(){
        Cursor cur = dbku.rawQuery("select * from mhs where nrp='"+nrp.getText().toString()+"'", null);
        if (cur.getCount() > 0){
            Toast.makeText(this, "Data ditemukan sejumlah " + cur.getCount(), Toast.LENGTH_LONG).show();
            cur.moveToFirst();
            nama.setText(cur.getString(cur.getColumnIndex("nama")));
        } else {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_LONG).show();
        }
    }
}