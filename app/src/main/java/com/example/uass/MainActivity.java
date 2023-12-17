package com.example.uass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonTambahMahasiswa = findViewById(R.id.buttonTambahMahasiswa);
        Button buttonTambahDosen = findViewById(R.id.buttonTambahDosen);
        Button buttonTambahMataKuliah = findViewById(R.id.buttonTambahMataKuliah);
        Button buttonTambahNilai = findViewById(R.id.buttonTambahNilai);

        Button buttonListMahasiswa = findViewById(R.id.buttonListMahasiswa);
        Button buttonListDosen = findViewById(R.id.buttonListDosen);
        Button buttonListMataKuliah = findViewById(R.id.buttonListMataKuliah);
        Button buttonListNilai = findViewById(R.id.buttonListNilai);

        buttonTambahMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TambahMahasiswaActivity.class);
                startActivity(intent);
            }
        });

        buttonTambahDosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TambahDosenActivity.class);
                startActivity(intent);
            }
        });

        buttonTambahMataKuliah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TambahMataKuliahActivity.class);
                startActivity(intent);
            }
        });

        buttonTambahNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TambahNilaiActivity.class);
                startActivity(intent);
            }
        });

        buttonListMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListMahasiswaActivity.class);
                startActivity(intent);
            }
        });

        buttonListDosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDosenActivity.class);
                startActivity(intent);
            }
        });

        buttonListMataKuliah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListMataKuliahActivity.class);
                startActivity(intent);
            }
        });

        buttonListNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListNilaiActivity.class);
                startActivity(intent);
            }
        });
    }
}
