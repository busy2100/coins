package com.example.button.webcoursesbangkok;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MyClickListener {
    private static final String LOG_TAG = "Main Activity Log Tag";
    private List<Currency> listOfCurrencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getCurrenciesFromInternet();



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
}


