package com.sensorsdata.asm_example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testLog();
        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testString("","");
            }
        });
    }

    public void testLog() {
        Log.d("TAG","dsw");
        Log.i("TAG","dsw");
        Log.v("TAG","dsw");
        Log.w("TAG","dsw");
        Log.e("TAG","dsw");
    }

    public String testString(String a, String b) {
        int result = 5 / 0;
        return "HelloWorld";
    }

    public Object getObject() {
        return null;
    }

    public char getObjectChar() {
        return 0;
    }

    public short getObjectShort() {
        return 0;
    }

    public byte getObjectByte() {
        return 0;
    }
}