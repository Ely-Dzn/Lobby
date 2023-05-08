package com.example.lobby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    private Button botao_troca_cadasto;
    private  Button botao_troca_curtida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botao_troca_cadasto= findViewById(R.id.button);
        botao_troca_curtida= findViewById(R.id.button2);

        botao_troca_cadasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),cadastro.class);
                startActivity(intent);
            }
        });


        botao_troca_curtida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Curtida.class);
                startActivity(intent);

            }
        });


    }


}