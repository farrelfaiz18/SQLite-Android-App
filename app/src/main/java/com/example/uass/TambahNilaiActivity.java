package com.example.uass;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TambahNilaiActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_nilai);

        dbHelper = new DBHelper(this);

        final EditText editTextNIMNilai = findViewById(R.id.editTextNIMNilai);
        final EditText editTextKodeMKNilai = findViewById(R.id.editTextKodeMKNilai);
        final EditText editTextNilai = findViewById(R.id.editTextNilai);

        Button buttonTambahNilai = findViewById(R.id.buttonTambahNilai);
        buttonTambahNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahDataNilai(
                        editTextNIMNilai.getText().toString(),
                        editTextKodeMKNilai.getText().toString(),
                        Double.parseDouble(editTextNilai.getText().toString())
                );
            }
        });
    }

    private void tambahDataNilai(String nim, String kodeMK, double nilai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NILAI_NIM, nim);
        values.put(DBHelper.COLUMN_NILAI_KODE_MK, kodeMK);
        values.put(DBHelper.COLUMN_NILAI, nilai);

        long result = db.insert(DBHelper.TABLE_NILAI, null, values);

        if (result == -1) {
            Toast.makeText(this, "Gagal menambahkan data nilai", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data nilai berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            finish(); // Tutup aktivitas setelah berhasil menambahkan data
        }
    }
}
