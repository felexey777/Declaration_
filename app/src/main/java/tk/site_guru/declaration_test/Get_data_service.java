package tk.site_guru.declaration_test;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Alex on 28.04.2017.
 */
public class Get_data_service extends AsyncTask<String, String, BoxAdapter_ser_dec> {
    Context context;
    ProgressBar progressBar;
    ListView listView;
    boolean inet = false;
    public Get_data_service(Context context, ProgressBar progressBar, ListView listView) {
        this.context =context;
        this.progressBar = progressBar;
        this.listView = listView;

    }

    @Override
    protected BoxAdapter_ser_dec doInBackground(String... params) {
        if (hasConnection(context)) {
            String ask = params[0];
            String uri = "https://public-api.nazk.gov.ua/v1/declaration/?q=";


            try {
                uri += URLEncoder.encode(ask, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //Log.d("Alex",uri);


            // uri = "https://public-api.nazk.gov.ua/v1/declaration/?q=%D0%A7%D0%B5%D1%80";
            Sender sender = new Sender(uri, "", "GET");
            try {
                JSONObject object = new JSONObject(sender.send());
                if (object.has("page")) {
                    JSONArray array = object.getJSONArray("items");
                    ArrayList<String> name = new ArrayList<>();
                    ArrayList<String> plase = new ArrayList<>();
                    ArrayList<String> position = new ArrayList<>();
                    ArrayList<String> link = new ArrayList<>();
                    ArrayList<String> select = new ArrayList<>();
                    ArrayList<String> id = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        try {
                            select.add("0");
                            id.add(ob.getString("id"));


                            name.add(ob.getString("lastname") + " " + ob.getString("firstname"));

                            plase.add(ob.getString("placeOfWork"));
                            if (ob.has("position")) {
                                position.add(ob.getString("position"));
                            } else {
                                position.add("н");
                            }
                            if (ob.has("linkPDF")) {
                                link.add(ob.getString("linkPDF"));

                            } else {
                                link.add("0");
                            }

                        } catch (Exception e) {
                            Log.d("Alex", "erorr: " + e.getMessage());
                        }

                    }
                    BoxAdapter_ser_dec adapter = new BoxAdapter_ser_dec(context, name, plase, position, link, select, id);
                    return adapter;

                } else {
                    BoxAdapter_ser_dec adapter = new BoxAdapter_ser_dec(context);
                    return adapter;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            inet = true;
        }

            BoxAdapter_ser_dec adapter = new BoxAdapter_ser_dec(context);

            return adapter;

    }

    @Override
    protected void onPostExecute(BoxAdapter_ser_dec adapter) {
        super.onPostExecute(adapter);
        try {
            listView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
            if (inet){
                Toast.makeText(context,"Нет подключения к интернету",Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Log.d("Alex","error: " +  e.getMessage());
        }

    }
    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }
}
