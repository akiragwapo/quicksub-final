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

public class NewSub3 extends Activity {

    // The order
    Order meal;

    // Checkbox objects
    CheckBox btn_cheese1;
    CheckBox btn_cheese2;
    CheckBox btn_cheese3;
    CheckBox btn_cheese4;
    CheckBox btn_cheese5;
    CheckBox btn_cheese6;


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
        setContentView(R.layout.newsub3);

        // Enable Up Navigation, Set title/subtitle
        getActionBar().setTitle("Quicksub");
        getActionBar().setSubtitle("Cheese");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Get Checkbox objects
        btn_cheese1 = (CheckBox) findViewById(R.id.checkbox_cheese1);
        btn_cheese2 = (CheckBox) findViewById(R.id.checkbox_cheese2);
        btn_cheese3 = (CheckBox) findViewById(R.id.checkbox_cheese3);
        btn_cheese4 = (CheckBox) findViewById(R.id.checkbox_cheese4);
        btn_cheese5 = (CheckBox) findViewById(R.id.checkbox_cheese5);
        btn_cheese6 = (CheckBox) findViewById(R.id.checkbox_cheese6);

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
        // Clear Cheese category from order
        meal.clear("Cheese");

        // Add selected checkboxes to Order
        if (btn_cheese1.isChecked())
            meal.cheese.add(new Item((String) btn_cheese1.getText(), "Cheese"));
        if (btn_cheese2.isChecked())
            meal.cheese.add(new Item((String) btn_cheese2.getText(), "Cheese"));
        if (btn_cheese3.isChecked())
            meal.cheese.add(new Item((String) btn_cheese3.getText(), "Cheese"));
        if (btn_cheese4.isChecked())
            meal.cheese.add(new Item((String) btn_cheese4.getText(), "Cheese"));
        if (btn_cheese5.isChecked())
            meal.cheese.add(new Item((String) btn_cheese5.getText(), "Cheese"));
        if (btn_cheese6.isChecked())
            meal.cheese.add(new Item((String) btn_cheese6.getText(), "Cheese"));

        // Recalculate Calories/Price
        meal.update();



        // Go to next Activity
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent prev = new Intent(this, NewSub2.class);
                prev.putExtra("meal", meal);
                startActivity(prev);
                overridePendingTransition(R.anim.enter, R.anim.leave);
                return true;

            case R.id.next:
                Intent next = new Intent(this, NewSub4.class);
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
        for (int i=0; i<meal.cheese.size(); i++) {
            if (meal.cheese.get(i).name.equals(btn_cheese1.getText()))
                btn_cheese1.setChecked(true);
            else if (meal.cheese.get(i).name.equals(btn_cheese2.getText()))
                btn_cheese2.setChecked(true);
            else if (meal.cheese.get(i).name.equals(btn_cheese3.getText()))
                btn_cheese3.setChecked(true);
            else if (meal.cheese.get(i).name.equals(btn_cheese4.getText()))
                btn_cheese4.setChecked(true);
            else if (meal.cheese.get(i).name.equals(btn_cheese5.getText()))
                btn_cheese5.setChecked(true);
            else if (meal.cheese.get(i).name.equals(btn_cheese6.getText()))
                btn_cheese6.setChecked(true);
        }
    }


    // Display real-time calories and price
    public void refreshInfo(){
        meal.update();

        TextView calText = (TextView) findViewById(R.id.calTextView);
        TextView priceText = (TextView) findViewById(R.id.priceTextView);
        calText.setText("Calories: " + meal.calories);
        priceText.setText("Price: $"+String.format("%.2f", meal.price));
    }


    // Listener for any change to checkboxes
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox)view).isChecked();

        // Edit Order according to selected/deselected checkbox and update price/calories
        switch(view.getId()) {
            case R.id.checkbox_cheese1:
                if (checked)
                    meal.cheese.add(new Item((String) btn_cheese1.getText(), "Cheese"));
                else
                    meal.removeItem((String)btn_cheese1.getText(), "Cheese");
                break;

            case R.id.checkbox_cheese2:
                if (checked)
                    meal.cheese.add(new Item((String) btn_cheese2.getText(), "Cheese"));
                else
                    meal.removeItem((String)btn_cheese2.getText(), "Cheese");
                break;

            case R.id.checkbox_cheese3:
                if (checked)
                    meal.cheese.add(new Item((String) btn_cheese3.getText(), "Cheese"));
                else
                    meal.removeItem((String)btn_cheese3.getText(), "Cheese");
                break;

            case R.id.checkbox_cheese4:
                if (checked)
                    meal.cheese.add(new Item((String) btn_cheese4.getText(), "Cheese"));
                else
                    meal.removeItem((String)btn_cheese4.getText(), "Cheese");
                break;

            case R.id.checkbox_cheese5:
                if (checked)
                    meal.cheese.add(new Item((String) btn_cheese5.getText(), "Cheese"));
                else
                    meal.removeItem((String)btn_cheese5.getText(), "Cheese");
                break;

            case R.id.checkbox_cheese6:
                if (checked)
                    meal.cheese.add(new Item((String) btn_cheese6.getText(), "Cheese"));
                else
                    meal.removeItem((String)btn_cheese6.getText(), "Cheese");
                break;

            default:
                break;
        }

        refreshInfo();
    }
}
