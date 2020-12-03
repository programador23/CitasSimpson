package cl.inacap.citassimpson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cl.inacap.citassimpson.adapters.PersonajesAdapter;
import cl.inacap.citassimpson.dto.Personaje;

public class MainActivity extends AppCompatActivity {
Spinner selector_numb;

private List<Personaje> personajes = new ArrayList<>();
private ListView personajesList;
private PersonajesAdapter personajesAdapter;
private RequestQueue queue;
private TextView pruebanombre;
private Button crearPeticion;

    @Override
    protected void onResume() {

        super.onResume();
        queue = Volley.newRequestQueue(this);
        this.personajesList = findViewById(R.id.id_lisView);
        this.personajesAdapter = new PersonajesAdapter(this,R.layout.list_personajes,this.personajes);
        this.personajesList.setAdapter(this.personajesAdapter);
        this.crearPeticion=findViewById(R.id.btn_Solicitar);

        this.crearPeticion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String opcion_spinner;
                opcion_spinner=selector_numb.getSelectedItem().toString();






        StringRequest request = new StringRequest(Request.Method.GET, "https://thesimpsonsquoteapi.glitch.me/quotes?count="+opcion_spinner
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                personajes.clear();
                try {


                    Personaje[] arreglo = new Gson()
                            .fromJson(response, Personaje[].class);
                    personajes.addAll(Arrays.asList(arreglo));

                }catch (Exception ex){
                    personajes.clear();
                    Log.e("mono","Error de peticion");
                }finally {
                    personajesAdapter.notifyDataSetChanged();
                }
            }



       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
            personajes.clear();
            Log.e("Personajes","Error de respuesta");
           personajesAdapter.notifyDataSetChanged();
           }
       });

        queue.add(request);

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        selector_numb = this.findViewById(R.id.spinner_number);
        ArrayList<String> Spinner_number = new ArrayList<>();
        Spinner_number.add("1");
        Spinner_number.add("2");
        Spinner_number.add("3");
        Spinner_number.add("4");
        Spinner_number.add("5");
        Spinner_number.add("6");
        Spinner_number.add("7");
        Spinner_number.add("8");
        Spinner_number.add("9");
        Spinner_number.add("10");
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Spinner_number);
        selector_numb.setAdapter(adapter);



    }



}