package com.nicergames.a2048;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityJuego extends AppCompatActivity {

    private TextView txt11,txt12,txt13,txt14,
            txt21,txt22,txt23,txt24,
            txt31,txt32,txt33,txt34,
            txt41,txt42,txt43,txt44, txtPuntaje, txtRecord;


    private Tablero tablero;
    private Integer record;

    private static final int DELAY_MIN = 3;
    private static final int DELAY_MAX = 60;

    private float x1, y1, x2, y2, dX, dY;
    static final int MIN_MOVE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        //getSupportActionBar().hide();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt11 = findViewById(R.id.c11); txt12 = findViewById(R.id.c12);
        txt13 = findViewById(R.id.c13); txt14 = findViewById(R.id.c14);
        txt21 = findViewById(R.id.c21); txt22 = findViewById(R.id.c22);
        txt23 = findViewById(R.id.c23); txt24 = findViewById(R.id.c24);
        txt31 = findViewById(R.id.c31); txt32 = findViewById(R.id.c32);
        txt33 = findViewById(R.id.c33); txt34 = findViewById(R.id.c34);
        txt41 = findViewById(R.id.c41); txt42 = findViewById(R.id.c42);
        txt43 = findViewById(R.id.c43); txt44 = findViewById(R.id.c44);

        txtPuntaje = findViewById(R.id.txt_puntaje);
        txtRecord = findViewById(R.id.txt_record);

        record = leerPreferencias();
        //Toast.makeText(this, "record: "+record, Toast.LENGTH_SHORT).show();
        if (record == 0){
            guardarPreferencias(0);
        }
        txtRecord.setText("Record: "+record);



        tablero = new Tablero(this);
        tablero.setComponentes(txt11, txt12, txt13, txt14,
                txt21, txt22, txt23, txt24,
                txt31, txt32, txt33, txt34,
                txt41, txt42, txt43, txt44);


        //crear 2 fichas en pos aleatorias (estan todas libres)
        Ficha f1 = new Ficha(2);
        Ficha f2 = new Ficha(2);
        tablero.setFichaAleatoria(f1);
        tablero.setFichaAleatoria(f2);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_juego, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                this.onBackPressed();
                return true;
            case R.id.reset:
                tablero.reiniciar();
                txtPuntaje.setText("Puntaje: "+tablero.getPuntaje());
                //Toast.makeText(this, "RESET", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.share:
                Toast.makeText(this, "SHARE", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int leerPreferencias() {
        SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
        int puntos = preferencias.getInt("record",0);
        return puntos;
    }

    private void guardarPreferencias(int puntos) {
        SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("record", puntos);
        editor.commit();
    }

    private void actualizarRecord(int puntaje){
        if (puntaje > record) {
            record = puntaje;
            txtRecord.setText("Record: "+record);
            guardarPreferencias(record);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();*/
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x2 = event.getX();
                y2 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x1 = event.getX();
                y1 = event.getY();
                dX = x1-x2;
                dY = y2-y1;
                if (Math.abs(dX) > Math.abs(dY)){ //la dif de X es mayor a la de Y = mov horizontal
                    if (Math.abs(dX) > MIN_MOVE) {
                        if (dX < 0) {
                            this.moverIzq();
                            Toast.makeText(this, "Left", Toast.LENGTH_SHORT).show();
                        } else {
                            this.moverDer();
                            Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (Math.abs(dY) > MIN_MOVE) {
                        if (dY < 0) {
                            this.moverAbajo();
                            Toast.makeText(this, "Down", Toast.LENGTH_SHORT).show();
                        } else {
                            this.moverArriba();
                            Toast.makeText(this, "Up", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }


    private void moverArriba(){
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int k=0; k < 4; k++){
                    for (int j = 0; j < 4; j++) {
                        tablero.up(j);
                        tablero.actualizarTablero();
                        txtPuntaje.setText("Puntaje: "+tablero.getPuntaje());

                        actualizarRecord(tablero.getPuntaje());

                        try { Thread.sleep(DELAY_MIN); } catch (InterruptedException e) { e.printStackTrace(); }
                    }
                }
                try { Thread.sleep(DELAY_MAX); } catch (InterruptedException e) { e.printStackTrace(); }
                tablero.afterMov();
            }
        });
        hilo.start();
    }

    private void moverAbajo(){
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int k=0; k < 4; k++){
                    for (int j = 0; j < 4; j++) {
                        tablero.down(j);
                        tablero.actualizarTablero();
                        txtPuntaje.setText("Puntaje: "+tablero.getPuntaje());
                        actualizarRecord(tablero.getPuntaje());
                        try { Thread.sleep(DELAY_MIN); } catch (InterruptedException e) { e.printStackTrace(); }
                    }
                }
                try { Thread.sleep(DELAY_MAX); } catch (InterruptedException e) { e.printStackTrace(); }
                tablero.afterMov();
            }
        });
        hilo.start();
    }

    private void moverIzq(){
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int k=0; k < 4; k++){
                    for (int i = 0; i < 4; i++) {
                        tablero.left(i);
                        tablero.actualizarTablero();
                        txtPuntaje.setText("Puntaje: "+tablero.getPuntaje());
                        actualizarRecord(tablero.getPuntaje());
                        try { Thread.sleep(DELAY_MIN); } catch (InterruptedException e) { e.printStackTrace(); }
                    }
                }
                try { Thread.sleep(DELAY_MAX); } catch (InterruptedException e) { e.printStackTrace(); }
                tablero.afterMov();
            }
        });
        hilo.start();
    }

    private void moverDer(){
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int k=0; k < 4; k++){
                    for (int i = 0; i < 4; i++) {
                        tablero.right(i);
                        tablero.actualizarTablero();
                        txtPuntaje.setText("Puntaje: "+tablero.getPuntaje());
                        actualizarRecord(tablero.getPuntaje());
                        try { Thread.sleep(DELAY_MIN); } catch (InterruptedException e) { e.printStackTrace(); }
                    }
                }
                try { Thread.sleep(DELAY_MAX); } catch (InterruptedException e) { e.printStackTrace(); }
                tablero.afterMov();
            }
        });
        hilo.start();
    }
}