package com.example.t_plus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Inicio extends AppCompatActivity {

    TextView nom, idd, cred, uidd;
    Button actualizar;
    String nombre, credito, id, date, uid;
    int creditoInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        nom = findViewById(R.id.txt_usuario);
        cred = findViewById(R.id.txt_credito);
        idd = findViewById(R.id.txt_id);
        uidd = findViewById(R.id.txt_uid);
        actualizar = findViewById(R.id.btn_costo);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nombre = extras.getString("nombre");
            id = extras.getString("id");
            uid = extras.getString("uid");
            nom.setText(nombre);
            idd.setText(id);
            uidd.setText(uid);
        }

        obtenerDatos();

        actualizar.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerDatos();

            }
        });
    }

    private void obtenerDatos(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://192.168.1.68:8080/tesis_/x.php?id="+idd.getText()+"", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        cred.setText(jsonObject.getString("credito"));
                        uidd.setText(jsonObject.getString("uid"));

                    } catch (JSONException e) {
                        Toast.makeText(Inicio.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

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


    public void Historial (View v1){
        String idValue = idd.getText().toString();
        String uidValue = uidd.getText().toString();
        Intent intent = new Intent(getApplicationContext(), Historial.class);
        intent.putExtra("idValue", idValue);
        intent.putExtra("uidValue", uidValue);
        startActivity(intent);
    }

    public void Recarga (View v1){
        String idValue = idd.getText().toString();
        //String uidValue = uidd.getText().toString();
        String credValue = cred.getText().toString();
        Intent intent = new Intent(getApplicationContext(), Recarga.class);
        intent.putExtra("idValue", idValue);
        //intent.putExtra("uidValue", uidValue);
        intent.putExtra("credValue", credValue);
        startActivity(intent);
    }

    public void tuTarjeta (View v1){
        String idValue = idd.getText().toString();
        String uidValue = uidd.getText().toString();
        Intent intent = new Intent(getApplicationContext(), vincularUID.class);
        intent.putExtra("idValue", idValue);
        intent.putExtra("uidValue", uidValue);
        startActivity(intent);
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
