package com.example.android.justjava;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity=1;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean isHasChocolate() {
        return hasChocolate;
    }

    public boolean isHasWhippedCream() {
        return hasWhippedCream;
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void increment(View view) {
        if(quantity == 10) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Only 10 cups of coffe");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(MainActivity.this, "Click on Order button", Toast.LENGTH_SHORT).show();
                }
            });
            alerta = builder.create();
            alerta.show();

            return;
        }
            quantity +=1;
                displayQuantity(quantity);

    }

    public void decrement(View view) {
        if(quantity == 1) {
            return;
        }else{
            quantity -=1;
            displayQuantity(quantity);
        }
    }

    public void submitOrder(View view) {
        EditText fieldName = (EditText) findViewById(R.id.field_name);
        String name = fieldName.getText().toString();
        Log.v("MainAct.", "Name:" + name);

        CheckBox whippedCreamCHeckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);

        boolean hasWhippedCream = whippedCreamCHeckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();


        Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);
        Log.v("MainActivity", "Has whipped cream: " + hasChocolate);

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        displayMessage(priceMessage);

        Log.v("Mai,", "Price: " + price);
    }

    public void sendEmail (View view){
    //public void sendEmail (String[]addresses, String subject){
        EditText fieldName = (EditText) findViewById(R.id.field_name);
        Editable nameEditable = fieldName.getText();
        String name = nameEditable.toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        //Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        //emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this

// The intent does not have a URI, so declare the "text/plain" MIME type
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,"tcheleza@gmail.com"); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Udacity teste");
        emailIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);
// You can also attach multiple items by passing an ArrayList of Uris
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            //startActivity(Intent.createChooser(emailIntent, "Send Email"));
            startActivity(emailIntent);
        }

    }


    /**
     * Calculates the price of the order.
     *
     * @param addChocolate
     * @param addWhippedCream
     * @return total price
     *
     */

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice =5;

        if(addWhippedCream){
            basePrice = basePrice+1;
        }if(addChocolate) {
            basePrice = basePrice+2;
        }
        return quantity * basePrice;
    }

    /**
     * Creat summary of the order.
     *
     * @param name of customer
     * @param price of order
     * @param addWhippedCream
     * @param addChocolate
     * @return text summary
     */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name){
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: "+quantity;
        priceMessage += "\nTotal: $"+price +".00";
        priceMessage += "\nThanks";
        priceMessage += "\nWould you like to send the bill for an Email account?";
        priceMessage += "\nClick on Send Email button";
        return priceMessage;
    }

   // private void Toaster (int toastText) {
    //    LayoutInflater myInflater = getLayoutInflater();
    //    View layout = myInflater.inflate(R.layout.custom_toast,(ViewGroup)findViewById(R.id.toast_message));

    //    TextView teste = (TextView)layout.findViewById(R.id.toast_text);
    //    teste.setText("Less than 10");

    //    Toast toast = new Toast(getApplicationContext());
    //    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
    //    toast.setDuration(Toast.LENGTH_LONG);
    //    toast.setView(layout);
    //   toast.show();
    //}

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(double number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }


}
