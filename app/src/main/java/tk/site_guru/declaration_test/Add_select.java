package tk.site_guru.declaration_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Alex on 28.04.2017.
 */
public class Add_select extends AsyncTask<Bundle,String,String> {
    Context context;

    public Add_select(Context context ) {
       this.context = context;
    }

    @Override
    protected String doInBackground(Bundle... params) {
        Log.d("Alex","start add");
        Dbcon dbcon = new Dbcon(context);
        SQLiteDatabase sq = dbcon.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Bundle bundle = params[0];
        String name = bundle.getString("name");
        String position = bundle.getString("position");
        String place = bundle.getString("place");
        String id = bundle.getString("id");
        String link = bundle.getString("link");
        String com = bundle.getString("comment");
        cv.put("id",id);
        cv.put("name",name);
        cv.put("position",position);
        cv.put("place",place);
        cv.put("link",link);
        cv.put("comment",com);
       Log.d("Alex","insert: " +  sq.insert("declar_user",null,cv));
        dbcon.close();
        return null;
    }
}
