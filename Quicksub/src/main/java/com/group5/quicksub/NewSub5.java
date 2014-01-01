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

public class NewSub5 extends Activity {

    // The order
    Order meal;

    // Checkbox objects
    CheckBox btn_veg1;
    CheckBox btn_veg2;
    CheckBox btn_veg3;
    CheckBox btn_veg4;
    CheckBox btn_veg5;
    CheckBox btn_veg6;
    CheckBox btn_veg7;
    CheckBox btn_veg8;
    CheckBox btn_veg9;
    CheckBox btn_veg10;
    CheckBox btn_veg11;

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
        setContentView(R.layout.newsub5);

        // Enable Up Navigation, Set title/subtitle
        getActionBar().setTitle("Quicksub");
        getActionBar().setSubtitle("Veggies");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Get Checkbox objects
        btn_veg1 = (CheckBox) findViewById(R.id.checkbox_veg1);
        btn_veg2 = (CheckBox) findViewById(R.id.checkbox_veg2);
        btn_veg3 = (CheckBox) findViewById(R.id.checkbox_veg3);
        btn_veg4 = (CheckBox) findViewById(R.id.checkbox_veg4);
        btn_veg5 = (CheckBox) findViewById(R.id.checkbox_veg5);
        btn_veg6 = (CheckBox) findViewById(R.id.checkbox_veg6);
        btn_veg7 = (CheckBox) findViewById(R.id.checkbox_veg7);
        btn_veg8 = (CheckBox) findViewById(R.id.checkbox_veg8);
        btn_veg9 = (CheckBox) findViewById(R.id.checkbox_veg9);
        btn_veg10 = (CheckBox) findViewById(R.id.checkbox_veg10);
        btn_veg11 = (CheckBox) findViewById(R.id.checkbox_veg11);

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
        // Clear Veggies category from order
        meal.clear("Veggies");

        // Add selected fields to Order
        if (btn_veg1.isChecked())
            meal.veggies.add(new Item((String) btn_veg1.getText(), "Veggies"));
        if (btn_veg2.isChecked())
            meal.veggies.add(new Item((String) btn_veg2.getText(), "Veggies"));
        if (btn_veg3.isChecked())
            meal.veggies.add(new Item((String) btn_veg3.getText(), "Veggies"));
        if (btn_veg4.isChecked())
            meal.veggies.add(new Item((String) btn_veg4.getText(), "Veggies"));
        if (btn_veg5.isChecked())
            meal.veggies.add(new Item((String) btn_veg5.getText(), "Veggies"));
        if (btn_veg6.isChecked())
            meal.veggies.add(new Item((String) btn_veg6.getText(), "Veggies"));
        if (btn_veg7.isChecked())
            meal.veggies.add(new Item((String) btn_veg7.getText(), "Veggies"));
        if (btn_veg8.isChecked())
            meal.veggies.add(new Item((String) btn_veg8.getText(), "Veggies"));
        if (btn_veg9.isChecked())
            meal.veggies.add(new Item((String) btn_veg9.getText(), "Veggies"));
        if (btn_veg10.isChecked())
            meal.veggies.add(new Item((String) btn_veg10.getText(), "Veggies"));
        if (btn_veg11.isChecked())
            meal.veggies.add(new Item((String) btn_veg11.getText(), "Veggies"));

        // Recalculate Calories/Price
        meal.update();


        // Go to next Activity
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent prev = new Intent(this, NewSub4.class);
                prev.putExtra("meal", meal);
                startActivity(prev);
                overridePendingTransition(R.anim.enter, R.anim.leave);
                return true;

