package com.apex.bank.gismap;


import com.apex.bank.sftp.DB2Handle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GisPoint {


    double longitude=0L;//经度
    double  latitude=0L;//纬度
    int  pointType=0;//定义地图标记点分类 支行分布点，村落分布点,助农分布点
    long id=0l;
    String name="";

    List pointInfos = null;//存储坐标信息

    public GisPoint() {
    }

    public GisPoint(String name, double longitude, double latitude, int pointType) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.pointType = pointType;
    }


    public GisPoint(long id, String name, double longitude, double latitude, int pointType) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.pointType = pointType;
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getPointType() {
        return pointType;
    }

    public void setPointType(int pointType) {
        this.pointType = pointType;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private List createPointInfo(Long id, String fmodule) {

        Connection connection = null;
        ResultSet rs = null;
        Statement sta = null;
        try {

            connection = DB2Handle.getConnection();
            pointInfos = new ArrayList();
            String name = "";
            String fval = "";
            /*创建初始化地图*/
            String sql = "select *  from CPT_PointInfo where FPinCode="
                    + id + " and FMODULE='" + fmodule + "'";
            sta = connection.createStatement();
            sta.executeQuery(sql);
            rs = (ResultSet) sta.getResultSet();
            while (rs.next()) { // 当前记录指针移动到下一条记录上
                //报错时候跳过异常构建下个点
                try {
                    Map pointInfo = new HashMap();
                    name = rs.getString("FNAME");
                    fval = rs.getString("FVAL");
                    pointInfo.put("name", name);
                    pointInfo.put("value", fval);
                    pointInfos.add(pointInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    //continue;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                System.out.println("Connected successfully.");
                DB2Handle.close(connection, sta, rs);
            }
        }
        return pointInfos;
    }


    public List getPointInfos(Long pointId, String fmoduel) {
        createPointInfo(pointId, fmoduel);
   /*     pointInfos=new ArrayList();
        Map pointInfo=new HashMap();
        pointInfo.put("name","授信额度");
        pointInfo.put("value",80);
        Map pointInfo2=new HashMap();
        pointInfo2.put("name","授信额度2");
        pointInfo2.put("value",800);
        Map pointInfo3=new HashMap();
        pointInfo3.put("name","授信额度3");
        pointInfo3.put("value",500);
        pointInfos.add(pointInfo);
        pointInfos.add(pointInfo2);
        pointInfos.add(pointInfo3);*/
        return pointInfos;
    }

    public void setPointInfo(List pointInfo) {
        this.pointInfos = pointInfo;
    }


}
