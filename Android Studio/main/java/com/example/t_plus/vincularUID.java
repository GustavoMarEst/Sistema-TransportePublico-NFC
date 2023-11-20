package com.example.t_plus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class vincularUID extends AppCompatActivity {

    Button confirmar,eliminar;
    TextView bienvenida, idd, hola, uidVal;
    EditText uidd, cUID;
    boolean isEditMode = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vincular_uid);

        hola = findViewById(R.id.txt_hola);
        confirmar = findViewById(R.id.btn_confirmar);
        eliminar = findViewById(R.id.btn_eliminar);

        idd = findViewById(R.id.txt_id);
        uidVal = findViewById(R.id.txt_uidValue);

        uidd = findViewById(R.id.txt_uid);
        cUID = findViewById(R.id.txt_confUid);

        String idValue = getIntent().getStringExtra("idValue");
        String uidValue = getIntent().getStringExtra("uidValue");

        idd.setText(idValue);
        uidVal.setText(uidValue);


        if (uidValue.equals("0")) {
            confirmar.setText("Ingresar UID");
            habilitarEdicion();

        } else {
            confirmar.setText("Actualizar uid");
            uidVal.setText(uidValue);

            deshabilitarEdicion();
            isEditMode = false;
        }

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditMode){
                    agregarUID();
                }else{
                    habilitarEdicion();
                    confirmar.setText("Actualizar UID");
                }
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarUID();
            }
        });
    }


    private void eliminarUID(){
        final String id = idd.getText().toString().trim();
        final String uid = uidd.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.1.68:8080/tesis_/eliminarUID.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("UID eliminado correctamente")) {
                    Toast.makeText(vincularUID.this, response, Toast.LENGTH_SHORT).show();
                    eliminacion();
                    finish();
                } else {
                    Toast.makeText(vincularUID.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(vincularUID.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>param = new HashMap<String, String>();
                param.put("id",id);
                param.put("uid",uid);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(vincularUID.this);
        requestQueue.add(request);
    }

    private void eliminacion(){
        isEditMode = true;
        confirmar.setText("Ingresar UID");
        uidd.setEnabled(true);
        cUID.setEnabled(true);
        uidd.setVisibility(View.VISIBLE);
        cUID.setVisibility(View.VISIBLE);
        uidVal.setVisibility(View.INVISIBLE);
        eliminar.setVisibility(View.INVISIBLE);
        eliminar.setEnabled(false);
        hola.setText("Introduce los datos siguientes para vincular un UID:");
    }

    private void agregarUID() {
        final String id = idd.getText().toString().trim();
        final String uid = uidd.getText().toString().trim();
        final String confirmarUID = cUID.getText().toString().trim();

        if (uid.isEmpty()) {
            uidd.setError("Introduce el UID de la tarjeta");
            return;
        } else if (confirmarUID.isEmpty()) {
            cUID.setError("Confirma el UID de la tarjeta");
            return;
        } else if (!uid.equals(confirmarUID)) {
            cUID.setError("Las UID colocadas no coinciden");
            return;
        }
        else{
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.1.68:8080/tesis_/insertarUID.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("UID enlazado correctamente")) {
                        Toast.makeText(vincularUID.this, response, Toast.LENGTH_SHORT).show();
                        deshabilitarEdicion();
                        finish();
                    } else {
                        Toast.makeText(vincularUID.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(vincularUID.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String>param = new HashMap<String, String>();
                    param.put("id",id);
                    param.put("uid",uid);
                    return param;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(vincularUID.this);
            requestQueue.add(request);
        }
    }

    private void habilitarEdicion(){
        isEditMode = true;
        uidd.setEnabled(true);
        cUID.setEnabled(true);
        uidd.setVisibility(View.VISIBLE);
        cUID.setVisibility(View.VISIBLE);
        uidVal.setVisibility(View.INVISIBLE);
        eliminar.setVisibility(View.INVISIBLE);
        eliminar.setEnabled(false);
        hola.setText("Introduce los datos siguientes para vincular un UID:");
    }

    private void deshabilitarEdicion(){
        isEditMode = false;
        uidd.setEnabled(false);
        cUID.setEnabled(false);
        uidd.setVisibility(View.INVISIBLE);
        cUID.setVisibility(View.INVISIBLE);
        confirmar.setVisibility(View.VISIBLE);
        confirmar.setEnabled(true);
        eliminar.setVisibility(View.VISIBLE);
        eliminar.setEnabled(true);
        confirmar.setText("Actualizar UID");
        hola.setText("¡Genial, ya cuentas con un UID enlazada!");
    }
}