            case R.id.next:
                Intent next = new Intent(this, NewSub6.class);
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
                overridePendingTransition(R.anim.enter, R.anim.leave);
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
        for (int i=0; i<meal.veggies.size(); i++) {
            if (meal.veggies.get(i).name.equals(btn_veg1.getText()))
                btn_veg1.setChecked(true);
            else if (meal.veggies.get(i).name.equals(btn_veg2.getText()))
                btn_veg2.setChecked(true);
            else if (meal.veggies.get(i).name.equals(btn_veg3.getText()))
                btn_veg3.setChecked(true);
            else if (meal.veggies.get(i).name.equals(btn_veg4.getText()))
                btn_veg4.setChecked(true);
            else if (meal.veggies.get(i).name.equals(btn_veg5.getText()))
                btn_veg5.setChecked(true);
            else if (meal.veggies.get(i).name.equals(btn_veg6.getText()))
                btn_veg6.setChecked(true);
            else if (meal.veggies.get(i).name.equals(btn_veg7.getText()))
                btn_veg7.setChecked(true);
            else if (meal.veggies.get(i).name.equals(btn_veg8.getText()))
                btn_veg8.setChecked(true);
            else if (meal.veggies.get(i).name.equals(btn_veg9.getText()))
                btn_veg9.setChecked(true);
            else if (meal.veggies.get(i).name.equals(btn_veg10.getText()))
                btn_veg10.setChecked(true);
            else if (meal.veggies.get(i).name.equals(btn_veg11.getText()))
                btn_veg11.setChecked(true);
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
            case R.id.checkbox_veg1:
                if (checked)
                    meal.veggies.add(new Item((String) btn_veg1.getText(), "Veggies"));
                else
                    meal.removeItem((String)btn_veg1.getText(), "Veggies");
                break;

            case R.id.checkbox_veg2:
                if (checked)
                    meal.veggies.add(new Item((String) btn_veg2.getText(), "Veggies"));
                else
                    meal.removeItem((String)btn_veg2.getText(), "Veggies");
                break;

            case R.id.checkbox_veg3:
                if (checked)
                    meal.veggies.add(new Item((String) btn_veg3.getText(), "Veggies"));
                else
                    meal.removeItem((String)btn_veg3.getText(), "Veggies");
                break;

            case R.id.checkbox_veg4:
                if (checked)
                    meal.veggies.add(new Item((String) btn_veg4.getText(), "Veggies"));
                else
                    meal.removeItem((String)btn_veg4.getText(), "Veggies");
                break;

            case R.id.checkbox_veg5:
                if (checked)
                    meal.veggies.add(new Item((String) btn_veg5.getText(), "Veggies"));
                else
                    meal.removeItem((String)btn_veg5.getText(), "Veggies");
                break;

            case R.id.checkbox_veg6:
                if (checked)
                    meal.veggies.add(new Item((String) btn_veg6.getText(), "Veggies"));
                else
                    meal.removeItem((String)btn_veg6.getText(), "Veggies");
                break;

            case R.id.checkbox_veg7:
                if (checked)
                    meal.veggies.add(new Item((String) btn_veg7.getText(), "Veggies"));
                else
                    meal.removeItem((String)btn_veg7.getText(), "Veggies");
                break;

            case R.id.checkbox_veg8:
                if (checked)
                    meal.veggies.add(new Item((String) btn_veg8.getText(), "Veggies"));
                else
                    meal.removeItem((String)btn_veg8.getText(), "Veggies");
                break;

            case R.id.checkbox_veg9:
                if (checked)
                    meal.veggies.add(new Item((String) btn_veg9.getText(), "Veggies"));
                else
                    meal.removeItem((String)btn_veg9.getText(), "Veggies");
                break;

            case R.id.checkbox_veg10:
                if (checked)
                    meal.veggies.add(new Item((String) btn_veg10.getText(), "Veggies"));
                else
                    meal.removeItem((String)btn_veg10.getText(), "Veggies");
                break;

            case R.id.checkbox_veg11:
                if (checked)
                    meal.veggies.add(new Item((String) btn_veg11.getText(), "Veggies"));
                else
                    meal.removeItem((String)btn_veg11.getText(), "Veggies");
                break;

            default:
                break;
        }
        refreshInfo();
    }
}