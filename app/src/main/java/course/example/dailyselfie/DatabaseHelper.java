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

    DatabaseHelper mDbHelper;


    public DatabaseHelper(Context context) {
        super(context, DB_Name, null, DB_VERSION);
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

    public void insert(Context context, String name, String filePath){
        mDbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("filePath", filePath);
        db.insert(TABLE_NAME, null, values);

        db.close();
        mDbHelper.close();
    }

    public void read(Context context){
        mDbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String name;
        String path;

        Cursor cursor = db.query(TABLE_NAME,null,null,null,null,null,null);
        if(cursor.getCount() != 0){
            do{
                int nameIndex = cursor.getColumnIndex("name");
                int pathIndex = cursor.getColumnIndex("filePath");

                name = cursor.getString(nameIndex);
                path = cursor.getString(pathIndex);

                Log.d(LOG_TAG, name + "  ->  " + path);
            }while (cursor.moveToFirst());
        }
    }

}
