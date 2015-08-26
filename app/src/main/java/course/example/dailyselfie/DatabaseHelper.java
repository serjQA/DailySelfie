package course.example.dailyselfie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    public static final String TABLE_NAME = "selfie";


    Context context;


    public DatabaseHelper(Context context) {
        super(context, DB_Name, null, DB_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table " + TABLE_NAME +" ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "filePath text" + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String name, String filePath){
        Log.d(LOG_TAG, "-------------------------------------Insert----------------------");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("filePath", filePath);
        db.insert(TABLE_NAME, null, values);


        db.close();
       this.close();
    }

    public Cursor read(){
        Log.d(LOG_TAG, "-------------------------------------read----------------------");
        SQLiteDatabase db = this.getReadableDatabase();
        String name;
        String path;
        Cursor cursor = null;
        try {
            cursor= db.query(TABLE_NAME, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    int nameIndex = cursor.getColumnIndex("name");
                    int pathIndex = cursor.getColumnIndex("filePath");

                    name = cursor.getString(nameIndex);
                    path = cursor.getString(pathIndex);

                    Log.d(LOG_TAG, name + "  ->  " + path);
                } while (cursor.moveToNext());
            }
            return cursor;
        }
        finally{
            cursor.close();
            db.close();
        }

    }
}
