package com.example.uass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "university.db";
    private static final int DATABASE_VERSION = 1;

    // Tabel Mahasiswa
    public static final String TABLE_MAHASISWA = "mahasiswa";
    public static final String COLUMN_NIM = "nim";
    public static final String COLUMN_NAMA_MAHASISWA = "nama_mahasiswa";
    public static final String COLUMN_PROGRAM_STUDI = "program_studi";
    public static final String COLUMN_ANGKATAN = "angkatan";

    // Tabel Dosen
    public static final String TABLE_DOSEN = "dosen";
    public static final String COLUMN_NIP = "nip";
    public static final String COLUMN_NAMA_DOSEN = "nama_dosen";
    public static final String COLUMN_PROGRAM_STUDI_DOSEN = "program_studi_dosen";

    // Tabel Mata Kuliah
    public static final String TABLE_MATA_KULIAH = "mata_kuliah";
    public static final String COLUMN_KODE_MK = "kode_mk";
    public static final String COLUMN_NAMA_MK = "nama_mk";
    public static final String COLUMN_SKS = "sks";
    public static final String COLUMN_DESKRIPSI_MK = "deskripsi_mk";

    // Tabel Nilai
    public static final String TABLE_NILAI = "nilai";
    public static final String COLUMN_NILAI_NIM = "nim";
    public static final String COLUMN_NILAI_KODE_MK = "kode_mk";
    public static final String COLUMN_NILAI = "nilai";

    // Query untuk membuat tabel
    private static final String CREATE_TABLE_MAHASISWA = "CREATE TABLE " + TABLE_MAHASISWA + " (" +
            COLUMN_NIM + " TEXT PRIMARY KEY, " +
            COLUMN_NAMA_MAHASISWA + " TEXT, " +
            COLUMN_PROGRAM_STUDI + " TEXT, " +
            COLUMN_ANGKATAN + " INTEGER);";

    private static final String CREATE_TABLE_DOSEN = "CREATE TABLE " + TABLE_DOSEN + " (" +
            COLUMN_NIP + " TEXT PRIMARY KEY, " +
            COLUMN_NAMA_DOSEN + " TEXT, " +
            COLUMN_PROGRAM_STUDI_DOSEN + " TEXT);";

    private static final String CREATE_TABLE_MATA_KULIAH = "CREATE TABLE " + TABLE_MATA_KULIAH + " (" +
            COLUMN_KODE_MK + " TEXT PRIMARY KEY, " +
            COLUMN_NAMA_MK + " TEXT, " +
            COLUMN_SKS + " INTEGER, " +
            COLUMN_DESKRIPSI_MK + " TEXT);";

    private static final String CREATE_TABLE_NILAI = "CREATE TABLE " + TABLE_NILAI + " (" +
            COLUMN_NILAI_NIM + " TEXT REFERENCES " + TABLE_MAHASISWA + "(" + COLUMN_NIM + "), " +
            COLUMN_NILAI_KODE_MK + " TEXT REFERENCES " + TABLE_MATA_KULIAH + "(" + COLUMN_KODE_MK + "), " +
            COLUMN_NILAI + " INTEGER, " +
            "PRIMARY KEY (" + COLUMN_NILAI_NIM + ", " + COLUMN_NILAI_KODE_MK + "));";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MAHASISWA);
        db.execSQL(CREATE_TABLE_DOSEN);
        db.execSQL(CREATE_TABLE_MATA_KULIAH);
        db.execSQL(CREATE_TABLE_NILAI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAHASISWA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOSEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATA_KULIAH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NILAI);
        onCreate(db);
    }
}
