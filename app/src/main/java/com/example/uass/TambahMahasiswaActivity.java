package com.example.uass;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TambahMahasiswaActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_mahasiswa);

        dbHelper = new DBHelper(this);

        final EditText editTextNIM = findViewById(R.id.editTextNIM);
        final EditText editTextNama = findViewById(R.id.editTextNama);
        final EditText editTextProgramStudi = findViewById(R.id.editTextProgramStudi);
        final EditText editTextAngkatan = findViewById(R.id.editTextAngkatan);

        Button buttonTambahMahasiswa = findViewById(R.id.buttonTambahMahasiswa);
        buttonTambahMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahDataMahasiswa(
                        editTextNIM.getText().toString(),
                        editTextNama.getText().toString(),
                        editTextProgramStudi.getText().toString(),
                        Integer.parseInt(editTextAngkatan.getText().toString())
                );
            }
        });
    }

    private void tambahDataMahasiswa(String nim, String nama, String programStudi, int angkatan) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NIM, nim);
        values.put(DBHelper.COLUMN_NAMA_MAHASISWA, nama);
        values.put(DBHelper.COLUMN_PROGRAM_STUDI, programStudi);
        values.put(DBHelper.COLUMN_ANGKATAN, angkatan);

        long result = db.insert(DBHelper.TABLE_MAHASISWA, null, values);

        if (result == -1) {
            Toast.makeText(this, "Gagal menambahkan data mahasiswa", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data mahasiswa berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            finish(); // Tutup aktivitas setelah berhasil menambahkan data
        }
    }
}
