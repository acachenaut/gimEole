package com.dutinfo.gimeole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ReglageJauge extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        //Permet de bloquer la flêche retour de l'appareil
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglage_jauge);


        Button boutonReglageJauges = findViewById(R.id.boutonReglageActiviteReglage);

        boutonReglageJauges.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent modeProdActivity = new Intent(ReglageJauge.this, MainActivity.class);
                startActivity(modeProdActivity);
                finish();

            }
        });


    }
}
