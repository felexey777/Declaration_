package tk.site_guru.declaration_test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

public class SelectedDeclarationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
   public ListView listView;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_declaration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



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
       listView = (ListView)findViewById(R.id.select_listview);
       progressBar = (ProgressBar)findViewById(R.id.progress);
        Get_selected get = new Get_selected(this,listView,progressBar);
        get.execute();
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
//        getMenuInflater().inflate(R.menu.selected_declaration, menu);
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

        if (id == R.id.search_dec) {
            Intent intent = new Intent(this,SearchDeclarationActivity.class);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
    public void star(View view) {


        TextView textView = (TextView)view;
        BoxAdapter_ser_dec adapter = (BoxAdapter_ser_dec) listView.getAdapter();
        int position = (Integer) textView.getTag();
        String id = adapter.id.get(position);
        Delete_user del = new Delete_user(this);
        del.execute(id);



            adapter.select.remove(position);
            adapter.name.remove(position);
            adapter.plase.remove(position);
            adapter.positon.remove(position);
            adapter.link.remove(position);
            adapter.id.remove(position);
            adapter.comment.remove(position);
        adapter.size --;
            adapter.notifyDataSetChanged();











    }

    public void add_coment(View view) {
        coment_new com = new coment_new();
        Bundle bundle = new Bundle();
        int position = (Integer) ((TextView)view).getTag();
        String id =((BoxAdapter_ser_dec) listView.getAdapter()).id.get(position);
        bundle.putString("id",id);
        bundle.putInt("position",position);
       // Log.d("Alex","update: " +  id );
        com.setArguments(bundle);

        com.show(getSupportFragmentManager(),"comment_new");
    }
}
