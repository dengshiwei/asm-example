package com.sensorsdata.asm_example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testLog();
        Log.d("TAG","dsw");
        Log.i("TAG","dsw");
        Log.v("TAG","dsw");
        Log.w("TAG","dsw");
        Log.e("TAG","dsw");
    }

    public void testLog() {
        Log.d("TAG","dsw");
        Log.i("TAG","dsw");
        Log.v("TAG","dsw");
        Log.w("TAG","dsw");
        Log.e("TAG","dsw");
    }
}