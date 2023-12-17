package com.example.uass;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListDosenActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dosen);

        dbHelper = new DBHelper(this);

        ArrayList<String> listDosen = getDataDosen();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listDosen);

        ListView listViewDosen = findViewById(R.id.listViewDosen);
        listViewDosen.setAdapter(adapter);

        listViewDosen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedNIP = listDosen.get(position).split("\n")[0].substring(5); // Mendapatkan NIP dari item yang dipilih
                Intent intent = new Intent(ListDosenActivity.this, UpdateHapusDosenActivity.class);
                intent.putExtra("NIP", selectedNIP); // Mengirim NIP ke UpdateHapusDosenActivity
                startActivity(intent);
            }
        });
    }

    private ArrayList<String> getDataDosen() {
        ArrayList<String> listDosen = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DBHelper.COLUMN_NIP,
                DBHelper.COLUMN_NAMA_DOSEN,
                DBHelper.COLUMN_PROGRAM_STUDI_DOSEN
        };

        Cursor cursor = db.query(
                DBHelper.TABLE_DOSEN,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String nip = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NIP));
            String namaDosen = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAMA_DOSEN));
            String programStudiDosen = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROGRAM_STUDI_DOSEN));

            listDosen.add("NIP: " + nip + "\nNama Dosen: " + namaDosen + "\nProgram Studi Dosen: " + programStudiDosen);
        }

        cursor.close();
        return listDosen;
    }
}
