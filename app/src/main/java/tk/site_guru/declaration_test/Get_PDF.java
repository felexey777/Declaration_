package tk.site_guru.declaration_test;

import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;



/**
 * Created by Alex on 28.04.2017.
 */
public class Get_PDF extends AsyncTask<String, String,File> {
    Context context;
    String link;
    ProgressBar progressBar;
    //final String FILENAME = "file.pdf";

    final String DIR_SD = "DECLARATIONS";
     String FILENAME_SD = "file.pdf";

    public Get_PDF(Context context, String link, ProgressBar progressBar) {
       this.context = context;
        this.link = link;
        this.progressBar = progressBar;
    }

    @Override
    protected File doInBackground(String... params) {
        try {
           // Log.d("Alex",link);
            //link = "https://public.nazk.gov.ua/storage/documents/pdf/2/2/f/0/22f0bd2f-5f52-43e1-8f16-043e40184441.pdf";
            URL url = new URL(link);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            byte [] mybytearray  = new byte [ urlConnection.getContentLength()];
          Log.d("Alex","length: " +   urlConnection.getContentLength());
            InputStream inputStream = urlConnection.getInputStream();




           //  проверяем доступность SD
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                Log.d("Alex", "SD-карта не доступна: " + Environment.getExternalStorageState());
                return null;
            }
            // получаем путь к SD
            File sdPath = Environment.getExternalStorageDirectory();
            // добавляем свой каталог к пути
            sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
            // создаем каталог
            sdPath.mkdirs();
            FILENAME_SD = new Date().getTime() + ".pdf";
            // формируем объект File, который содержит путь к файлу
            File sdFile = new File(sdPath, FILENAME_SD);

            InputStream is =inputStream;
            FileOutputStream fos = new FileOutputStream(sdFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
           int bytesRead = is.read(mybytearray,0,mybytearray.length);
           int current = bytesRead;

            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bos.write(mybytearray, 0 , current);
            bos.flush();
            return  sdFile;




        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Alex",e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(File s) {
        super.onPostExecute(s);
        try {
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.fromFile(s));
            context.startActivity(intent);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

