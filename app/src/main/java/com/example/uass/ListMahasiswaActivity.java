package com.example.uass;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListMahasiswaActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mahasiswa);

        dbHelper = new DBHelper(this);

        ArrayList<String> listMahasiswa = getDataMahasiswa();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMahasiswa);

        ListView listViewMahasiswa = findViewById(R.id.listViewMahasiswa);
        listViewMahasiswa.setAdapter(adapter);

        listViewMahasiswa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedNIM = listMahasiswa.get(position).split("\n")[0].substring(5); // Mendapatkan NIM dari item yang dipilih
                Intent intent = new Intent(ListMahasiswaActivity.this, UpdateHapusMahasiswaActivity.class);
                intent.putExtra("NIM", selectedNIM); // Mengirim NIM ke UpdateHapusMahasiswaActivity
                startActivity(intent);
            }
        });
    }

    private ArrayList<String> getDataMahasiswa() {
        ArrayList<String> listMahasiswa = new ArrayList<>();

        // Mengambil data mahasiswa dari database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DBHelper.COLUMN_NIM,
                DBHelper.COLUMN_NAMA_MAHASISWA,
                DBHelper.COLUMN_PROGRAM_STUDI,
                DBHelper.COLUMN_ANGKATAN
        };

        Cursor cursor = db.query(
                DBHelper.TABLE_MAHASISWA,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String nim = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NIM));
            String namaMahasiswa = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAMA_MAHASISWA));
            String programStudi = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRAM_STUDI));
            String angkatan = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ANGKATAN));

            listMahasiswa.add("NIM: " + nim + "\nNama: " + namaMahasiswa + "\nProgram Studi: " + programStudi + "\nAngkatan: " + angkatan);
        }

        cursor.close();
        return listMahasiswa;
    }
}
