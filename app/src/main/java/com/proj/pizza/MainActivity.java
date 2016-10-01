package com.proj.pizza;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by mbarcelona on 10/1/16.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d("pizza","onCreate finish");
        finish();
    }
}
