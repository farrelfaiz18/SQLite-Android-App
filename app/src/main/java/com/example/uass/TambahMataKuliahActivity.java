package com.example.uass;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TambahMataKuliahActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_mata_kuliah);

        dbHelper = new DBHelper(this);

        final EditText editTextKodeMK = findViewById(R.id.editTextKodeMK);
        final EditText editTextNamaMK = findViewById(R.id.editTextNamaMK);
        final EditText editTextSKS = findViewById(R.id.editTextSKS);
        final EditText editTextDeskripsiMK = findViewById(R.id.editTextDeskripsiMK);

        Button buttonTambahMK = findViewById(R.id.buttonTambahMK);
        buttonTambahMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahDataMataKuliah(
                        editTextKodeMK.getText().toString(),
                        editTextNamaMK.getText().toString(),
                        Integer.parseInt(editTextSKS.getText().toString()),
                        editTextDeskripsiMK.getText().toString()
                );
            }
        });
    }

    private void tambahDataMataKuliah(String kodeMK, String namaMK, int sks, String deskripsiMK) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_KODE_MK, kodeMK);
        values.put(DBHelper.COLUMN_NAMA_MK, namaMK);
        values.put(DBHelper.COLUMN_SKS, sks);
        values.put(DBHelper.COLUMN_DESKRIPSI_MK, deskripsiMK);

        long result = db.insert(DBHelper.TABLE_MATA_KULIAH, null, values);

        if (result == -1) {
            Toast.makeText(this, "Gagal menambahkan data mata kuliah", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data mata kuliah berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            finish(); // Tutup aktivitas setelah berhasil menambahkan data
        }
    }
}
