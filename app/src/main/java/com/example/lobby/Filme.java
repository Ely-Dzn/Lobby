package com.example.lobby;

import androidx.annotation.NonNull;

public class Filme {
    String nome;
    String Ano;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAno(String ano) {
        Ano = ano;
    }

    public String getNome() {
        return nome;
    }

    public String getAno() {
        return Ano;
    }

    @NonNull
    @Override
    public String toString() {
        return ""+getNome()+"  "+getAno();
    }
}
