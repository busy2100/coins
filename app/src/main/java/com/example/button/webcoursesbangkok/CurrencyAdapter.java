package com.example.button.webcoursesbangkok;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by button on 9/21/2017 AD.
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyHolder> {

    private Context mContext;
    private List<Currency> mListOfCurrencies;
    private MyClickListener mClickListener;

    public CurrencyAdapter(Context context, List<Currency> listOfCurrencies, MyClickListener clickListener) {
        mClickListener = clickListener;
        mContext = context;
        mListOfCurrencies = listOfCurrencies;
    }



    @Override
    public CurrencyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_layout, parent, false);
        CurrencyHolder holder = new CurrencyHolder(mContext, view, mClickListener);
        return holder;
    }


    @Override
    public void onBindViewHolder(CurrencyHolder holder, int position) {
        Currency newCurrency = mListOfCurrencies.get(position);
        holder.bindCurrency(newCurrency);
    }

    @Override
    public int getItemCount() {
        return mListOfCurrencies.size();
    }
}
