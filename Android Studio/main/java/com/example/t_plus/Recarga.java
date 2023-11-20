package com.example.t_plus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recarga extends AppCompatActivity {

    TextView cred, idd, credVal;
    EditText tarjeta, expira, cvv;
    Button button10, button20, button50, button100, button200, button500, confirmar;

    List<Button> botones = new ArrayList<>();
    private int recargaActual = 0;
    private Button botonPrevioClicado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga);

        cred = findViewById(R.id.txt_credito);
        tarjeta = findViewById(R.id.txt_tarjeta);
        expira = findViewById(R.id.txt_expire);
        cvv = findViewById(R.id.txt_cvv);
        idd = findViewById(R.id.txt_id);
        credVal = findViewById(R.id.txt_credValue);

        button10 = findViewById(R.id.btn_10);
        button20 = findViewById(R.id.btn_20);
        button50 = findViewById(R.id.btn_50);
        button100 = findViewById(R.id.btn_100);
        button200 = findViewById(R.id.btn_200);
        button500 = findViewById(R.id.btn_500);
        confirmar = findViewById(R.id.btn_confirmar);


        String idValue = getIntent().getStringExtra("idValue");
        String credValue = getIntent().getStringExtra("credValue");

        idd.setText(idValue);
        credVal.setText(credValue);


        botones.add(button10);
        botones.add(button20);
        botones.add(button50);
        botones.add(button100);
        botones.add(button200);
        botones.add(button500);

        for(final Button boton : botones){
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(botonPrevioClicado != null){
                        botonPrevioClicado.setEnabled(true);
                    }

                    boton.setEnabled(false);
                    botonPrevioClicado = boton;

                    int cantidad = 0;
                    switch (boton.getId()){
                        case R.id.btn_10:
                            cantidad = 10;
                            break;
                        case R.id.btn_20:
                            cantidad = 20;
                            break;
                        case R.id.btn_50:
                            cantidad = 50;
                            break;
                        case R.id.btn_100:
                            cantidad = 100;
                            break;
                        case R.id.btn_200:
                            cantidad = 200;
                            break;
                        case R.id.btn_500:
                            cantidad = 500;
                            break;
                    }
                    actualizarCantidadRecarga(cantidad);
                }
            });
        }




        /*button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarCantidadRecarga(10);
            }
        });

        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarCantidadRecarga(20);
            }
        });

        button50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarCantidadRecarga(50);
            }
        });

        button100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarCantidadRecarga(100);
            }
        });

        button200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarCantidadRecarga(200);
            }
        });

        button500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarCantidadRecarga(500);
            }
        });*/

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recargaCredito();
            }
        });
    }

    private void recargaCredito(){

        final String nTrajeta = tarjeta.getText().toString().trim();
        final String expireDate = expira.getText().toString().trim();
        final String nCVV = cvv.getText().toString().trim();
        final String id = idd.getText().toString().trim();
        final String credito = credVal.getText().toString().trim();

        if(nTrajeta.isEmpty()) {
            tarjeta.setError("Introduce tu numero de tarjeta");
            return;
        }else if(expireDate.isEmpty()) {
            expira.setError("Introduce la fecha de expiracion de la tarjeta");
            return;

        }else if(nCVV.isEmpty()){
            cvv.setError("Introduce el CVV de la tarjeta");
            return;
        }else{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.68:8080/tesis_/update.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equalsIgnoreCase("La recarga se ha realizado correctamente")){
                        Toast.makeText(Recarga.this, response, Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(Recarga.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Recarga.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("id",id);
                    param.put("credito", credito);
                    return param;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

            for(Button boton : botones){
                boton.setEnabled(true);
            }

        }
    }

    private void actualizarCantidadRecarga(int cantidad) {

        cred.setText(String.valueOf(cantidad));

        int intCred = Integer.parseInt(credVal.getText().toString());

        intCred -= recargaActual;

        int recarga = intCred + cantidad;
        recargaActual = cantidad;

        credVal.setText(String.valueOf(recarga));
    }
}
