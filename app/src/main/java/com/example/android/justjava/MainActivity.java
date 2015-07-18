package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    boolean checked1;
    boolean checked2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void increment(View view) {

        quantity++;

        display(quantity);
    }

    public void decrement(View view) {

        if (quantity > 0)
            quantity--;

        display(quantity);
    }

    //onClick Order button
    public void submitOrder(View view) {


        CheckBox cb1 = (CheckBox) findViewById(R.id.cbCream);
        CheckBox cb2 = (CheckBox) findViewById(R.id.cbChocolate);

        EditText name = (EditText) findViewById(R.id.nameET);

        String nameText = name.getText().toString();

        checked1 = cb1.isChecked();
        checked2 = cb2.isChecked();

        if (quantity > 0) {

            String orderSummaryMsg = orderSummary(nameText, calculatePrice(checked1, checked2), checked1, checked2);
            sendEmail(orderSummaryMsg, nameText);

        } else {
            Toast.makeText(getApplicationContext(), "Please choose a number before you order", Toast.LENGTH_SHORT).show();
        }
    }

    private int calculatePrice(boolean wCream, boolean Chocolate) {
        int baseprice = 5;

        if (wCream)
            baseprice += 1;
        if (Chocolate)
            baseprice += 2;

        return (quantity * baseprice);
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private String orderSummary(String name, int price, boolean chk1, boolean chk2) {


        String msg = "Name:" + name + "\n" + "And whipped cream? " + chk1 + "\n" + "And chocolate ?" + chk2 + "\n" + "Quantity: " + quantity + "\n" + "Total:" + price + "\n" + "Thank you!";
        return msg;
    }

    private void sendEmail(String content, String nameForEmail){

        Intent intent = new Intent(Intent.ACTION_SENDTO);

        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java for " + nameForEmail );
        intent.putExtra(Intent.EXTRA_TEXT,content);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }


    }




}
