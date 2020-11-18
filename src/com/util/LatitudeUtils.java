package com.util;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LatitudeUtils {
    public static final String KEY_1 = "1YERxoKdUYizFt6ahiUXXKW3Mnq3uSie";//"自己的AK";

    public static Map getGeocoderLatitude(String address) {
        BufferedReader in = null;
        try {
            // 将地址转换成utf-8的16进制
            address = URLEncoder.encode(address, "UTF-8");
            //http://api.map.baidu.com/geocoding/v3/?address=北京市海淀区上地十街10号&output=json&ak=您的ak&callback=showLocation
            //URL tirc = new URL("http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=" + KEY_1);
            String url="http://api.map.baidu.com/geocoding/v3/?address=" + address + "&output=json&ak=" + KEY_1;
            URL tirc = new URL(url);
            in = new BufferedReader(new InputStreamReader(tirc.openStream(), "UTF-8"));
            String res;
            StringBuilder sb = new StringBuilder("");
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            String str = sb.toString();
            Map map = null;
            if (StringUtils.isNotEmpty(str)) {
                int lngStart = str.indexOf("lng\":");
                int lngEnd = str.indexOf(",\"lat");
                int latEnd = str.indexOf("},\"precise");
                if (lngStart > 0 && lngEnd > 0 && latEnd > 0) {
                    String lng = str.substring(lngStart + 5, lngEnd);
                    String lat = str.substring(lngEnd + 7, latEnd);
                    map = new HashMap();
                    map.put("lng", lng);
                    map.put("lat", lat);
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static Map getLocattion(String lng,String lat){
        BufferedReader in = null;
        try {
            // 将地址转换成utf-8的16进制

            //http://api.map.baidu.com/geocoding/v3/?address=北京市海淀区上地十街10号&output=json&ak=您的ak&callback=showLocation
            //URL tirc = new URL("http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=" + KEY_1);
            String url="http://api.map.baidu.com/reverse_geocoding/v3/?ak="+KEY_1+"&output=json&coordtype=wgs84ll&location="+lng+","+lat+" ";
            URL tirc = new URL(url);
            in = new BufferedReader(new InputStreamReader(tirc.openStream(), "UTF-8"));
            String res;
            StringBuilder sb = new StringBuilder("");
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            String str = sb.toString();
            Map map = null;
            if (StringUtils.isNotEmpty(str)) {
                int addrStart = str.indexOf("formatted_address\":");
                int addrEnd = str.indexOf(",\"business");
                if (addrStart > 0 && addrEnd > 0 ) {
                    String formatted_address = str.substring(addrStart , addrEnd);
                    map = new HashMap();
                    map.put("formatted_address", formatted_address);
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        //Map map = getGeocoderLatitude("北京市海淀区上地十街十号");
        //System.out.println(map.get("lng")+","+map.get("lat"));

        Map map = getLocattion("40.05703033345938","116.3084202915042");
        System.out.println(map.get("formatted_address"));

    }

}
