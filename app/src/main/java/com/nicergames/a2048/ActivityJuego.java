package com.nicergames.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityJuego extends AppCompatActivity {

    private TextView txt11,txt12,txt13,txt14,
            txt21,txt22,txt23,txt24,
            txt31,txt32,txt33,txt34,
            txt41,txt42,txt43,txt44;

    private ImageView btnUp, btnDown, btnLeft, btnRight;

    //private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        txt11 = findViewById(R.id.c11); txt12 = findViewById(R.id.c12);
        txt13 = findViewById(R.id.c13); txt14 = findViewById(R.id.c14);
        txt21 = findViewById(R.id.c21); txt22 = findViewById(R.id.c22);
        txt23 = findViewById(R.id.c23); txt24 = findViewById(R.id.c24);
        txt31 = findViewById(R.id.c31); txt32 = findViewById(R.id.c32);
        txt33 = findViewById(R.id.c33); txt34 = findViewById(R.id.c34);
        txt41 = findViewById(R.id.c41); txt42 = findViewById(R.id.c42);
        txt43 = findViewById(R.id.c43); txt44 = findViewById(R.id.c44);

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
                tablero.up();
            }
        });
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tablero.down();
            }
        });
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tablero.left();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tablero.right();
            }
        });


        // Se implementa el boton atras para volver al MainMenu
        /*
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openMainActivity();
            }
        });
        */
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}