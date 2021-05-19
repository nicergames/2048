package com.nicergames.a2048;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private Ficha[][] tablero; //matriz de fichas
    private int[][] posLibres; //matriz de posiciones libres (0 libre o 1 ocupado)
    private List<String> libres; //Lista de posiciones libres

    private TextView[][] txt; //Matriz de textviews
    private Context context; //contexto/actividad
    private static final int MARCA_FIN = 32; //Puntaje objetivo para ganar
    private boolean flagFin;

    private static int puntaje = 0;


    //private String[] colores;

    public Tablero(Context context) {
        this.tablero = new Ficha[4][4];
        this.posLibres = new int[4][4];

        this.context = context; //Contexto para los Toast
        this.flagFin = false; //Flag de finalizacion

        //Inicio de lista, todas las posiciones libres
        libres = new ArrayList<String>();
        libres.add("11");libres.add("12");
        libres.add("13");libres.add("14");
        libres.add("21");libres.add("22");
        libres.add("23");libres.add("24");
        libres.add("31");libres.add("32");
        libres.add("33");libres.add("34");
        libres.add("41");libres.add("42");
        libres.add("43");libres.add("44");


        //colores = context.getResources().getStringArray(R.array.background_numbers);
        //Log.i("Colores", context.getResources().getStringArray(R.array.background_numbers)[1]+"");
        //Log.i("COLORES", context.getResources().getColor(R.color.md_2)+"");
        //String id[] = context.getResources().getStringArray(R.array.background_numbers);
        //Log.i("COLORES", context.getResources().getIntArray(R.array.background_numbers)[1]+"");
        //getColor(R.color.md_2)

        //inicio de matriz de posiciones libres. 0 lire, 1 ocupado
        for (int i=0; i < this.tablero.length; i++) {
            for (int j=0; j < this.tablero[i].length; j++) {
                this.posLibres[i][j] = 0;
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

    public int getPuntaje(){
        return this.puntaje;
    }
    public Ficha[][] getTablero(){
        return this.tablero;
    }

    public int[][] getPosLib(){
        return this.posLibres;
    }
    public List<String> getListPosLib(){
        return this.libres;
    }



    public void setFichaAleatoria(Ficha f){
        int pos = (int)(Math.random()*libres.size());
        String posLibre = libres.get(pos);
        Log.i("POSICIONES","Random pos: "+pos+", get: "+posLibre);
        libres.remove(pos);

        // "11" -> "1" (fila) "1" (columna) -> transformar de string a entero
        int fila = Integer.parseInt(posLibre.substring(0,1)); //valor de la fila
        int columna = Integer.parseInt(posLibre.substring(1));
        Log.i("POSICIONES","fila: "+fila+", columna: "+columna);


        this.tablero[fila-1][columna-1] = f; //se asigna la ficha a la matriz
        this.posLibres[fila-1][columna-1]  = 1; //actualizar la matriz de posiciones libres

        this.actualizarTablero();
    }

    public void setFichaAleatoria(){
        if (this.hayLugar()){

            //Crear una ficha con valor 2
            Ficha f = new Ficha(2);

            //this.setFichaAleatoria(f);

            //Numero random entre 0 y el tamaño de la lista
            int pos = (int)(Math.random()*libres.size());

            //Obtener dato de la posicion random
            String posLibre = libres.get(pos);

            //Debug en consola
            Log.d("POSICIONES","Random pos: "+pos+", get: "+posLibre);

            //Eliminar el elemento extraido en la lista para que queda actualizado (ahora ya no sera una pos libre)
            libres.remove(pos);

            //Partir a la mitad el string y convertirlo a int. primer digito es fila, segundo digito es columna
            int fila = Integer.parseInt(posLibre.substring(0,1));
            int columna = Integer.parseInt(posLibre.substring(1));

            //Debug en consola
            Log.d("POSICIONES","fila: "+fila+", columna: "+columna);

            //Ubicar la ficha en la posicion y actualizar matriz de posiciones libres.
            this.tablero[fila-1][columna-1] = f;
            int w = obtenerColor(0,0,this.tablero[fila-1][columna-1].getValor());
            this.posLibres[fila-1][columna-1]  = 1; //  1=ocupada
        } else {
            Toast.makeText(this.context, "No hay posiciones vacias para realizar este movimiento", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean hayLugar(){
        return !libres.isEmpty();
    }

    public static int obtenerColor(int k, int aux, int numero){
        if (numero==2) {
            k=k+1;
        } else if (numero%2 != 0){
            k=k+1;
            aux=numero/10;
            numero=aux;
            obtenerColor(k, aux, numero);
        }
        return k;
    }

    public void actualizarTablero(){
        /*
        //Debug de posiciones libres (lista)
        String str = "";
        for (int k=0; k < libres.size(); k++){
            str += libres.get(k)+" - ";
        }
        Log.d("LIBRES",str);
        */

        //Recorrer matriz para actualizar valores de textviews
        for (int i=0; i < this.tablero.length; i++) {
            for (int j=0; j < this.tablero[i].length; j++) {
                if (this.posLibres[i][j] == 1){ //Si esta ocupada la posicion muestro valor de la ficha
                    this.txt[i][j].setText(this.tablero[i][j].getValor()+""); //setear el textview con el valor de la ficha
                } else {
                    this.txt[i][j].setText("");
                }
            }
        }

        if (this.flagFin){
            //Toast.makeText(this.context, "WIN!", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetFlags(){
        //Recorrer matriz para actualizar flags de combinacion
        for (int i=0; i < this.tablero.length; i++) {
            for (int j=0; j < this.tablero[i].length; j++) {
                if (this.posLibres[i][j] == 1){ //Si esta ocupada seteo el flag en 0
                    this.tablero[i][j].setFlag(0);
                }
            }
        }
    }

    /*

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },1);

     */

    public void up2(int j){
        Log.i("MOVIMIENTOS","UP");
        int posSig, _i, _j, _pSig; //Variables auxiliares
        for (int i=0; i < this.tablero[0].length-1; i++) { //Fila
            posSig = i+1; //La siguiente ficha, como el recorrido es vertical es i+1
            _i = i +1; _j = j + 1; _pSig = posSig+1; //Auxiliares para la lista, tienen un +1 porque en matriz la pos [0][0] en lista es "11"

            Ficha sig = this.tablero[posSig][j]; //Ficha siguiente
            Ficha act = this.tablero[i][j]; //Ficha actual
            if (sig != null){ //si el siguiente tiene ficha
                if (act != null) { //si la posicion actual tiene ficha
                    //si ambas posiciones tienen ficha, verifico si tiene el mismo valor para sumarlos
                    if (act.getValor() == sig.getValor()){
                        Log.d("FLAG", "Ficha actual: ("+i+j+"), y ficha siguiente: ("+posSig+j+") son iguales. Flags: "+act.getFlag()+" y "+sig.getFlag());
                        //verificar flag antes de sumar
                        if (act.getFlag() == 0 && sig.getFlag() == 0){
                            //Setear el valor de la ficha actual con la suma de actual + siguiente
                            this.tablero[i][j].setValor(act.getValor() + sig.getValor());
                            this.tablero[i][j].setFlag(1);
                            int w = obtenerColor(0, 0,this.tablero[i][j].getValor());
                            this.puntaje = this.puntaje + this.tablero[i][j].getValor();

                            this.txt[i][j].setBackgroundColor(context.getResources().getIntArray(R.array.background_numbers)[w]);
                            this.txt[i][posSig].setBackgroundColor(context.getResources().getIntArray(R.array.background_numbers)[w]);
                            //Controlar puntaje ganador
                            if (this.tablero[i][j].getValor() == this.MARCA_FIN) { this.flagFin = true; }

                            //La posicion siguiente queda null porque fue combinada con la actual
                            this.tablero[posSig][j] = null;

                            //Añadir a lista de posiciones libres el lugar de la ficha quitada (sig) (+1 todos -> variables auxiliares)
                            this.libres.add(_pSig+""+_j); //21
                            //Eliminar de la lista de posiciones libres el lugar de la ficha combinada (+1 todos -> variables auxiliares)
                            this.libres.remove(_i+""+_j); //11

                            //Actualizo posiciones ocupadas en la matriz
                            this.posLibres[i][j]=1; //ocupada
                            this.posLibres[posSig][j]=0; //libre
                        }
                    }
                } else {
                    this.tablero[i][j] = sig; //actual = siguiente
                    this.tablero[posSig][j] = null; //siguiente = null
                    this.libres.add(_pSig+""+_j);
                    this.libres.remove(_i+""+_j);
                    this.posLibres[i][j]=1;
                    this.posLibres[posSig][j]=0;
                }
            }

        }
    }

    public void down2(int j){
        Log.d("MOVIMIENTOS","DOWN");
        int posSig, _i, _j, _pSig;
        for (int i=this.tablero.length-1; i > 0; i--) {
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
                                    this.tablero[i][j].setValor(act.getValor() + sig.getValor());
                                    this.tablero[i][j].setFlag(1);
                                    final int w = obtenerColor(0,0,this.tablero[i][j].getValor());
                                    this.puntaje = this.puntaje + this.tablero[i][j].getValor();

                                    this.txt[i][j].setBackgroundColor(context.getResources().getIntArray(R.array.background_numbers)[w]);
                                    this.txt[i][posSig].setBackgroundColor(context.getResources().getIntArray(R.array.background_numbers)[w]);
                                    //Controlar puntaje ganador
                                    if (this.tablero[i][j].getValor() == this.MARCA_FIN) { this.flagFin = true; }

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

    public void left2(int i){
        Log.d("MOVIMIENTOS","LEFT");
        int posSig, _i, _j, _pSig;
        for (int j=0; j < this.tablero[i].length-1; j++) { //filas
            posSig = j+1; _i = i +1; _j = j +1; _pSig = posSig+1;
            Ficha sig = this.tablero[i][posSig];
            Ficha act = this.tablero[i][j];
            if (sig != null){ //si el proximo a la posicion actual tiene ficha
                if (act != null) { //si la posicion actual tiene ficha
                    //si ambas posiciones tiene ficha, verifico si tiene el mismo valor para sumarlos
                    if (act.getValor() == sig.getValor()){
                        Log.d("FLAG", "Ficha actual: ("+i+j+"), y ficha siguiente: ("+posSig+j+") son iguales. Flags: "+act.getFlag()+" y "+sig.getFlag());
                        //verificar flag antes de sumar
                        if (this.tablero[i][j].getFlag() == 0 && this.tablero[i][posSig].getFlag() == 0){
                            //Setear el valor de la ficha actual con la suma de actual + siguiente
                            this.tablero[i][j].setValor(act.getValor() + sig.getValor());
                            this.tablero[i][j].setFlag(1);
                            int w = obtenerColor(0,0,this.tablero[i][j].getValor());
                            this.puntaje = this.puntaje + this.tablero[i][j].getValor();

                            this.txt[i][j].setBackgroundColor(context.getResources().getIntArray(R.array.background_numbers)[w]);
                            this.txt[i][posSig].setBackgroundColor(context.getResources().getIntArray(R.array.background_numbers)[w]);
                            //Controlar puntaje ganador
                            if (this.tablero[i][j].getValor() == this.MARCA_FIN) { this.flagFin = true; }

                            //La posicion siguiente queda null porque fue convinada con la actual
                            this.tablero[i][posSig] = null;

                            //Añadir a lista de posiciones libres el lugar de la ficha quitada (sig) (+1 todos)
                            this.libres.add(_i+""+_pSig);
                            this.libres.remove(_i+""+_j);

                            //Actualizo posiciones ocupadas en la matriz
                            this.posLibres[i][j]=1;
                            this.posLibres[i][posSig]=0;
                        }
                    }
                } else {
                    this.tablero[i][j] = sig; //actual = siguiente
                    this.tablero[i][posSig] = null;
                    this.libres.add(_i+""+_pSig);
                    this.libres.remove(_i+""+_j);
                    this.posLibres[i][j]=1;
                    this.posLibres[i][posSig]=0;
                }
            }
        }
    }

    public void right2(int i){
        Log.d("MOVIMIENTOS","RIGHT");
        int posSig, _i, _j, _pSig;
        for (int j=this.tablero[i].length-1; j > 0; j--) { //filas
                    posSig = j-1; _i = i +1; _j = j +1; _pSig = posSig+1;
                    Ficha sig = this.tablero[i][posSig];
                    Ficha act = this.tablero[i][j];
                    if (sig != null){ //si el proximo a la posicion actual tiene ficha
                        if (act != null) { //si la posicion actual tiene ficha
                            //si ambas posiciones tiene ficha, verifico si tiene el mismo valor para sumarlos
                            if (act.getValor() == sig.getValor()){
                                Log.d("FLAG", "Ficha actual: ("+i+j+"), y ficha siguiente: ("+posSig+j+") son iguales. Flags: "+act.getFlag()+" y "+sig.getFlag());
                                //verificar flag antes de sumar
                                if (this.tablero[i][j].getFlag() == 0 && this.tablero[i][posSig].getFlag() == 0){
                                    //Setear el valor de la ficha actual con la suma de actual + siguiente
                                    this.tablero[i][j].setValor(act.getValor() + sig.getValor());
                                    this.tablero[i][j].setFlag(1);
                                    final int w = obtenerColor(0,0,this.tablero[i][j].getValor());
                                    this.puntaje = this.puntaje + this.tablero[i][j].getValor();

                                    this.txt[i][j].setBackgroundColor(context.getResources().getIntArray(R.array.background_numbers)[w]);
                                    this.txt[i][posSig].setBackgroundColor(context.getResources().getIntArray(R.array.background_numbers)[w]);
                                    //Controlar puntaje ganador
                                    if (this.tablero[i][j].getValor() == this.MARCA_FIN) { this.flagFin = true; }

                                    //La posicion siguiente queda null porque fue convinada con la actual
                                    this.tablero[i][posSig] = null;

                                    //Añadir a lista de posiciones libres el lugar de la ficha quitada (sig) (+1 todos)
                                    this.libres.add(_i+""+_pSig);
                                    this.libres.remove(_i+""+_j);

                                    //Actualizo posiciones ocupadas en la matriz
                                    this.posLibres[i][j]=1;
                                    this.posLibres[i][posSig]=0;
                                }
                            }
                        } else {
                            this.tablero[i][j] = sig; //actual = siguiente
                            this.tablero[i][posSig] = null;
                            this.libres.add(_i+""+_pSig);
                            this.libres.remove(_i+""+_j);
                            this.posLibres[i][j]=1;
                            this.posLibres[i][posSig]=0;
                        }
                    }
                }
    }

    public void afterMov(){
        this.resetFlags();
        this.setFichaAleatoria();
        this.actualizarTablero();
    }

    //El metodo UP tiene mayor nivel de detalle en los comentarios
    public void up(){
        Log.i("MOVIMIENTOS","UP");
        int posSig, _i, _j, _pSig; //Variables auxiliares

        for (int j=0; j < this.tablero[0].length; j++) { //Columna
            for (int k=0; k < this.tablero[0].length; k++){ //Repeticion para mover fichas en toda la longitud
                for (int i=0; i < this.tablero[0].length-1; i++) { //Fila
                    posSig = i+1; //La siguiente ficha, como el recorrido es vertical es i+1
                    _i = i +1; _j = j + 1; _pSig = posSig+1; //Auxiliares para la lista, tienen un +1 porque en matriz la pos [0][0] en lista es "11"

                    Ficha sig = this.tablero[posSig][j]; //Ficha siguiente
                    Ficha act = this.tablero[i][j]; //Ficha actual
                    if (sig != null){ //si el siguiente tiene ficha
                        if (act != null) { //si la posicion actual tiene ficha
                            //si ambas posiciones tienen ficha, verifico si tiene el mismo valor para sumarlos
                            if (act.getValor() == sig.getValor()){
                                Log.d("FLAG", "Ficha actual: ("+i+j+"), y ficha siguiente: ("+posSig+j+") son iguales. Flags: "+act.getFlag()+" y "+sig.getFlag());
                                //verificar flag antes de sumar
                                if (act.getFlag() == 0 && sig.getFlag() == 0){
                                    //Setear el valor de la ficha actual con la suma de actual + siguiente
                                    this.tablero[i][j].setValor(act.getValor() + sig.getValor());
                                    this.tablero[i][j].setFlag(1);

                                    this.puntaje = this.puntaje + this.tablero[i][j].getValor();
                                    //this.txt[i][j].setBackgroundColor(Color.parseColor(colores[k]));


                                    //Controlar puntaje ganador
                                    if (this.tablero[i][j].getValor() == this.MARCA_FIN) { this.flagFin = true; }

                                    //La posicion siguiente queda null porque fue combinada con la actual
                                    this.tablero[posSig][j] = null;

                                    //Añadir a lista de posiciones libres el lugar de la ficha quitada (sig) (+1 todos -> variables auxiliares)
                                    this.libres.add(_pSig+""+_j); //21
                                    //Eliminar de la lista de posiciones libres el lugar de la ficha combinada (+1 todos -> variables auxiliares)
                                    this.libres.remove(_i+""+_j); //11

                                    //Actualizo posiciones ocupadas en la matriz
                                    this.posLibres[i][j]=1; //ocupada
                                    this.posLibres[posSig][j]=0; //libre
                                }
                            }
                        } else {
                            this.tablero[i][j] = sig; //actual = siguiente
                            this.tablero[posSig][j] = null; //siguiente = null
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

        for (int j=0; j < this.tablero[0].length; j++) {
            for (int k=0; k < this.tablero[0].length; k++){
                for (int i=this.tablero.length-1; i > 0; i--) {
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
                                    this.puntaje = this.puntaje + this.tablero[i][j].getValor();
                                    //Controlar puntaje ganador
                                    if (this.tablero[i][j].getValor() == this.MARCA_FIN) { this.flagFin = true; }

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
        int posSig, _i, _j, _pSig;



        for (int i=0; i < this.tablero.length; i++) { //cantidad de columnas
            for (int k=0; k < this.tablero.length; k++){ //repetir movimientos celda x celda
                for (int j=0; j < this.tablero[i].length-1; j++) { //filas
                    posSig = j+1; _i = i +1; _j = j +1; _pSig = posSig+1;
                    Ficha sig = this.tablero[i][posSig];
                    Ficha act = this.tablero[i][j];
                    if (sig != null){ //si el proximo a la posicion actual tiene ficha
                        if (act != null) { //si la posicion actual tiene ficha
                            //si ambas posiciones tiene ficha, verifico si tiene el mismo valor para sumarlos
                            if (act.getValor() == sig.getValor()){
                                Log.d("FLAG", "Ficha actual: ("+i+j+"), y ficha siguiente: ("+posSig+j+") son iguales. Flags: "+act.getFlag()+" y "+sig.getFlag());
                                //verificar flag antes de sumar
                                if (this.tablero[i][j].getFlag() == 0 && this.tablero[i][posSig].getFlag() == 0){
                                    //Setear el valor de la ficha actual con la suma de actual + siguiente
                                    this.tablero[i][j].setValor(this.tablero[i][j].getValor() + this.tablero[i][posSig].getValor());
                                    this.tablero[i][j].setFlag(1);
                                    this.puntaje = this.puntaje + this.tablero[i][j].getValor();
                                    //Controlar puntaje ganador
                                    if (this.tablero[i][j].getValor() == this.MARCA_FIN) { this.flagFin = true; }

                                    //La posicion siguiente queda null porque fue convinada con la actual
                                    this.tablero[i][posSig] = null;

                                    //Añadir a lista de posiciones libres el lugar de la ficha quitada (sig) (+1 todos)
                                    this.libres.add(_i+""+_pSig);
                                    this.libres.remove(_i+""+_j);

                                    //Actualizo posiciones ocupadas en la matriz
                                    this.posLibres[i][j]=1;
                                    this.posLibres[i][posSig]=0;
                                }
                            }
                        } else {
                            this.tablero[i][j] = sig; //actual = siguiente
                            this.tablero[i][posSig] = null;
                            this.libres.add(_i+""+_pSig);
                            this.libres.remove(_i+""+_j);
                            this.posLibres[i][j]=1;
                            this.posLibres[i][posSig]=0;
                        }
                    }
                }
            }
        }
        this.resetFlags();
        this.setFichaAleatoria();
        this.actualizarTablero();
    }
    public void right(){
        Log.d("MOVIMIENTOS","RIGHT");
        int posSig, _i, _j, _pSig;

        for (int i=0; i < this.tablero.length; i++) { //cantidad de columnas
            for (int k=0; k < this.tablero.length; k++){ //repetir movimientos celda x celda
                for (int j=this.tablero[i].length-1; j > 0; j--) { //filas
                    posSig = j-1; _i = i +1; _j = j +1; _pSig = posSig+1;
                    Ficha sig = this.tablero[i][posSig];
                    Ficha act = this.tablero[i][j];
                    if (sig != null){ //si el proximo a la posicion actual tiene ficha
                        if (act != null) { //si la posicion actual tiene ficha
                            //si ambas posiciones tiene ficha, verifico si tiene el mismo valor para sumarlos
                            if (act.getValor() == sig.getValor()){
                                Log.d("FLAG", "Ficha actual: ("+i+j+"), y ficha siguiente: ("+posSig+j+") son iguales. Flags: "+act.getFlag()+" y "+sig.getFlag());
                                //verificar flag antes de sumar
                                if (this.tablero[i][j].getFlag() == 0 && this.tablero[i][posSig].getFlag() == 0){
                                    //Setear el valor de la ficha actual con la suma de actual + siguiente
                                    this.tablero[i][j].setValor(this.tablero[i][j].getValor() + this.tablero[i][posSig].getValor());
                                    this.tablero[i][j].setFlag(1);
                                    this.puntaje = this.puntaje + this.tablero[i][j].getValor();
                                    //Controlar puntaje ganador
                                    if (this.tablero[i][j].getValor() == this.MARCA_FIN) { this.flagFin = true; }

                                    //La posicion siguiente queda null porque fue convinada con la actual
                                    this.tablero[i][posSig] = null;

                                    //Añadir a lista de posiciones libres el lugar de la ficha quitada (sig) (+1 todos)
                                    this.libres.add(_i+""+_pSig);
                                    this.libres.remove(_i+""+_j);

                                    //Actualizo posiciones ocupadas en la matriz
                                    this.posLibres[i][j]=1;
                                    this.posLibres[i][posSig]=0;
                                }
                            }
                        } else {
                            this.tablero[i][j] = sig; //actual = siguiente
                            this.tablero[i][posSig] = null;
                            this.libres.add(_i+""+_pSig);
                            this.libres.remove(_i+""+_j);
                            this.posLibres[i][j]=1;
                            this.posLibres[i][posSig]=0;
                        }
                    }
                }
            }
        }
        this.resetFlags();
        this.setFichaAleatoria();
        this.actualizarTablero();
    }

}


