package com.group5.quicksub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class NewSub7 extends Activity {

    // The order
    Order meal;

    // Checkbox objects
    CheckBox btn_extras1;
    CheckBox btn_extras2;
    CheckBox btn_extras3;


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
        setContentView(R.layout.newsub7);

        // Enable Up Navigation, Set title/subtitle
        getActionBar().setTitle("Quicksub");
        getActionBar().setSubtitle("Extras");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Get Checkbox objects
        btn_extras1 = (CheckBox) findViewById(R.id.checkbox_extras1);
        btn_extras2 = (CheckBox) findViewById(R.id.checkbox_extras2);
        btn_extras3 = (CheckBox) findViewById(R.id.checkbox_extras3);

        // Get the current order
        meal = (Order)getIntent().getSerializableExtra("meal");

        // Restore Checkbox states
        restorePreferences();

        // Update Calories/Price text
        refreshInfo();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_newsub7, menu);
        return true;
    }


    // Listen for QS Up icon press, Next, or Option selected from action bar menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Clear Extras category from order
        meal.clear("Extras");

        // Get the Order object from Intent
        meal = (Order) getIntent().getSerializableExtra("meal");

        // Add selected fields to Order
        if (btn_extras1.isChecked())
            meal.extras.add(new Item((String) btn_extras1.getText(), "Extras"));
        if (btn_extras2.isChecked())
            meal.extras.add(new Item((String) btn_extras2.getText(), "Extras"));
        if (btn_extras3.isChecked())
            meal.extras.add(new Item((String) btn_extras3.getText(), "Extras"));

        // Recalculate Calories/Price
        meal.update();


        // Go to next Activity
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent prev = new Intent(this, NewSub6.class);
                prev.putExtra("meal", meal);
                startActivity(prev);
                overridePendingTransition(R.anim.enter, R.anim.leave);
                return true;

            case R.id.next:
                Intent next = new Intent(this, NewSub8.class);
                next.putExtra("meal", meal);
                startActivity(next);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;

            case R.id.option_size:
                Intent size = new Intent(this, NewSub1.class);
                size.putExtra("meal", meal);
                startActivity(size);
                overridePendingTransition(R.anim.enter, R.anim.leave);
                return true;

            case R.id.option_bread:
                Intent bread = new Intent(this, NewSub2.class);
                bread.putExtra("meal", meal);
                startActivity(bread);
                overridePendingTransition(R.anim.enter, R.anim.leave);
                return true;

            case R.id.option_cheese:
                Intent cheese = new Intent(this, NewSub3.class);
                cheese.putExtra("meal", meal);
                startActivity(cheese);
                overridePendingTransition(R.anim.enter, R.anim.leave);
                return true;

            case R.id.option_meat:
                Intent meat = new Intent(this, NewSub4.class);
                meat.putExtra("meal", meal);
                startActivity(meat);
                overridePendingTransition(R.anim.enter, R.anim.leave);
                return true;

            case R.id.option_veggies:
                Intent veggies = new Intent(this, NewSub5.class);
                veggies.putExtra("meal", meal);
                startActivity(veggies);
                overridePendingTransition(R.anim.enter, R.anim.leave);
                return true;

            case R.id.option_condiments:
                Intent condiments = new Intent(this, NewSub6.class);
                condiments.putExtra("meal", meal);
                startActivity(condiments);
                overridePendingTransition(R.anim.enter, R.anim.leave);
                return true;

            case R.id.option_complete:
                if (meal.bread.name == null)
                    Toast.makeText(this, "Please select a type of Bread", Toast.LENGTH_LONG).show();
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


    // Restore checkbox states
    public void restorePreferences() {
        for (int i=0; i<meal.extras.size(); i++) {
            if (meal.extras.get(i).name.equals(btn_extras1.getText()))
                btn_extras1.setChecked(true);
            else if (meal.extras.get(i).name.equals(btn_extras2.getText()))
                btn_extras2.setChecked(true);
            else if (meal.extras.get(i).name.equals(btn_extras3.getText()))
                btn_extras3.setChecked(true);
        }
    }


    // Display real-time calories and price
    public void refreshInfo(){
        meal.update();

        TextView calText = (TextView) findViewById(R.id.calTextView);
        TextView priceText = (TextView) findViewById(R.id.priceTextView);
        calText.setText("Calories: "+meal.calories);
        priceText.setText("Price: $"+String.format("%.2f", meal.price));
    }


    // Listener for any change to checkboxes
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox)view).isChecked();

        // Edit Order according to selected/deselected checkbox and update price/calories
        switch(view.getId()) {
            case R.id.checkbox_extras1:
                if (checked)
                    meal.extras.add(new Item((String) btn_extras1.getText(), "Extras"));
                else
                    meal.removeItem((String)btn_extras1.getText(), "Extras");
                break;

            case R.id.checkbox_extras2:
                if (checked)
                    meal.extras.add(new Item((String) btn_extras2.getText(), "Extras"));
                else
                    meal.removeItem((String)btn_extras2.getText(), "Extras");
                break;

            case R.id.checkbox_extras3:
                if (checked)
                    meal.extras.add(new Item((String) btn_extras3.getText(), "Extras"));
                else
                    meal.removeItem((String)btn_extras3.getText(), "Extras");
                break;

            default:
                break;
        }
        refreshInfo();
    }
}

