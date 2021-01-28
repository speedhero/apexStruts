package com.apex.bank.util;

import java.util.*;

public class MapUtil {
    /**
     * 遍历Map集合,将所有的Key值转为小写
     * @author liqs
     * @param Map<String , Object>
     * @return Map<String , Object>
     */
    public static  Map<String , Object> getMapLowerCase(Map<String , Object> map) {

        Map<String , Object> newMap =  new HashMap<String, Object>();
        Set set = map.keySet();

        for (Iterator it = set.iterator(); it.hasNext();) {
            String key = (String) it.next();
            Object value = map.get(key);
            newMap.put(key.toLowerCase(), value);
        }


        return newMap;
    }


    /**
     * 遍历Map集合,将所有的Key值转为大写
     * @author liqs
     * @param Map<String , Object>
     * @return Map<String , Object>
     */
    public static  Map<String , Object> getMapUpperCase(Map<String , Object> map) {

        Map<String , Object> newMap =  new HashMap<String, Object>();
        Set set = map.keySet();

        for (Iterator it = set.iterator(); it.hasNext();) {
            String key = (String) it.next();
            Object value = map.get(key);
            newMap.put(key.toUpperCase(), value);
        }


        return newMap;
    }




    /**
     * 遍历Map集合,替换单个Key值
     * @author liqs
     * @param Map<String , Object> 待替换的Map
     * @param keyName  Key值
     * @param renameKeyName 替换key值
     * @return Map<String , Object>
     */
    public static  Map<String , Object> getMapChangeKey(Map<String , Object> map,String keyName,String renameKeyName) {

        Map<String , Object> newMap =  new HashMap<String, Object>();
        Set set = map.keySet();

        for (Iterator it = set.iterator(); it.hasNext();) {
            String key = (String) it.next();
            Object value = map.get(key);
            if (keyName.trim().equals(key)) {
                key=renameKeyName;
            }
            newMap.put(key.toLowerCase(), value);
        }


        return newMap;
    }


    /**
     * 遍历Map集合,替换单个Key值
     * @author liqs
     * @param Map<String , Object> 待替换的Map
     * @param keyName  Key值
     * @param renameKeyName 替换key值
     * @return Map<String , Object>  key没有转换小写
     */
    public static  Map<String , Object> getMapChangeKeyNoLowerCase(Map<String , Object> map,String keyName,String renameKeyName) {

        Map<String , Object> newMap =  new HashMap<String, Object>();
        Set set = map.keySet();

        for (Iterator it = set.iterator(); it.hasNext();) {
            String key = (String) it.next();
            Object value = map.get(key);
            if (keyName.trim().equals(key)) {
                key=renameKeyName;
            }
            newMap.put(key, value);
        }


        return newMap;
    }

    /**
     * 遍历Map集合,替换单个Key值
     * @author liqs
     * @param Map<String , Object> 待替换的Map
     * @param Map<key值 , 替换Key值 > reMap
     * @return Map<String , Object>
     */
    public static  Map<String , Object> getMapChangeKey(Map<String , Object> map,Map<String , String > reMap) {

        Map<String , Object> newMap =  new HashMap<String, Object>();
        Set set = map.keySet();

        for (Iterator it = set.iterator(); it.hasNext();) {
            String key = (String) it.next();
            Object value = map.get(key);
            String reName=reMap.get(key)+"".trim();
            if (!reName.equals("null")) {
                key=reName;
            }
            newMap.put(key.toLowerCase(), value);
        }


        return newMap;
    }


