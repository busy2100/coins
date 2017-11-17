package com.example.button.webcoursesbangkok;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by body on 05/10/2017.
 */

public class CoinActivity extends AppCompatActivity {


    private static final int SIGN_IN_REQUEST_CODE = 100;
    private static final String TAG = "Coin Activity";
    private String mTicker;
    private TextView mSymbol;
    private FirebaseAuth mAuth;
    private FirebaseListAdapter<ChatMessage> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
        mAuth = FirebaseAuth.getInstance();


        mTicker = getIntent().getStringExtra("Symbol");
        mSymbol = (TextView) findViewById(R.id.coinActivityTitle);
        mSymbol.setText(mTicker);
        final Button mMoon = (Button) findViewById(R.id.moonbutton);
        setUpEmojiButton(mMoon);


        final Button mProfit = (Button) findViewById(R.id.profitbutton);
        setUpEmojiButton(mProfit);

        final Button mHodl = (Button) findViewById(R.id.hodlbutton);
        setUpEmojiButton(mHodl);

        final Button mPray = (Button) findViewById(R.id.praybutton);
        setUpEmojiButton(mPray);

        final Button mSubmit = (Button) findViewById(R.id.submit_button);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();

            }
        });

    }

    private void sendMessage() {


        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity

        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast


            EditText input = (EditText) findViewById(R.id.edittextone);

            // Read the input field and push a new instance
            // of ChatMessage to the Firebase database
            FirebaseDatabase.getInstance()
                    .getReference()
                    .push()
                    .setValue(new ChatMessage(input.getText().toString(),
                            FirebaseAuth.getInstance()
                                    .getCurrentUser()
                                    .getUid(), mTicker)
                    );

            // Clear the input
            input.setText("");
        }


    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);



        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(CoinActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });





    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null){

            displayChatMessages();
        }
    }


    private void setUpEmojiButton(final Button button) {

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

    private void displayChatMessages(){

        ListView listOfMessages = (ListView)findViewById(R.id.listview);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.chat_message, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                ImageView avatar = v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);


                // Set their text
                messageText.setText(model.getMessageText());
                Picasso.with(CoinActivity.this).load("https://robohash.org/" + model.getMessageUser() + " .png").into(avatar);

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }

}
