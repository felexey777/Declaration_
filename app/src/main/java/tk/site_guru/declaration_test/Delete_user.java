package tk.site_guru.declaration_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Alex on 28.04.2017.
 */
public class Delete_user extends AsyncTask<String,String,String> {
    Context context;
    public Delete_user(Context context) {
  this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        Dbcon db = new Dbcon(context);
        SQLiteDatabase sq = db.getWritableDatabase();
        String id = params[0];
        String where = "id = '" + id + "'";
       Log.d("Alex","delete: " +  sq.delete("declar_user",where,null));
        db.close();
        return null;
    }
}
