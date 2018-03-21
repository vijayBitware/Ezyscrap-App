package com.ezyscrap.View.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ezyscrap.R;

/**
 * Created by bitware on 23/12/17.
 */

public class FragmentProccedToPayment extends Fragment implements View.OnClickListener{

    View view;
    TextView txt_totalAmount,txt_debitCard,txt_creditCard;
    LinearLayout ll_debitCard,ll_creditCard;
    EditText edt_debitCardNumber,edt_debitCardExpiry,edt_debitCardCVV,edt_debitCardAccountHolderName,edt_creditCardNumber,edt_creditCardExpiry;
    EditText edt_crediCardCVV;
    Button btnProceedToPayment;
    boolean isDebitCardSelected=false,isCreditCardSelected=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_payment,container,false);
        init();

        return view;
    }

    private void init() {
        txt_totalAmount = view.findViewById(R.id.txt_totalAmount);
        txt_debitCard = view.findViewById(R.id.txt_debitCard);
        ll_debitCard = view.findViewById(R.id.ll_debitCard);
        edt_debitCardNumber = view.findViewById(R.id.edt_debitCardNumber);
        edt_debitCardExpiry = view.findViewById(R.id.edt_debitCardExpiry);
        edt_debitCardCVV = view.findViewById(R.id.edt_debitCardCVV);
        edt_debitCardAccountHolderName = view.findViewById(R.id.edt_debitCardAccountHolderName);
        txt_creditCard = view.findViewById(R.id.txt_creditCard);
        ll_creditCard =view.findViewById(R.id.ll_creditCard);
        edt_creditCardNumber = view.findViewById(R.id.edt_creditCardNumber);
        edt_creditCardExpiry = view.findViewById(R.id.edt_creditCardExpiry);
        edt_crediCardCVV = view.findViewById(R.id.edt_crediCardCVV);
        btnProceedToPayment = view.findViewById(R.id.btnProceedToPayment);

        txt_creditCard.setOnClickListener(this);
        txt_debitCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_debitCard:
                if (!isDebitCardSelected){
                    ll_debitCard.setVisibility(View.VISIBLE);
                    isDebitCardSelected=true;
                }else {
                    ll_debitCard.setVisibility(View.GONE);
                    isDebitCardSelected=false;
                }
                break;
            case R.id.txt_creditCard:
                if (!isCreditCardSelected){
                    ll_creditCard.setVisibility(View.VISIBLE);
                    isCreditCardSelected=true;
                }else {
                    ll_creditCard.setVisibility(View.GONE);
                    isCreditCardSelected=false;
                }
                break;
        }
    }
}
