package com.apex.bank.link;

import com.apex.bank.sftp.DB2Handle;
import com.apex.form.DataAccess;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

public class ComDisplatAction  extends DispatchAction {



    public ActionForward getDispalyData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");//注意设置为json，如果为xml，则设为xml
        response.setCharacterEncoding("utf-8");
        PrintWriter pw=response.getWriter();
        String tableCode= request.getParameter("tableCode");
        //String colums= request.getParameter("colums");
        String[] colums= request.getParameterMap().get("colums[]")==null?null:(String[])request.getParameterMap().get("colums[]");
        String jsonCond= request.getParameterMap().get("list")==null?null:(request.getParameter("list"));
        String combingStr=request.getParameterMap().get("combingStr")==null?"AND":(request.getParameter("combingStr"));//获取关联条件 AND OR
        Gson gson=new Gson();
        List<Map> queryConds=null;
        String sqlCond=" ";
        if(jsonCond!=null&&jsonCond!=""){
            queryConds = gson.fromJson(jsonCond,new TypeToken<List<Map>>() {}.getType());
            for(int i=0;i<queryConds.size();i++){
                Map condMap=queryConds.get(i);
                String[] filedPro= condMap.get("field").toString().split(":");
                String field=filedPro[0];
                String fieldEditType=filedPro[1];
                sqlCond=sqlCond+" "+combingStr+" "+field+" "+condMap.get("type").toString()+" ";
                //1|文本类型;2|数值类型;3|日期类型;4|大数据类型;{
                 if("1".equalsIgnoreCase(fieldEditType)) {
                     sqlCond=sqlCond+" '"+ condMap.get("value").toString()+"' ";
                 }else if("2".equalsIgnoreCase(fieldEditType)) {
                     sqlCond=sqlCond+condMap.get("value").toString();
                 }else if("3".equalsIgnoreCase(fieldEditType)) {
                     sqlCond=sqlCond+" '" +condMap.get("value").toString()+"' ";
                 }else if("4".equalsIgnoreCase(fieldEditType)) {
                     sqlCond=sqlCond+" '" +condMap.get("value").toString()+"' ";
                 }


            }
        }

        int curPage=Integer.parseInt(request.getParameter("page")==null?"0":request.getParameter("page"));
        int limit=Integer.parseInt(request.getParameter("limit")==null?"10":request.getParameter("limit"));
        int firstIndex = (curPage - 1) * limit+1;
           // 到第几条数据结束
        int lastIndex = curPage * limit;


        int  code=0;
        String msg="";
        int count=0;
        List data=new ArrayList();
        Map retMap=new HashMap();
        retMap.put("code",code);
        retMap.put("msg",msg);
        retMap.put("data",data);
 /*       if(colums.length>0){
            String sqlcount="select  count(*) from  ";
            String sqlsort="select ";
            String sqliInner = "select  row_number() over () as ROWNUM," ;
            for(String colum:colums){
                sqliInner=sqliInner+" "+colum+" ,";
                sqlsort=sqlsort+" "+colum+" ,";
            }

            sqlcount=sqlcount+"  "+tableCode+"  where 1=1 ";

            sqliInner=sqliInner.substring(0,sqliInner.lastIndexOf(","));
            sqliInner=sqliInner+"  from "+tableCode+"  where 1=1 "+sqlCond;

            sqlsort=sqlsort.substring(0,sqlsort.lastIndexOf(","));
            sqlsort=sqlsort+"  from ("+sqliInner+" ) where  ROWNUM between "+ firstIndex+" and  "+lastIndex;

            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            try {

                JdbcTemplate jdbcTemplate = new JdbcTemplate(DB2Handle.getDataSource());
                dataList=  jdbcTemplate.queryForList(sqlsort);
                retMap.put("data",dataList);
                count=jdbcTemplate.queryForInt(sqlcount);

            }catch (Exception e) {
                e.printStackTrace();
                retMap.put("code",0);
                retMap.put("msg","查询出错");
            }finally{
                retMap.put("count",count);
                retMap.put("code",1);
                retMap.put("msg","查询成功");

            }
        }*/
        List<Map<String, Object>> urlList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1=new HashMap<>();
        map1.put("ID","10001");
        map1.put("USERNAME","杜甫");
        map1.put("EMAIL","xianxin@layui.com");
        map1.put("SEX","男");
        map1.put("CITY","浙江杭州");
        Map<String, Object> map2=new HashMap<>();
        map2.put("ID","10001");
        map2.put("USERNAME","杜甫");
        map2.put("EMAIL","xianxin@layui.com");
        map2.put("SEX","男");
        map2.put("CITY","浙江杭州");

