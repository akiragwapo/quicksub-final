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

public class NewSub6 extends Activity {

    // The order
    Order meal;

    // Checkbox objects
    CheckBox btn_condiments1;
    CheckBox btn_condiments2;
    CheckBox btn_condiments3;
    CheckBox btn_condiments4;
    CheckBox btn_condiments5;
    CheckBox btn_condiments6;
    CheckBox btn_condiments7;
    CheckBox btn_condiments8;
    CheckBox btn_condiments9;
    CheckBox btn_condiments10;
    CheckBox btn_condiments11;
    CheckBox btn_condiments12;

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
        setContentView(R.layout.newsub6);

        // Enable Up Navigation, Set title/subtitle
        getActionBar().setTitle("Quicksub");
        getActionBar().setSubtitle("Condiments");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Get Checkbox objects
        btn_condiments1 = (CheckBox) findViewById(R.id.checkbox_condiments1);
        btn_condiments2 = (CheckBox) findViewById(R.id.checkbox_condiments2);
        btn_condiments3 = (CheckBox) findViewById(R.id.checkbox_condiments3);
        btn_condiments4 = (CheckBox) findViewById(R.id.checkbox_condiments4);
        btn_condiments5 = (CheckBox) findViewById(R.id.checkbox_condiments5);
        btn_condiments6 = (CheckBox) findViewById(R.id.checkbox_condiments6);
        btn_condiments7 = (CheckBox) findViewById(R.id.checkbox_condiments7);
        btn_condiments8 = (CheckBox) findViewById(R.id.checkbox_condiments8);
        btn_condiments9 = (CheckBox) findViewById(R.id.checkbox_condiments9);
        btn_condiments10 = (CheckBox) findViewById(R.id.checkbox_condiments10);
        btn_condiments11 = (CheckBox) findViewById(R.id.checkbox_condiments11);
        btn_condiments12 = (CheckBox) findViewById(R.id.checkbox_condiments12);

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
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    // Listen for QS Up icon press, Next, or Option selected from action bar menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Clear Condiments category from order
        meal.clear("Condiments");

        // Add selected fields to Order
        if (btn_condiments1.isChecked())
            meal.condiments.add(new Item((String) btn_condiments1.getText(), "Condiments"));
        if (btn_condiments2.isChecked())
            meal.condiments.add(new Item((String) btn_condiments2.getText(), "Condiments"));
        if (btn_condiments3.isChecked())
            meal.condiments.add(new Item((String) btn_condiments3.getText(), "Condiments"));
        if (btn_condiments4.isChecked())
            meal.condiments.add(new Item((String) btn_condiments4.getText(), "Condiments"));
        if (btn_condiments5.isChecked())
            meal.condiments.add(new Item((String) btn_condiments5.getText(), "Condiments"));
        if (btn_condiments6.isChecked())
            meal.condiments.add(new Item((String) btn_condiments6.getText(), "Condiments"));
        if (btn_condiments7.isChecked())
            meal.condiments.add(new Item((String) btn_condiments7.getText(), "Condiments"));
        if (btn_condiments8.isChecked())
            meal.condiments.add(new Item((String) btn_condiments8.getText(), "Condiments"));
        if (btn_condiments9.isChecked())
            meal.condiments.add(new Item((String) btn_condiments9.getText(), "Condiments"));
        if (btn_condiments10.isChecked())
            meal.condiments.add(new Item((String) btn_condiments10.getText(), "Condiments"));
        if (btn_condiments11.isChecked())
            meal.condiments.add(new Item((String) btn_condiments11.getText(), "Condiments"));
        if (btn_condiments12.isChecked())
            meal.condiments.add(new Item((String) btn_condiments12.getText(), "Condiments"));

        // Recalculate Calories/Price
        meal.update();

        // Go to next Activity
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent prev = new Intent(this, NewSub5.class);
                prev.putExtra("meal", meal);
                startActivity(prev);
                overridePendingTransition(R.anim.enter, R.anim.leave);
                return true;

            case R.id.next:
                Intent next = new Intent(this, NewSub7.class);
                next.putExtra("meal", meal);
                startActivity(next);
                overridePendingTransition(R.anim.enter_right, R.anim.leave_left);
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
                return true;

            case R.id.option_veggies:
                Intent veggies = new Intent(this, NewSub5.class);
                veggies.putExtra("meal", meal);
                startActivity(veggies);
                overridePendingTransition(R.anim.enter, R.anim.leave);
                return true;

