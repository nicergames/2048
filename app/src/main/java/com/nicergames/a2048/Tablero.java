package com.nicergames.a2048;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Tablero {
    private Ficha[][] tablero;
    private int[][] posLibres; //guardo posiciones libres
    //private ArrayList<String> libres = new ArrayList<String>();
    /*private List<String> libres = Arrays.asList("11","12","13","14",
                                                "21","22","23","24",
                                                "31","32","33","34",
                                                "41","42","43","44");*/ //posiciones libres
    private List<String> libres;
    private TextView[][] txt;

    public Tablero() {
        this.tablero = new Ficha[4][4];
        this.posLibres = new int[4][4];

        libres = new ArrayList<String>();
        libres.add("11");libres.add("12");
        libres.add("13");libres.add("14");
        libres.add("21");libres.add("22");
        libres.add("23");libres.add("24");
        libres.add("31");libres.add("32");
        libres.add("33");libres.add("34");
        libres.add("41");libres.add("42");
        libres.add("43");libres.add("44");


        //inicio posiciones libres. 0 lire, 1 ocupado
        for (int i=0; i < this.tablero.length; i++) {
            for (int j=0; j < this.tablero[i].length; j++) {
                this.posLibres[i][j] = 0;
                //this.libres.add(i+""+j);
            }
        }


    }

    public void setComponentes(TextView txt11, TextView txt12, TextView txt13, TextView txt14,
                               TextView txt21, TextView txt22, TextView txt23, TextView txt24,
                               TextView txt31, TextView txt32, TextView txt33, TextView txt34,
                               TextView txt41, TextView txt42, TextView txt43, TextView txt44){
        this.txt = new TextView[4][4];
        this.txt[0][0] = txt11;
        this.txt[0][1] = txt12;
        this.txt[0][2] = txt13;
        this.txt[0][3] = txt14;

        this.txt[1][0] = txt21;
        this.txt[1][1] = txt22;
        this.txt[1][2] = txt23;
        this.txt[1][3] = txt24;

        this.txt[2][0] = txt31;
        this.txt[2][1] = txt32;
        this.txt[2][2] = txt33;
        this.txt[2][3] = txt34;

        this.txt[3][0] = txt41;
        this.txt[3][1] = txt42;
        this.txt[3][2] = txt43;
        this.txt[3][3] = txt44;
    }

    public void setFichaAleatoria(Ficha f){
        int pos = (int)(Math.random()*libres.size());
        String posLibre = libres.get(pos);
        Log.i("POSICIONES","Random pos: "+pos+", get: "+posLibre);
        libres.remove(pos);
        int fila = Integer.parseInt(posLibre.substring(0,1));
        int columna = Integer.parseInt(posLibre.substring(1));
        Log.i("POSICIONES","fila: "+fila+", columna: "+columna);


        this.tablero[fila-1][columna-1] = f;
        this.posLibres[fila-1][columna-1]  = 1;

        this.actualizarTablero();
    }

    public void setFichaAleatoria(){
        Ficha f = new Ficha(2);

        int pos = (int)(Math.random()*libres.size());
        String posLibre = libres.get(pos);
        Log.i("POSICIONES","Random pos: "+pos+", get: "+posLibre);
        libres.remove(pos);
        int fila = Integer.parseInt(posLibre.substring(0,1));
        int columna = Integer.parseInt(posLibre.substring(1));
        Log.i("POSICIONES","fila: "+fila+", columna: "+columna);


        this.tablero[fila-1][columna-1] = f;
        this.posLibres[fila-1][columna-1]  = 1;

        //this.actualizarTablero();
    }

    public void actualizarTablero(){
        String str = "";
        for (int k=0; k < libres.size(); k++){
            str += libres.get(k)+" - ";
        }
        Log.d("LIBRES",str);


        for (int i=0; i < this.tablero.length; i++) {
            for (int j=0; j < this.tablero[i].length; j++) {
                if (this.posLibres[i][j] == 1){
                    this.txt[i][j].setText(this.tablero[i][j].getValor()+"");
                } else {
                    this.txt[i][j].setText("*");
                }
            }
        }
    }

    private void resetFlags(){
        for (int i=0; i < this.tablero.length; i++) {
            for (int j=0; j < this.tablero[i].length; j++) {
                if (this.posLibres[i][j] == 1){
                    this.tablero[i][j].setFlag(0);
                }
            }
        }
    }

    /*
    public void up_old(){
        int posSig, _i, _j, _pSig;
        Log.i("MOVIMIENTOS","UP");

        for (int j=0; j < this.tablero[0].length; j++) {
            for (int i=0; i < this.tablero.length-1; i++) {
                posSig = i+1; _i = i +1; _j = j + 1; _pSig = posSig+1;
                Ficha sig = this.tablero[posSig][j];
                Ficha act = this.tablero[i][j];
                if (sig != null){ //si el proximo a la posicion actual tiene ficha
                    if (act != null) { //si la posicion actual tiene ficha
                        //si ambas posiciones tiene ficha, verifico si tiene el mismo valor para sumarlos
                        if (act.getValor() == sig.getValor()){
                            //TODO: verificar aca si tienen el flag de ya convinado

                            //Setear el valor de la ficha actual con la suma de actual + siguiente
                            this.tablero[i][j].setValor(this.tablero[i][j].getValor() + this.tablero[posSig][j].getValor());
                            //La posicion siguiente queda null porque fue convinada con la actual
                            this.tablero[posSig][j] = null;

                            //Añadir a lista de posiciones libres el lugar de la ficha quitada (sig) (+1 todos)
                            this.libres.add(_pSig+""+_j);
                            this.libres.remove(_i+""+_j);

                            //Actualizo posiciones ocupadas en la matriz
                            this.posLibres[i][j]=1;
                            this.posLibres[posSig][j]=0;
                        } else { //si las fichas son de distinto valor, quedan en el lugar
                            //this.tablero[i][j] = sig; //actual = siguiente
                            //this.tablero[posSig][j] = null;
                            //this.libres.add(_pSig+""+_j);
                            //this.libres.remove(_i+""+_j);
                            //this.posLibres[i][j]=1;
                            //this.posLibres[posSig][j]=0;
                        }
                    } else {
                        this.tablero[i][j] = sig; //actual = siguiente
                        this.tablero[posSig][j] = null;
                        this.libres.add(_pSig+""+_j);
                        this.libres.remove(_i+""+_j);
                        this.posLibres[i][j]=1;
                        this.posLibres[posSig][j]=0;
                    }

                }

            }
        }
        this.setFichaAleatoria();
        this.actualizarTablero();
    }
    */
    public void up(){
        Log.i("MOVIMIENTOS","UP");
        int posSig, _i, _j, _pSig;

        for (int j=0; j < this.tablero[0].length; j++) {
            for (int k=0; k < this.tablero[0].length; k++){
                for (int i=0; i < this.tablero.length-1; i++) {
                    posSig = i+1; _i = i +1; _j = j + 1; _pSig = posSig+1;
                    Ficha sig = this.tablero[posSig][j];
                    Ficha act = this.tablero[i][j];
                    if (sig != null){ //si el proximo a la posicion actual tiene ficha
                        if (act != null) { //si la posicion actual tiene ficha
                            //si ambas posiciones tiene ficha, verifico si tiene el mismo valor para sumarlos
                            if (act.getValor() == sig.getValor()){
                                Log.d("FLAG", "Ficha actual: ("+i+j+"), y ficha siguiente: ("+posSig+j+") son iguales. Flags: "+act.getFlag()+" y "+sig.getFlag());
                                //verificar flag antes de sumar
                                if (this.tablero[i][j].getFlag() == 0 && this.tablero[posSig][j].getFlag() == 0){
                                    //Setear el valor de la ficha actual con la suma de actual + siguiente
                                    this.tablero[i][j].setValor(this.tablero[i][j].getValor() + this.tablero[posSig][j].getValor());
                                    this.tablero[i][j].setFlag(1);
                                    //La posicion siguiente queda null porque fue convinada con la actual
                                    this.tablero[posSig][j] = null;

                                    //Añadir a lista de posiciones libres el lugar de la ficha quitada (sig) (+1 todos)
                                    this.libres.add(_pSig+""+_j);
                                    this.libres.remove(_i+""+_j);

                                    //Actualizo posiciones ocupadas en la matriz
                                    this.posLibres[i][j]=1;
                                    this.posLibres[posSig][j]=0;
                                }
                            }
                        } else {
                            this.tablero[i][j] = sig; //actual = siguiente
                            this.tablero[posSig][j] = null;
                            this.libres.add(_pSig+""+_j);
                            this.libres.remove(_i+""+_j);
                            this.posLibres[i][j]=1;
                            this.posLibres[posSig][j]=0;
                        }
                    }
                }
            }
        }
        this.resetFlags();
        this.setFichaAleatoria();
        this.actualizarTablero();
    }

    public void down(){
        Log.d("MOVIMIENTOS","DOWN");
        int posSig, _i, _j, _pSig;

        for (int j=0; j < this.tablero[0].length; j++) { //cantidad de columnas
            for (int k=0; k < this.tablero[0].length; k++){ //repetir movimientos celda x celda
                for (int i=this.tablero.length-1; i > 0; i--) { //filas
                    posSig = i-1; _i = i +1; _j = j +1; _pSig = posSig+1;
                    Ficha sig = this.tablero[posSig][j];
                    Ficha act = this.tablero[i][j];
                    if (sig != null){ //si el proximo a la posicion actual tiene ficha
                        if (act != null) { //si la posicion actual tiene ficha
                            //si ambas posiciones tiene ficha, verifico si tiene el mismo valor para sumarlos
                            if (act.getValor() == sig.getValor()){
                                Log.d("FLAG", "Ficha actual: ("+i+j+"), y ficha siguiente: ("+posSig+j+") son iguales. Flags: "+act.getFlag()+" y "+sig.getFlag());
                                //verificar flag antes de sumar
                                if (this.tablero[i][j].getFlag() == 0 && this.tablero[posSig][j].getFlag() == 0){
                                    //Setear el valor de la ficha actual con la suma de actual + siguiente
                                    this.tablero[i][j].setValor(this.tablero[i][j].getValor() + this.tablero[posSig][j].getValor());
                                    this.tablero[i][j].setFlag(1);
                                    //La posicion siguiente queda null porque fue convinada con la actual
                                    this.tablero[posSig][j] = null;

                                    //Añadir a lista de posiciones libres el lugar de la ficha quitada (sig) (+1 todos)
                                    this.libres.add(_pSig+""+_j);
                                    this.libres.remove(_i+""+_j);

                                    //Actualizo posiciones ocupadas en la matriz
                                    this.posLibres[i][j]=1;
                                    this.posLibres[posSig][j]=0;
                                }
                            }
                        } else {
                            this.tablero[i][j] = sig; //actual = siguiente
                            this.tablero[posSig][j] = null;
                            this.libres.add(_pSig+""+_j);
                            this.libres.remove(_i+""+_j);
                            this.posLibres[i][j]=1;
                            this.posLibres[posSig][j]=0;
                        }
                    }
                }
            }
        }
        this.resetFlags();
        this.setFichaAleatoria();
        this.actualizarTablero();
    }

    public void left(){
        Log.d("MOVIMIENTOS","LEFT");
    }
    public void right(){
        Log.d("MOVIMIENTOS","RIGHT");
    }

}
