package com.example.travelbuddy.Data;

import android.app.Application;
import android.content.Context;

import com.example.travelbuddy.Constants.MyConstants;
import com.example.travelbuddy.Database.RoomDB;
import com.example.travelbuddy.Models.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Data is kept here before putting to Database
public class AppData extends Application {

    RoomDB database;
    String category;
    Context context;

    public static final String LAST_VERSION = "LAST_VERSION";
    public static final int NEW_VERSION = 1;

    //Database instance to save the data
    public AppData(RoomDB database) {
        this.database = database;
    }

    public AppData(RoomDB database, Context context) {
        this.database = database;
        this.context = context;
    }

    // List of items prefixed in basicNeeds
    //One way to enter the data
    public List<Items> getBasicData() {
        category = "Basic Needs";
        List<Items> basicItem = new ArrayList<>();
        basicItem.add(new Items("Visa", category,false));
        basicItem.add(new Items("Passport", category,false));
        basicItem.add(new Items("Tickets", category,false));
        basicItem.add(new Items("Wallet", category,false));
        basicItem.add(new Items("Driving License", category,false));
        basicItem.add(new Items("Currency", category,false));
        basicItem.add(new Items("House Keys", category,false));
        return basicItem;
    }

    // second way to enter the data with the use of prepareItemsList() function
    public List<Items> getPersonalCareData(){
        String[] data = {"Tooth-brush", "Tooth-paste", "Mouthwash", "Sunscreen", "Shampoo", "Conditioner", "Soap", "Handwash", "Comb", "Moisturizer","Perfume", "Makeup" };
        return prepareItemsList(MyConstants.PERSONAL_CARE_CAMEL_CASE, data);
    }

    public List<Items> getBabyNeedsData(){
        String[] data = {"Clothes", "Underwear", "Diapers", "Lotion", "Pacifier", "Candy", "Wet Wipes", "Baby Soap", "Mosquito Repellent", "Baby Food", "Toys"};
        return prepareItemsList(MyConstants.BABY_NEEDS_CAMEL_CASE, data);
    }

    public List<Items> getClothingData(){
        String[] data = {"Underwear", "Shorts", "Shirt", "Watch", "Bracelet", "Vest", "Shoes", "Socks", "T-Shirt", "Sunglasses", "Belt", "Trousers", "NightDress", "Slippers", "Hat", "TrackPant"};
        return prepareItemsList(MyConstants.CLOTHING_CAMEL_CASE, data);
    }

    public List<Items> getHealthData(){
        String[] data = {"Paracetamol", "First Aid", "Vitamins", "Antiseptic Cream","Pain killer spray", "Allergy Medicines"};
        return prepareItemsList(MyConstants.HEALTH_CAMEL_CASE, data);
    }

    public List<Items> getTechnologyData(){
        String[] data = {"Mobile Phone", "Laptop", "Charger", "Adapter", "Extension Cable", "Power bank", "Headphones", "Camera"};
        return prepareItemsList(MyConstants.TECHNOLOGY_CAMEL_CASE, data);
    }

    public List<Items> getFoodData(){
        String[] data = {"Snacks", "Instant Tea Powder", "Water", "Chips"};
        return prepareItemsList(MyConstants.FOOD_CAMEL_CASE , data);
    }

    public List<Items> getCarSuppliesData(){
        String[] data = {"Car Jack", "Spare Tyre", "Car Documents", "Window Sun Shades", "Air Freshener"};
        return prepareItemsList(MyConstants.CAR_SUPPLIES_CAMEL_CASE, data);
    }

    public List<Items> prepareItemsList(String category, String []data){
        List<String> list = Arrays.asList(data);
        List<Items> dataList = new ArrayList<>();
        dataList.clear();;

        for( int i=0; i<list.size(); i++){
            dataList.add(new Items(list.get(i), category, false));
        }
        return dataList;
    }

    public List<List<Items>> getAllData(){
        List<List<Items>> listOfAllItems = new ArrayList<>();
        listOfAllItems.clear();
        listOfAllItems.add(getBasicData());
        listOfAllItems.add(getClothingData());
        listOfAllItems.add(getPersonalCareData());
        listOfAllItems.add(getBabyNeedsData());
        listOfAllItems.add(getTechnologyData());
        listOfAllItems.add(getCarSuppliesData());
        listOfAllItems.add(getHealthData());
        listOfAllItems.add(getFoodData());
        return listOfAllItems;
    }

    public void persistAllData(){
        List<List<Items>> listOfAllItems = getAllData();
        for(List<Items> list:listOfAllItems){
            for(Items items :list){
                database.mainDao().saveItem(items);
            }
        }
        System.out.println("Data added");
    }

}
