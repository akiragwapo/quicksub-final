package com.group5.quicksub;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.EnumMap;

public class NewSub8 extends Activity {

    // The order
    Order meal;

    boolean returnToSavedSubs = false;


    @Override
    public void onBackPressed() {
        Intent home = new Intent(this, Main.class);
        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(home);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsub8);

        // Disable Up Navigation, Set title/subtitle
        getActionBar().setTitle("Quicksub");
        getActionBar().setSubtitle("Order");
        getActionBar().setDisplayHomeAsUpEnabled(false);

        // Get Calories/Price TextView objects
        TextView calText = (TextView) findViewById(R.id.calTextView);
        TextView priceText = (TextView) findViewById(R.id.priceTextView);

        String order_string;

        //Determining if need to return to return back to savedSubs instead of home screen
        if(getIntent().hasExtra("ReturnToSavedSubsActivity"))
            returnToSavedSubs = true;

        // Get the Order from SavedSubs
        if (getIntent().hasExtra("order")) {

            // Get Order & update Calories/Price
            meal = (Order)getIntent().getSerializableExtra("order");
            meal.update();

            order_string = meal.name + "\n" + meal.toString();

            // if Combined Order
            if (getIntent().hasExtra("order2")) {

                // Get 2nd Order & update Calories/Price
                Order order2 = (Order)getIntent().getSerializableExtra("order2");
                order2.update();

                // Combine Orders
                order_string = order_string + "\n\n" + order2.name + "\n" + order2.toString();

                // Hide Edit/Save buttons
                Button edit = (Button)findViewById(R.id.buttonEditSub);
                Button save = (Button)findViewById(R.id.buttonSaveSub);
                edit.setVisibility(View.INVISIBLE);
                save.setVisibility(View.INVISIBLE);

                // Display Calories/Price total & for each order
                calText.setText("Total Calories: "+(meal.calories+order2.calories)
                                +"\n"+meal.name+": "+meal.calories
                                +"\n"+order2.name+": "+order2.calories);
                priceText.setText("Total Price: $"+String.format("%.2f", meal.price+order2.price)
                                +"\n"+meal.name+": $"+String.format("%.2f", meal.price)
                                +"\n"+order2.name+": $"+String.format("%.2f", order2.price));

                // Generate & show QR Code
                encode(meal.toString()+"\n\n"+order2.toString()+"\n\nTotal Price: $"+String.format("%.2f", meal.price+order2.price));
            }
            // not a Combined Order
            else {
                // Edit size of TextView
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setPadding(25,100,25,25);

                // Display Calories/Price
                calText.setText("Calories: "+meal.calories);
                priceText.setText("Price: $"+String.format("%.2f", meal.price));

                // Generate & show QR Code
                encode(meal.toString()+"\n\nTotal Price: $"+String.format("%.2f", meal.price));
            }
        }
        // Get the Order from NewSubX
        else {
            // Get Order & update Calories/Price
            meal = (Order)getIntent().getSerializableExtra("meal");
            meal.update();

            order_string = meal.toString();

            // Edit size of TextView
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setPadding(25,100,25,25);

            // Display Calories/Price
            calText.setText("Calories: "+meal.calories);
            priceText.setText("Price: $"+String.format("%.2f", meal.price));

            // Generate & show QR Code
            encode(meal.toString()+"\n\nTotal Price: $"+String.format("%.2f", meal.price));
        }

        // Display Order
        TextView textview = (TextView) findViewById(R.id.textView);
        textview.setKeyListener(null);
        textview.append(order_string);

        // Add listener to Save & Edit Buttons
        addListenerOnButtons();
    }


    // Listener for Save/Edit Buttons
    public void addListenerOnButtons() {
        // Get Button objects
        Button save = (Button)findViewById(R.id.buttonSaveSub);
        Button edit = (Button)findViewById(R.id.buttonEditSub);

        // Save
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //If this order is an order that was already saved, no need to prompt user to give it a new name
                //Just save the order and return to home screen
                if (meal.editedOrder == true) {

                    //Save the changes to the file
                    saveEditedOrder();

                    Intent intent;

                    //If this screen was reached from the SavedSubs screen, return to the SavedSubs screen
                    if(returnToSavedSubs)
                        intent = new Intent(NewSub8.this, SavedSubs.class);
                    else
                        intent = new Intent(NewSub8.this, Main.class);

                    //Clear out all the previous activities in memory and return to home screen
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    //create intent and pass the order to the next activity
                    Intent intent = new Intent(NewSub8.this, SaveSub.class);
                    intent.putExtra("meal", meal.toString());
                    startActivity(intent);
                }
            }
        });

        // Edit
        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewSub8.this, NewSub1.class);
                intent.putExtra("meal",meal);
                startActivity(intent);
            }
        });
    }


    // Function for saving the changes of a saved order that was edited
    public void saveEditedOrder(){
        BufferedWriter writer;
        try {
            //Write the order to the file
            writer = new BufferedWriter(new OutputStreamWriter(openFileOutput(meal.filename, MODE_PRIVATE)));
            writer.write(meal.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Display QRCode
    private void encode(String uniqueID) {
        BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;

        int width0 = 250;
        int height0 = 250;

        QRCodeWriter writer = new QRCodeWriter();
        try {
            EnumMap<EncodeHintType, Object> hint = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hint.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");
            BitMatrix bitMatrix = writer.encode(uniqueID, barcodeFormat, width0, height0, hint);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++)
                    pixels[offset + x] = bitMatrix.get(x, y) ? Color.rgb(0,0,0) : Color.rgb(255,255,255);
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            ImageView imageview = (ImageView)findViewById(R.id.imageView);
            imageview.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}