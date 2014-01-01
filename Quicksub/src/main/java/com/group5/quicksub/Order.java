package com.group5.quicksub;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Josh on 7/14/13.
 * Updated by Michael on 7/14/2013.
 * Updated by Sam on 7/27/2013
 */

public class Order implements Serializable {
    public String name;
    public String size;
    public boolean toasted;

    //Variable indicating if the order represents a saved order that is currently being edited
    boolean editedOrder;
    //If order represents a saved order that is being edited, this name is instantiated
    String filename;

    public Item bread;
    public ArrayList<Item> cheese;
    public ArrayList<Item> meat;
    public ArrayList<Item> veggies;
    public ArrayList<Item> condiments;
    public ArrayList<Item> extras;

    public int calories;
    public double price;
    public long dateCreated;


    public Order() {
        name = "";
        size = "Half";
        toasted = false;

        editedOrder = false;
        filename = null;

        bread = new Item();
        cheese = new ArrayList<Item>();
        meat = new ArrayList<Item>();
        veggies = new ArrayList<Item>();
        condiments = new ArrayList<Item>();
        extras = new ArrayList<Item>();

        calories = 0;
        price = 0.00;
        dateCreated = System.currentTimeMillis();
    }


    @Override
    public String toString(){
        this.update();

        /* Trying to remove brackets from ArrayLists'
        StringBuilder cheese = new StringBuilder();
        for (int i=0; i<this.cheese.size(); i++) {
            cheese.append(this.cheese.get(i).name);
            if (i!=this.cheese.size()-1) cheese.append(", ");
        }
        StringBuilder meat = new StringBuilder();
        for (int i=0; i<this.meat.size(); i++) {
            meat.append(this.meat.get(i).name);
            if (i!=this.meat.size()-1) meat.append(", ");
        }
        StringBuilder veggies = new StringBuilder();
        for (int i=0; i<this.veggies.size(); i++) {
            veggies.append(this.veggies.get(i).name);
            if (i!=this.veggies.size()-1) veggies.append(", ");
        }
        StringBuilder condiments = new StringBuilder();
        for (int i=0; i<this.condiments.size(); i++) {
            condiments.append(this.condiments.get(i).name);
            if (i!=this.condiments.size()-1) condiments.append(", ");
        }
        StringBuilder extras = new StringBuilder();
        for (int i=0; i<this.extras.size(); i++) {
            extras.append(this.extras.get(i).name);
            if (i!=this.extras.size()-1) extras.append(", ");
        }
        */

        String contents = "";

        contents = contents + "Size: " + this.size;
        if (this.toasted) contents = contents + "\nToasted";
        if (this.bread.name!=null) contents = contents + "\nBread: " + this.bread.toString();
        if(!this.cheese.isEmpty())
            contents = contents + "\nCheese: "+this.cheese.toString();
        if(!this.meat.isEmpty())
            contents = contents + "\nMeat: "+this.meat.toString();
        if(!this.veggies.isEmpty())
            contents = contents + "\nVeggies: "+this.veggies.toString();
        if(!this.condiments.isEmpty())
            contents = contents + "\nCondiments: "+this.condiments.toString();
        if(!this.extras.isEmpty())
            contents = contents + "\nExtras: "+this.extras.toString();

        return contents;
    }


    public void clear(String category) {
        if (category.equals("Cheese")) {
            while (cheese.size()!=0)
                cheese.remove(0);
        }
        else if (category.equals("Meat")) {
            while (meat.size()!=0)
                meat.remove(0);
        }
        else if (category.equals("Veggies")) {
            while (veggies.size()!=0)
                veggies.remove(0);
        }
        else if (category.equals("Condiments")) {
            while (condiments.size()!=0)
                condiments.remove(0);
        }
        else if (category.equals("Extras")) {
            while (extras.size()!=0)
                extras.remove(0);
        }
    }


    public void removeItem(String name, String category) {
        if (category.equals("Cheese")) {
            for(int i=0; i<cheese.size(); i++) {
                if (cheese.get(i).name.equals(name))
                    cheese.remove(i);
            }
        }
        else if (category.equals("Meat")) {
            for(int i=0; i<meat.size(); i++) {
                if (meat.get(i).name.equals(name))
                    meat.remove(i);
            }
        }
        else if (category.equals("Veggies")) {
            for(int i=0; i<veggies.size(); i++) {
                if (veggies.get(i).name.equals(name))
                    veggies.remove(i);
            }
        }
        else if (category.equals("Condiments")) {
            for(int i=0; i<condiments.size(); i++) {
                if (condiments.get(i).name.equals(name))
                    condiments.remove(i);
            }
        }
        else if (category.equals("Extras")) {
            for(int i=0; i<extras.size(); i++)
                if (extras.get(i).name.equals(name)) {
                    extras.remove(i);
            }
        }
    }


    // Update Calories/Price for Order based on Quantity
    public void update() {
        double calories = 0.00;
        double price = 0.00;

        if (size.equals("Half")) {
            calories = 250;
            price = 3.00;
        }
        else if (size.equals("Whole")) {
            calories = 500;
            price = 5.00;
        }

        calories+=bread.calories;
        price+=bread.price;

        for(int i = 0; i<cheese.size();i++) {
            calories+=cheese.get(i).calories;
            price+=cheese.get(i).price;
        }
        for(int i = 0; i<meat.size();i++) {
            calories+=meat.get(i).calories;
            price+=meat.get(i).price;
        }
        for(int i = 0; i<veggies.size();i++) {
            calories+=veggies.get(i).calories;
            price+=veggies.get(i).price;
        }
        for(int i = 0; i<condiments.size();i++) {
            calories+=condiments.get(i).calories;
            price+=condiments.get(i).price;
        }
        for(int i = 0; i<extras.size();i++) {
            calories+=extras.get(i).calories;
            price+=extras.get(i).price;
        }

        this.calories = (int)calories;

        // Round to two decimal places
        price = Math.round(price*100);
        price = price/100;
        this.price = price;
    }
}







