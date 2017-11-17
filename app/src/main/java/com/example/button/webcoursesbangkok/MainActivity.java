package com.example.button.webcoursesbangkok;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MyClickListener {
    private static final String LOG_TAG = "Main Activity Log Tag";
    private static final String TAG = "MainActivity";
    private List<Currency> listOfCurrencies;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getCurrenciesFromInternet();
        mAuth = FirebaseAuth.getInstance();




    }




//    private void getLocalCurrencies() {
//        listOfCurrencies = new ArrayList<>();
//        Currency btc = new Currency();
//        listOfCurrencies.add(btc);
//        btc.setSymbol("BTC");
//        btc.setName("BitCoin");
//        btc.setLogo(getResources().getDrawable(R.drawable.bitcoin_logo));
//
//        Currency xrp = new Currency();
//        listOfCurrencies.add(xrp);
//        xrp.setSymbol("XRP");
//        xrp.setName("Ripple");
//        xrp.setLogo(getResources().getDrawable((R.drawable.ripple_logo)));
//
//        setUpRecyclerView();
//    }


    private void getCurrenciesFromInternet() {

        final MainActivity self = this;

        OkHttpClient client = new OkHttpClient();
        // GET request
        Request request = new Request.Builder()
                .url("https://api.coinmarketcap.com/v1/ticker/") // //http://cryptomining-blog.com/wp-json/wp/v2/posts?_embed
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(LOG_TAG, call.toString());

                Log.d(LOG_TAG, "failed");
                Log.d(LOG_TAG, e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String string = response.body().string();

                // Get a handler that can be used to post to the main thread
                Handler mainHandler = new Handler(self.getMainLooper());

                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.d(LOG_TAG, string);
                        Log.d(LOG_TAG, "Successful");
                        parsePosts(string);
                    }
                };
                mainHandler.post(myRunnable);
            }

        });
    }

    void parsePosts(String json) {

        Gson gson = new GsonBuilder().create();
        //Convert Json to List of Currency Objects
        Currency[] list = gson.fromJson(json, Currency[].class);
        listOfCurrencies = Arrays.asList(list);
        setUpRecyclerView();
    }


    private void setUpRecyclerView() {
        RecyclerView newRecycleView = (RecyclerView) findViewById(R.id.recyclerView);
        newRecycleView.setLayoutManager(new LinearLayoutManager(this));
        CurrencyAdapter newAdapter = new CurrencyAdapter(this, listOfCurrencies, this);
        newRecycleView.setAdapter(newAdapter);

    }


    @Override
    public void onItemSelected(Currency selectedCurrency) {
        Intent intent = new Intent(this, CoinActivity.class);
        intent.putExtra("Symbol", selectedCurrency.getSymbol());

        startActivity(intent);

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
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });





    }

    private void updateUI(FirebaseUser currentUser) {

        Log.d(TAG, "Logged in as: " + currentUser.getUid());
        Toast.makeText(this, " Logged in As" + currentUser.getUid(), Toast.LENGTH_SHORT).show();



// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap <String, String> textStorageMap = new HashMap<>();


                for (DataSnapshot postSnapShot: dataSnapshot.getChildren()) {
                    ChatMessage message = postSnapShot.getValue(ChatMessage.class);

                    String channel = message.getChannel();
                    if (channel != null) {
                        if (!textStorageMap.containsKey(channel)) {
                            textStorageMap.put(channel, "");
                        }

                        String oldText = textStorageMap.get(channel);
                        String newText = oldText + message.getMessageText();
                        textStorageMap.put(channel, newText);


                    }
                }

                for (String channel : textStorageMap.keySet()) {

                    String allText = textStorageMap.get(channel);

                    allText.contains("\uD83D\uDE80");

                    

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}


