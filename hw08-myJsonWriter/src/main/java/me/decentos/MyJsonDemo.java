package me.decentos;

import com.google.gson.Gson;
import me.decentos.myJsonWriter.MyJson;
import me.decentos.myJsonWriter.MyJsonImpl;

public class MyJsonDemo {
    public static void main(String[] args) {
        Gson gson = new Gson();
        MyJson myJson = new MyJsonImpl();

////        // Test Object:
//        BagOfPrimitives toJsonObj = new BagOfPrimitives(22, "test", 10);
//        System.out.println(toJsonObj);
//
//        String json = myJson.toJson(toJsonObj);
//        System.out.println(json);
//
//        Object fromJsonObj = gson.fromJson(json, BagOfPrimitives.class);
//        System.out.println(fromJsonObj);
//        System.out.println("Equals objects: " + toJsonObj.equals(fromJsonObj) + "\n");
//
//        // Test Array:
//        Integer[] arrayToJson = {34, 23, 41, 88, 5, 17};
//        System.out.println(Arrays.toString(arrayToJson));
//
//        String arrayJson = myJson.toJson(arrayToJson);
//        System.out.println(arrayJson);
//
//        Integer[] fromJsonArray = gson.fromJson(arrayJson, Integer[].class);
//        System.out.println(Arrays.toString(fromJsonArray));
//        System.out.println("Equals arrays: " + Arrays.equals(arrayToJson, fromJsonArray) + "\n");
//
//        // Test Collection:
//        List<String> listToJson = new ArrayList<>(Arrays.asList("First", "Second", "Third"));
//        System.out.println(listToJson);
//
//        String listJson = myJson.toJson(listToJson);
//        System.out.println(listJson);
//
//        List<?> fromJsonList = gson.fromJson(listJson, List.class);
//        System.out.println(fromJsonList);
//        System.out.println("Equals lists: " + listToJson.equals(fromJsonList) + "\n");

        // PR comments:
        System.out.println(myJson.toJson(null));
        System.out.println(gson.toJson(null));

        System.out.println(myJson.toJson((byte)1));
        System.out.println(gson.toJson((byte)1));

        System.out.println(myJson.toJson((short)2f));
        System.out.println(gson.toJson((short)2f));

        System.out.println(myJson.toJson(3));
        System.out.println(gson.toJson(3));

        System.out.println(myJson.toJson(4L));
        System.out.println(gson.toJson(4L));

        System.out.println(myJson.toJson(5f));
        System.out.println(gson.toJson(5f));

        System.out.println(myJson.toJson(6d));
        System.out.println(gson.toJson(6d));

        System.out.println(myJson.toJson("aaa"));
        System.out.println(gson.toJson("aaa"));

        System.out.println(myJson.toJson(true));
        System.out.println(gson.toJson(true));

        System.out.println(myJson.toJson('b'));
        System.out.println(gson.toJson('b'));

    }
}
