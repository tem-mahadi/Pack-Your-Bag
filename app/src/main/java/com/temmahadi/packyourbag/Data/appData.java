package com.temmahadi.packyourbag.Data;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.room.Room;

import com.temmahadi.packyourbag.Constants.MyConstants;
import com.temmahadi.packyourbag.DataBase.roomDB;
import com.temmahadi.packyourbag.Models.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class appData extends Application {
    roomDB database;
    String category;
    Context context;
    public static final String LAST_VERSION ="Last Version";
    public static final int NEW_VERSION = 1;

    public appData(roomDB database) {
        this.database = database;
    }

    public appData(roomDB database, Context context) {
        this.database = database;
        this.context = context;
    }
    public List<items> getBasicData (){
        category = "Basic Needs";
        List<items> basicItem = new ArrayList<>();
        basicItem.add(new items("Visa",category,false));
        basicItem.add(new items("Passport",category,false));
        return basicItem;
    }
    public List<items> getPersonalCareData(){
        String[] data= {"Tooth brush","Tooth Paste","Cloths","Mouthwase"};
        return prepareItemList(MyConstants.PERSONAL_CARE_CAMEL_CASE,data);
    }
    public List<items> getClothingData(){
        String[] data= {"Tooth brush","Tooth Paste","Cloths","Mouthwase"};
        return prepareItemList(MyConstants.CLOTHING_CAMEL_CASE,data);
    }
    public List<items> getBabyNeedsData(){
        String[] data= {"Tooth brush","Tooth Paste","Cloths","Mouthwase"};
        return prepareItemList(MyConstants.BABY_NEEDS_CAMEL_CASE,data);
    }
    public List<items> getTechnologyData(){
        String[] data= {"Tooth brush","Tooth Paste","Cloths","Mouthwase"};
        return prepareItemList(MyConstants.TECHNOLOGY_CAMEL_CASE,data);
    }
    public List<items> getHealthData(){
        String[] data= {"Tooth brush","Tooth Paste","Cloths","Mouthwase"};
        return prepareItemList(MyConstants. HEALTH_CAMEL_CASE,data);
    }
    public List<items> getFoodData(){
        String[] data= {"Tooth brush","Tooth Paste","Cloths","Mouthwase"};
        return prepareItemList(MyConstants.FOOD_CAMEL_CASE,data);
    }
    public List<items> getBeachSuppliesData(){
        String[] data= {"Tooth brush","Tooth Paste","Cloths","Mouthwase"};
        return prepareItemList(MyConstants.BEACH_SUPPLIES_CAMEL_CASE,data);
    }
    public List<items> getCarSuppliesData(){
        String[] data= {"Tooth brush","Tooth Paste","Cloths","Mouthwase"};
        return prepareItemList(MyConstants.CAR_SUPPLIES_CAMEL_CASE,data);
    }
    public List<items> getNeedsData(){
        String[] data= {"Tooth brush","Tooth Paste","Cloths","Mouthwase"};
        return prepareItemList(MyConstants.NEEDS_CAMEL_CASE,data);
    }
    public List<items> prepareItemList(String category,String[] data){
        List<String> list = Arrays.asList(data);
        List<items> dataList = new ArrayList<>();
        for(int i= 0; i<list.size();i++){
            dataList.add(new items(list.get(i),category,false));
        }
        return dataList;
    }

    public List<List<items>> getAllData(){
        List<List<items>> listOfAllItems = new ArrayList<>();
        listOfAllItems.add(getBasicData());
        listOfAllItems.add(getClothingData());
        listOfAllItems.add(getPersonalCareData());
        listOfAllItems.add(getBabyNeedsData());
        listOfAllItems.add(getHealthData());
        listOfAllItems.add(getTechnologyData());
        listOfAllItems.add(getFoodData());
        listOfAllItems.add(getBeachSuppliesData());
        listOfAllItems.add(getCarSuppliesData());
        listOfAllItems.add(getNeedsData());
        return listOfAllItems;
    }
    public void persistAlldata(){
        List<List<items>> listOfAllItems= getAllData();
        for (List<items> list: listOfAllItems){
            for (items item: list){
                database.mainDAO().saveItem(item);
            }
        }
        System.out.println("Data Added");
    }

    public void persistDataByCategory(String category, Boolean onlyDelete) {
            try {
                List<items> list = deleteAndGetListByCategory(category, onlyDelete);
                if (!onlyDelete) {
                    for (items item : list) {
                        database.mainDAO().saveItem(item);
                        Toast.makeText(context, category + " Reset Successfully.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                        Toast.makeText(context, category + " Reset Successfully.", Toast.LENGTH_SHORT).show();
                    }
            }catch(Exception ex) {
        ex.printStackTrace();
        Toast.makeText(context,"Something Went Wrong", Toast.LENGTH_SHORT).show();
    }
}
    private List<items> deleteAndGetListByCategory(String category, Boolean onlyDelete) {
        if (onlyDelete) {
            database.mainDAO().deleteAllByCategoryAndAddedBy(category, MyConstants.SYSTEM_SMALL);
        } else {
            database.mainDAO().deleteAllByCategory(category) ;
        }
        switch (category) {
            case MyConstants.BASIC_NEEDS_CAMEL_CASE:
                return getBasicData();
            case MyConstants.CLOTHING_CAMEL_CASE:
                return getClothingData();
            case MyConstants.PERSONAL_CARE_CAMEL_CASE:
                return getPersonalCareData();
            case MyConstants.BABY_NEEDS_CAMEL_CASE:
                return getBabyNeedsData();
            case MyConstants.HEALTH_CAMEL_CASE:
                return getHealthData();
            case MyConstants.TECHNOLOGY_CAMEL_CASE:
                return getTechnologyData();
            case MyConstants.FOOD_CAMEL_CASE:
                return getFoodData();
            case MyConstants.BEACH_SUPPLIES_CAMEL_CASE:
                return getBeachSuppliesData();
            case MyConstants.CAR_SUPPLIES_CAMEL_CASE:
                return getCarSuppliesData();
            case MyConstants.NEEDS_CAMEL_CASE:
                return getNeedsData();
            default:
                return new ArrayList<>();
        }
        }
}
