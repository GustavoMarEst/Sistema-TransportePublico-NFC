package com.example.t_plus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Historial extends AppCompatActivity {

    TextView uid, id;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        //uid = findViewById(R.id.txt_uid);
        id = findViewById(R.id.txt_uid);
        listView = findViewById(R.id.txt_historial);

        //String uidValue = getIntent().getStringExtra("uidValue");
        String idValue = getIntent().getStringExtra("idValue");

        //uid.setText(uidValue);
        id.setText(idValue);


        //new FetchDatesTask().execute(idValue);

        obtenerHistorial(idValue);


        //NO----------------------------------------------------
        //String uidd = uid.getText().toString(); // Obtén el UID del TextView
        //String url = "http://192.168.1.68:8080/tesis_/historial.php?uid=" + uidd;
        //new FetchData().execute(url);
        //NO----------------------------------------------------

    }

    private void obtenerHistorial (final String idValue) {
        //String url = "http://192.168.1.68:8080/tesis_/historial_2.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.68:8080/tesis_/historial_2.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    ArrayList<String> dates = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        dates.add(jsonArray.getString(i));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Historial.this, android.R.layout.simple_list_item_1, dates);
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Historial.this, "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_usuario", idValue);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }









    private class FetchDatesTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            ArrayList<String> dates = new ArrayList<>();
            String idValue = params[0];

            try {
                URL url = new URL("http://192.168.1.68:8080/tesis_/historial.php?id_usuario=" + idValue);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                JSONArray jsonArray = new JSONArray(response.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    dates.add(jsonArray.getString(i));
                }

                reader.close();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return dates;
        }

        @Override
        protected void onPostExecute(ArrayList<String> dates) {
            super.onPostExecute(dates);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(Historial.this, android.R.layout.simple_list_item_1, dates);
            listView.setAdapter(adapter);
        }
    }







}
