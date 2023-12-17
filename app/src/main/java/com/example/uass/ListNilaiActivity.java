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

public class ListNilaiActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nilai);

        dbHelper = new DBHelper(this);

        ArrayList<String> listNilai = getDataNilai();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listNilai);

        ListView listViewNilai = findViewById(R.id.listViewNilai);
        listViewNilai.setAdapter(adapter);

        listViewNilai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedNIM = listNilai.get(position).split("\n")[0].substring(5); // Mendapatkan NIM dari item yang dipilih
                String selectedKodeMK = listNilai.get(position).split("\n")[2].substring(13); // Mendapatkan Kode MK dari item yang dipilih
                Intent intent = new Intent(ListNilaiActivity.this, UpdateHapusNilaiActivity.class);
                intent.putExtra("NIM", selectedNIM);
                intent.putExtra("KodeMK", selectedKodeMK);
                startActivity(intent);
            }
        });
        // Tambahkan ini pada metode onCreate di ListNilaiActivity setelah mendapatkan data
        Log.d("ListNilaiActivity", "Jumlah Data Nilai: " + listNilai.size());
        Toast.makeText(this, "Jumlah Data Nilai: " + listNilai.size(), Toast.LENGTH_SHORT).show();

    }

    private ArrayList<String> getDataNilai() {
        ArrayList<String> listNilai = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT " +
                DBHelper.TABLE_NILAI + "." + DBHelper.COLUMN_NIM + ", " +
                DBHelper.TABLE_MAHASISWA + "." + DBHelper.COLUMN_NAMA_MAHASISWA + ", " +
                DBHelper.TABLE_MATA_KULIAH + "." + DBHelper.COLUMN_NAMA_MK + ", " +
                DBHelper.TABLE_NILAI + "." + DBHelper.COLUMN_NILAI +
                " FROM " + DBHelper.TABLE_NILAI +
                " JOIN " + DBHelper.TABLE_MAHASISWA +
                " ON " + DBHelper.TABLE_NILAI + "." + DBHelper.COLUMN_NIM + " = " + DBHelper.TABLE_MAHASISWA + "." + DBHelper.COLUMN_NIM +
                " JOIN " + DBHelper.TABLE_MATA_KULIAH +
                " ON " + DBHelper.TABLE_NILAI + "." + DBHelper.COLUMN_KODE_MK + " = " + DBHelper.TABLE_MATA_KULIAH + "." + DBHelper.COLUMN_KODE_MK;

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            String nim = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NIM));
            String namaMahasiswa = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAMA_MAHASISWA));
            String namaMataKuliah = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAMA_MK));
            int nilai = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NILAI));

            listNilai.add("NIM: " + nim + "\nNama Mahasiswa: " + namaMahasiswa + "\nMata Kuliah: " + namaMataKuliah + "\nNilai: " + nilai);
        }

        cursor.close();
        return listNilai;
    }
}
