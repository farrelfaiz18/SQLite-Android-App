package com.example.uass;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateHapusMahasiswaActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private String nim;

    private EditText editTextNama;
    private EditText editTextProgramStudi;
    private EditText editTextAngkatan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hapus_mahasiswa);

        dbHelper = new DBHelper(this);

        editTextNama = findViewById(R.id.editTextNamaUpdateMahasiswa);
        editTextProgramStudi = findViewById(R.id.editTextProgramStudiUpdateMahasiswa);
        editTextAngkatan = findViewById(R.id.editTextAngkatanUpdateMahasiswa);

        // Mendapatkan NIM dari Intent
        Intent intent = getIntent();
        if (intent.hasExtra("NIM")) {
            nim = intent.getStringExtra("NIM");
            // Menggunakan NIM untuk mengambil data mahasiswa dari database dan menampilkan di UI
            displayMahasiswaData(nim);
        } else {
            Toast.makeText(this, "NIM tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish(); // Tutup aktivitas jika NIM tidak ditemukan
        }

        Button buttonUpdateMahasiswa = findViewById(R.id.buttonUpdateMahasiswa);
        Button buttonHapusMahasiswa = findViewById(R.id.buttonHapusMahasiswa);

        buttonUpdateMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil metode untuk menyimpan perubahan data mahasiswa ke database
                updateMahasiswaData();
            }
        });

        buttonHapusMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil metode untuk menghapus data mahasiswa dari database
                hapusMahasiswa();
            }
        });
    }

    private void displayMahasiswaData(String nim) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DBHelper.COLUMN_NAMA_MAHASISWA,
                DBHelper.COLUMN_PROGRAM_STUDI,
                DBHelper.COLUMN_ANGKATAN
        };

        String selection = DBHelper.COLUMN_NIM + " = ?";
        String[] selectionArgs = {nim};

        Cursor cursor = db.query(
                DBHelper.TABLE_MAHASISWA,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String nama = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAMA_MAHASISWA));
            String programStudi = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRAM_STUDI));
            String angkatan = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ANGKATAN));

            editTextNama.setText(nama);
            editTextProgramStudi.setText(programStudi);
            editTextAngkatan.setText(angkatan);
        }

        cursor.close();
    }

    private void updateMahasiswaData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String nama = editTextNama.getText().toString().trim();
        String programStudi = editTextProgramStudi.getText().toString().trim();
        String angkatan = editTextAngkatan.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAMA_MAHASISWA, nama);
        values.put(DBHelper.COLUMN_PROGRAM_STUDI, programStudi);
        values.put(DBHelper.COLUMN_ANGKATAN, angkatan);

        String selection = DBHelper.COLUMN_NIM + " = ?";
        String[] selectionArgs = {nim};

        int count = db.update(
                DBHelper.TABLE_MAHASISWA,
                values,
                selection,
                selectionArgs
        );

        if (count > 0) {
            Toast.makeText(this, "Data mahasiswa berhasil diupdate", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateHapusMahasiswaActivity.this, ListMahasiswaActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            // Kembali ke halaman sebelumnya setelah berhasil update
        } else {
            Toast.makeText(this, "Gagal update data mahasiswa", Toast.LENGTH_SHORT).show();
        }
    }

    private void hapusMahasiswa() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = DBHelper.COLUMN_NIM + " = ?";
        String[] selectionArgs = {nim};

        int count = db.delete(
                DBHelper.TABLE_MAHASISWA,
                selection,
                selectionArgs
        );

        if (count > 0) {
            Toast.makeText(this, "Data mahasiswa berhasil dihapus", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateHapusMahasiswaActivity.this, ListMahasiswaActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Kembali ke halaman sebelumnya setelah berhasil hapus
        } else {
            Toast.makeText(this, "Gagal hapus data mahasiswa", Toast.LENGTH_SHORT).show();
        }
    }
}
