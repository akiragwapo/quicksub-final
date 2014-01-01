package com.group5.quicksub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by Josh on 7/14/13.
 * Edited by Sam on 7/27/13.
 */

public class CombineOrder extends Activity {

    String[] orders;        // String Array containing file names of saved orders
    String[] orderNames;    // String Array containing names of saved orders w/o .txt ext
    int positionPrevious;   // Position of the 1st order selected

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savedsubs);

        // Disable Up Navigation, Set title/subtitle
        getActionBar().setTitle("Quicksub");
        getActionBar().setSubtitle("Combine Order");
        getActionBar().setDisplayHomeAsUpEnabled(false);

        //Get the position of the first order
        positionPrevious = getIntent().getIntExtra("Position",0);

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
        ArrayAdapter adapter = new ArrayAdapter(CombineOrder.this, android.R.layout.simple_list_item_1, orderNames);
        listView.setAdapter(adapter);

        //Display the order selected by the user.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Order first_order = parseFile(positionPrevious);
                Order second_order = parseFile(position);

                Intent myIntent = new Intent(CombineOrder.this, NewSub8.class);

                myIntent.putExtra("order", first_order);
                myIntent.putExtra("order2", second_order);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
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

                    //Get the veggie items
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
                    else {
                        for (int i = 0; i<extras.length; i++) {
                            //For the first item, remove the bracket at the front

                            if (i==0) {
                                extras[i]=extras[i].substring(1,extras[i].length());
                                order.extras.add(new Item(extras[i], "Extras"));

                            }
                            //For the last item remove the bracket at the back
                            else if (i==extras.length-1){

                                extras[i] = extras[i].substring(1,extras[i].length()-1);
                                order.extras.add(new Item(extras[i], "Extras"));

                            }
                            //For items in the middle
                            else {
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

        //Saving the filename of the file
        order.filename = orders[position];

        String[] split = order.filename.split(".txt");

        order.name = split[0];

        return order;
    }
}