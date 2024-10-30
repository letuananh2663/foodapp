package com.foodapp.Activity;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.Helper.ManagmentCart;
import com.foodapp.databinding.ActivityPaymentBinding;

public class PaymentActivity extends BaseActivity {
    private ActivityPaymentBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    private double tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart=new ManagmentCart(this);

        setVariable();
        calculateCart();
    }

    private void calculateCart() {
        double percentTax=0.02;
        double delivery=4;

        tax=Math.round(managmentCart.getTotalFee()*percentTax*100.0)/100;

        double total=Math.round((managmentCart.getTotalFee()+tax+delivery)*100)/100;
        double itemTotal=Math.round(managmentCart.getTotalFee()*100)/100;

        binding.totalFeeTxt.setText("$"+itemTotal);
        binding.taxTxt.setText("$"+tax);
        binding.deliveryTxt.setText("$"+delivery);
        binding.totalTxt.setText("$"+total);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(view -> finish());
    }
}