package com.apex.bank.restSql;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateRestSqlUtil {


    public static String generateTbCloums(String tableCode){
        // var columnSrc={field:ffieldcode.toUpperCase(), title:ffieldname,ffieldtype:ffieldtype,align:falign };
        Gson gson=new Gson();
        List tab1=new ArrayList();

        for(int i=0;i<10;i++){
            Map mapCloum=new HashMap();
            mapCloum.put("field","username"+i);
            mapCloum.put("title","名称"+i);
            if(i%3==0){
                mapCloum.put("ffieldtype",1);//文本
            }else if(i%3==1){
                mapCloum.put("ffieldtype",2);//数值
            }else if(i%3==2){
                mapCloum.put("ffieldtype",3);//日期类型
            }
            tab1.add(mapCloum);

        }
        String cloums= gson.toJson(tab1);

        return cloums;
    }
}
