package com.example.server;

import android.annotation.SuppressLint;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {
    ServerSocket serverSocket;
    Thread Thread1 = null;
    TextView tvIP, tvPort;
    TextView tvMessages, etMessage;
    Button btnSend;
    public static String SERVER_IP = "";
    public static final int SERVER_PORT = 8080;
    String message;
    int count = 0;
    Socket s1;
    Socket s2;
    Socket s3;
    Socket s4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvIP = findViewById(R.id.tvIP);
        tvPort = findViewById(R.id.tvPort);
        tvMessages = findViewById(R.id.tvMessages);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        try {
            SERVER_IP = getLocalIpAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Thread1 = new Thread(new Thread1());
        Thread1.start();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = Integer.toString(count);
                etMessage.setText(""+message);
                count++;
                if (!message.isEmpty()&& count <1000) {
                    new Thread(new Thread3(message)).start();
                } else if (!message.isEmpty()&& count >=1000){
                    new Thread(new Thread3("NULL")).start();
                }
            }
        });
    }


    private String getLocalIpAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        assert wifiManager != null;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipInt = wifiInfo.getIpAddress();
        return InetAddress.getByAddress(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array()).getHostAddress();
    }

    private PrintWriter output1;
    private PrintWriter output2;
    private PrintWriter output3;
    private PrintWriter output4;
    private BufferedReader input;
    class Thread1 extends Thread {
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(SERVER_PORT);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMessages.setText("Not connected");
                        tvIP.setText("IP: " + SERVER_IP);
                        tvPort.setText("Port: " + String.valueOf(SERVER_PORT));
                    }
                });
                try {
                    if(s1 == null){
                        s1 = serverSocket.accept();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMessages.setText("S1 Connected \n");
                            }
                        });
                    }
                    if(s2 == null){
                        s2 = serverSocket.accept();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMessages.append("S2 Connected \n");
                            }
                        });
                    }
                    if(s3 == null){
                        s3 = serverSocket.accept();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMessages.append("S3 Connected \n");
                            }
                        });
                    }
                    if(s4 == null){
                        s4 = serverSocket.accept();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMessages.append("S4 Connected \n");
                            }
                        });
                    }
                    output1 = new PrintWriter(s1.getOutputStream());
                    output2 = new PrintWriter(s2.getOutputStream());
                    output3 = new PrintWriter(s3.getOutputStream());
                    output4 = new PrintWriter(s4.getOutputStream());
                    input = new BufferedReader(new InputStreamReader(s1.getInputStream()));
                    new Thread(new Thread2()).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class Thread2 implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    final String message = input.readLine();
                    if (message != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMessages.setText("SERVER HACKED");
                            }
                        });
                        count = 1000;
                    } else {
                        Thread1 = new Thread(new Thread1());
                        Thread1.start();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class Thread3 implements Runnable {
        private String message;
        Thread3(String message) {
            this.message = message;
        }
        @Override
        public void run() {
            output1.write(message+"\n");
            output1.flush();
            output2.write(message+"\n");
            output2.flush();
            output3.write(message+"\n");
            output3.flush();
            output4.write(message+"\n");
            output4.flush();
        }
    }
}