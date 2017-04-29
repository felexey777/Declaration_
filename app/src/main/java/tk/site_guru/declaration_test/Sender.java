package tk.site_guru.declaration_test;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by Alex on 03.04.2017.
 */

public class Sender {
    String uri;
    String param;
    String method;
    public Sender(String uri, String param, String method) {
        this.uri = uri;
        this.param = param;
        this.method = method;

    }
    public String send(){
        try {



            Log.d("Alex",uri);
            URL url = new URL(uri);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            //устанавливаем метод HTTP
            urlConnection.setRequestMethod(method);
            //включаем OUT
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            if (method.equals("POST")) {
                OutputStream os = urlConnection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true);
                writer.println(param);
                os.flush();
                os.close();
                Log.d("Alex", param);
            }
            InputStream inputStream = urlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            Log.d("Alex",buffer.toString());
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Alex",e.getMessage());
        }

        return null;
    }
}