        urlList.add(map1);
        urlList.add(map2);
        retMap.put("data",urlList);
        String string = new Gson().toJson(retMap);
        // JsonArray jsonArray = new JsonParser().parse(string).getAsJsonArray();
        PrintWriter out=response.getWriter();
        System.out.println(string);
        out.print(string);
        out.flush();
        return null;
    }


    public ActionForward importExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json;charset=utf-8");//注意设置为json，如果为xml，则设为xml
            int  code=0;
            String msg="";
            int count=3000;
            Map retMap=new HashMap();
            retMap.put("code",code);
            retMap.put("msg",code);
            retMap.put("count",count);
            retMap.put("data",null);
            PrintWriter pw=response.getWriter();
            String[] colums= request.getParameterMap().get("colums[]")==null?null:(String[])request.getParameterMap().get("colums[]");
            String tableCode= request.getParameter("tableCode");
            String data= request.getParameter("data") ;
            Gson gson=new Gson();
            List datalist = gson.fromJson(data, new TypeToken<List>(){}.getType());
            if(data!=null&&data!=""){
                if(colums.length>0){


                    try {

                        System.out.println(datalist);
                        //String sql = "INSERT INTO tableCode(`name`,`area`) VALUES (?,?)";
                        JdbcTemplate jdbcTemplate = new JdbcTemplate(DB2Handle.getDataSource());
                        String queryCondition="select FFieldCode,FFieldType from  Exa_TableField where  FTABLE='"+tableCode+"' AND UPPER(FFieldCode) in (";
                        for(String colum:colums){
                          queryCondition=queryCondition+"'"+colum+"',";
                        }
                        queryCondition=queryCondition.substring(0,queryCondition.lastIndexOf(","));
                        queryCondition=queryCondition+")";
                        List<Map<String,Object>> columInfos= jdbcTemplate.queryForList(queryCondition);

                        String sqlFiled="";
                        String sqlValue="";
                        String sqliInner = "INSERT INTO "+tableCode+"(" ;
                       for(int j=0;j<datalist.size();j++){

                               for(Map columInfo:columInfos){
                                   String field    =  columInfo.get("FFieldCode").toString();
                                   String editType = columInfo.get("FFieldType").toString();

                                   sqlFiled=sqlFiled+" "+field+" ,";

                                   //1|文本类型;2|数值类型;3|日期类型;4|大数据类型;{
                                   if("1".equalsIgnoreCase(editType)) {
                                       sqlValue=sqlValue+" '"+((Map)datalist.get(j)).get(field).toString()+"' ,";
                                   }else if("2".equalsIgnoreCase(editType)) {
                                       sqlValue=sqlValue+" "+((Map)datalist.get(j)).get(field).toString()+" ,";
                                   }else if("3".equalsIgnoreCase(editType)) {
                                       sqlValue=sqlValue+" '"+((Map)datalist.get(j)).get(field).toString()+"' ,";
                                   }
                               }
                           sqlFiled=sqlFiled.substring(0,sqlFiled.lastIndexOf(","));
                           sqlValue=sqlValue.substring(0,sqlValue.lastIndexOf(","));
                           sqliInner=sqliInner+sqlFiled+") VALUES ("+ sqlValue+")";
                           jdbcTemplate.update(sqliInner);
                           sqlFiled="";
                           sqlValue="";
                           sqliInner = "INSERT INTO "+tableCode+"(" ;
                       }

               /*         jdbcTemplate.batchUpdate(sqliInner,
                                new BatchPreparedStatementSetter() {
                                    @Override
                                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                                        for(Map columInfo:columInfos){
                                           String field    =  columInfo.get("FFieldCode").toString();
                                           String editType = columInfo.get("FFieldType").toString();
                                            //1|文本类型;2|数值类型;3|日期类型;4|大数据类型;{
                                            if("1".equalsIgnoreCase(editType)) {
                                                ps.setString(1, ((Map)datalist.get(i)).get(field).toString());
                                            }else if("2".equalsIgnoreCase(editType)) {
                                                ps.setLong(1,Long.parseLong( ((Map)datalist.get(i)).get(field).toString()));
                                            }else if("3".equalsIgnoreCase(editType)) {
                                                ps.setString(1,  ((Map)datalist.get(i)).get(field).toString());
                                            }
                                        }
                                    }

                                    @Override
                                    public int getBatchSize() {
                                        return datalist.size();
                                    }
                                });*/
                    }catch (Exception e) {
                        e.printStackTrace();
                        retMap.put("code",0);
                        retMap.put("msg","查询出错");
                    }finally{
                        retMap.put("count",count);
                        retMap.put("code",1);
                        retMap.put("msg","插入成功");
                        String string = new Gson().toJson(retMap);
                        // JsonArray jsonArray = new JsonParser().parse(string).getAsJsonArray();
                        PrintWriter out=response.getWriter();
                        System.out.println(string);
                        out.print(string);
                        out.flush();
                    }
                }

            }
            return null;
    }
}
