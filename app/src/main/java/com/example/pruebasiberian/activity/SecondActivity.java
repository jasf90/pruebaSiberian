package com.example.pruebasiberian.activity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebasiberian.R;
import com.example.pruebasiberian.adapter.TarjetasAdapter;
import com.example.pruebasiberian.model.Tarjetas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    List<Tarjetas> items = new ArrayList<Tarjetas>();
    Tarjetas lista = new Tarjetas();

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ctx = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) this.findViewById(R.id.recyclerview);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        new TaskGetTarjetas().execute();

    }

    public class TaskGetTarjetas extends AsyncTask<String, Void, Integer> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);
            System.out.println("paso onPreExecute Tarjetas");
        }
        @Override
        protected Integer doInBackground(String... params) {
            String TAG="TaskGetTarjetas";
            int size = 0;
            String URL= "http://prueba.siberian.com.ec/ws.php";
            HttpURLConnection connection = null;
            try {
                Log.v(TAG, URL);
                java.net.URL url = new URL(URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(70000);
                connection.setReadTimeout(60000);
                int statusCode = connection.getResponseCode();
                if (statusCode == 200) {
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    String result= convertStreamToString(in);
                    try {
                        JSONObject json = new JSONObject(result);
                        JSONArray tarjetas = json.getJSONArray("listadoTarjetas");

                        items = new ArrayList<Tarjetas>();
                        for (int i = 0; i < tarjetas.length(); i++) {
                            JSONObject jsonobject = tarjetas.getJSONObject(i);
                            lista = new Tarjetas();
                            lista.setNumero(jsonobject.getString("numero"));
                            lista.setTipo(jsonobject.getString("tipo"));
                            items.add(lista);
                        }
                        size = items.size();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i(TAG," ERROR JSON ");
                    }
                } else {
                    Log.e(TAG," ERROR getTarjetas");
                }
            }   catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG," Exception Error . "+e.getMessage() );
            } finally {
                connection.disconnect();
            }
            return size;
        }

        @Override
        protected void onPostExecute(Integer a) {
            if(pDialog != null){
                pDialog.dismiss();
                if(a > 0){
                    adapter = new TarjetasAdapter(items, ctx);
                    recyclerView.setAdapter(adapter);
                }else{

                }
            }

        }

    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            Log.v("ERROR", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}
