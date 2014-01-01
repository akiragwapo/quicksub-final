package com.group5.quicksub;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.main);

        // Set Listeners on Buttons
        Button btnNew = (Button)findViewById(R.id.buttonNew);
        btnNew.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newsub = new Intent(Main.this, NewSub1.class);
                Order meal = new Order();
                newsub.putExtra("meal",meal);
                startActivity(newsub);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Button btnSaved = (Button)findViewById(R.id.buttonSaved);
        btnSaved.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Main.this, SavedSubs.class);
                Main.this.startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Button btnFavorite = (Button)findViewById(R.id.buttonFavorite);
        btnFavorite.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean result = FavoriteExists();


                //If there is a designated favorite, show contents: parse file and create an Order object
                if(result==true){

                    //Create the Order object
                    Order order = parseFile();

                    //Display the Favorite Order
                    Intent myIntent = new Intent(Main.this, NewSub8.class);
                    myIntent.putExtra("order", order);


                    startActivity(myIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }
                else{
                    //show alert dialog
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main.this);

                    alertDialogBuilder.setTitle("No Favorite Set");
                    alertDialogBuilder.setMessage("Choose one of your saved orders to designate as a Favorite first.");
                    alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            //Refresh Activity
                            //Refresh the activity so it shows an updated list of order names
                            /*
                            Intent myIntent = new Intent(Main.this, Main.class);
                            finish();
                            startActivity(myIntent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                            */

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

            }
        });

        /*
        // ListView to display options
        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayList<String> options = new ArrayList<String>();
        options.add("New Order");
        options.add("Saved Orders");
        ArrayAdapter adapter = new ArrayAdapter(Main.this, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(position==0){
                    Intent newsub = new Intent(Main.this, NewSub1.class);
                    Order meal = new Order();
                    newsub.putExtra("meal",meal);
                    startActivity(newsub);
                }
               if(position==1){
                    Intent myIntent = new Intent(Main.this, SavedSubs.class);
                    Main.this.startActivity(myIntent);
                }
            }
        });
        */
    }

    //Function indicating whether or not there is a designated Favorite sub
    public boolean FavoriteExists(){

        //Returns an array of strings naming the private files associated with this Context's application package.
        String [] orders = fileList();

        //Sort the order names
        Arrays.sort(orders);

        //Perform a binary search to see if there is a sub designated as a favorite
        if(Arrays.binarySearch(orders,"Favorite.txt")>=0)
            return true;
        else
            return false;



    }

    //Function for parsing the contents of the file into an Order object
    public Order parseFile(){
        Order order = new Order();
        BufferedReader input;
        String line;

        //Read from the file that want to parse
        try {
            input = new BufferedReader(new InputStreamReader(openFileInput("Favorite.txt")));

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
        order.filename = "Favorite.txt";

        order.name = "Favorite";

        return order;
    }
}
