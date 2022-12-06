package com.example.mongodb_criptos;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ArrayList<Criptomoneda> arrayCriptomonedas;

    ActivityResultLauncher<Intent> lanzadorActividadLista = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG, "onActivityResult:");
                    Intent datosDeMongo = result.getData();


                }
            }

    );
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayCriptomonedas = new ArrayList<>();
        /*Criptomoneda cripto=new Criptomoneda("btc", "bitcoin", 200.0);
        arrayCriptomonedas.add(cripto);*/
        //  if(getIntent().getExtras()!=null||getIntent().getExtras()==null){

        getMongo();
        //Duermo el hilo por 1000 milisegundos para que me muestre los datos en el listview
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayCriptomonedas );
        ListView listaComp = findViewById(R.id.listView);
        listaComp.setAdapter(adaptador);
        listaComp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //lanzo un intent a otra actividad

                //intento.putExtra("array", arrayCriptomonedas);
                Intent irAlista = new Intent(getApplicationContext(), MostrarPrecioBitCoin.class);
                irAlista.putExtra("cripto", arrayCriptomonedas.get(position));
                lanzadorActividadLista.launch(irAlista);

            }
        });
    }
//}


    //CONEXION CON MONGODB
    private void getMongo() { // mongod --port 27017 --dbpath C:\MongoDB\data\db --bind_ip_all

        class GetMONGO extends AsyncTask<Void, Void, String> {
            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            //Document doc;
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String jsonStr) {
                super.onPostExecute(jsonStr);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "________________________json: " + jsonStr);
                System.out.println(jsonStr);
                Toast.makeText(getApplicationContext(), jsonStr, Toast.LENGTH_SHORT).show();
            }

            //in this method we are fetching the json string
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    String uri = "mongodb://192.168.1.129:27017";
                    MongoClient mongoClient = MongoClients.create(uri);
                    MongoDatabase db = mongoClient.getDatabase("criptomonedasdb");
                    MongoCollection<Document> collection = db.getCollection("misCriptomonedas");

                    collection.find().forEach(doc -> {
                        System.out.println(doc.toJson());
                        String token = null;
                        String nombre = null;
                        String precio = null;
                        JSONObject jsonObject = new JSONObject(doc);
                        try {
                            token = jsonObject.getString("token");
                            nombre = jsonObject.getString("nombre");
                            precio = jsonObject.getString("precio");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Criptomoneda cripto = new Criptomoneda(token, nombre, precio);
                        arrayCriptomonedas.add(cripto);

                        System.out.println("tamaño del array" +  arrayCriptomonedas.size());

                    });

                    return uri;
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetMONGO getMongo = new GetMONGO();
        getMongo.execute();

       /* try {
            // MongoClient mongoClient = new MongoClient(new ServerAddress("localhost"));
            MongoClient mongoClient = new MongoClient("192.168.56.1", 27017);
            MongoDatabase db = mongoClient.getDatabase("adivinandoPalabras");
            MongoCollection<Document> collection = db.getCollection("palabras");
            Document doc = (Document) collection.find();
            System.out.println(doc.toJson());
            Toast.makeText(getApplicationContext(), "doc.toJson() ", Toast.LENGTH_SHORT).show();
            mongoClient.close();
        }catch (Exception e){e.printStackTrace();}   */
    }

    //FIN CONEXION CON MONGODB
}