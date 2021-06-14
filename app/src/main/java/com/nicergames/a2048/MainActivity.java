package com.nicergames.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openJuego();
            }
        });
    }

    public void openJuego() {
        Intent i = new Intent(this, ActivityJuego.class);
        //startActivity(intent);
        startActivityForResult(i, 5);
    }


}