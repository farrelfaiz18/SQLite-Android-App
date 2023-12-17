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

public class UpdateHapusDosenActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private String nip;

    private EditText editTextNamaDosen;
    private EditText editTextProgramStudiDosen;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hapus_dosen);

        dbHelper = new DBHelper(this);

        editTextNamaDosen = findViewById(R.id.editTextNamaUpdateDosen);
        editTextProgramStudiDosen = findViewById(R.id.editTextProgramStudiUpdateDosen);

        // Mendapatkan NIP dari Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("NIP")) {
            nip = extras.getString("NIP");
            // Menggunakan NIP untuk mengambil data dosen dari database dan menampilkan di UI
            displayDosenData(nip);
        } else {
            Toast.makeText(this, "NIP tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish(); // Tutup aktivitas jika NIP tidak ditemukan
        }

        Button buttonUpdateDosen = findViewById(R.id.buttonUpdateDosen);
        Button buttonHapusDosen = findViewById(R.id.buttonHapusDosen);

        buttonUpdateDosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil metode untuk menyimpan perubahan data dosen ke database
                updateDosenData();
            }
        });

        buttonHapusDosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil metode untuk menghapus data dosen dari database
                hapusDosen();
            }
        });
    }

    private void displayDosenData(String nip) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DBHelper.COLUMN_NAMA_DOSEN,
                DBHelper.COLUMN_PROGRAM_STUDI_DOSEN
        };

        String selection = DBHelper.COLUMN_NIP + " = ?";
        String[] selectionArgs = {nip};

        Cursor cursor = db.query(
                DBHelper.TABLE_DOSEN,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String namaDosen = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAMA_DOSEN));
            String programStudiDosen = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRAM_STUDI_DOSEN));

            editTextNamaDosen.setText(namaDosen);
            editTextProgramStudiDosen.setText(programStudiDosen);
        }

        cursor.close();
    }

    private void updateDosenData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String namaDosen = editTextNamaDosen.getText().toString().trim();
        String programStudiDosen = editTextProgramStudiDosen.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAMA_DOSEN, namaDosen);
        values.put(DBHelper.COLUMN_PROGRAM_STUDI_DOSEN, programStudiDosen);

        String selection = DBHelper.COLUMN_NIP + " = ?";
        String[] selectionArgs = {nip};

        int count = db.update(
                DBHelper.TABLE_DOSEN,
                values,
                selection,
                selectionArgs
        );

        if (count > 0) {
            Toast.makeText(this, "Data dosen berhasil diupdate", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateHapusDosenActivity.this, ListDosenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Kembali ke halaman sebelumnya setelah berhasil update
        } else {
            Toast.makeText(this, "Gagal update data dosen", Toast.LENGTH_SHORT).show();
        }
    }

    private void hapusDosen() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = DBHelper.COLUMN_NIP + " = ?";
        String[] selectionArgs = {nip};

        int count = db.delete(
                DBHelper.TABLE_DOSEN,
                selection,
                selectionArgs
        );

        if (count > 0) {
            Toast.makeText(this, "Data dosen berhasil dihapus", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateHapusDosenActivity.this, ListDosenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Kembali ke halaman sebelumnya setelah berhasil hapus
        } else {
            Toast.makeText(this, "Gagal hapus data dosen", Toast.LENGTH_SHORT).show();
        }
    }
}
