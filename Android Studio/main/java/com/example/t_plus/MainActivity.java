package com.example.t_plus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.PortUnreachableException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    EditText tlogin, tcontraseña;
    String correo, contraseña, login;
    Button bt_sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tlogin = findViewById(R.id.txt_login);
        tcontraseña = findViewById(R.id.txt_contraseña);
        bt_sesion = findViewById(R.id.btn_sesion);


        bt_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validacion();
            }
        });

    }

    public void validacion(){
        if(tlogin.getText().toString().equals("")){
            tlogin.setError("Introduce tu correo electronico");
            return;
        }else if(tcontraseña.getText().toString().equals("")){
            tcontraseña.setError("Introduce tu contraseña");
            return;
        }else{
            login = tlogin.getText().toString().trim();
            contraseña = tcontraseña.getText().toString().trim();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.68:8080/tesis_/login.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.contains("Se ha iniciado sesión correctamente")) {
                        String[] datos = response.split(",");
                        String nombre = datos[1];
                        String id = datos[2];
                        String uid = datos[3];
                        Intent intent = new Intent(getApplicationContext(), Inicio.class);
                        intent.putExtra("nombre", nombre);
                        intent.putExtra("id", id);
                        intent.putExtra("uid", uid);
                        startActivity(intent);


                        guardarUsuario();

                    } else {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }

                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> param = new HashMap<>();
                    param.put("login", login);
                    param.put("contraseña", contraseña);
                    return param;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }
    }


    public void guardarUsuario(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.68:8080/tesis_/recarga.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*if(response.equalsIgnoreCase("El registro se ha completado Exito")){
                    Toast.makeText(MainActivity.this, "Sesion Iniciada", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                }*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("login", login);
                param.put("contraseña", contraseña);
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }

    public void Registro (View v1){
        startActivity(new Intent(getApplicationContext(),Registro.class));

    }




















    /*public void validacionUsuario(String URL){
        StringRequest stringrequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(), Inicio.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"El Usuario o Contraseña ingresados no son correctos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("correo",tlogin.getText().toString());
                parametros.put("contraseña",tcontraseña.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringrequest);


    }*/






















    /*public void Registro (View v2){
        Intent siguiente = new Intent(this,Registro.class);
        startActivity(siguiente);


    }*/


    /*public void Ejecutar (View v1){
        Intent siguiente = new Intent(this,Inicio.class);
        startActivity(siguiente);
    }*/

    //SIN BASE DE DATOS
    /*bt_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tlogin.getText().toString().trim().length() == 0){

                    Toast.makeText(MainActivity.this,"Ingresa el usuario", Toast.LENGTH_SHORT).show();

                }else if(tcontraseña.getText().toString().trim().length() == 0){
                    Toast.makeText(MainActivity.this, "Ingresa la contraseña", Toast.LENGTH_SHORT).show();
                }


                else{

                    if(tlogin.getText().toString().trim().equals(usuario) && tcontraseña.getText().toString().trim().equals(contraseña)){

                        Intent siguiente = new Intent(MainActivity.this,Inicio.class);
                        startActivity(siguiente);

                        Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(MainActivity.this,"Los campos no son correctos", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });*/
}
