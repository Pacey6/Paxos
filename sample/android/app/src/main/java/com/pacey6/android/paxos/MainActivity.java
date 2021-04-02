package com.pacey6.android.paxos;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Paxos";

    private Thread currentThread;
    private AndroidPaxos androidPaxos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.currentThread = new Thread(() -> {
            try (AndroidPaxos androidPaxos = new AndroidPaxos(this, InetAddress.getByName("224.1.2.3"), 1027)) {
                this.androidPaxos = androidPaxos;
                Log.d(TAG, "connected");
                while (!Thread.interrupted()) {
                    try {
                        androidPaxos.receive();
                    } catch (IOException ignored) {}
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Log.d(TAG, "disconnected");
            }
        });
        this.currentThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Thread(() -> {
            Thread currentThread = this.currentThread;
            if (null != currentThread) {
                currentThread.interrupt();
            }
            AndroidPaxos androidPaxos = this.androidPaxos;
            if (null != androidPaxos) {
                try {
                    androidPaxos.close();
                } catch (IOException ignored) {}
            }
        }).start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            new Thread(() -> {
                AndroidPaxos androidPaxos = this.androidPaxos;
                if (null != androidPaxos) {
                    androidPaxos.propose("cb79d100-3bc4-41f4-be7b-eba21702fda5", "Helr");
                }
            }).start();
        }
        return super.onTouchEvent(event);
    }
}