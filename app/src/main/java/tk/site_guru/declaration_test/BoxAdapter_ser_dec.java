package tk.site_guru.declaration_test;


import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BoxAdapter_ser_dec extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    public ArrayList<String > name;
    public ArrayList<String > plase;
    public ArrayList<String > positon;
    public ArrayList<String > link;
    public  ArrayList<String> select;
    public  ArrayList<String > id;
   public int size  = 0;
    public  ArrayList<String> comment;
    boolean is_comment = false;



    public BoxAdapter_ser_dec(Context context) {
        ctx = context;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }
    public BoxAdapter_ser_dec(Context context,
                              ArrayList<String> name,
                              ArrayList<String> plase,
                              ArrayList<String> position,
                              ArrayList<String> link,
                              ArrayList<String> select,
                                ArrayList<String> id) {
        ctx = context;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.name = name;
        this.plase  = plase;
        this.positon = position;
        this.link = link;
        size = name.size();
        this.select = select;
        this.id = id;



    }
    public BoxAdapter_ser_dec(Context context,
                              ArrayList<String> name,
                              ArrayList<String> plase,
                              ArrayList<String> position,
                              ArrayList<String> link,
                              ArrayList<String> select,
                              ArrayList<String> id,
                              ArrayList<String> comment) {
        ctx = context;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.name = name;
        this.plase  = plase;
        this.positon = position;
        this.link = link;
        size = name.size();
        this.select = select;
        this.id = id;
        this.comment = comment;
        is_comment = true;



    }


    // кол-во элементов
    @Override
    public int getCount() {

            return size;

    }
    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return position;
    }
    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_serch_dec, parent, false);
            ((TextView) view.findViewById(R.id.star)).setTypeface(Typeface.createFromAsset(ctx.getAssets(), "fonts/fontawesome-webfont.ttf"));
            ((TextView) view.findViewById(R.id.book)).setTypeface(Typeface.createFromAsset(ctx.getAssets(), "fonts/fontawesome-webfont.ttf"));
        }



        ((TextView) view.findViewById(R.id.star)).setTag(position);

        ((TextView) view.findViewById(R.id.book)).setTextColor(Color.GRAY);
        ((TextView) view.findViewById(R.id.book)).setTag(position);

            ((TextView) view.findViewById(R.id.name)).setText(ctx.getString(R.string.name) + name.get(position));
            ((TextView) view.findViewById(R.id.plase)).setText(ctx.getString(R.string.place) + plase.get(position));
            ((TextView) view.findViewById(R.id.position)).setText(ctx.getString(R.string.position) + positon.get(position));
        if (select.get(position).equals("1")){
            ((TextView) view.findViewById(R.id.star)).setTextColor(Color.YELLOW);
        }else {
            ((TextView) view.findViewById(R.id.star)).setTextColor(Color.GRAY);
        }
        if (is_comment) {
            ((TextView) view.findViewById(R.id.comment)).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.comment)).setTag(position);

            ((TextView) view.findViewById(R.id.comment)).setText("Коментарий: " + comment.get(position));

        }






        return view;
    }
}
