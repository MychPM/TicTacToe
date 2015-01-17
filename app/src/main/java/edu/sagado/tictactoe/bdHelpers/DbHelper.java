package edu.sagado.tictactoe.bdHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MychPM on 12/01/2015.
 */
public class DbHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "tictactoe.sqlite";
    private static final int DB_SCHEME_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null,DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseManager.CREATE_TABLE_PLAYERS);
        db.execSQL(DataBaseManager.CREATE_TABLE_SCORES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {

        Log.w(DB_NAME, "Upgrading database from version " + i + "to " + i2 + ", which will destroy all data");
        db.execSQL(DataBaseManager.DROP_TABLEP);
        db.execSQL(DataBaseManager.DROP_TABLE);
        onCreate(db);
    }


}
