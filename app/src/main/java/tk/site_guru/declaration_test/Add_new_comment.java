package tk.site_guru.declaration_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by Alex on 28.04.2017.
 */
public class Add_new_comment extends AsyncTask<Bundle, String,String> {
    Context context;
    int position = 0;
    String comment;
    public Add_new_comment(Context context) {
    this.context = context;
    }

    @Override
    protected String doInBackground(Bundle... params) {
        Dbcon db = new Dbcon(context);
        SQLiteDatabase sq = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Bundle bundle = params[0];
       comment = bundle.getString("comment");
        String id = bundle.getString("id");
        position = bundle.getInt("position");
        String where = "id = '" + id + "'";
        cv.put("comment",comment);
        Log.d("Alex","update: " +  where );
        Log.d("Alex","update: " +  bundle );

       Log.d("Alex","update: " +  sq.update("declar_user",cv,where,null));
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            ListView listView = ((SelectedDeclarationActivity)context).listView;
            BoxAdapter_ser_dec adapter = (BoxAdapter_ser_dec) listView.getAdapter();
            adapter.comment.remove(position);
            adapter.comment.add(position,comment);
            adapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
