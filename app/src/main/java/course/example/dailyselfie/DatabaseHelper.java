package course.example.dailyselfie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by user on 20.08.2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_Name = "Selfie.db";
    static final String LOG_TAG = "myLog";

    public DatabaseHelper(Context context) {
        super(context, DB_Name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table selfie ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "pathName text" + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
