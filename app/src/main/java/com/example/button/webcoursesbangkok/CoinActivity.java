package com.example.button.webcoursesbangkok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by body on 05/10/2017.
 */

public class CoinActivity extends AppCompatActivity{


    private TextView mSymbol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);




        String symbol = getIntent().getStringExtra("Symbol");
        mSymbol = (TextView) findViewById(R.id.coinActivityTitle);
        mSymbol.setText(symbol);


    }



}
