package com.group5.quicksub;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * Created by Josh on 7/5/13.
 * Edited by Sam on 7/27/13.
 */

//Activity representing the "Saved Subs" tab. Displays all the saved subs and enables the user to
//Edit, delete, or view a saved sub.
public class SavedSubs extends Activity {

    //String that will contain the filenames of the saved orders
    String[] orders;

    //String that will contain the name (without the .txt extension) of the saved orders
    String[] orderNames;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savedsubs);

        // Disable Up Navigation, Set title/subtitle
        getActionBar().setTitle("Quicksub");
        getActionBar().setSubtitle("Saved Orders");
        getActionBar().setDisplayHomeAsUpEnabled(false);

        //Returns an array of strings naming the private files associated with this Context's application package.
        orders = fileList();

        //Remove the ".txt" to get the name of the sub
        orderNames = new String[orders.length];

        for(int i = 0; i<orderNames.length;i++)
            orderNames[i] = orders[i].substring(0,orders[i].length()-4);

        //Sort the order names
        Arrays.sort(orderNames);

        //See if there is a Favorite order saved. If there is, display it as the first order.
        int index = Arrays.binarySearch(orderNames,"Favorite");

        //Swap the favorite with first value
        if(index>0){

            orderNames[index] = orderNames[0];

            orderNames[0] = "Favorite";

            //Sort the rest of the array, except for the first spot
            Arrays.sort(orderNames,1,orderNames.length);

        }

        //Apply same changes to the actual files so that the items shown on list
        //correctly map to the actual files
        for(int i = 0; i<orders.length; i++)
            orders[i] = orderNames[i]+".txt";


        // ListView to display Saved Orders
        ListView listView = (ListView)findViewById(R.id.listView2);
        ArrayAdapter adapter = new ArrayAdapter(SavedSubs.this, android.R.layout.simple_list_item_1, orderNames);
        listView.setAdapter(adapter);
        // Register Options Context Menu
        registerForContextMenu(listView);

        // Send selected Saved Order to NewSub8
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Order order = parseFile(position);

                Intent myIntent = new Intent(SavedSubs.this, NewSub8.class);
                myIntent.putExtra("order", order);

                //Inform NewSub8 that need to return to the SavedSubs Activity instead of home
                myIntent.putExtra("ReturnToSavedSubsActivity",true);

                //finish();

                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }


    @Override
    // Create context menu for options
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.saved_subs, menu);
    }


    //Function for reading the file of the order, and storing it into a string
    public String getOrderString(int position){

        String order = "";  // Store contents of file
        BufferedReader input;
        StringBuffer buffer = null;

        //Read the contents of the order from the textfile
        try {
            input = new BufferedReader(new InputStreamReader(openFileInput(orders[position])));
            String line;
            buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(order + line + "\n");
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }



    //Function for parsing the contents of the file into an Order object
    //The position of the order name in the variable Orders (which contains all the filenames) is passed in
    public Order parseFile(int position){
        Order order = new Order();
        BufferedReader input;
        String line;

        //Read from the file that want to parse
        try {
            input = new BufferedReader(new InputStreamReader(openFileInput(orders[position])));

            while ((line = input.readLine()) != null) {

                //Get the category. Will be in the first index (0) of the string
                //Everything else will be in the second index (1)
                String [] categoryAtFront = line.split(": ");

                //Determine what the category is, and construct the appropriate items

                //The size category
                if(categoryAtFront[0].compareTo("Size")==0){

                    //Put the size information into the Order
                    //Size info will be second index of the string
                    String size = categoryAtFront[1].substring(0,categoryAtFront[1].length());

                    order.size = size;

                }
                //The Toasted category
                else if(categoryAtFront[0].compareTo("Toasted")==0){
                    /*
                    //Put the toasted info into the Order
                    //Toased info will be second index of the string
                    String toasted = categoryAtFront[1].substring(0,categoryAtFront[1].length());
                    if(toasted.compareTo("true")==0)
                        order.toasted = true;
                    else
                        order.toasted = false;
                    */
                    order.toasted = true;
                }
                //The Bread category
                else if(categoryAtFront[0].compareTo("Bread")==0){

                    //Add the bread Items to the order

                    //Get the name of the bread
                    String bread = categoryAtFront[1];
                    order.bread = new Item(bread, "Bread");


                }
                //The Cheese category
                else if(categoryAtFront[0].compareTo("Cheese")==0){


                    //Get the cheese items
                    String [] cheeses = categoryAtFront[1].split(",");

                    //If there is only one item, there is a bracket in the front and back
                    if(cheeses.length==1){
                        cheeses[0]=cheeses[0].substring(1, cheeses[0].length()-1);
                        //Add the cheese item
                        order.cheese.add(new Item(cheeses[0],"Cheese"));

                    }
                    else{
                        for(int i = 0; i<cheeses.length; i++){
                            //For the first item, remove the bracket at the front

                            if(i==0){
                                cheeses[i]=cheeses[i].substring(1,cheeses[i].length());
                                order.cheese.add(new Item(cheeses[i], "Cheese"));

                            }
                            //For the last item remove the bracket at the back
                            else if(i==cheeses.length-1){

                                cheeses[i] = cheeses[i].substring(1,cheeses[i].length()-1);
                                order.cheese.add(new Item(cheeses[i], "Cheese"));

                            }
                            //For items in the middle
                            else{
                                cheeses[i]=cheeses[i].substring(1,cheeses[i].length());
                                order.cheese.add(new Item(cheeses[i], "Cheese"));


                            }

                        }
                    }
                }
                //The Meat category
                else if(categoryAtFront[0].compareTo("Meat")==0){


                    //Get the meat items
                    String [] meats = categoryAtFront[1].split(",");

                    //If there is only one item, there is a bracket in the front and back
                    if(meats.length==1){
                        meats[0]=meats[0].substring(1, meats[0].length()-1);
                        //Add the cheese item
                        order.meat.add(new Item(meats[0],"Meat"));

                    }
                    else{
                        for(int i = 0; i<meats.length; i++){
                            //For the first item, remove the bracket at the front

                            if(i==0){
                                meats[i]=meats[i].substring(1,meats[i].length());
                                order.meat.add(new Item(meats[i], "Meat"));

                            }
                            //For the last item remove the bracket at the back
                            else if(i==meats.length-1){

                                meats[i] = meats[i].substring(1,meats[i].length()-1);
                                order.meat.add(new Item(meats[i], "Meat"));

                            }
                            //For items in the middle
                            else{
                                meats[i]=meats[i].substring(1,meats[i].length());
                                order.meat.add(new Item(meats[i], "Meat"));


                            }

                        }
                    }
                }
                //The Veggies category
                else if(categoryAtFront[0].compareTo("Veggies")==0){


                    //Get the meat items
                    String [] veggies = categoryAtFront[1].split(",");

                    //If there is only one item, there is a bracket in the front and back
                    if(veggies.length==1){
                        veggies[0]=veggies[0].substring(1, veggies[0].length()-1);
                        //Add the cheese item
                        order.veggies.add(new Item(veggies[0],"Veggies"));

                    }
                    else{
                        for(int i = 0; i<veggies.length; i++){
                            //For the first item, remove the bracket at the front

                            if(i==0){
                                veggies[i]=veggies[i].substring(1,veggies[i].length());
                                order.veggies.add(new Item(veggies[i], "Veggies"));

                            }
                            //For the last item remove the bracket at the back
                            else if(i==veggies.length-1){

                                veggies[i] = veggies[i].substring(1,veggies[i].length()-1);
                                order.veggies.add(new Item(veggies[i], "Veggies"));

                            }
                            //For items in the middle
                            else{
                                veggies[i]=veggies[i].substring(1,veggies[i].length());
                                order.veggies.add(new Item(veggies[i], "Veggies"));


                            }

                        }
                    }
                }

                //The Condiments category
                else if(categoryAtFront[0].compareTo("Condiments")==0){


                    //Get the meat items
                    String [] condiments = categoryAtFront[1].split(",");

                    //If there is only one item, there is a bracket in the front and back
                    if(condiments.length==1){
                        condiments[0]=condiments[0].substring(1, condiments[0].length()-1);
                        //Add the cheese item
                        order.condiments.add(new Item(condiments[0],"Condiments"));

                    }
                    else{
                        for(int i = 0; i<condiments.length; i++){
                            //For the first item, remove the bracket at the front

                            if(i==0){
                                condiments[i]=condiments[i].substring(1,condiments[i].length());
                                order.condiments.add(new Item(condiments[i], "Condiments"));

                            }
                            //For the last item remove the bracket at the back
                            else if(i==condiments.length-1){

                                condiments[i] = condiments[i].substring(1,condiments[i].length()-1);
                                order.condiments.add(new Item(condiments[i], "Condiments"));

                            }
                            //For items in the middle
                            else{
                                condiments[i]=condiments[i].substring(1,condiments[i].length());
                                order.condiments.add(new Item(condiments[i], "Condiments"));


                            }

                        }
                    }
                }

                //The Extras category
                else if(categoryAtFront[0].compareTo("Extras")==0){


                    //Get the meat items
                    String [] extras = categoryAtFront[1].split(",");

                    //If there is only one item, there is a bracket in the front and back
                    if(extras.length==1){
                        extras[0]=extras[0].substring(1, extras[0].length()-1);
                        //Add the cheese item
                        order.extras.add(new Item(extras[0],"Extras"));

                    }
                    else{
                        for(int i = 0; i<extras.length; i++){
                            //For the first item, remove the bracket at the front

                            if(i==0){
                                extras[i]=extras[i].substring(1,extras[i].length());
                                order.extras.add(new Item(extras[i], "Extras"));

                            }
                            //For the last item remove the bracket at the back
                            else if(i==extras.length-1){

                                extras[i] = extras[i].substring(1,extras[i].length()-1);
                                order.extras.add(new Item(extras[i], "Extras"));

                            }
                            //For items in the middle
                            else{
                                extras[i]=extras[i].substring(1,extras[i].length());
                                order.extras.add(new Item(extras[i], "Extras"));


                            }

                        }
                    }
                }

            }

            input.close();


        } catch (Exception e) {
            e.printStackTrace();
        }



        //Make it known that this Order object represents an order that is already saved, and is being edited
        order.editedOrder = true;

        //Saving the filename of the file
        order.filename = orders[position];

        String[] split = order.filename.split(".txt");

        order.name = split[0];

        return order;
    }

    @Override
    //Deleting or editing an order, based upon the user's selection
    //TODO: Add functionality for editing a saved order
    //TODO: Will do intent.hasExtra(name) to see if order is brand new or is an order being edited in the new sub activity
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            // Edit
            case R.id.editOrder:{

                // Construct the Order object
                Order order = parseFile(info.position);

                // Send Order to NewSub1 to be edited
                Intent myIntent = new Intent(SavedSubs.this, NewSub1.class);
                myIntent.putExtra("meal",order);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            }
            //If user chooses to delete the order, find the order and delete it
            case R.id.deleteOrder:{

                //Determine which order the context menu was invoked on
                final int position = info.position;

                //If the order being deleted is the Favorite Order, tell user
                if(orders[position].compareTo("Favorite.txt")==0){

                    //show alert dialog
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SavedSubs.this);

                    alertDialogBuilder.setTitle("Favorite Order being deleted");
                    alertDialogBuilder.setMessage("Deleting the 'Favorite' will cause it to disappear from the Favorite section. Proceed?");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                            //Delete the order
                            deleteFile(orders[position]);

                            //Refresh the activity so it shows an updated list of order names
                            Intent myIntent = new Intent(SavedSubs.this, SavedSubs.class);
                            finish();
                            startActivity(myIntent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            //Close the dialog
                            dialog.cancel();

                        }
                    });

                    alertDialogBuilder.setCancelable(false);
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                }

                else{
                //Delete the order
                deleteFile(orders[position]);

                //Refresh the activity so it shows an updated list of order names
                Intent myIntent = new Intent(SavedSubs.this, SavedSubs.class);
                finish();
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                return true;
            }
            //If user chooses to combine this order with another order, launch another activity and let user choose the order
            // to combine with
            case R.id.combineOrder:{

                Intent myIntent = new Intent(SavedSubs.this, CombineOrder.class);

                //Pass in the index of the order that the context menu was invoked on
                //so that know which order will be combined
                int position = info.position;
                myIntent.putExtra("Position",position);

                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            }
            //If user chooses to rename the order
            case R.id.renameOrder:{

                final int position = info.position;

                //If the order being renamed is the Favorite Order, tell user
                if(orders[info.position].compareTo("Favorite.txt")==0){

                    //show alert dialog
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SavedSubs.this);

                    alertDialogBuilder.setTitle("Favorite Order being deleted");
                    alertDialogBuilder.setMessage("Renaming the 'Favorite' will cause it to disappear from the Favorite section. Proceed?");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                            //Create the intent, and launch the display sub activity
                            //Will delete order in the SaveSub activity after renaming the order is successful

                            //Get contents of file, pass it into a string, launch the SaveOrder Activity, and delete the current order
                            String order = getOrderString(position);
                            Intent myIntent = new Intent(SavedSubs.this, SaveSub.class);

                            //Putting the order (string format) in the intent
                            myIntent.putExtra("order",order);

                            //Put the filename of the order to delete in the intent
                            myIntent.putExtra("old name",orders[position]);

                            finish();

                            //Start the activity that will prompt user to enter name for the sub
                            SavedSubs.this.startActivity(myIntent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            //Close the dialog
                            dialog.cancel();

                        }
                    });

                    alertDialogBuilder.setCancelable(false);
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                }

                else{
                    //Get contents of file, pass it into a string, launch the SaveOrder Activity, and delete the current order
                    String order = getOrderString(info.position);

                    //Create the intent, and launch the display sub activity
                    //Will delete order in the SaveSub activity after renaming the order is successful

                    Intent myIntent = new Intent(SavedSubs.this, SaveSub.class);

                    //Putting the order (string format) in the intent
                    myIntent.putExtra("order",order);

                    //Put the filename of the order to delete in the intent
                    myIntent.putExtra("old name",orders[info.position]);

                    finish();

                    //Start the activity that will prompt user to enter name for the sub
                    SavedSubs.this.startActivity(myIntent);



                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                return true;
            }
            //If User chooses to set this order as Favorite
            case R.id.makeFavorite:{

                //Get contents of file, pass it into a string, and save it to a File called "Favorite"
                String order = getOrderString(info.position);

                //Create the file
                //File file = new File(getFilesDir(), "Favorite.txt");

                BufferedWriter writer = null;


                try {

                    //Write the order to the file
                    writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("Favorite.txt", MODE_PRIVATE)));
                    writer.write(order);
                    writer.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }



                //show alert dialog
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SavedSubs.this);

                alertDialogBuilder.setTitle("Favorite Set");
                alertDialogBuilder.setMessage("Order set as Favorite. Can be accessed in Favorite section on main page.\nTo access order in the Saved section, it will be the order name 'Favorite' and will be the first order.");
                alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        //Refresh the activity so it shows an updated list of order names, including the Favorite order
                        //Just made
                        Intent myIntent = new Intent(SavedSubs.this, SavedSubs.class);
                        finish();
                        startActivity(myIntent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                });

                alertDialogBuilder.setCancelable(false);
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

                return true;

            }

            default:
                return super.onContextItemSelected(item);
        }
    }
}