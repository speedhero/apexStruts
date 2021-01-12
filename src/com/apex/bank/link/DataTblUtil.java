package com.apex.bank.link;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTblUtil {

    //获得excel输出列
    public static String getCloumsHead(int num){
        String[] array = new String[] { "A", "B", "C", "D", "E", "F", "G", "H","I", "J", "K", "L", "M", "N", "O", "P", "Q",
                "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
        int count = 26;
        System.out.println("num/count=" + num/count);
        String out = "";
        if (num/count != 0) {
            out = array[num/count-1];
            if (num%count == 0) {
                out = out + array[num%count-1];
                System.out.println(out);
            } else {
                out = out + array[num%count-1];
                System.out.println(out);
            }
        }else if (num%count != 0) {
            out = out + array[num%count-1];
            System.out.println(out);
        }
        return out;
    }

    //获得excel输出列
    public static Map getCloumsMap(List<Map<String, Object>> fieldList,String filedKey){
        Map filed=new HashMap();
        for(int i=0;i<fieldList.size();i++){
            filed.put(fieldList.get(i).get(filedKey),getCloumsHead(i+1));
        }
        return filed;
    }

    public static void main(String[] args) {
        List<Map<String, Object>> urlList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1=new HashMap<>();
        map1.put("FFIELDCODE","id");
        map1.put("FFIELDNAME","ID");
        urlList.add(map1);
        Map<String, Object> map2=new HashMap<>();
        map2.put("FFIELDCODE","username");
        map2.put("FFIELDNAME","用户名");
        urlList.add(map2);
        Map<String, Object> map3=new HashMap<>();
        map3.put("FFIELDCODE","email");
        map3.put("FFIELDNAME","邮箱");
        urlList.add(map3);
        Map<String, Object> map4=new HashMap<>();
        map4.put("FFIELDCODE","sex");
        map4.put("FFIELDNAME","性别");
        urlList.add(map4);
        Map<String, Object> map5=new HashMap<>();
        map5.put("FFIELDCODE","city");
        map5.put("FFIELDNAME","城市");
        urlList.add(map5);
       String  jsonstring = new Gson().toJson( DataTblUtil.getCloumsMap(urlList,"FFIELDCODE"));
        System.out.println(jsonstring);

    }

}
