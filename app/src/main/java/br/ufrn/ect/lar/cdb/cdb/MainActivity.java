package br.ufrn.ect.lar.cdb.cdb;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fBluetooh;
    private FloatingActionButton fPlay;
    private FloatingActionButton fStop;
    private ImageView imgViewSad;
    private TextView tvSad;
    private BluetoothAdapter mBluetoothAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String address = "";
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        fBluetooh = (FloatingActionButton) findViewById(R.id.fBluetooh);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        fBluetooh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(),"Dispositivo não suporta bluetooh",Toast.LENGTH_LONG).show();
                }
                if (!mBluetoothAdapter.isEnabled()) {
                    int REQUEST_ENABLE_BT = 1;
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                if(mBluetoothAdapter.isEnabled()){
                    startActivity(new Intent(getApplicationContext(),ConnectBluetoohActivity.class));
                }
            }
        });

    }
}
