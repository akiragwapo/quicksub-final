package com.group5.quicksub;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * Created by Josh on 7/3/13.
 */
//Activity that handles saving a sub. Saves the order to a txt file.
public class SaveSub extends Activity {

    //The order
    String order;

    //The name that user chooses to name saved order (has .txt appended to it)
    String filename;

    //The name of the old filename that will be renamed (if this activity is being launched witht he purpose of renmaing an order)
    String oldFilename;

    //Name of the order without the .txt file extension
    String ordername;

    //variable indicating whether or not this activity represents saving a new order, or renaming an existing order
    Boolean rename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savesub);

        // Disable Up Navigation, Set title/subtitle
        getActionBar().setTitle("Quicksub");
        getActionBar().setSubtitle("Save Order");
        getActionBar().setDisplayHomeAsUpEnabled(false);

        //Check to see if the intent passed in represents an already saved order that is being renamed
        if(getIntent().hasExtra("old name")){

            //Getting the filename of the order that is to be deleted once the order is given a new name
            oldFilename = getIntent().getStringExtra("old name");
            rename = true;

            //Getting the order of the file in string format
            order = getIntent().getStringExtra("order");
        }

        //If this is a new order being saved
        else{
            order = getIntent().getStringExtra("meal");
            rename = false;
        }

        addListenerOnButton();
    }


    //Registering the button click.
    public void addListenerOnButton() {

        //Button for saving the sub
        Button btn = (Button) findViewById(R.id.SaveTheSub);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Getting the name of the sub that user has chosen
                EditText editText = (EditText) findViewById(R.id.FileName);

                filename = editText.getText().toString();

                ordername = filename;

                //Sanitize the order to make sure there are no trailing spaces after order name
                //This can happen if person uses autocorrection or a keyboard prediction to name a sub
                //If don't sanitize order, the string comparisons below will not work

                //Get the length of the string
                int length = filename.length();

                //If the user did not enter anything into text box, don't sanitize because will get an index out of bounds exception
                if(length!=0){
                    if(filename.charAt(length-1)==' ')
                        filename = SanitizeInput(filename);
                }
                //Add the .txt file extension since this order will be saved as a text file
                filename = filename+".txt";

                //String containing the names of the previous orders
                String[] orders= fileList();

                //Sort the files
                Arrays.sort(orders);



                //Make sure that filename is not empty. If it is, inform user
                if(filename.compareTo(".txt")==0)
                    showPrompt(1);
                    //Make sure the filename is not the same name as an existing order
                    //Make sure that filename is not "Favorite". This is reserved for the saved order
                    //That is designated as the favorite by performing a long click, and choosing "Make Favorite"
                else if(filename.compareTo("Favorite.txt")==0)
                    showPrompt(3);
                else if(Arrays.binarySearch(orders,filename)>=0)
                    showPrompt(4);
                    //Make sure filename is less than 20 characters
                else if(filename.length()>24)
                    showPrompt(2);

                    //If there are no issues with the sub name, save the order
                else {



                    //If this activity was launched with the purpose of renaming an existing order, need to delete the old file
                    //first.
                    if(rename)
                        deleteFile(oldFilename);


                    //Create the file
                    //File file = new File(getFilesDir(), filename);

                    BufferedWriter writer = null;


                    try {

                        //Write the order to the file
                        writer = new BufferedWriter(new OutputStreamWriter(openFileOutput(filename, MODE_PRIVATE)));
                        writer.write(order);
                        writer.close();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent;

                    //If the sub is being renamed, return back to the Saved Sub section
                    if(rename)
                        intent = new Intent(SaveSub.this, SavedSubs.class);

                    //Otherwise, return back to the main page
                    else{
                         intent = new Intent(SaveSub.this, Main.class);
                        //Clear out all the previous activities in memory
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }

                    //Finish this activity
                    finish();


                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }



            }

        });


    }

    //Function that sanitizes the name given to an order: removes all trailing spaces
    public static String SanitizeInput(String input){

        int length = input.length();


        //If user didn't enter anything, just return
        if(length==0)
            return input;


        //Remove trailing spaces until there aren't any
        while(input.charAt(length-1)==' '){


            //Remove the first  empty space
            input = input.substring(0,length-1);

            //Get the new length
            length = input.length();

            //Make sure that there is still as string left
            if(length==0)
                break;

        }

        return input;
    }

    //Function that shows a dialog message menu informing user if the order name is invalid

    public void showPrompt(int type){

        //Create the Dialog message
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);



        //Inform user that they need to give the order a name
        if(type==1){
            alertDialogBuilder.setTitle("Invalid order name");
            alertDialogBuilder.setMessage("Give the order a name");
            alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    //Refresh the activity
                    Intent myIntent = new Intent(SaveSub.this, SaveSub.class);

                    //If this activity is launched with the purpose of saving a new order, not renaming an exisitng one
                    if(!rename)
                        myIntent.putExtra("meal",order);
                        //If this activity is launched with the intention of renaming an existing one
                    else{

                        //Putting the order (string format) in the intent
                        myIntent.putExtra("order",order);

                        //Put the filename of the order to delete in the intent
                        myIntent.putExtra("old name",oldFilename);

                    }
                    finish();
                    startActivity(myIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }
            });

        }
        //Inform user that order name needs to be less than 20 characters
        else if(type==2){
            alertDialogBuilder.setTitle("Invalid order name");
            alertDialogBuilder.setMessage("Order name must be less than 20 characters");

            alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    //Refresh the activity
                    Intent myIntent = new Intent(SaveSub.this, SaveSub.class);

                    //If this activity is launched with the purpose of saving a new order, not renaming an exisitng one
                    if(!rename)
                        myIntent.putExtra("meal",order);
                        //If this activity is launched with the intention of renaming an existing one
                    else{

                        //Putting the order (string format) in the intent
                        myIntent.putExtra("order",order);

                        //Put the filename of the order to delete in the intent
                        myIntent.putExtra("old name",oldFilename);


                    }
                    finish();
                    startActivity(myIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }
            });
        }
        //Inform user that the order name is invalid (can't name order "Favorite" as that file is used for the app's system settings

        else if(type==3){
            alertDialogBuilder.setTitle("Invalid order name");
            alertDialogBuilder.setMessage("Order can't be named 'Favorite'\nTo designate an order as a Favorite, go to Saved Subs and choose an order to set as favorite.");

            alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    //Refresh the activity
                    Intent myIntent = new Intent(SaveSub.this, SaveSub.class);

                    //If this activity is launched with the purpose of saving a new order, not renaming an exisitng one
                    if(!rename)
                        myIntent.putExtra("meal",order);
                        //If this activity is launched with the intention of renaming an existing one
                    else{

                        //Putting the order (string format) in the intent
                        myIntent.putExtra("order",order);

                        //Put the filename of the order to delete in the intent
                        myIntent.putExtra("old name",oldFilename);


                    }
                    finish();
                    startActivity(myIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }
            });
        }

        //Inform user that have a pre-existing order of the same name

        else if(type==4){
            alertDialogBuilder.setTitle("Invalid order name");
            alertDialogBuilder.setMessage("Order name already taken");

            alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                    //dialog.cancel();
                    //Refresh the activity
                    Intent myIntent = new Intent(SaveSub.this, SaveSub.class);

                    //If this activity is launched with the purpose of saving a new order, not renaming an exisitng one
                    if(!rename)
                        myIntent.putExtra("meal",order);
                        //If this activity is launched with the intention of renaming an existing one
                    else{

                        //Putting the order (string format) in the intent
                        myIntent.putExtra("order",order);

                        //Put the filename of the order to delete in the intent
                        myIntent.putExtra("old name",oldFilename);


                    }
                    finish();
                    startActivity(myIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
        }


        alertDialogBuilder.setCancelable(false);


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


}
