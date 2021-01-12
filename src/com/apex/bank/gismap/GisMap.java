package com.apex.bank.gismap;

import com.alibaba.fastjson.JSON;
import com.apex.bank.sftp.DB2Handle;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GisMap {


    public static void main(String[] args) {


        Connection connection = null;
        try {
            //Establish connection
            connection = DB2Handle.getConnection();


            String sql = "select FCODE from CPT_GisMap ";
            Statement ps = connection.createStatement();

            ps.executeQuery(sql);
            ResultSet rs = (ResultSet) ps.getResultSet();
            while (rs.next()) { // 当前记录指针移动到下一条记录上
                System.out.println(rs.getString("FCODE"));
                String code=rs.getString("FCODE");
                System.out.println(code);
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    GisPoint centerPoint=null;//new GisPoint(117.972872, 34.328991,0);//心坐标
    Map centerInfo=new HashMap();
    int zoomLevel=15;//地图展示级别
    //List<GisPoint> listPoint=new ArrayList<GisPoint>();
    Map lsitPointMap=new HashMap();
    int centerPointTypeNum=3;//坐标种类
    String code="PZ_Map";

    public GisMap(String code) {
        this.code=code;
    }


    public GisMap(GisPoint centerPoint, int zoomLevel) {
        this.centerPoint = centerPoint;
        this.zoomLevel = zoomLevel;
    }


    public GisMap(String name,double longitude, double latitude, int zoomLevel) {
        GisPoint centerPoint=new GisPoint(name,longitude,latitude,centerPointTypeNum);
        this.centerPoint = centerPoint;
        this.zoomLevel = zoomLevel;
    }

    public GisMap(String name,double longitude, double latitude, int zoomLevel,String code) {
        GisPoint centerPoint=new GisPoint(name,longitude,latitude,centerPointTypeNum);
        this.centerPoint = centerPoint;
        this.zoomLevel = zoomLevel;
    }


    public GisMap(String name,double longitude, double latitude, int zoomLevel,int centerPointType,String code) {
        GisPoint centerPoint=new GisPoint(name,longitude,latitude,centerPointType);
        this.centerPoint = centerPoint;
        this.zoomLevel = zoomLevel;
        this.code=code;
    }

/*    private void createMapPoints1(){

        for(int vType=0;vType<3;vType++){
            List<GisPoint> listPoint=new ArrayList<GisPoint>();
            //模拟数据创建
            int num=0;
            String  pinName="";
            if(vType==0){
                pinName="邳州支行";
                num=50;
            }else if(vType==1){
                pinName="村落";
                num=80;
            }else if(vType==2){
                pinName="助农服务点";
                num=500;
            }
            for (int i=0;i<num;i++){
                double vlongitude=centerPoint.longitude-0.2*Math.random();//经度
                double  vlatitude=centerPoint.latitude-0.2*Math.random();//纬度
                int  pointType=0;
                pointType=i%3;

                GisPoint point=new GisPoint(0,pinName,vlongitude,vlatitude,vType);
                Map info=new HashMap();

            }
            lsitPointMap.put(vType,getStrPointsJSON(listPoint));
        }

    }*/

    private void createMapPoints(){
        //select * from CPT_GISPOINT where FCODE=(SELECT ID FROM  CPT_GISMAP);
        Connection connection = null;
        ResultSet rs=null;
        Statement sta=null;
        try {

            connection = DB2Handle.getConnection();
            double longitude=0L;
            double latitude=0L;
            int vType=0;
            String name="";
            long CPT_GISMAP_ID=0L;


            /*创建初始化地图*/
            String sql = "SELECT ID ,FNAME, FCODE, FCENTERLONG, FCENTERLAT, FZOOM FROM  CPT_GISMAP where CPT_GISMAP.FCODE='"+code+"'";
            sta= connection.createStatement();
            sta.executeQuery(sql);
            rs = (ResultSet) sta.getResultSet();



            while (rs.next()) { // 当前记录指针移动到下一条记录上
                System.out.println(rs.getString("FCODE"));
                name=rs.getString("FNAME");
                setCode(rs.getString("FCODE"));
                //如果构造器没传以系统为主
                if(centerPoint==null){
                    longitude=rs.getDouble("FCENTERLONG");
                    latitude=rs.getDouble("FCENTERLAT");
                    setCenterPoint(new GisPoint(name,longitude,latitude,0));
                }
                setZoomLevel(rs.getInt("FZOOM"));
                CPT_GISMAP_ID=rs.getLong("ID");
            }

            /*创建初始化点*/
            Long pointId=0L;
            for (int i=0;i<centerPointTypeNum;i++){
                sql = "select ID, FPINTYPE, FNAME, FLONGITUDE, FLATITUDE, FLINK, FREMARK, FCODE from CPT_GISPOINT where FCODE= "+CPT_GISMAP_ID+" AND FPINTYPE="+i;
                sta = connection.createStatement();
                sta.executeQuery(sql);
                rs = (ResultSet) sta.getResultSet();
                List<GisPoint> listPoint=new ArrayList<GisPoint>();
                while (rs.next()) { // 当前记录指针移动到下一条记录上
                    //报错时候跳过异常构建下个点
                    try {

                        pointId=rs.getLong("ID");
                        longitude=rs.getDouble("FLONGITUDE");
                        latitude=rs.getDouble("FLATITUDE");
                        vType=rs.getInt("FPINTYPE");
                        name=rs.getString("FNAME");
                        GisPoint point=new GisPoint(pointId,name,longitude,latitude,vType);
                        listPoint.add(point);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //continue;
                    }
                }
                lsitPointMap.put(i,listPoint);
            }




        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                DB2Handle.close(connection,sta,rs);
            }
        }
    }

    private void createJsonMapPoints(){
        //select * from CPT_GISPOINT where FCODE=(SELECT ID FROM  CPT_GISMAP);
        Connection connection = null;
        ResultSet rs=null;
        Statement sta=null;
        try {

            connection = DB2Handle.getConnection();
            double longitude=0L;
            double latitude=0L;
            int vType=0;
            String name="";
            long CPT_GISMAP_ID=0L;


            /*创建初始化地图*/
            String sql = "SELECT ID ,FNAME, FCODE, FCENTERLONG, FCENTERLAT, FZOOM FROM  CPT_GISMAP where CPT_GISMAP.FCODE='"+code+"'";
            sta= connection.createStatement();
            sta.executeQuery(sql);
            rs = (ResultSet) sta.getResultSet();



            while (rs.next()) { // 当前记录指针移动到下一条记录上
                System.out.println(rs.getString("FCODE"));
                name=rs.getString("FNAME");
                setCode(rs.getString("FCODE"));
                //如果构造器没传以系统为主
                if(centerPoint==null){
                    longitude=rs.getDouble("FCENTERLONG");
                    latitude=rs.getDouble("FCENTERLAT");
                    setCenterPoint(new GisPoint(name,longitude,latitude,0));
                }
                setZoomLevel(rs.getInt("FZOOM"));
                CPT_GISMAP_ID=rs.getLong("ID");
            }

            /*创建初始化点*/
            Long pointId=0L;
            for (int i=0;i<centerPointTypeNum;i++){
                sql = "select ID, FPINTYPE, FNAME, FLONGITUDE, FLATITUDE, FLINK, FREMARK, FCODE from CPT_GISPOINT where FCODE= "+CPT_GISMAP_ID+" AND FPINTYPE="+i;
                sta = connection.createStatement();
                sta.executeQuery(sql);
                rs = (ResultSet) sta.getResultSet();
                List<GisPoint> listPoint=new ArrayList<GisPoint>();
                while (rs.next()) { // 当前记录指针移动到下一条记录上
                    //报错时候跳过异常构建下个点
                    try {

                        pointId=rs.getLong("ID");
                        longitude=rs.getDouble("FLONGITUDE");
                        latitude=rs.getDouble("FLATITUDE");
                        vType=rs.getInt("FPINTYPE");
                        name=rs.getString("FNAME");
                        GisPoint point=new GisPoint(pointId,name,longitude,latitude,vType);
                        listPoint.add(point);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //continue;
                    }
                }
                lsitPointMap.put(i,getStrPointsJSON(listPoint));
            }




        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                DB2Handle.close(connection,sta,rs);
            }
        }
    }




    public List createPoints(String mapcode,int pointType){
        //select * from CPT_GISPOINT where FCODE=(SELECT ID FROM  CPT_GISMAP);
        Connection connection = null;
        ResultSet rs=null;
        Statement sta=null;
        List<GisPoint> listPoint=new ArrayList<GisPoint>();
        try {

            connection = DB2Handle.getConnection();
            double longitude=0L;
            double latitude=0L;
            int vType=0;
            String name="";
            long CPT_GISMAP_ID=0L;


            /*创建初始化地图*/
            String sql = "SELECT ID ,FNAME, FCODE, FCENTERLONG, FCENTERLAT, FZOOM FROM  CPT_GISMAP where CPT_GISMAP.FCODE='"+mapcode+"'";
            sta= connection.createStatement();
            sta.executeQuery(sql);
            rs = (ResultSet) sta.getResultSet();



            while (rs.next()) { // 当前记录指针移动到下一条记录上
                System.out.println(rs.getString("FCODE"));
                name=rs.getString("FNAME");
                setCode(rs.getString("FCODE"));
                //如果构造器没传以系统为主
                if(centerPoint==null){
                    longitude=rs.getDouble("FCENTERLONG");
                    latitude=rs.getDouble("FCENTERLAT");
                    setCenterPoint(new GisPoint(name,longitude,latitude,0));
                }
                setZoomLevel(rs.getInt("FZOOM"));
                CPT_GISMAP_ID=rs.getLong("ID");
            }

            /*创建初始化点*/
            Long pointId=0L;
            sql = "select ID, FPINTYPE, FNAME, FLONGITUDE, FLATITUDE, FLINK, FREMARK, FCODE from CPT_GISPOINT where FCODE= "+CPT_GISMAP_ID+" AND FPINTYPE="+pointType;
            sta = connection.createStatement();
            sta.executeQuery(sql);
            rs = (ResultSet) sta.getResultSet();

            while (rs.next()) { // 当前记录指针移动到下一条记录上
                //报错时候跳过异常构建下个点
                try {

                    pointId=rs.getLong("ID");
                    longitude=rs.getDouble("FLONGITUDE");
                    latitude=rs.getDouble("FLATITUDE");
                    vType=rs.getInt("FPINTYPE");
                    name=rs.getString("FNAME");
                    GisPoint point=new GisPoint(pointId,name,longitude,latitude,vType);
                    listPoint.add(point);
                } catch (Exception e) {
                    e.printStackTrace();
                    //continue;
                }
            }

          return listPoint;

        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                DB2Handle.close(connection,sta,rs);
            }
        }
        return listPoint;
    }


    public GisPoint getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(GisPoint centerPoint) {
        this.centerPoint = centerPoint;
    }

    public int getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(int zoomLevel) {
        this.zoomLevel = zoomLevel;
    }


    public Map getLsitPointMap(int pointType) {
        createJsonMapPoints();
        return lsitPointMap;
    }

    public Map getAllLsitPointMap() {
        createMapPoints();
        return lsitPointMap;
    }

    public void setLsitPointMap(Map lsitPointMap) {
        this.lsitPointMap = lsitPointMap;
    }

    public String  getStrPointsJSON(List<GisPoint> listPoint) {
        return  JSON.toJSONString(listPoint);
    }

    public Map getCenterInfo() {
        return centerInfo;
    }

    public void setCenterInfo(Map centerInfo) {
        this.centerInfo = centerInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map getLsitPointMap() {
        createJsonMapPoints();
        return lsitPointMap;
    }

    public Map  getConutry(String countryName) {
        Connection connection = null;
        ResultSet rs=null;
        Statement sta=null;
        Map counryMap=new HashMap();

        try {
            connection = DB2Handle.getConnection();
            String name="";
            String fval="";
            /*创建初始化地图*/
            String sql = "select ID, FVILLAGE, FTOWN, FVILLAGECUSTNUM, FTOWNCUSTNUM,  FVILLAGEDOMICILE, FTOWNFULLNAME, FVILLAGEFULLNAME from CPT_TOWN where FVILLAGE ='" +countryName+"'";
            sta = connection.createStatement();
            sta.executeQuery(sql);
            rs = (ResultSet) sta.getResultSet();
            while (rs.next()) { // 当前记录指针移动到下一条记录上
                //报错时候跳过异常构建下个点
                try {
                    Map pointInfo=new HashMap();
                    String  countryid  =rs.getString("ID");
                    String  fvillage   =rs.getString("FVILLAGE");
                    String  ftown      =rs.getString("FTOWN");
                    String  fvillagecustnum  =rs.getString("FVILLAGECUSTNUM");
                    String  ftowncustnum     =rs.getString("FTOWNCUSTNUM");
                    String  fvillagedomicile =rs.getString("FVILLAGEDOMICILE");
                    String  ftownfullname    = rs.getString("FTOWNFULLNAME");
                    String  fvillagefullname =rs.getString("FVILLAGEFULLNAME");
                    counryMap.put("countryid",countryid);
                    counryMap.put("fvillage",fvillage);
                    counryMap.put("ftown",ftown);
                    counryMap.put("fvillagecustnum",fvillagecustnum);
                    counryMap.put("ftowncustnum",ftowncustnum);
                    counryMap.put("fvillagedomicile",fvillagedomicile);
                    counryMap.put("ftownfullname",ftownfullname);
                    counryMap.put("fvillagefullname",fvillagefullname);
                    return  counryMap;
                } catch (Exception e) {
                    e.printStackTrace();
                    //continue;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                DB2Handle.close(connection,sta,rs);
            }
        }
        return  counryMap;
    }
}
