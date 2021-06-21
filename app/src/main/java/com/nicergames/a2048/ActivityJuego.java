package com.nicergames.a2048;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
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
        txtRecord.setText(getString(R.string.puntajeMax)+record);

        //String str1 = existePartida();
        //Log.d("ESTADO", str1+"");



        tablero = new Tablero(this);
        tablero.setComponentes(txt11, txt12, txt13, txt14,
                txt21, txt22, txt23, txt24,
                txt31, txt32, txt33, txt34,
                txt41, txt42, txt43, txt44);

        String estado = existePartida();
        if(!estado.equals("")){
            //TODO: Cargar estado
            JSONArray jsonFichas = null;
            int i, j, puntos;
            try {
                JSONObject jsonEstado = new JSONObject(estado);
                puntos = jsonEstado.getInt("puntaje");
                txtPuntaje.setText(getString(R.string.puntaje)+puntos);
                tablero.setPuntaje(puntos);
                jsonFichas = jsonEstado.getJSONArray("fichas");

                for (int k = 0; k < jsonFichas.length(); k++){
                    JSONObject jFicha = jsonFichas.getJSONObject(k);
                    Ficha f = new Ficha(jFicha.getInt("valor"), jFicha.getInt("flag"));
                    i = jFicha.getInt("i");
                    j = jFicha.getInt("j");
                    tablero.ponerFicha(f, i, j);
                }
                tablero.actualizarTablero();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //crear 2 fichas en pos aleatorias (estan todas libres)
            Ficha f1 = new Ficha(2);
            Ficha f2 = new Ficha(2);
            tablero.setFichaAleatoria(f1);
            tablero.setFichaAleatoria(f2);
        }
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
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
                txtPuntaje.setText(getString(R.string.puntaje)+tablero.getPuntaje());
                return true;
            case R.id.share:
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getString(R.string.record)+record);
                if(i.resolveActivity(getPackageManager()) != null){
                    startActivity(i);
                }
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
            txtRecord.setText(getString(R.string.puntajeMax)+record);
            guardarPreferencias(record);
        }
    }

    private String existePartida() {
        Log.d("ESTADO", "Cargando estado");
        SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
        String existe = preferencias.getString("estado","");

        return existe;
    }



    @Override
    protected void onDestroy() {

        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.putString("estado", tablero.crearJSON());
        ed.commit();

        Log.d("ESTADO", "Estado guardado");
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
                        } else {
                            this.moverDer();
                        }
                        checkFinJuego();
                    }
                } else {
                    if (Math.abs(dY) > MIN_MOVE) {
                        if (dY < 0) {
                            this.moverAbajo();
                        } else {
                            this.moverArriba();
                        }
                        checkFinJuego();
                    }
                }
                break;
        }

        //Log.d("JSON", tablero.crearJSON());
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
                        txtPuntaje.setText(getString(R.string.puntaje)+tablero.getPuntaje());

                        actualizarRecord(tablero.getPuntaje());

                        try { Thread.sleep(DELAY_MIN); } catch (InterruptedException e) { e.printStackTrace(); }
                    }
                }
                try { Thread.sleep(DELAY_MAX); } catch (InterruptedException e) { e.printStackTrace(); }
                tablero.afterMov();

                //checkFinJuego();
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
                        txtPuntaje.setText(getString(R.string.puntaje)+tablero.getPuntaje());
                        actualizarRecord(tablero.getPuntaje());
                        try { Thread.sleep(DELAY_MIN); } catch (InterruptedException e) { e.printStackTrace(); }
                    }
                }
                try { Thread.sleep(DELAY_MAX); } catch (InterruptedException e) { e.printStackTrace(); }
                tablero.afterMov();
                //checkFinJuego();
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
                        txtPuntaje.setText(getString(R.string.puntaje)+tablero.getPuntaje());
                        actualizarRecord(tablero.getPuntaje());
                        try { Thread.sleep(DELAY_MIN); } catch (InterruptedException e) { e.printStackTrace(); }
                    }
                }
                try { Thread.sleep(DELAY_MAX); } catch (InterruptedException e) { e.printStackTrace(); }
                tablero.afterMov();
                //checkFinJuego();
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
                        txtPuntaje.setText(getString(R.string.puntaje)+tablero.getPuntaje());
                        actualizarRecord(tablero.getPuntaje());
                        try { Thread.sleep(DELAY_MIN); } catch (InterruptedException e) { e.printStackTrace(); }
                    }
                }
                try { Thread.sleep(DELAY_MAX); } catch (InterruptedException e) { e.printStackTrace(); }
                tablero.afterMov();
                //checkFinJuego();
            }
        });
        hilo.start();
    }

    public void checkFinJuego(){
        if (tablero.win()){
            mostrarDlg(1);
        }

        if (tablero.gameOver()){
            mostrarDlg(0);
        }
    }

    public void mostrarDlg(int msg){
        //Looper.prepare();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String strMsg = (String) getText(R.string.puntosObtenidos);
        strMsg = strMsg+" "+tablero.getPuntaje();
        builder.setMessage(strMsg);
        if (msg == 1){
            builder.setTitle(R.string.win);
        } else {
            builder.setTitle(R.string.lose);
        }

        builder.setPositiveButton(R.string.share, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getString(R.string.puntosObtenidos)+ tablero.getPuntaje());
                if(i.resolveActivity(getPackageManager()) != null){
                    startActivity(i);
                }
            }
        })
        .setNegativeButton(R.string.reset, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tablero.reiniciar();
                txtPuntaje.setText(getString(R.string.puntaje)+tablero.getPuntaje());
            }
        });
        builder.show();
    }
}