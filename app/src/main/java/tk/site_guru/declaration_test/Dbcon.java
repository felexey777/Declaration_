package tk.site_guru.declaration_test;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


public class Dbcon extends SQLiteOpenHelper implements BaseColumns {
    String DATABASE_NAME = "daclaration.db";


    private  static final String DATABASE_CREATE_SCRIPT_ = "create table " +
            "declar_user" + " ("
            + "id STRING, "
            + "name STRING, "
            + " position STRING, "
            + " place STRING, "
            + " link STRING, "
            + " comment STRING"



            +");";



    public Dbcon(Context context, String name, SQLiteDatabase.CursorFactory factory,
                 int version) {
        super(context, name, factory, version);
    }

    public Dbcon(Context context, String name, SQLiteDatabase.CursorFactory factory,
                 int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    public Dbcon(Context context) {
        super(context, "daclaration.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT_);





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Запишем в журнал
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        // Удаляем старую таблицу и создаём новую
       // db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
        // Создаём новую таблицу
        onCreate(db);

    }

        }