package com.example.lobby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class cadastro extends AppCompatActivity {

    private TextView RA;
    private TextView Filme1;

    private TextView ano1;



    private Button  registra;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        RA = findViewById(R.id.TextInputEditText);
        Filme1 = findViewById(R.id.editTextTextPersonName3);

        ano1 = findViewById(R.id.anos1);

        registra = findViewById(R.id.button6);


        registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String registro = String.valueOf(RA.getText());
               String filme1 = String.valueOf(Filme1.getText());
                DatabaseReference usuario = reference.child("Usuario").child(registro);
                String Ano1 = String.valueOf(ano1.getText());

                usuario.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot date = task.getResult();
                            if(date.exists()){
                                CadastroExiste(registro,filme1,Ano1);
                            }
                            else{
                                CadastroNaoExiste(registro,filme1,Ano1);
                            }
                         }

                        else {
                         }
                    }
                });

            }});
    }

    public void CadastroExiste(String registro,String filme1,String Ano1){
        DatabaseReference usuario = reference.child("Usuario").child(registro).child("Filme");

        usuario.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot date = task.getResult();
                    if(date.exists()){
                        List<String> verTam=new ArrayList<>();
                        for(DataSnapshot ver: date.getChildren()){
                            String nome,ano;
                            nome=ver.child("Nome").getValue().toString();
                            Log.d("teste",nome);
                            verTam.add(nome);
                        }
                        if(verTam.size()<4){
                            int id = verTam.size()+1;
                            String ID =String.valueOf(id);
                            reference.child("Usuario").child(registro).child("Filme").child(ID).child("Nome").setValue(filme1);
                            reference.child("Usuario").child(registro).child("Filme").child(ID).child("Ano").setValue(Ano1);
                        }
                        else{

                        }
                    }
                    else{

                    }
                }

                else {
                }
            }
        });


    }



    public void CadastroNaoExiste(String registro,String filme1,String Ano1){
        reference.child("Usuario").child(registro).child("Filme").child("1").child("Nome").setValue(filme1);
        reference.child("Usuario").child(registro).child("Filme").child("1").child("Ano").setValue(Ano1);
        reference.child("Usuario").child(registro).child("Curtida").setValue(0);
    }



}