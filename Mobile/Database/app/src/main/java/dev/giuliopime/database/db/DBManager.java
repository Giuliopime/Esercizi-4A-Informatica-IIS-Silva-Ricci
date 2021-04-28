package dev.giuliopime.database.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBManager {
    private final DBHelper dbHelper;

    public DBManager(Context ctx) {
        dbHelper = new DBHelper(ctx);
    }

    public Cursor getAllRecords() {
        return dbHelper.getReadableDatabase().rawQuery("SELECT * FROM " + DBStrings.TBL_NAME, null);
    }

    public boolean save(String titolo, String descrizione) {
        ContentValues cv = new ContentValues();
        cv.put(DBStrings.FIELD_TITOLO, titolo);
        cv.put(DBStrings.FIELD_DESCRIZIONE, descrizione);

        return dbHelper.getWritableDatabase().insert(DBStrings.TBL_NAME, null, cv) != -1;
    }

    public boolean delete(int id) {
        String[] args = new String[] { String.valueOf(id) };

        return dbHelper.getWritableDatabase().delete(DBStrings.TBL_NAME, DBStrings.FIELD_ID + "=?", args) > 0;
    }
}
