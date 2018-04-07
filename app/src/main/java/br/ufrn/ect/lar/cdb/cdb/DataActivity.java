package br.ufrn.ect.lar.cdb.cdb;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DataActivity extends AppCompatActivity {
    private BluetoothAdapter mBluetoothAdapter;
    private FloatingActionButton fBluetooh;
    private FloatingActionButton fPlay;
    private FloatingActionButton fStop;
    private TextView tVStatus;
    static TextView tVDados;
    static String dadosSensor="";
    ConnectionThread connect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        String address = "";
        fBluetooh = (FloatingActionButton) findViewById(R.id.fBluetooD);
        fPlay = (FloatingActionButton) findViewById(R.id.fPlay);
        fStop = (FloatingActionButton) findViewById(R.id.fStop);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        tVStatus = (TextView) findViewById(R.id.tVStatus);
        tVDados = (TextView) findViewById(R.id.tvDados);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            address = extras.getString("address");
        }else{
            Toast.makeText(getApplicationContext(),"Problema na conexão Bluetooh",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        fPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start = "start\n";
                connect.write(start.getBytes());
            }
        });
        fStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stop = "stop\n";
                connect.write(stop.getBytes());
            }
        });
        connect = new ConnectionThread(address);
        connect.start();
        try {
            Thread.sleep(1000);
        } catch (Exception E) {
            E.printStackTrace();
        }
        if(connect.isConnected()){
            tVStatus.setText("\nConectado");
        }else{
            tVStatus.setText("\nDesconectado");
        }
        fBluetooh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connect.isConnected()){
                    connect.cancel();
                }
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            /* Esse método é invocado na Activity principal
                sempre que a thread de conexão Bluetooth recebe
                uma mensagem.
             */
            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString= new String(data);
            dadosSensor+=dataString+"\n\n";
            /* Aqui ocorre a decisão de ação, baseada na string
                recebida. Caso a string corresponda à uma das
                mensagens de status de conexão (iniciadas com --),
                atualizamos o status da conexão conforme o código.
             */
            if(dataString.equals("---N")) {
                //statusMessage.setText("Ocorreu um erro durante a conexão D:");
                Log.i("msg", "Ocorreu um erro");
            } else if(dataString.equals("---S")) {
                //statusMessage.setText("Conectado :D");
                Log.i("msg", "Conectado");
            } else {

                /* Se a mensagem não for um código de status,
                    então ela deve ser tratada pelo aplicativo
                    como uma mensagem vinda diretamente do outro
                    lado da conexão. Nesse caso, simplesmente
                    atualizamos o valor contido no TextView do
                    contador.
                 */
                tVDados.setText(dadosSensor);
                Log.i("msg", dataString);
            }

        }
    };

    /* Esse método é invocado sempre que o usuário clicar na TextView
        que contem o contador. O app Android transmite a string "restart",
        seguido de uma quebra de linha, que é o indicador de fim de mensagem.
    */
    public void restartCounter(View view) {
        connect.write("restart\n".getBytes());
    }
}
