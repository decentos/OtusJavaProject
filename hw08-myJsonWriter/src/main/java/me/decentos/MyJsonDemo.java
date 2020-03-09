package me.decentos;

import com.google.gson.Gson;
import me.decentos.myJsonWriter.MyJson;
import me.decentos.myJsonWriter.MyJsonImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyJsonDemo {
    public static void main(String[] args) {
        Gson gson = new Gson();
        MyJson myJson = new MyJsonImpl();

//        // Test Object:
        BagOfPrimitives toJsonObj = new BagOfPrimitives(22, "test", 10);
        System.out.println(toJsonObj);

        String json = myJson.toJson(toJsonObj);
        System.out.println(json);

        Object fromJsonObj = gson.fromJson(json, BagOfPrimitives.class);
        System.out.println(fromJsonObj);
        System.out.println("Equals objects: " + toJsonObj.equals(fromJsonObj) + "\n");

        // Test Array:
        Integer[] arrayToJson = {34, 23, 41, 88, 5, 17};
        System.out.println(Arrays.toString(arrayToJson));

        String arrayJson = myJson.toJson(arrayToJson);
        System.out.println(arrayJson);

        Integer[] fromJsonArray = gson.fromJson(arrayJson, Integer[].class);
        System.out.println(Arrays.toString(fromJsonArray));
        System.out.println("Equals arrays: " + Arrays.equals(arrayToJson, fromJsonArray) + "\n");

        // Test Collection:
        List<String> listToJson = new ArrayList<>(Arrays.asList("First", "Second", "Third"));
        System.out.println(listToJson);

        String listJson = myJson.toJson(listToJson);
        System.out.println(listJson);

        List<?> fromJsonList = gson.fromJson(listJson, List.class);
        System.out.println(fromJsonList);
        System.out.println("Equals lists: " + listToJson.equals(fromJsonList) + "\n");
    }
}
