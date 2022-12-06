package com.example.mongodb_criptos;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MostrarPrecioBitCoin extends AppCompatActivity {
    private ArrayList<Criptomoneda> array;
    private Criptomoneda cripto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_preciobit_coin);
        //recojo los datos que recibo de la otra actividad
        //  array=getIntent().getParcelableArrayListExtra("array");
        //recojo el precio
        Intent recogerPartida = getIntent();
        cripto = (Criptomoneda) recogerPartida.getSerializableExtra("cripto");

        String valor = cripto.getPrecio();
        String nombre=cripto.getNombre();
        if(valor!=null) {
            System.out.println("precio recibido " + valor);
            EditText mostrarPrecio =  findViewById(R.id.idMostrarPrecio);
            mostrarPrecio.setText(valor);
            TextView texto=findViewById(R.id.textView);
            texto.setText("El precio de " + nombre+": ");
        }else{
            Toast.makeText(getApplicationContext(), "Mostrando actividad sin datos!!", Toast.LENGTH_SHORT).show();
        }



    }


    public void volverMain(View vista){
        Intent i=new Intent(getApplicationContext(), MainActivity.class);
        // i.putExtra("array", array);
        startActivity(i);
    }


}