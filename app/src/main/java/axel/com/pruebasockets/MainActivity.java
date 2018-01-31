package axel.com.pruebasockets;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.axel.mensajes.Casa;
import com.axel.mensajes.Coche;
import com.axel.mensajes.Paquete;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import axel.com.pruebasockets.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btnEnviar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_enviar:
                int pisos = Integer.parseInt(binding.etPisos.getText().toString());
                String direccion = binding.etDireccion.getText().toString();
                InetAddress ipDestino;
                try {
                    ipDestino = InetAddress.getByName(binding.etIpDestino.getText().toString());
                } catch (UnknownHostException | NetworkOnMainThreadException e) {
                    Log.w("Warning", "Dirección IP no válida, la ejecución continúa");
                    e.printStackTrace();
                    binding.etIpDestino.setError("Dirección IP no válida");
                    break;
                }
                new EnviarPaquete(new Paquete(new Casa(pisos, direccion)), ipDestino, this).execute();
                break;
            default:
                break;
        }
    }


    /******************************* Clase empleada para la conexión con internet ********************************/

    public class EnviarPaquete extends AsyncTask<Void, Void, Void> {

        Serializable paquete;
        InetAddress ipDestino;
        View.OnClickListener listener;

        public EnviarPaquete(Serializable paquete, InetAddress ipDestino, View.OnClickListener listener){
            this.paquete = paquete;
            this.ipDestino = ipDestino;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<NetworkInterface> interfaces = null;
            try {
                interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            } catch (SocketException e) {
                e.printStackTrace();
            }
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        Log.w("IP", sAddr);
                    }
                }
            }
            Socket socket = null;
            try {
                socket = new Socket(ipDestino, 1234);
            } catch (IOException e) {
                Log.w("Warning", "No se pudo crear el Socket, la ejecución continúa");
                e.printStackTrace();
                publishProgress();
                return null;
            }

            ObjectOutputStream outputStream = null;
            try {
                outputStream = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                Log.w("Warning", "No se pudo crear el ObjectOutputStream, la ejecución continúa");
                e.printStackTrace();
                publishProgress();
                return null;
            }

            try {
                outputStream.writeObject(paquete);
            } catch (IOException e) {
                Log.w("Warning", "No se pudo escribir el paquete, la ejecución continúa");
                e.printStackTrace();
                publishProgress();
                return null;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("No se pudo enviar el paquete").setMessage("Compruebe su conexión a internet, que la dirección IP sea correcta y que el dispositivo de destino esté disponible")
                    .setPositiveButton("Aceptar", null);
            builder.create().show();
        }
    }

}
