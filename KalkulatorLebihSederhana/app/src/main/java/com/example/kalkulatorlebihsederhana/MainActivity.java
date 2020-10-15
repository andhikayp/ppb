package com.example.kalkulatorlebihsederhana;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText editBil1, editBil2;
    private TextView textHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editBil1 = findViewById(R.id.text1);
        editBil2 = findViewById(R.id.text2);
        textHasil = findViewById(R.id.textViewHasil);
    }

    public void tambah(View v){
        textHasil.setText(hitung("+"));
    }

    public void kurang(View v){
        textHasil.setText(hitung("-"));
    }

    public void kali(View v){
        textHasil.setText(hitung("*"));
    }

    public void bagi(View v){
        textHasil.setText(hitung("/"));
    }

    public String hitung(String s){
        float bil1,bil2,hasil;
        if (editBil1.getText().length()>0 && editBil2.getText().length()>0){
            bil1 = Float.parseFloat(editBil1.getText().toString());
            bil2 = Float.parseFloat(editBil2.getText().toString());
            if (s == "+") hasil = bil1+bil2;
            else if(s == "-") hasil = bil1-bil2;
            else if(s == "/") hasil = bil1/bil2;
            else if(s == "*") hasil = bil1*bil2;
            else hasil = 0;
            return bil1+s+bil2+"="+hasil;
        }
        else return "Masukkan angka!";
    }

//    public void kurang(View v){
//        float bil1,bil2,hasil;
//        bil1 = Float.parseFloat(editBil1.getText().toString());
//        bil2 = Float.parseFloat(editBil2.getText().toString());
//        hasil = bil1-bil2;
//        textHasil.setText(bil1+"-"+bil2+"="+hasil);
//    }
//
//    public void kali(View v){
//        float bil1,bil2,hasil;
//        bil1 = Float.parseFloat(editBil1.getText().toString());
//        bil2 = Float.parseFloat(editBil2.getText().toString());
//        hasil = bil1*bil2;
//        textHasil.setText(bil1+"x"+bil2+"="+hasil);
//    }
//
//    public void bagi(View v){
//        float bil1,bil2,hasil;
//        bil1 = Float.parseFloat(editBil1.getText().toString());
//        bil2 = Float.parseFloat(editBil2.getText().toString());
//        hasil = bil1/bil2;
//        textHasil.setText(bil1+"/"+bil2+"="+hasil);
//    }
}