            case R.id.option_extras:
                Intent extras = new Intent(this, NewSub7.class);
                extras.putExtra("meal", meal);
                startActivity(extras);
                overridePendingTransition(R.anim.enter_right, R.anim.leave_left);
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
        for (int i=0; i<meal.condiments.size(); i++) {
            if (meal.condiments.get(i).name.equals(btn_condiments1.getText()))
                btn_condiments1.setChecked(true);
            else if (meal.condiments.get(i).name.equals(btn_condiments2.getText()))
                btn_condiments2.setChecked(true);
            else if (meal.condiments.get(i).name.equals(btn_condiments3.getText()))
                btn_condiments3.setChecked(true);
            else if (meal.condiments.get(i).name.equals(btn_condiments4.getText()))
                btn_condiments4.setChecked(true);
            else if (meal.condiments.get(i).name.equals(btn_condiments5.getText()))
                btn_condiments5.setChecked(true);
            else if (meal.condiments.get(i).name.equals(btn_condiments6.getText()))
                btn_condiments6.setChecked(true);
            else if (meal.condiments.get(i).name.equals(btn_condiments7.getText()))
                btn_condiments7.setChecked(true);
            else if (meal.condiments.get(i).name.equals(btn_condiments8.getText()))
                btn_condiments8.setChecked(true);
            else if (meal.condiments.get(i).name.equals(btn_condiments9.getText()))
                btn_condiments9.setChecked(true);
            else if (meal.condiments.get(i).name.equals(btn_condiments10.getText()))
                btn_condiments10.setChecked(true);
            else if (meal.condiments.get(i).name.equals(btn_condiments11.getText()))
                btn_condiments11.setChecked(true);
            else if (meal.condiments.get(i).name.equals(btn_condiments12.getText()))
                btn_condiments12.setChecked(true);
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
            case R.id.checkbox_condiments1:
                if (checked)
                    meal.condiments.add(new Item((String) btn_condiments1.getText(), "Condiments"));
                else
                    meal.removeItem((String)btn_condiments1.getText(), "Condiments");
                break;

            case R.id.checkbox_condiments2:
                if (checked)
                    meal.condiments.add(new Item((String) btn_condiments2.getText(), "Condiments"));
                else
                    meal.removeItem((String)btn_condiments2.getText(), "Condiments");
                break;

            case R.id.checkbox_condiments3:
                if (checked)
                    meal.condiments.add(new Item((String) btn_condiments3.getText(), "Condiments"));
                else
                    meal.removeItem((String)btn_condiments3.getText(), "Condiments");
                break;

            case R.id.checkbox_condiments4:
                if (checked)
                    meal.condiments.add(new Item((String) btn_condiments4.getText(), "Condiments"));
                else
                    meal.removeItem((String)btn_condiments4.getText(), "Condiments");
                break;

            case R.id.checkbox_condiments5:
                if (checked)
                    meal.condiments.add(new Item((String) btn_condiments5.getText(), "Condiments"));
                else
                    meal.removeItem((String)btn_condiments5.getText(), "Condiments");
                break;

            case R.id.checkbox_condiments6:
                if (checked)
                    meal.condiments.add(new Item((String) btn_condiments6.getText(), "Condiments"));
                else
                    meal.removeItem((String)btn_condiments6.getText(), "Condiments");
                break;

            case R.id.checkbox_condiments7:
                if (checked)
                    meal.condiments.add(new Item((String) btn_condiments7.getText(), "Condiments"));
                else
                    meal.removeItem((String)btn_condiments7.getText(), "Condiments");
                break;

            case R.id.checkbox_condiments8:
                if (checked)
                    meal.condiments.add(new Item((String) btn_condiments8.getText(), "Condiments"));
                else
                    meal.removeItem((String)btn_condiments8.getText(), "Condiments");
                break;

            case R.id.checkbox_condiments9:
                if (checked)
                    meal.condiments.add(new Item((String) btn_condiments9.getText(), "Condiments"));
                else
                    meal.removeItem((String)btn_condiments9.getText(), "Condiments");
                break;

            case R.id.checkbox_condiments10:
                if (checked)
                    meal.condiments.add(new Item((String) btn_condiments10.getText(), "Condiments"));
                else
                    meal.removeItem((String)btn_condiments10.getText(), "Condiments");
                break;

            case R.id.checkbox_condiments11:
                if (checked)
                    meal.condiments.add(new Item((String) btn_condiments11.getText(), "Condiments"));
                else
                    meal.removeItem((String)btn_condiments11.getText(), "Condiments");
                break;

            case R.id.checkbox_condiments12:
                if (checked)
                    meal.condiments.add(new Item((String) btn_condiments12.getText(), "Condiments"));
                else
                    meal.removeItem((String)btn_condiments12.getText(), "Condiments");
                break;

            default:
                break;
        }
        refreshInfo();
    }
}

