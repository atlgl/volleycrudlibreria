package com.example.angelus.applibrovolleyv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String url="http://10.0.2.2/wsciudades/applibros/LibroRest.php";//url en donde se obtienen json
    private TextView txtid;
    private EditText txtnombre,txtdesc;
    private ListView listViewlibros;
    private Button btnok,btnno;
    private RequestQueue volleycola;
    private AdapterLibros libroArrayAdapter;
    private int opc=0;
    private int count=0;

    public void init(){
        txtid=(TextView) findViewById(R.id.txtid);
        txtnombre=(EditText) findViewById(R.id.txtnombre);
        txtdesc=(EditText) findViewById(R.id.txtdescripcion);
        listViewlibros=(ListView) findViewById(R.id.listLibros);

        listViewlibros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                count++;
                if(count>=2) {

                    Libro l = libroArrayAdapter.getItem(position);
                    libroArrayAdapter.remove(l);
                    libroArrayAdapter.notifyDataSetChanged();
                    //////////-------------------Metodo para eliminar
                    JSONObject objdel = new JSONObject();
                    try {
                        objdel.put("idoper", 3);
                        objdel.put("idlibros", l.getIdlibros());
                    } catch (JSONException ex1) {
                        ex1.printStackTrace();
                    }

                    JsonObjectRequest requestdel = new JsonObjectRequest(Request.Method.POST,
                            url, objdel, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Toast.makeText(getBaseContext(), response.get("mensaje").toString(), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
                    );

                    volleycola.add(requestdel);
                    /////////////--------------------fin del metodo para eliminar
                }else
                {
                    Libro l2=libroArrayAdapter.getItem(position);
                    txtid.setText(Integer.toString(l2.getIdlibros()));
                    txtnombre.setText(l2.getNombre());
                    txtdesc.setText(l2.getDescripcion());
                    opc=1;

                }
            }
        });
        btnok=(Button) findViewById(R.id.btnok);
        btnno=(Button) findViewById(R.id.btnno);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crear objeto para guarda+r en bd
                if(opc==0) {
                    JSONObject object = new JSONObject();
                    try {

                        object.put("idoper", 1);
                        object.put("nombre", txtnombre.getText().toString());
                        object.put("descripcion", txtdesc.getText().toString());
                        object.put("foto", "portada" + txtnombre.getText().toString() + ".jpg");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest request =
                            new JsonObjectRequest(
                                    Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    String mensaje = null;
                                    try {

                                        mensaje = response.getString("mensaje");
                                        Toast.makeText(getBaseContext(), mensaje, Toast.LENGTH_LONG).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                    volleycola.add(request);
                } else
                {
                    opc=0;
                    JSONObject objedit=new JSONObject();
                    try {
                        objedit.put("idoper",2);
                        objedit.put("idlibros",Integer.parseInt(txtid.getText().toString()));
                        objedit.put("nombre",txtnombre.getText().toString());
                        objedit.put("descripcion",txtdesc.getText().toString());
                        objedit.put("foto","portada"+txtnombre.getText().toString()+".jpg");

                    }catch (JSONException ex1){
                        ex1.printStackTrace();
                    }
                    JsonObjectRequest objectReqEdit=new JsonObjectRequest(Request.Method.POST,
                            url, objedit, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String mensaje=response.getString("mensaje");
                                Toast.makeText(getBaseContext(),mensaje,Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    volleycola.add(objectReqEdit);



                }

            }
        });



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        volleycola= Volley.newRequestQueue(this); //crear la cola de mensajes


        //crear objeto que recibe los datos
        JsonObjectRequest objgetall=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array=response.getJSONArray("libro");

                            List<Libro> libroList=new ArrayList<>();
                            for(int i=0; i<array.length();i++){
                                Libro l=new Libro();
                                l.setIdlibros(array.getJSONObject(i).getInt("idlibros"));
                                l.setNombre(array.getJSONObject(i).getString("nombre"));
                                l.setDescripcion(array.getJSONObject(i).getString("descripcion"));
                                libroList.add(l);
                            }
                            libroArrayAdapter=new AdapterLibros(getBaseContext(),libroList);
                            listViewlibros.setAdapter(libroArrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

            }
        });

        volleycola.add(objgetall);






        //volleycola.add(objectRequest1);

        //volleycola.add(objectRequest);//peticion para devuelva todos los objetos
        //volleycola.add(request);//peticion para enviar un objeto y lo guarde
    }
}
