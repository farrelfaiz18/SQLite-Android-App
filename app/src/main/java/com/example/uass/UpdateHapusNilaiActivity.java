package com.example.uass;

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

public class UpdateHapusNilaiActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private String selectedNIM;
    private String selectedKodeMK;

    private EditText editTextNilai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hapus_nilai);

        dbHelper = new DBHelper(this);

        editTextNilai = findViewById(R.id.editTextNilaiUpdate);

        // Mendapatkan data yang dikirimkan dari ListNilaiActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedNIM = extras.getString("NIM");
            selectedKodeMK = extras.getString("KodeMK");

            // Menampilkan Nilai di UI
            displayNilaiData(selectedNIM, selectedKodeMK);
        } else {
            Toast.makeText(this, "Data nilai tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish(); // Menutup aktivitas jika data nilai tidak ditemukan
        }

        Button buttonUpdateNilai = findViewById(R.id.buttonUpdateNilai);
        Button buttonHapusNilai = findViewById(R.id.buttonHapusNilai);

        buttonUpdateNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil metode untuk menyimpan perubahan data nilai ke database
                updateNilaiData();
            }
        });

        buttonHapusNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil metode untuk menghapus data nilai dari database
                hapusNilai();
            }
        });
    }

    private void displayNilaiData(String nim, String kodeMK) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DBHelper.COLUMN_NILAI
        };

        String selection = DBHelper.COLUMN_NIM + " = ? AND " + DBHelper.COLUMN_KODE_MK + " = ?";
        String[] selectionArgs = {nim, kodeMK};

        Cursor cursor = db.query(
                DBHelper.TABLE_NILAI,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            int nilai = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NILAI));
            editTextNilai.setText(String.valueOf(nilai));
        }

        cursor.close();
    }

    private void updateNilaiData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int nilai = Integer.parseInt(editTextNilai.getText().toString().trim());

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NILAI, nilai);

        String selection = DBHelper.COLUMN_NIM + " = ? AND " + DBHelper.COLUMN_KODE_MK + " = ?";
        String[] selectionArgs = {selectedNIM, selectedKodeMK};

        int count = db.update(
                DBHelper.TABLE_NILAI,
                values,
                selection,
                selectionArgs
        );

        if (count > 0) {
            Toast.makeText(this, "Data nilai berhasil diupdate", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateHapusNilaiActivity.this, ListNilaiActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Kembali ke halaman sebelumnya setelah berhasil update
        } else {
            Toast.makeText(this, "Gagal update data nilai", Toast.LENGTH_SHORT).show();
        }
    }

    private void hapusNilai() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = DBHelper.COLUMN_NIM + " = ? AND " + DBHelper.COLUMN_KODE_MK + " = ?";
        String[] selectionArgs = {selectedNIM, selectedKodeMK};

        int count = db.delete(
                DBHelper.TABLE_NILAI,
                selection,
                selectionArgs
        );

        if (count > 0) {
            Toast.makeText(this, "Data nilai berhasil dihapus", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateHapusNilaiActivity.this, ListNilaiActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Kembali ke halaman sebelumnya setelah berhasil hapus
        } else {
            Toast.makeText(this, "Gagal hapus data nilai", Toast.LENGTH_SHORT).show();
        }
    }
}
