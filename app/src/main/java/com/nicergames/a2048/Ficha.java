package com.nicergames.a2048;

public class Ficha {
    private int valor;
    private int flag; // 1 -> ya combinada

    public void setValor(int valor){
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public void setFlag(int flag){
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public Ficha(int valor){
        this.flag = 0;
        this.valor = valor;
    }

    public Ficha(int valor, int flag){
        this.flag = flag;
        this.valor = valor;
    }
}
