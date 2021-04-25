package com.nicergames.a2048;

public class Ficha {
    //private int id; // posicion en la matriz
    private int valor;
    private int flag; // 1 -> ya fusionada

    public void setValor(int valor){
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public Ficha(/*int id*/){
        //this.id = id;
        this.flag = 0;

        //aca calcular valor aleatoriamente: 2 o 4, con > porcentaje de 2
    }

    public Ficha(int valor){
        //this.id = id;
        this.flag = 0;
        this.valor = valor;
    }
}
