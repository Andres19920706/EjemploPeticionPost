package es.ujaen.ssmm.ejemplopeticionpost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv=(TextView) findViewById(R.id.resultado);
        //
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("http://IP/index.asp");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());

                    String data = "Usuario=Andres";
                    data = data + "&Clave=england";
                    os.writeBytes(data);
                    os.flush();
                    os.close();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    // use a string builder to bufferize the response body
                    // read from the input strea.
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append('\n');
                    }

                    // use the string builder directly,
                    // or convert it into a String
                    String body = sb.toString();

                    tv.setText(body);
                    Log.d("HTTP-POST", body);
                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());


                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }
}