    /**
     * 获取Map中的List
     * @param Map<String, Object>
     * @return  List
     */
    public static List getList (Map<String, Object> map) {

        List list = new ArrayList();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            Object type = (Object) entry.getValue();
            list.add(type);
        }
        return list;
    }

    /**
     * 获取Map中指定的Map
     * @param map
     * @param key K值
     * @return value值转Map
     */
    public static Map<String, Object> getKeyMap (Map<String, Object> map,String key){

        Map<String, Object> newMap = new HashMap<String, Object>();


        List list = new ArrayList();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if (key.equals(entry.getKey().toString())) {

                Object object =entry.getValue();
                if(object instanceof List || object instanceof ArrayList ){
                    while (object instanceof List || object instanceof ArrayList ){
                        list=(List) object;
                        object=list.get(0);
                    }
                    list.add(object);
                }else {
                    list.add(entry.getValue());
                }

            }

        }
        if (list.size()>0) {
            newMap=(Map<String, Object>) list.get(0);
        }else {
            newMap=null;
        }


        return newMap;
    }

    /**
     * 获取Map中指定的Map
     * @param map
     * @param key K值
     * @return value值转Map
     */
    public static List  getListKeyList (Map<String, Object> map,String key){

        List list = new ArrayList();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if (key.equals(entry.getKey().toString())) {

                Object object =entry.getValue();
                if(object instanceof List || object instanceof ArrayList ){
                    while (object instanceof List || object instanceof ArrayList ){
                        list=(List) object;
                        object=list.get(0);
                    }
                    //    list.add(object);
                }else {
                    list.add(entry.getValue());
                }

            }

        }



        return list;
    }


    /**
     * 获取Map中指定的List
     * @param map
     * @param key K值
     * @return value值转List
     */
    public static List getMapList (Map<String, Object> map,String key){

        List list = new ArrayList();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if (key.equals(entry.getKey().toString())) {
                Object object=entry.getValue();
                if(object instanceof HashMap ){
                    list.add((Map<String, Object>)entry.getValue());
                }else if(object instanceof List){
                    list=(List) entry.getValue();
                }

            }

        }
        if (list.size()==0) {
            list=null;
        }


        return list;
    }
    /**
     * 获取Map中指定的List
     * @param map
     * @param key K值
     * @return value值转List
     */
    public static String getKeyString (Map<String, Object> map,String key){

        String value = null;
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if (key.equals(entry.getKey().toString())) {
                value = (String) entry.getValue();

            }

        }


        return value;
    }

    public static String getKeyString (Map<String, Object> map,String key,String defaultValue){

        String value = null;
        Object val= map.get(key);
        if(val==null){
            value=defaultValue;
            return value;
        }
        if("".equalsIgnoreCase(val.toString())){
            value=defaultValue;
            return value;
        }
        value=map.get(key).toString();
        return value;
    }


    public static Integer getKeyInt(Map<String, Object> map,String key,int defaultValue){

        Integer value = 0;
        Object val= map.get(key);
        if(val==null){
            value=defaultValue;
            return value;
        }
        if("".equalsIgnoreCase(val.toString())){
            value=defaultValue;
            return value;
        }
        value=Integer.parseInt(map.get(key).toString());
        return value;
    }

    public static Long getKeyLong (Map<String, Object> map,String key,Long defaultValue){

        Long value = 0L;
        Object val= map.get(key);
        if(val==null){
            value=defaultValue;
            return value;
        }
        if("".equalsIgnoreCase(val.toString())){
            value=defaultValue;
            return value;
        }
        value=Long.parseLong(map.get(key).toString());
        return value;
    }
    public static Double getKeyDouble (Map<String, Object> map,String key,Double defaultValue){

        Double value = 0d;
        Object val= map.get(key);
        if(val==null){
            value=defaultValue;
            return value;
        }
        if("".equalsIgnoreCase(val.toString())){
            value=defaultValue;
            return value;
        }
        value=Double.parseDouble(map.get(key).toString());
        return value;
    }

    /**
     * 获取Map中指定的List中的第一个Map
     * @param Map<String, Object>
     * @return  Map<String, Object>
     */
    public static Map<String, Object> getKeyList (Map<String, Object> map,String key) {

        List list = new ArrayList();
        Map<String, Object> result = new HashMap<String, Object>();


        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if(key.equals(entry.getKey())){
                Object object = entry.getValue();
                if (object instanceof List) {
                    list = (List) object;
                    if (list.size()>0) {
                        result=(Map<String, Object>) list.get(0);
                    }
                }else {
                    result= (Map<String, Object>) object;
                }


            }

        }
        return result;
    }

    /**
     * 合并list中的Map为一个Map
     * @param list  list 中 Map的K值是唯一的
     * @return  Map<String, Object> String为小写
     */
    public static Map<String, Object> getListToMap (List list) {

        Map<String, Object> result = new HashMap<String, Object>();
        if (list!=null && list.size()>0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String,Object> map =(Map<String, Object>) list.get(i);
                Set set = map.keySet();

                for (Iterator it = set.iterator(); it.hasNext();) {
                    String key = (String) it.next();
                    Object value = map.get(key);
                    result.put(key.toLowerCase(), value);
                }
            }
        }else{
            System.out.println("传入list为null或list的大小小于1");
        }


        return result;
    }
}
