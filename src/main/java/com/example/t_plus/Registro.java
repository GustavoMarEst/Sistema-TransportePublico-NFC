package com.example.t_plus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    Button mBtn_crear;
    EditText mUsuario, mCorreo, mContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        mUsuario = findViewById(R.id.txtr_usuario);
        mCorreo = findViewById(R.id.txtr_correo);
        mContraseña = findViewById(R.id.txtr_contraseña);
        mBtn_crear = findViewById(R.id.btnr_crearcuenta);

        mBtn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarDatos();
            }
        });

        //SIN BASE DE DATOS
    }

    private void insertarDatos(){
        final String nombre = mUsuario.getText().toString().trim();
        final String correo = mCorreo.getText().toString().trim();
        final String contraseña = mContraseña.getText().toString().trim();

        if(nombre.isEmpty()){
            mUsuario.setError("Introduce un nombre de usuario");
            return;
        }else if(correo.isEmpty()){
            mCorreo.setError("Introduce tu correo electronico");
            return;
        }else if(contraseña.isEmpty()){
            mContraseña.setError("Introduce una contraseña");
            return;
        }
        else{
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.1.70:8080/tesis_/register.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("El registro se ha completado Exito")) {
                        Toast.makeText(Registro.this, response, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Registro.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Registro.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registro.this, error.toString(), Toast.LENGTH_SHORT).show();
                    //progressDialog.dismiss();
                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String>param = new HashMap<String, String>();
                    param.put("nombre",nombre);
                    param.put("correo",correo);
                    param.put("contraseña",contraseña);
                    return param;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Registro.this);
            requestQueue.add(request);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void Inicio (View v1){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    /*public void Inicio (View v1){
        Intent siguiente = new Intent(this,Inicio.class);
        startActivity(siguiente);
    }*/

























    /*mUsuario = (EditText) findViewById(R.id.txtr_usuario);
        mContraseña = (EditText) findViewById(R.id.txtr_contraseña);
        mConfirmar = (EditText) findViewById(R.id.txtr_confirmar);
        mBtn_crear = (Button) findViewById(R.id.btnr_crearcuenta);

        mBtn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preference = getSharedPreferences("Reg", MODE_PRIVATE);
                String usuario = mUsuario.getText().toString().trim();
                String contraseña = mContraseña.getText().toString().trim();
                String cContraseña = mConfirmar.getText().toString().trim();


                if (usuario.length() == 0){

                    Toast.makeText(Registro.this, "Ingrese un nombre de usuario", Toast.LENGTH_SHORT).show();

                } else if (contraseña.length() == 0){

                    Toast.makeText(Registro.this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();

                }else if (cContraseña.length() == 0){

                    Toast.makeText(Registro.this, "Confirme su contraseña", Toast.LENGTH_SHORT).show();

                }else if (contraseña.equals(cContraseña)){

                    SharedPreferences.Editor editor = preference.edit();
                    editor.putString("Usuario", usuario);
                    editor.putString("Contraseña", contraseña);
                    editor.putString("Confirmar Contraseña", cContraseña);
                    editor.commit();

                    finish();

                    Toast.makeText(Registro.this, "Cuenta creada con exito", Toast.LENGTH_SHORT).show();
                    Intent siguiente = new Intent(Registro.this,Inicio.class);
                    startActivity(siguiente);


                }

                else{
                    Toast.makeText(Registro.this, "Las constraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }




            }
        });*/
}
