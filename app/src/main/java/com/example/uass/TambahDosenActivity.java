package com.example.uass;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TambahDosenActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_dosen);

        dbHelper = new DBHelper(this);

        final EditText editTextNIP = findViewById(R.id.editTextNIP);
        final EditText editTextNamaDosen = findViewById(R.id.editTextNamaDosen);
        final EditText editTextProgramStudiDosen = findViewById(R.id.editTextProgramStudiDosen);

        Button buttonTambahDosen = findViewById(R.id.buttonTambahDosen);
        buttonTambahDosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahDataDosen(
                        editTextNIP.getText().toString(),
                        editTextNamaDosen.getText().toString(),
                        editTextProgramStudiDosen.getText().toString()
                );
            }
        });
    }

    private void tambahDataDosen(String nip, String namaDosen, String programStudiDosen) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NIP, nip);
        values.put(DBHelper.COLUMN_NAMA_DOSEN, namaDosen);
        values.put(DBHelper.COLUMN_PROGRAM_STUDI_DOSEN, programStudiDosen);

        long result = db.insert(DBHelper.TABLE_DOSEN, null, values);

        if (result == -1) {
            Toast.makeText(this, "Gagal menambahkan data dosen", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data dosen berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            finish(); // Tutup aktivitas setelah berhasil menambahkan data
        }
    }
}
