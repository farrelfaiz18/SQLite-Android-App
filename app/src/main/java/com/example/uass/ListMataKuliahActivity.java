package com.example.uass;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListMataKuliahActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mata_kuliah);

        dbHelper = new DBHelper(this);

        ArrayList<String> listMataKuliah = getDataMataKuliah();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMataKuliah);

        ListView listViewMataKuliah = findViewById(R.id.listViewMataKuliah);
        listViewMataKuliah.setAdapter(adapter);

        listViewMataKuliah.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = listMataKuliah.get(position);
                String[] parts = selectedItem.split("\n");

                // Assuming the first part contains "Kode MK: XXXX"
                String selectedKodeMK = parts[0].substring(parts[0].indexOf(":") + 2);

                Log.d("ListMataKuliahActivity", "Selected KodeMK: " + selectedKodeMK);

                Intent intent = new Intent(ListMataKuliahActivity.this, UpdateHapusMataKuliahActivity.class);
                intent.putExtra("KodeMK", selectedKodeMK);
                startActivity(intent);
            }
        });
    }

    private ArrayList<String> getDataMataKuliah() {
        ArrayList<String> listMataKuliah = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DBHelper.COLUMN_KODE_MK,
                DBHelper.COLUMN_NAMA_MK,
                DBHelper.COLUMN_SKS,
                DBHelper.COLUMN_DESKRIPSI_MK
        };

        Cursor cursor = db.query(
                DBHelper.TABLE_MATA_KULIAH,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // Log the number of rows returned by the query
        Log.d("ListMataKuliahActivity", "Number of rows in the Mata Kuliah table: " + cursor.getCount());

        while (cursor.moveToNext()) {
            String kodeMK = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_KODE_MK));
            String namaMK = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAMA_MK));
            int sks = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SKS));
            String deskripsiMK = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DESKRIPSI_MK));

            listMataKuliah.add("Kode MK: " + kodeMK + "\nNama MK: " + namaMK + "\nSKS: " + sks + "\nDeskripsi MK: " + deskripsiMK);

            // Log each Mata Kuliah entry
            Log.d("ListMataKuliahActivity", "Data retrieved: Kode MK: " + kodeMK + ", Nama MK: " + namaMK + ", SKS: " + sks + ", Deskripsi MK: " + deskripsiMK);
        }

        cursor.close();
        return listMataKuliah;
    }
}

