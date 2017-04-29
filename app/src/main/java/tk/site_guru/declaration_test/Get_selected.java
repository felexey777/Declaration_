package tk.site_guru.declaration_test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

/**
 * Created by Alex on 28.04.2017.
 */
public class Get_selected extends AsyncTask<String,String,BoxAdapter_ser_dec> {
    Context context;
    ListView listView;
    ProgressBar progressBar;
    public Get_selected(Context context, ListView listView, ProgressBar progressBar) {
        this.progressBar = progressBar;
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected BoxAdapter_ser_dec doInBackground(String... params) {
        Dbcon db = new Dbcon(context);
        SQLiteDatabase sq = db.getReadableDatabase();
        Cursor cursor = sq.query("declar_user",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            ArrayList<String > name = new ArrayList<>();
            ArrayList<String> plase = new ArrayList<>();
            ArrayList<String > position = new ArrayList<>();
            ArrayList<String> link = new ArrayList<>();
            ArrayList<String> select = new ArrayList<>();
            ArrayList<String> id = new ArrayList<>();
            ArrayList<String> com = new ArrayList<>();
            for (int i = 0; i < cursor.getCount();i ++){
                name.add(cursor.getString(cursor.getColumnIndex("name")));
                plase.add(cursor.getString(cursor.getColumnIndex("place")));
                position.add(cursor.getString(cursor.getColumnIndex("position")));
                link.add(cursor.getString(cursor.getColumnIndex("link")));
                select.add("1");
                id.add(cursor.getString(cursor.getColumnIndex("id")));
                com.add(cursor.getString(cursor.getColumnIndex("comment")));
                cursor.moveToNext();
            }
            BoxAdapter_ser_dec adapter = new BoxAdapter_ser_dec(context, name,plase,position,link,select,id,com);
            cursor.close();
            db.close();
            return  adapter;
        }cursor.close();
        db.close();
        return null;
    }

    @Override
    protected void onPostExecute(BoxAdapter_ser_dec boxAdapter_ser_dec) {
        super.onPostExecute(boxAdapter_ser_dec);
        try {
            listView.setAdapter(boxAdapter_ser_dec);
            progressBar.setVisibility(View.GONE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
