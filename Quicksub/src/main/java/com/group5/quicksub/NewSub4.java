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

public class NewSub4 extends Activity {

    // The order
    Order meal;

    // Checkbox objects
    CheckBox btn_meat1;
    CheckBox btn_meat2;
    CheckBox btn_meat3;
    CheckBox btn_meat4;
    CheckBox btn_meat5;
    CheckBox btn_meat6;
    CheckBox btn_meat7;
    CheckBox btn_meat8;


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
        setContentView(R.layout.newsub4);

        // Enable Up Navigation, Set title/subtitle
        getActionBar().setTitle("Quicksub");
        getActionBar().setSubtitle("Meat");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Get Checkbox objects
        btn_meat1 = (CheckBox) findViewById(R.id.checkbox_meat1);
        btn_meat2 = (CheckBox) findViewById(R.id.checkbox_meat2);
        btn_meat3 = (CheckBox) findViewById(R.id.checkbox_meat3);
        btn_meat4 = (CheckBox) findViewById(R.id.checkbox_meat4);
        btn_meat5 = (CheckBox) findViewById(R.id.checkbox_meat5);
        btn_meat6 = (CheckBox) findViewById(R.id.checkbox_meat6);
        btn_meat7 = (CheckBox) findViewById(R.id.checkbox_meat7);
        btn_meat8 = (CheckBox) findViewById(R.id.checkbox_meat8);

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
        // Clear Meat category from order
        meal.clear("Meat");

        // Add selected fields to Order
        if (btn_meat1.isChecked())
            meal.meat.add(new Item((String)btn_meat1.getText(),"Meat"));
        if (btn_meat2.isChecked())
            meal.meat.add(new Item((String)btn_meat2.getText(),"Meat"));
        if (btn_meat3.isChecked())
            meal.meat.add(new Item((String)btn_meat3.getText(),"Meat"));
        if (btn_meat4.isChecked())
            meal.meat.add(new Item((String)btn_meat4.getText(),"Meat"));
        if (btn_meat5.isChecked())
            meal.meat.add(new Item((String)btn_meat5.getText(),"Meat"));
        if (btn_meat6.isChecked())
            meal.meat.add(new Item((String)btn_meat6.getText(),"Meat"));
        if (btn_meat7.isChecked())
            meal.meat.add(new Item((String)btn_meat7.getText(),"Meat"));
        if (btn_meat8.isChecked())
            meal.meat.add(new Item((String)btn_meat8.getText(),"Meat"));

        // Recalculate Calories/Price
        meal.update();



        // Go to next Activity
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent prev = new Intent(this, NewSub3.class);
                prev.putExtra("meal", meal);
                startActivity(prev);
                overridePendingTransition(R.anim.enter, R.anim.leave);
                return true;

            case R.id.next:
                Intent next = new Intent(this, NewSub5.class);
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
        for (int i=0; i<meal.meat.size(); i++) {
            if (meal.meat.get(i).name.equals(btn_meat1.getText()))
                btn_meat1.setChecked(true);
            else if (meal.meat.get(i).name.equals(btn_meat2.getText()))
                btn_meat2.setChecked(true);
            else if (meal.meat.get(i).name.equals(btn_meat3.getText()))
                btn_meat3.setChecked(true);
            else if (meal.meat.get(i).name.equals(btn_meat4.getText()))
                btn_meat4.setChecked(true);
            else if (meal.meat.get(i).name.equals(btn_meat5.getText()))
                btn_meat5.setChecked(true);
            else if (meal.meat.get(i).name.equals(btn_meat6.getText()))
                btn_meat6.setChecked(true);
            else if (meal.meat.get(i).name.equals(btn_meat7.getText()))
                btn_meat7.setChecked(true);
            else if (meal.meat.get(i).name.equals(btn_meat8.getText()))
                btn_meat8.setChecked(true);
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
            case R.id.checkbox_meat1:
                if (checked)
                    meal.meat.add(new Item((String) btn_meat1.getText(), "Meat"));
                else
                    meal.removeItem((String)btn_meat1.getText(), "Meat");
                break;

            case R.id.checkbox_meat2:
                if (checked)
                    meal.meat.add(new Item((String) btn_meat2.getText(), "Meat"));
                else
                    meal.removeItem((String)btn_meat2.getText(), "Meat");
                break;

            case R.id.checkbox_meat3:
                if (checked)
                    meal.meat.add(new Item((String) btn_meat3.getText(), "Meat"));
                else
                    meal.removeItem((String)btn_meat3.getText(), "Meat");
                break;

            case R.id.checkbox_meat4:
                if (checked)
                    meal.meat.add(new Item((String) btn_meat4.getText(), "Meat"));
                else
                    meal.removeItem((String)btn_meat4.getText(), "Meat");
                break;

            case R.id.checkbox_meat5:
                if (checked)
                    meal.meat.add(new Item((String) btn_meat5.getText(), "Meat"));
                else
                    meal.removeItem((String)btn_meat5.getText(), "Meat");
                break;

            case R.id.checkbox_meat6:
                if (checked)
                    meal.meat.add(new Item((String) btn_meat6.getText(), "Meat"));
                else
                    meal.removeItem((String)btn_meat6.getText(), "Meat");
                break;

            case R.id.checkbox_meat7:
                if (checked)
                    meal.meat.add(new Item((String) btn_meat7.getText(), "Meat"));
                else
                    meal.removeItem((String)btn_meat7.getText(), "Meat");
                break;

            case R.id.checkbox_meat8:
                if (checked)
                    meal.meat.add(new Item((String) btn_meat8.getText(), "Meat"));
                else
                    meal.removeItem((String)btn_meat8.getText(), "Meat");
                break;

            default:
                break;
        }
        refreshInfo();
    }
}
