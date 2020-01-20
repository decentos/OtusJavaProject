package me.decentos;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class MyArrayListTest {
    public static void main(String[] args) {
        List<Integer> list1 = new MyArrayList<>();
        List<Integer> list2 = new MyArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            list1.add(random.nextInt(100));
            list2.add(random.nextInt(100));
        }

        System.out.println("MyArrayList: " + list1.toString());
        System.out.println("Size: " + list1.size());
        System.out.println("First element: " + list1.get(0));

        list1.set(0, 100);
        System.out.println("After set: " + list1.toString());

        list1.remove(29);
        list2.remove(29);
        System.out.println("List1 after remove: " + list1.toString());
        System.out.println("List2 after remove: " + list2.toString());
        System.out.println("Size after remove: " + list1.size());

        list1.addAll(list2);
        System.out.println("After addAll: " + list1.toString());
        System.out.println("Size after addAll: " + list1.size());

        list1.removeAll(list2);
        System.out.println("After removeAll: " + list1.toString());
        System.out.println("Size after removeAll: " + list1.size());

        System.out.println("=============================");

        Collections.copy(list2, list1);
        System.out.println("Collections.copy: " + list2.toString());
        Collections.addAll(list1, 555, 666, 777);
        System.out.println("Collections.addAll: " + list1.toString());
        Collections.sort(list1);
        System.out.println("Collections.sort: " + list1.toString());
    }
}
