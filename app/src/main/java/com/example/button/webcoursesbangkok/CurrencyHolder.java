package com.example.button.webcoursesbangkok;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by button on 9/21/2017 AD.
 */

class CurrencyHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private View mView;
    private TextView mSymbolTxt;
    private TextView mNameTxt;
    private ImageView mLogo;
    private TextView mPrice;
    private TextView m24Change;
    private MyClickListener mClickListener;

    public CurrencyHolder(Context context, View view, MyClickListener clickListener) {
        super(view);
        mContext = context;
        mView = view;
        mSymbolTxt = view.findViewById(R.id.symbol);
        mNameTxt = view.findViewById(R.id.name);
        mLogo = view.findViewById(R.id.logo);
        m24Change = view.findViewById(R.id.change);
        mClickListener = clickListener;
        mPrice = view.findViewById(R.id.price);
    }


    public void bindCurrency(final Currency currency) {

        String change = currency.getChange24h();
        m24Change.setText(change);

        try {
            double changeDouble = Double.parseDouble(change);

            if (changeDouble < 0) {
                m24Change.setTextColor(mContext.getResources().getColor(R.color.colorNegative));


            } else {

                m24Change.setTextColor(mContext.getResources().getColor(R.color.colorPositive));
            }
        } catch (Exception e) {

            m24Change.setText("N/A");
            m24Change.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }
        String name = currency.getName();
        mNameTxt.setText(name);

        String symbol = currency.getSymbol();
        mSymbolTxt.setText(symbol);

        Drawable logo = currency.getLogo();
        mLogo.setImageDrawable(logo);

        String price = currency.getPrice();
        mPrice.setText(price);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemSelected(currency);
            }
        });


    }

}
