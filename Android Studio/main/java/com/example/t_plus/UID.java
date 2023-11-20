package com.example.t_plus;

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

import java.util.HashMap;
import java.util.Map;

public class UID extends AppCompatActivity {

    Button confirmar;
    private TextView bienvenida, id;
    EditText uidd, propietario, cUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uid);

        bienvenida = findViewById(R.id.textView2);
        confirmar = findViewById(R.id.btnr_CONFIRMAR);
        id = findViewById(R.id.txtrr_id);

        uidd = findViewById(R.id.txt_expire);
        propietario = findViewById(R.id.txt_namePro);
        cUID = findViewById(R.id.txt_confUid);

        String idValue = getIntent().getStringExtra("idValue");

        id.setText(idValue);


        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarUID();
            }
        });
    }


    private void agregarUID(){
        final String nombre = propietario.getText().toString().trim();
        final String uid = uidd.getText().toString().trim();
        final String confirmarUID = cUID.getText().toString().trim();

        if (nombre.isEmpty()){
            propietario.setError("Introduce el nombre del propietario de la tarjeta");
            return;
        }else if(uid.isEmpty()){
            uidd.setError("Introduce el UID de la tarjeta");
            return;
        }else if(confirmarUID.isEmpty()){
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
                    if (response.equalsIgnoreCase("El enlazamiento de UID se ha completado Exito")) {
                        Toast.makeText(UID.this, response, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UID.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(UID.this, error.toString(), Toast.LENGTH_SHORT).show();
                    //progressDialog.dismiss();
                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String>param = new HashMap<String, String>();
                    param.put("uid",uid);
                    return param;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(UID.this);
            requestQueue.add(request);

        }
    }
}
