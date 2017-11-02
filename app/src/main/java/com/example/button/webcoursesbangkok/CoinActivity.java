package com.example.button.webcoursesbangkok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by body on 05/10/2017.
 */

public class CoinActivity extends AppCompatActivity {


    private TextView mSymbol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);


        String symbol = getIntent().getStringExtra("Symbol");
        mSymbol = (TextView) findViewById(R.id.coinActivityTitle);
        mSymbol.setText(symbol);
        final Button mMoon = (Button) findViewById(R.id.moonbutton);
        setUpEmojiButton(mMoon);


        final Button mProfit = (Button) findViewById(R.id.profitbutton);
        setUpEmojiButton(mProfit);

        final Button mHodl = (Button) findViewById(R.id.hodlbutton);
        setUpEmojiButton(mHodl);

        final Button mPray = (Button) findViewById(R.id.praybutton);
        setUpEmojiButton(mPray);


    }

    private void setUpEmojiButton (final Button button){
        
        button.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {

                                         EditText mEditText = (EditText) (findViewById(R.id.edittextone));
                                         String emojiString = mEditText.getText().toString();
                                         String theMoonText = button.getText().toString();
                                         String moreVariables = emojiString + theMoonText;
                                         mEditText.setText(moreVariables);


                                     }

                                 }

        );

    }

}
