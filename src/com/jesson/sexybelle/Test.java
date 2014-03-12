package com.jesson.sexybelle;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdi on 14-3-8.
 */
public class Test {

    public static void main(String[] args) {

        List<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");

        String json = new Gson().toJson(list);
        System.out.println("toJson:" + json);

        List<String> list2 = new Gson().fromJson(json, List.class);
        System.out.println("fromJson:" + list2);

    }
}
