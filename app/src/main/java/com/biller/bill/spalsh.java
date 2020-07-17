package com.biller.bill;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class spalsh extends AppCompatActivity {
    private static int time = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_spalsh );
    }

    @Override
    protected void onStart() {
        new Handler(  ).postDelayed( new Runnable() {
            @Override
            public void run() {

            }
        } ,time);
        super.onStart();
    }
}
