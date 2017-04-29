package tk.site_guru.declaration_test;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

public class SearchDeclarationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,  coment.NoticeDialogListener {
    ProgressBar progressBar;
    ListView listView;
    EditText editText;

    coment coment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dec);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItem = navigationView.getMenu().getItem(0);


        menuItem.getSubMenu().getItem(0).setIcon(new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_search));

        menuItem = navigationView.getMenu().getItem(1);

        menuItem.getSubMenu().getItem(0).setIcon(new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_check));



        Button button = (Button)findViewById(R.id.button_search);
        button.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf"));
        progressBar = (ProgressBar)findViewById(R.id.progress);
        listView = (ListView)findViewById(R.id.list_declar);
        editText = (EditText)findViewById(R.id.search);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.search_declaration, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.select_dec) {
            Intent intent = new Intent(this,SelectedDeclarationActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void search(View view) {
        String text = editText.getText().toString();
        if (text.equals("")){
            Toast.makeText(this,"Введите текст",Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        Get_data_service get = new Get_data_service(this,progressBar,listView);
        get.execute(text);

    }

    public void star(View view) {


            TextView textView = (TextView)view;
           BoxAdapter_ser_dec adapter = (BoxAdapter_ser_dec) listView.getAdapter();
            int position = (Integer) textView.getTag();
      String tag =   adapter.select.get(position);
        if (tag.equals("0")){
            textView.setTextColor(Color.YELLOW);
            String name = adapter.name.get(position);
            String posit = adapter.positon.get(position);
            String place = adapter.plase.get(position);
            String id = adapter.id.get(position);
            String link = adapter.link.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("name",name);
            bundle.putString("position",posit);
            bundle.putString("place",place);
            bundle.putString("id",id);
            bundle.putString("link",link);

            coment = new coment();
            coment.setArguments(bundle);
            coment.show(getSupportFragmentManager(),"coment");
            adapter.select.remove(position);
            adapter.select.add(position,"1");


        }else {
            textView.setTextColor(Color.GRAY);
            adapter.select.remove(position);
            adapter.select.add(position,"0");
            String id = adapter.id.get(position);
            Delete_user del = new Delete_user(this);
            del.execute(id);
        }






    }

    public void book(View view) {
        TextView textView = (TextView)view;
        int tag = (Integer) textView.getTag();
       BoxAdapter_ser_dec adapter = (BoxAdapter_ser_dec) listView.getAdapter();
        String link = adapter.link.get(tag);
        if (!link.equals("0") ){
            progressBar.setVisibility(View.VISIBLE);

            Get_PDF  get = new Get_PDF(this,link,progressBar);
            get.execute();

        }else {
            Toast.makeText(this,"Нет файла PDF",Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onDialogPositiveClick(DialogFragment dialog,String coment) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
