package dev.giuliopime.database.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String dbName = "PROMEMORIA";

    public DBHelper (Context ctx) {
        super(ctx, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String initOp = "CREATE TABLE " +
                DBStrings.TBL_NAME + " ( " +
                DBStrings.FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBStrings.FIELD_TITOLO + " VARCHAR, " +
                DBStrings.FIELD_DESCRIZIONE + " VARCHAR)";

        db.execSQL(initOp);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
