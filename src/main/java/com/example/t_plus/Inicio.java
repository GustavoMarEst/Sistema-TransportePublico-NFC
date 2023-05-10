package com.example.t_plus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Inicio extends AppCompatActivity {

    TextView nom, idd, cred;
    Button boton;
    String nombre, credito, id, date;
    int creditoInt;
    private List<String> fechas = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        nom = findViewById(R.id.txt_usuario);
        cred = findViewById(R.id.txt_credito);
        idd = findViewById(R.id.txt_id);
        listView = findViewById(R.id.txt_fecha);
        boton = findViewById(R.id.btn_costo);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fechas);
        listView.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nombre = extras.getString("nombre");
            id = extras.getString("id");
            nom.setText(nombre);
            idd.setText(id);
        }

        obtenerDatos();

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerDatos();

            }
        });
    }

    private void obtenerDatos(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://192.168.1.70:8080/tesis_/x.php?id="+idd.getText()+"", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        cred.setText(jsonObject.getString("credito"));
                        String nuevaFecha = jsonObject.getString("fecha");
                        if(!fechas.contains(nuevaFecha)){
                            fechas.add(nuevaFecha);
                        }

                    } catch (JSONException e) {
                        Toast.makeText(Inicio.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Inicio.this, android.R.layout.simple_list_item_1, fechas);
                ListView listView = findViewById(R.id.txt_fecha);
                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Inicio.this, "Error al conectar", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

























    /*private void actualizarDatos(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.70:8080/tesis_/update.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Inicio.this, "Pago Realizado", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Inicio.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id",idd.getText().toString());
                param.put("credito",cred.getText().toString());
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/
}
