package com.example.uass;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class UpdateHapusMataKuliahActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private String kodeMK;

    private EditText editTextNamaMK;
    private EditText editTextSKS;
    private EditText editTextDeskripsiMK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hapus_mata_kuliah);

        dbHelper = new DBHelper(this);

        editTextNamaMK = findViewById(R.id.editTextNamaUpdateMataKuliah);
        editTextSKS = findViewById(R.id.editTextSKSUpdateMataKuliah);
        editTextDeskripsiMK = findViewById(R.id.editTextDeskripsiUpdateMataKuliah);

        Intent intent = getIntent();
        if (intent.hasExtra("KodeMK")) {
            kodeMK = intent.getStringExtra("KodeMK");
            Log.d("UpdateHapusMataKuliah", "Received KodeMK: " + kodeMK);
            displayMataKuliahData(kodeMK);
        } else {
            Toast.makeText(this, "Kode MK tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
        }

        Button buttonUpdateMataKuliah = findViewById(R.id.buttonUpdateMataKuliah);
        Button buttonHapusMataKuliah = findViewById(R.id.buttonHapusMataKuliah);

        buttonUpdateMataKuliah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMataKuliahData();
            }
        });

        buttonHapusMataKuliah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusMataKuliah();
            }
        });
    }

    private void displayMataKuliahData(String kodeMK) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DBHelper.COLUMN_NAMA_MK,
                DBHelper.COLUMN_SKS,
                DBHelper.COLUMN_DESKRIPSI_MK
        };

        String selection = DBHelper.COLUMN_KODE_MK + " = ?";
        String[] selectionArgs = {kodeMK};

        Cursor cursor = db.query(
                DBHelper.TABLE_MATA_KULIAH,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Log.d("UpdateHapusMataKuliah", "SQL Query: " + cursor.toString());
        Log.d("UpdateHapusMataKuliah", "Column Names: " + Arrays.toString(cursor.getColumnNames()));

        int numRows = cursor.getCount();
        Log.d("UpdateHapusMataKuliah", "Number of rows in the cursor: " + numRows);

        if (cursor.moveToFirst()) {
            String namaMK = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAMA_MK));
            int sks = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SKS));
            String deskripsiMK = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DESKRIPSI_MK));

            editTextNamaMK.setText(namaMK);
            editTextSKS.setText(String.valueOf(sks));
            editTextDeskripsiMK.setText(deskripsiMK);

            Log.d("UpdateHapusMataKuliah", "Displaying data for KodeMK: " + kodeMK);
            Log.d("UpdateHapusMataKuliah", "NamaMK: " + namaMK + ", SKS: " + sks + ", DeskripsiMK: " + deskripsiMK);
        } else {
            Log.d("UpdateHapusMataKuliah", "No data found for KodeMK: " + kodeMK);
        }

        cursor.close();
    }



    private void updateMataKuliahData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String namaMK = editTextNamaMK.getText().toString().trim();
        int sks = Integer.parseInt(editTextSKS.getText().toString().trim());
        String deskripsiMK = editTextDeskripsiMK.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAMA_MK, namaMK);
        values.put(DBHelper.COLUMN_SKS, sks);
        values.put(DBHelper.COLUMN_DESKRIPSI_MK, deskripsiMK);

        String selection = DBHelper.COLUMN_KODE_MK + " = ? COLLATE NOCASE";

        String[] selectionArgs = {kodeMK};

        int count = db.update(
                DBHelper.TABLE_MATA_KULIAH,
                values,
                selection,
                selectionArgs
        );

        if (count > 0) {
            Toast.makeText(this, "Data mata kuliah berhasil diupdate", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateHapusMataKuliahActivity.this, ListMataKuliahActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();;
        } else {
            Toast.makeText(this, "Gagal update data mata kuliah", Toast.LENGTH_SHORT).show();
        }
    }

    private void hapusMataKuliah() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = DBHelper.COLUMN_KODE_MK + " = ?";
        String[] selectionArgs = {kodeMK};

        int count = db.delete(
                DBHelper.TABLE_MATA_KULIAH,
                selection,
                selectionArgs
        );

        if (count > 0) {
            Toast.makeText(this, "Data mata kuliah berhasil dihapus", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateHapusMataKuliahActivity.this, ListMataKuliahActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Gagal hapus data mata kuliah", Toast.LENGTH_SHORT).show();
        }
    }
}
