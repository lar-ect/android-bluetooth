package br.ufrn.ect.lar.cdb.cdb;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler(); // Instanciando o handler
        handler.postDelayed(this,4000); // Definindo um time de 4 segundos para iniciar a tela de login.
    }

    @Override
    public void run() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}
