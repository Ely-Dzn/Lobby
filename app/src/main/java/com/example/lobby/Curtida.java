package com.example.lobby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class Curtida extends AppCompatActivity {
    private Button anterio;
    private Button curtir;
    private Button next;

    private List<String> reg;

    private List<Filme> movie;
    private int precusor;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference user = reference.child("Usuario");


    private ListView listinha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curtida);


        listinha = findViewById(R.id.ListView2);
        anterio = findViewById(R.id.button5);
        curtir = findViewById(R.id.button4);
        next = findViewById(R.id.button3);
        precusor = 0;
        reg = new ArrayList<>();
        movie = new ArrayList<>();

        user.addValueEventListener(new ValueEventListener() {
            ArrayList<String> regi= new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                regi.clear();

                if(snapshot.exists()){
                    for(DataSnapshot snap : snapshot.getChildren()){
                        regi.add(snap.getKey());
                    }
                    updateRa(regi);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        anterio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             if(!reg.isEmpty())   {
                if(precusor>0){
                    precusor--;
                    String ra;
                    ra=reg.get(precusor);

                    DatabaseReference verfilme=user.child(ra).child("Filme");
                    verfilme.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            movie.clear();
                            if(snapshot.exists()){
                                for(DataSnapshot snap : snapshot.getChildren()){
                                Filme fil=new Filme();
                                fil.setNome(snap.child("Nome").getValue().toString());
                                fil.setAno(snap.child("Ano").getValue().toString());
                                movie.add(fil);
                                }
                                update();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }}

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!reg.isEmpty())   {
                    if(precusor<reg.size()-1){
                        precusor++;
                        String ra;
                        ra=reg.get(precusor);

                        DatabaseReference verfilme=user.child(ra).child("Filme");
                        verfilme.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                movie.clear();
                                if(snapshot.exists()){
                                    for(DataSnapshot snap : snapshot.getChildren()){
                                        Filme fil=new Filme();
                                        fil.setNome(snap.child("Nome").getValue().toString());
                                        fil.setAno(snap.child("Ano").getValue().toString());

                                        movie.add(fil);
                                    }
                                    update();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }}

            }
        });


        curtir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference REA= user.child(reg.get(precusor)).child("Curtida");
                REA.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot date = task.getResult();
                            if(date.exists()){
                                int i = Integer.valueOf(date.getValue().toString());
                                incCurtida(i);

                            }
                         }
                        else {
                         }



                    }
                });



            }
        });



    }
    public void incCurtida(int curtida){
        curtida++;
        user.child(reg.get(precusor)).child("Curtida").setValue(curtida);
    }
public void updateRa(ArrayList<String> rege){
        reg=rege;
    if (!rege.isEmpty()){
        DatabaseReference verfilme = user.child(rege.get(precusor)).child("Filme");
        verfilme.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movie.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Filme fil = new Filme();
                        fil.setNome(snap.child("Nome").getValue().toString());
                        fil.setAno(snap.child("Ano").getValue().toString());
                        movie.add(fil);
                    }
                    update();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

    public void update(){
        ArrayAdapter<Filme> filme = new ArrayAdapter<>(
             getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                movie
        );
        listinha.setAdapter(filme);

    }

}