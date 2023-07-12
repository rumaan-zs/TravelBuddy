package com.example.travelbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.travelbuddy.Adapter.Adapter;
import com.example.travelbuddy.Constants.MyConstants;
import com.example.travelbuddy.Data.AppData;
import com.example.travelbuddy.Database.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> titles;
    List<Integer> images;
    Adapter adapter;
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); //hides the action bar
        recyclerView= findViewById(R.id.recycler_view);

        addAddTitles();
        addAllImages();
        persistAppData();
        database = RoomDB.getInstance(this);
        System.out.println("--------->"+database.mainDao().getAllSelected(false).get(0).getItemname());

        adapter = new Adapter(this,titles,images,MainActivity.this);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    //Variable to confirm that you need to exit the app
    private static final int TIME_INTERVAL = 2000;

    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if(mBackPressed+TIME_INTERVAL>System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else{
            Toast.makeText(this, "Tap Back Button To Exit App", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    private  void persistAppData(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        database = RoomDB.getInstance(this);
        AppData appData = new AppData(database);
        int last = prefs.getInt(AppData.LAST_VERSION, 0);
        if(!prefs.getBoolean(MyConstants.FIRST_TIME_CAMEL_CASE,false)){
            appData.persistAllData();
            editor.putBoolean(MyConstants.FIRST_TIME_CAMEL_CASE,true);
            editor.commit();
        } else if (last<AppData.NEW_VERSION) {
            database.mainDao().deleteAllSystemItems(MyConstants.SYSTEM_SMALL);
            appData.persistAllData();
            editor.putInt(AppData.LAST_VERSION,AppData.NEW_VERSION);
            editor.commit();
        }
    }
    private void addAddTitles(){
        titles = new ArrayList<>();
        titles.add(MyConstants.BASIC_NEEDS_CAMEL_CASE);
        titles.add(MyConstants.BABY_NEEDS_CAMEL_CASE);
        titles.add(MyConstants.CLOTHING_CAMEL_CASE);
        titles.add(MyConstants.FOOD_CAMEL_CASE);
        titles.add(MyConstants.HEALTH_CAMEL_CASE);
        titles.add(MyConstants.TECHNOLOGY_CAMEL_CASE);
        titles.add(MyConstants.PERSONAL_CARE_CAMEL_CASE);
        titles.add(MyConstants.CAR_SUPPLIES_CAMEL_CASE);
        titles.add(MyConstants.MY_LIST_CAMEL_CASE);
        titles.add(MyConstants.MY_SELECTIONS_CAMEL_CASE);
    }
    private void addAllImages(){
        images = new ArrayList<>();
        images.add(R.drawable.basicneeds);
        images.add(R.drawable.babyneeds);
        images.add(R.drawable.clothing);
        images.add(R.drawable.food);
        images.add(R.drawable.health);
        images.add(R.drawable.tech);
        images.add(R.drawable.personalcare);
        images.add(R.drawable.car);
        images.add(R.drawable.mylist);
        images.add(R.drawable.mysel);
    }

}