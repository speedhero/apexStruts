package com.apex.bank.link;


import com.apex.form.DataAccess;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.*;

public class LinkGuideAction  extends DispatchAction {

    //分类导航
    public ActionForward getLinkAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/json;charset=utf-8");//注意设置为json，如果为xml，则设为xml
        response.setCharacterEncoding("utf-8");
        PrintWriter pw=response.getWriter();

        Connection connection = null;
        try {
            //Establish connection
            connection = DataAccess.getDataSource().getConnection();
           // connection = DB2Handle.getConnection();
            List<Map<String, Object>> urlList = new ArrayList<Map<String, Object>>();
            String sql = "select a.ID, FADDRTYPE,b.FNAME , a.FICON, FAPPNAME, FURLLINK, FUSERNAMERULE, FINITPWD,  FDESCRIPTION,FPUB_SYSTEM,c.FCODE,c.FWELCOMEURL\n" +
                    "from PUB_AddressLink a inner join PUB_AddressType b on  a.FADDRTYPE=b.ID    left join PUB_SYSTEM c  on    a.FPUB_SYSTEM=c.ID";
            Statement ps = connection.createStatement();

            ps.executeQuery(sql);
            ResultSet rs = (ResultSet) ps.getResultSet();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                Map<String, Object> rowData = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                urlList.add(rowData);
            }


            List<String> addressNameList=new ArrayList();
           // Map infoMap=new HashMap();
            for(int i=0;i<urlList.size();i++){
                Map<String, Object>  urlMap=urlList.get(i);
               if(!addressNameList.contains(urlMap.get("FNAME"))){
                   addressNameList.add(urlMap.get("FNAME").toString());
               }
            }

            List urloutList=new ArrayList();
            for(int i=0;i<addressNameList.size();i++){
                String tempName=addressNameList.get(i);
                Map tiles=new HashMap();

                List links=new ArrayList();
                for(int j=0;j<urlList.size();j++){
                    Map<String, Object>  urlMap=urlList.get(j);
                    if(urlMap.get("FNAME").equals(tempName)){

                        links.add(urlMap);
                    }
                }
                tiles.put("title",tempName);
                tiles.put("info",links);
                tiles.put("cnt",links.size());
                urloutList.add(tiles);
            }
            Collections.sort(urloutList, new Comparator<Map>() {

                @Override
                public int compare(Map o1, Map o2) {
                    int i = (Integer)o2.get("cnt")-(Integer)o1.get("cnt") ;//降序
                    return i;
                }
            });


            String string = new Gson().toJson(urloutList);
            JsonArray jsonArray = new JsonParser().parse(string).getAsJsonArray();
            PrintWriter out=response.getWriter();
            System.out.println(jsonArray);
            out.print(jsonArray);
            out.flush();
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
        return null;


    }

   //头寸数据
    public ActionForward getBusiPosition(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/json;charset=utf-8");//注意设置为json，如果为xml，则设为xml
        response.setCharacterEncoding("utf-8");
        PrintWriter pw=response.getWriter();

        Connection connection = null;
        try {
            //Establish connection
            connection = DataAccess.getDataSource().getConnection();
            // connection = DB2Handle.getConnection();
            List<Map<String, Object>> urlList = new ArrayList<Map<String, Object>>();
            String sql = "select a.ID, FADDRTYPE,b.FNAME , a.FICON, FAPPNAME, FURLLINK, FUSERNAMERULE, FINITPWD,  FDESCRIPTION,FPUB_SYSTEM,c.FCODE,c.FWELCOMEURL\n" +
                    "from PUB_AddressLink a inner join PUB_AddressType b on  a.FADDRTYPE=b.ID    left join PUB_SYSTEM c  on    a.FPUB_SYSTEM=c.ID";
            Statement ps = connection.createStatement();

            ps.executeQuery(sql);
            ResultSet rs = (ResultSet) ps.getResultSet();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                Map<String, Object> rowData = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                urlList.add(rowData);
            }




            String string = new Gson().toJson(urlList);
            JsonArray jsonArray = new JsonParser().parse(string).getAsJsonArray();
            PrintWriter out=response.getWriter();
            System.out.println(jsonArray);
            out.print(jsonArray);
            out.flush();
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
        return null;




    }

}
