package com.group5.quicksub;

import java.io.Serializable;

/**
 * Created by Josh on 7/14/13.
 * Updated by Michael on 7/14/13.
 * Updated by Sam on 7/19/13.
 */

//The item class
public class Item implements Serializable {

    public String name = "";
    public String category;     //The category that this item belongs to (Meat, Cheese, etc)
    public int calories;
    public double price;


    public Item() {
        this.name = null;
        this.category = null;
        this.calories = 0;
        this.price = 0.0;
    }


    public Item(String name, String category) {
        this.name = name;
        this.category = category;
        this.calories = 0;
        this.price = 0.0;

        getItemInfo();
    }

    @Override
    public String toString() {
        return this.name;
    }


    // Update calorie and price information based on category name of item
    public void getItemInfo() {
        if(category.compareTo("Bread") == 0) {

            if(name.compareTo("Italian") == 0) {
                this.calories = 200;
            }
            else if(name.compareTo("9-Grain Wheat") == 0) {
                this.calories = 210;
            }
            else if(name.compareTo("Honey Oat") == 0) {
                this.calories = 260;
            }
            else if(name.compareTo("Monterey Cheddar") == 0) {
                this.calories = 240;
            }
            else if(name.compareTo("Italian Herbs and Cheese") == 0) {
                this.calories = 250;
            }
            else if(name.compareTo("Roasted Garlic") == 0) {
                this.calories = 230;
            }
            else if(name.compareTo("Flatbread") == 0) {
                this.calories = 220;
            }
            else {
                this.calories = 0;
            }
        }

        else if(category.compareTo("Cheese") == 0) {

            if(name.compareTo("American") == 0) {
                this.calories = 40;
            }
            else if(name.compareTo("Cheddar") == 0) {
                this.calories = 50;
            }
            else if(name.compareTo("Pepperjack") == 0) {
                this.calories = 50;
            }
            else if(name.compareTo("Mozzarella") == 0) {
                this.calories = 40;
            }
            else if(name.compareTo("Provolone") == 0) {
                this.calories = 50;
            }
            else if(name.compareTo("Swiss") == 0) {
                this.calories = 50;
            }
            else {
                this.calories = 0;
            }
        }

        else if(category.compareTo("Meat") == 0) {

            if(name.compareTo("Chicken") == 0) {
                this.calories = 80;
                this.price = .80;
            }
            else if(name.compareTo("Ham") == 0) {
                this.calories = 60;
                this.price = .8;
            }
            else if(name.compareTo("Cold Cuts") == 0) {
                this.calories = 140;
                this.price = 1.05;
            }
            else if(name.compareTo("Meatballs") == 0) {
                this.calories = 260;
                this.price = 1.05;
            }
            else if(name.compareTo("Roast Beef") == 0) {
                this.calories = 90;
                this.price = 1.05;
            }
            else if(name.compareTo("Steak") == 0) {
                this.calories = 110;
                this.price = 1.05;
            }
            else if(name.compareTo("Tuna") == 0) {
                this.calories = 260;
                this.price = 1.05;
            }
            else if(name.compareTo("Turkey") == 0) {
                this.calories = 50;
                this.price = 1.05;
            }
        }

        else if(category.compareTo("Veggies") == 0) {

            if(name.compareTo("Avocado") == 0) {
                this.calories = 60;
            }

            else if(name.compareTo("Banana Pepper") == 0) {
                this.calories = 5;
            }
            else if(name.compareTo("Cucumber") == 0) {
                this.calories = 5;
            }
            else if(name.compareTo("Green Pepper") == 0) {
                this.calories = 0;
            }
            else if(name.compareTo("Jalapeño") == 0) {
                this.calories = 5;
            }
            else if(name.compareTo("Lettuce") == 0) {
                this.calories = 5;
            }
            else if(name.compareTo("Onion") == 0) {
                this.calories = 5;
            }
            else if(name.compareTo("Olive") == 0) {
                this.calories = 5;
            }
            else if(name.compareTo("Pickle") == 0) {
                this.calories = 0;
            }
            else if(name.compareTo("Spinach") == 0) {
                this.calories = 2;
            }
            else if(name.compareTo("Tomato") == 0) {
                this.calories = 5;
            }

        }

        else if(category.compareTo("Condiments") == 0) {

            if(name.compareTo("Bacon") == 0) {
                this.calories = 45;
            }
            else if(name.compareTo("Buffalo Sauce") == 0) {
                this.calories = 5;
            }
            else if(name.compareTo("Chipotle Southwest Sauce") == 0) {
                this.calories = 100;
            }
            else if(name.compareTo("Honey Mustard") == 0) {
                this.calories = 30;
            }
            else if(name.compareTo("Light Mayo") == 0) {
                this.calories = 50;
            }
            else if(name.compareTo("Mayo") == 0) {
                this.calories = 110;
            }
            else if(name.compareTo("Mustard") == 0) {
                this.calories = 5;
            }
            else if(name.compareTo("Olive Oil") == 0) {
                this.calories = 45;
            }
            else if(name.compareTo("Ranch") == 0) {
                this.calories = 110;
            }
            else if(name.compareTo("Vinegar") == 0) {
                this.calories = 0;
            }
            else if(name.compareTo("Salt") == 0) {
                this.calories = 0;
            }
            else if(name.compareTo("Pepper") == 0) {
                this.calories = 0;
            }
        }

        else if(category.compareTo("Extras") == 0) {
            if(name.compareTo("Chips") == 0) {
                this.calories = 150;
                this.price = .99;
            }
            else if(name.compareTo("Cookie") == 0) {
                this.calories = 220;
                this.price = .99;
            }
            else if(name.compareTo("Drink") == 0) {
                this.calories = 200;
                this.price = 1.5;
            }
        }
    }
}
