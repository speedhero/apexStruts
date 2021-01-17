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
                if(condMap!=null&condMap.size()>0){
                    String[] filedPro= condMap.get("field").toString().split(":");
                    if("".equalsIgnoreCase(condMap.get("field").toString())||"".equalsIgnoreCase(condMap.get("type").toString())||"".equalsIgnoreCase(condMap.get("value").toString())){
                         continue;
                    }
                    String field=filedPro[0];
                    String fieldEditType=filedPro[1];
                    sqlCond=sqlCond+" "+combingStr+" "+field+" "+condMap.get("type").toString()+" ";
                    //1|文本类型;2|数值类型;3|日期类型;4|大数据类型;{
                    if("1".equalsIgnoreCase(fieldEditType)) {
                        if("like".equalsIgnoreCase(condMap.get("type").toString())){
                            sqlCond=sqlCond+" '%"+ condMap.get("value").toString()+"%' ";
                        }else{
                            sqlCond=sqlCond+" '"+ condMap.get("value").toString()+"' ";
                        }

                    }else if("2".equalsIgnoreCase(fieldEditType)) {
                        sqlCond=sqlCond+condMap.get("value").toString();
                    }else if("3".equalsIgnoreCase(fieldEditType)) {
                        sqlCond=sqlCond+" '" +condMap.get("value").toString()+"' ";
                    }else if("4".equalsIgnoreCase(fieldEditType)) {
                        sqlCond=sqlCond+" '" +condMap.get("value").toString()+"' ";
                    }
                }
            }
        }

        int curPage=Integer.parseInt(request.getParameter("page")==null?"0":request.getParameter("page"));
        int limit=Integer.parseInt(request.getParameter("limit")==null?"10":request.getParameter("limit"));
        int isAll=Integer.parseInt(request.getParameter("isAll")==null?"10":request.getParameter("isAll"));
        int firstIndex = (curPage - 1) * limit+1;
           // 到第几条数据结束
        int lastIndex = curPage * limit;


        int  code=1;//1失败，0成功
        String msg="";
        int count=0;
        List data=new ArrayList();
        Map retMap=new HashMap();
        retMap.put("code",code);
        retMap.put("msg",msg);
        retMap.put("data",data);
        if(colums.length>0){
            String sqlcount="select  count(*) from  ";
            String sqlsort="select ";
            String sqliInner = "select  row_number() over () as ROWNUM," ;
            if(isAll==1){
                sqliInner="select  ";
            }
            for(String colum:colums){
                sqliInner=sqliInner+" "+colum+" ,";
                sqlsort=sqlsort+" "+colum+" ,";
            }

            sqlcount=sqlcount+"  "+tableCode+"  where 1=1 "+sqlCond;

            sqliInner=sqliInner.substring(0,sqliInner.lastIndexOf(","));
            sqliInner=sqliInner+"  from "+tableCode+"  where 1=1 "+sqlCond;

            sqlsort=sqlsort.substring(0,sqlsort.lastIndexOf(","));
            sqlsort=sqlsort+"  from ("+sqliInner+" ) where  ROWNUM between "+ firstIndex+" and  "+lastIndex;

            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            try {
                if(isAll==1){
                    sqlsort=sqliInner;
                }
                JdbcTemplate jdbcTemplate = new JdbcTemplate(DB2Handle.getDataSource());
                dataList=  jdbcTemplate.queryForList(sqlsort);
                retMap.put("data",dataList);
                count=jdbcTemplate.queryForInt(sqlcount);

            }catch (Exception e) {
                e.printStackTrace();
                retMap.put("code",1);
                retMap.put("msg","查询出错");
            }finally{
                retMap.put("count",count);
                retMap.put("code",0);
                retMap.put("msg","查询成功");

            }
        }
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
            int count=0;
            Map retMap=new HashMap();
            retMap.put("code",code);
            retMap.put("msg",msg);
            retMap.put("count",count);
            retMap.put("data",null);
            PrintWriter pw=response.getWriter();
            String[] colums= request.getParameterMap().get("colums[]")==null?null:(String[])request.getParameterMap().get("colums[]");
            String tableCode= request.getParameter("tableCode");

            int isapend=Integer.parseInt(request.getParameter("isapend")) ;
            int importCheckData= Integer.parseInt(request.getParameter("importCheckData") );
            String tabelstr=importCheckData==1?"_TEMP ":"";
            Gson gson=new Gson();
            String data= request.getParameter("data") ;
            List datalist = gson.fromJson(data, new TypeToken<List>(){}.getType());
            if(data!=null&&data!=""){
                if(colums.length>0){


                    try {

                        System.out.println(datalist);
                        //String sql = "INSERT INTO tableCode(`name`,`area`) VALUES (?,?)";
                        JdbcTemplate jdbcTemplate = new JdbcTemplate(DB2Handle.getDataSource());
                        List<Map<String,Object>> columInfos= DataTblUtil.getColumInfos(tableCode,colums);
                        long maxId=jdbcTemplate.queryForInt("select NVL(max(ID),0)+1 MAXID from "+tableCode+tabelstr);
                        if(isapend==0){//1是追加 0 是清空数据
                            jdbcTemplate.update("delete from "+tableCode+tabelstr+"");
                        }

                        String sqlFiled="";
                        String sqlValue=""+maxId+",";
                        String sqliInner = "INSERT INTO "+tableCode+tabelstr+" ( ID," ;
                       for(int j=0;j<datalist.size();j++){
                           String field="";
                           String editType="";
                           try{
                               for(Map columInfo:columInfos){
                                    field    =  columInfo.get("FFieldCode").toString();
                                    editType = columInfo.get("FFieldType").toString();

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
                               maxId++;
                               sqlFiled="";
                               sqlValue=maxId+",";
                               sqliInner = "INSERT INTO "+tableCode+tabelstr+"( ID," ;
                           }catch (Exception e){
                               e.printStackTrace();
                               msg=msg+"第"+j+"条插入失败<br/>";
                           }
                           count++;

                       }
                       if(importCheckData==1){
                           Map proc=jdbcTemplate.queryForMap("SELECT FPROCEDURE FROM Exa_Table where FCODE='"+tableCode+"'");
                           if(proc!=null&&proc.get("FPROCEDURE")!=null){
                               String procedure=proc.get("FPROCEDURE").toString();
                               jdbcTemplate.execute("call "+procedure+"()");
                           }
                       }

                        retMap.put("count",count);
                        retMap.put("code",0);
                        retMap.put("msg","共"+count+"条插入成功<br/>"+msg);
                    }catch (Exception e) {
                        e.printStackTrace();
                        retMap.put("code",1);
                        retMap.put("msg","插入出错");
                    }finally{
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

    public ActionForward addData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");//注意设置为json，如果为xml，则设为xml
        int  code=0;
        String msg="";
        int count=0;
        Map retMap=new HashMap();
        retMap.put("code",code);
        retMap.put("msg",code);
        retMap.put("count",count);
        retMap.put("data",null);
        PrintWriter pw=response.getWriter();
        String tableCode= request.getParameter("tableCode");
        String[] colums= request.getParameterMap().get("colums[]")==null?null:(String[])request.getParameterMap().get("colums[]");
        Gson gson=new Gson();
        String data= request.getParameter("data") ;
        String tabelstr=1>3?"_TEMP ":"";
        Map dataMap = gson.fromJson(data, new TypeToken<Map>(){}.getType());

        if(data!=null&&data!=""){
            if(colums.length>0){


                    System.out.println(dataMap);
                    //String sql = "INSERT INTO tableCode(`name`,`area`) VALUES (?,?)";
                    JdbcTemplate jdbcTemplate = new JdbcTemplate(DB2Handle.getDataSource());
                    List<Map<String,Object>> columInfos= DataTblUtil.getColumInfos(tableCode,colums);
                    long maxId=jdbcTemplate.queryForInt("select NVL(max(ID),0)+1 MAXID from "+tableCode+tabelstr);
                    String sqlFiled="";
                    String sqlValue=""+maxId+",";
                    String sqliInner = "INSERT INTO "+tableCode+tabelstr+" ( ID," ;
                    String field="";
                    String editType="";
                try{
                    for(Map columInfo:columInfos){
                        field    =  columInfo.get("FFieldCode").toString().toUpperCase();
                        editType = columInfo.get("FFieldType").toString();
                        if("ID".equalsIgnoreCase(field)){
                            continue;
                        }
                        sqlFiled=sqlFiled+" "+field+" ,";

                        //1|文本类型;2|数值类型;3|日期类型;4|大数据类型;{
                        if("1".equalsIgnoreCase(editType)) {
                            sqlValue=sqlValue+" '"+dataMap.get(field).toString()+"' ,";
                        }else if("2".equalsIgnoreCase(editType)) {
                            sqlValue=sqlValue+" "+dataMap.get(field).toString()+" ,";
                        }else if("3".equalsIgnoreCase(editType)) {
                            sqlValue=sqlValue+" '"+dataMap.get(field).toString()+"' ,";
                        }
                    }
                    sqlFiled=sqlFiled.substring(0,sqlFiled.lastIndexOf(","));
                    sqlValue=sqlValue.substring(0,sqlValue.lastIndexOf(","));
                    sqliInner=sqliInner+sqlFiled+") VALUES ("+ sqlValue+")";
                    jdbcTemplate.update(sqliInner);
                    if(1!=1){
                        Map proc=jdbcTemplate.queryForMap("SELECT FPROCEDURE FROM Exa_Table where FCODE='"+tableCode+"'");
                        if(proc!=null&&proc.get("FPROCEDURE")!=null){
                            String procedure=proc.get("FPROCEDURE").toString();
                            jdbcTemplate.execute("call "+procedure+"()");
                        }
                    }
                    retMap.put("count",count);
                    retMap.put("code",0);
                    retMap.put("msg","新增成功");
                }catch (Exception e) {
                    e.printStackTrace();
                    retMap.put("code",1);
                    retMap.put("msg","插入出错");
                }finally{
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

    public ActionForward deletData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");//注意设置为json，如果为xml，则设为xml
        int  code=0;
        String msg="";
        int count=0;
        Map retMap=new HashMap();
        retMap.put("code",code);
        retMap.put("msg",code);
        retMap.put("count",count);
        retMap.put("data",null);
        PrintWriter pw=response.getWriter();
        String tableCode= request.getParameter("tableCode");
        String uuid= request.getParameter("uvid")==null?null:request.getParameter("uvid");
        String delDatas= request.getParameter("datas")==null?null:request.getParameter("datas");
        Gson gson=new Gson();
        String delCond="";
        if(uuid!=null){//单条删除
            delCond= " =" +uuid;
        }else if(uuid==null&&delDatas!=null){//批量删除
            delCond=" in (";
            List<String> dataList = gson.fromJson(delDatas, new TypeToken<List<String>>(){}.getType());
            for(String id:dataList){
                delCond=delCond+id+",";
            }
            delCond=delCond.substring(0,delCond.lastIndexOf(","));
        }else {

        }
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(DB2Handle.getDataSource());
            String sqliInner = "delete from " +tableCode+" where 1=1  and ID "+delCond;
            count=jdbcTemplate.update(sqliInner);
            retMap.put("count",count);
            retMap.put("code",0);
            retMap.put("msg","删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            retMap.put("code",1);
            retMap.put("msg","删除出错");
        }finally{
            String string = new Gson().toJson(retMap);
            // JsonArray jsonArray = new JsonParser().parse(string).getAsJsonArray();
            PrintWriter out=response.getWriter();
            System.out.println(string);
            out.print(string);
            out.flush();
        }

        return null;
    }


    public ActionForward updateData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");//注意设置为json，如果为xml，则设为xml
        int  code=0;
        String msg="";
        int count=0;
        Map retMap=new HashMap();
        retMap.put("code",code);
        retMap.put("msg",code);
        retMap.put("count",count);
        retMap.put("data",null);
        PrintWriter pw=response.getWriter();
        String tableCode= request.getParameter("tableCode");
        String[] colums= request.getParameterMap().get("colums[]")==null?null:(String[])request.getParameterMap().get("colums[]");
        String[] ids=request.getParameterMap().get("IDs[]")==null?null:(String[])request.getParameterMap().get("IDs[]");
        Gson gson=new Gson();
        String data= request.getParameter("data") ;
        String tabelstr=1>3?"_TEMP ":"";
        List datalist = gson.fromJson(data, new TypeToken<List>(){}.getType());

        try {
            if(data!=null&&data!=""){
                if(colums.length>0){
                    JdbcTemplate jdbcTemplate = new JdbcTemplate(DB2Handle.getDataSource());
                    System.out.println(datalist);
                  List<Map<String,Object>> columInfos= DataTblUtil.getColumInfos(tableCode,colums);;

                    String sqlFiled="";
                    String sqlValue="";
                    String sqliInner = "UPDATE  "+tableCode+tabelstr+" set " ;
                    String whereCond="";
                    if(ids!=null&&ids.length>0){
                        whereCond= "ID in (";
                        for(String id:ids){
                            whereCond= whereCond+id+",";
                        }
                        whereCond=whereCond.substring(0,whereCond.lastIndexOf(","));
                        whereCond= whereCond+")";
                    }


                    for(int j=0;j<datalist.size();j++){
                        String field="";
                        String editType="";
                        try{
                            for(Map columInfo:columInfos){
                                field    = columInfo.get("FFieldCode").toString().toUpperCase();
                                editType = columInfo.get("FFieldType").toString().toUpperCase();

                                sqlFiled=field+"=";

                                //1|文本类型;2|数值类型;3|日期类型;4|大数据类型;{
                                if("1".equalsIgnoreCase(editType)) {
                                    sqlValue=" '"+((Map)datalist.get(j)).get(field).toString()+"' ,";
                                }else if("2".equalsIgnoreCase(editType)) {
                                    sqlValue=" "+((Map)datalist.get(j)).get(field).toString()+" ,";
                                }else if("3".equalsIgnoreCase(editType)) {
                                    sqlValue=" '"+((Map)datalist.get(j)).get(field).toString()+"' ,";
                                }
                                sqliInner=sqliInner+sqlFiled+sqlValue;
                            }
                            sqliInner=sqliInner.substring(0,sqliInner.lastIndexOf(","));
                            if(!"".equalsIgnoreCase(whereCond)){
                                sqliInner=sqliInner+" where "+whereCond;
                            }
                            jdbcTemplate.update(sqliInner);
                            sqlFiled="";
                            sqlValue="";
                            sqliInner =  "UPDATE  "+tableCode+tabelstr+" set " ;
                        }catch (Exception e){
                            e.printStackTrace();
                            msg=msg+"第"+j+"条插入失败<br/>";
                        }
                        count++;

                    }
                }
               }


            retMap.put("count",count);
            retMap.put("code",0);
            retMap.put("msg","修改成功");
        }catch (Exception e) {
            e.printStackTrace();
            retMap.put("code",1);
            retMap.put("msg","修改出错");
        }finally{
            String string = new Gson().toJson(retMap);
            // JsonArray jsonArray = new JsonParser().parse(string).getAsJsonArray();
            PrintWriter out=response.getWriter();
            System.out.println(string);
            out.print(string);
            out.flush();
        }
        return null;
    }
}
