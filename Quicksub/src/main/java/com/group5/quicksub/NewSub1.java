package com.group5.quicksub;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class NewSub1 extends Activity{

    // The order
    Order meal;

    // RadioGroup/RadioButton objects
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;


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
        setContentView(R.layout.newsub1);

        // Disable Up Navigation, Set title/subtitle
        getActionBar().setTitle("Quicksub");
        getActionBar().setSubtitle("Size");
        getActionBar().setDisplayHomeAsUpEnabled(false);

        // Get RadioGroup/RadioButton objects
        radioGroup = (RadioGroup) findViewById(R.id.radioSize);

        // Get the current order
        meal = (Order)getIntent().getSerializableExtra("meal");

        // Restore RadioButton state
        restorePreferences();

        // Update Calories/Price text
        refreshInfo();

        // Add Listener to RadioGroup for real-time updates
        addListenerOnRadioGroup();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }


    // Listen for Next or Option selected from action bar menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get Toasted checkbox
        CheckBox toasted = (CheckBox) findViewById(R.id.checkbox_toasted);

        // Set toasted
        meal.toasted = toasted.isChecked();

        // Recalculate Calories/Price
        meal.update();



        // Go to next Activity
        switch (item.getItemId()) {
            case R.id.next:
                Intent next = new Intent(this, NewSub2.class);
                next.putExtra("meal", meal);
                startActivity(next);
                overridePendingTransition(R.anim.enter_right, R.anim.leave_left);
                return true;

            case R.id.option_bread:
                Intent bread = new Intent(this, NewSub2.class);
                bread.putExtra("meal", meal);
                startActivity(bread);
                overridePendingTransition(R.anim.enter_right, R.anim.leave_left);
                return true;

            case R.id.option_cheese:
                Intent cheese = new Intent(this, NewSub3.class);
                cheese.putExtra("meal", meal);
                startActivity(cheese);
                overridePendingTransition(R.anim.enter_right, R.anim.leave_left);
                return true;

            case R.id.option_meat:
                Intent meat = new Intent(this, NewSub4.class);
                meat.putExtra("meal", meal);
                startActivity(meat);
                overridePendingTransition(R.anim.enter_right, R.anim.leave_left);
                return true;

            case R.id.option_veggies:
                Intent veggies = new Intent(this, NewSub5.class);
                veggies.putExtra("meal", meal);
                startActivity(veggies);
                overridePendingTransition(R.anim.enter_right, R.anim.leave_left);
                return true;

            case R.id.option_condiments:
                Intent condiments = new Intent(this, NewSub6.class);
                condiments.putExtra("meal", meal);
                startActivity(condiments);
                overridePendingTransition(R.anim.enter_right, R.anim.leave_left);
                return true;

            case R.id.option_extras:
                Intent extras = new Intent(this, NewSub7.class);
                extras.putExtra("meal", meal);
                startActivity(extras);
                overridePendingTransition(R.anim.enter_right, R.anim.leave_left);
                return true;

            case R.id.option_complete:
                if (meal.bread.name == null){

                    //If user did not choose a bread, tell user

                    //show alert dialog
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewSub1.this);

                    alertDialogBuilder.setTitle("No Bread selected");
                    alertDialogBuilder.setMessage("Please select a type of Bread first.");
                    alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            //Close the Dialogue
                            dialog.cancel();

                        }
                    });

                    alertDialogBuilder.setCancelable(false);
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();



                }
                else {
                    Intent complete = new Intent(this, NewSub8.class);
                    complete.putExtra("meal", meal);
                    startActivity(complete);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Listen on RadioGroup so Price / Calories can be updated
    public void addListenerOnRadioGroup() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                meal.size = (String)selectedRadioButton.getText();
                refreshInfo();
            }
        });
    }


    // Restore RadioButton / Checkbox states
    public void restorePreferences() {
        RadioButton size1 = (RadioButton) findViewById(R.id.radioHalf);
        RadioButton size2 = (RadioButton) findViewById(R.id.radioWhole);
        CheckBox toasted = (CheckBox) findViewById(R.id.checkbox_toasted);

        toasted.setChecked(meal.toasted);

        if (meal.size.equals(size1.getText()))
            size1.setChecked(true);
        else if (meal.size.equals(size2.getText()))
            size2.setChecked(true);
    }


    // Display real-time calories and price
    public void refreshInfo(){
        meal.update();

        TextView calText = (TextView) findViewById(R.id.calTextView);
        TextView priceText = (TextView) findViewById(R.id.priceTextView);
        calText.setText("Calories: "+meal.calories);
        priceText.setText("Price: $"+String.format("%.2f", meal.price));
    }
}