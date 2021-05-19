package com.nicergames.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityJuego extends AppCompatActivity {

    private TextView txt11,txt12,txt13,txt14,
            txt21,txt22,txt23,txt24,
            txt31,txt32,txt33,txt34,
            txt41,txt42,txt43,txt44, txtPuntaje;

    private ImageView btnUp, btnDown, btnLeft, btnRight;

    //private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        getSupportActionBar().hide();

        txt11 = findViewById(R.id.c11); txt12 = findViewById(R.id.c12);
        txt13 = findViewById(R.id.c13); txt14 = findViewById(R.id.c14);
        txt21 = findViewById(R.id.c21); txt22 = findViewById(R.id.c22);
        txt23 = findViewById(R.id.c23); txt24 = findViewById(R.id.c24);
        txt31 = findViewById(R.id.c31); txt32 = findViewById(R.id.c32);
        txt33 = findViewById(R.id.c33); txt34 = findViewById(R.id.c34);
        txt41 = findViewById(R.id.c41); txt42 = findViewById(R.id.c42);
        txt43 = findViewById(R.id.c43); txt44 = findViewById(R.id.c44);

        txtPuntaje = findViewById(R.id.txt_puntaje);

        Tablero tablero = new Tablero(this);
        tablero.setComponentes(txt11, txt12, txt13, txt14,
                txt21, txt22, txt23, txt24,
                txt31, txt32, txt33, txt34,
                txt41, txt42, txt43, txt44);


        //crear 2 fichas en pos aleatorias (estan todas libres)
        Ficha f1 = new Ficha(2);
        Ficha f2 = new Ficha(2);
        tablero.setFichaAleatoria(f1);
        tablero.setFichaAleatoria(f2);


        btnUp = findViewById(R.id.arrowUp);
        btnDown = findViewById(R.id.arrowDown);
        btnLeft = findViewById(R.id.arrowLeft);
        btnRight = findViewById(R.id.arrowRight);

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread hilo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int k=0; k < 4; k++){
                            for (int j = 0; j < 4; j++) {
                                tablero.up(j);
                                tablero.actualizarTablero();
                                txtPuntaje.setText("Puntaje: "+tablero.getPuntaje());
                                try { Thread.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                            }
                        }
                        try { Thread.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                        tablero.afterMov();
                    }
                });
                hilo.start();
            }
        });
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread hilo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int k=0; k < 4; k++){
                            for (int j = 0; j < 4; j++) {
                                tablero.down(j);
                                tablero.actualizarTablero();
                                txtPuntaje.setText("Puntaje: "+tablero.getPuntaje());
                                try { Thread.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                            }
                        }
                        try { Thread.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                        tablero.afterMov();
                    }
                });
                hilo.start();
            }
        });
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread hilo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int k=0; k < 4; k++){
                            for (int i = 0; i < 4; i++) {
                                tablero.left(i);
                                tablero.actualizarTablero();
                                txtPuntaje.setText("Puntaje: "+tablero.getPuntaje());
                                try { Thread.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                            }
                        }
                        try { Thread.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                        tablero.afterMov();
                    }
                });
                hilo.start();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread hilo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int k=0; k < 4; k++){
                            for (int i = 0; i < 4; i++) {
                                tablero.right(i);
                                tablero.actualizarTablero();
                                txtPuntaje.setText("Puntaje: "+tablero.getPuntaje());
                                try { Thread.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                            }
                        }
                        try { Thread.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                        tablero.afterMov();
                    }
                });
                hilo.start();

            }
        });


        // Boton atras para volver al MainMenu
        /*
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openMainActivity();
            }
        });
        */
    }

    /*
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
     */
}