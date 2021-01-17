package com.apex.bank.link;

import com.apex.bank.sftp.DB2Handle;
import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
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
        int cnt=0;
        for(int i=0;i<fieldList.size();i++){
            String fishide=fieldList.get(i).get("FISHIDE")==null?"":fieldList.get(i).get("FISHIDE").toString();
            if("0".equalsIgnoreCase(fishide)){//过滤非隐藏列 0非隐藏1隐藏
                cnt++;
                filed.put(fieldList.get(i).get(filedKey),getCloumsHead(cnt));
            }

        }
        return filed;
    }

    public static  String getTemplate(String tableCode) throws SQLException {
        StringBuilder template=new StringBuilder("");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DB2Handle.getDataSource());
        List<Map<String, Object>> templates=jdbcTemplate.queryForList("select FTEMPLET from Exa_TableFieldCSS  where FTEMPLET  IS NOT NULL  AND  FTABLECODE='"+tableCode+"'");
        for(int i=0;i<templates.size();i++){
            template.append(templates.get(i).get("FTEMPLET"));
        }
        return template.toString();
    }

    public static  String getToolBarButtons(String tableCode) throws SQLException {
        StringBuilder button=new StringBuilder("");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DB2Handle.getDataSource());
        List<Map<String, Object>> buttons=jdbcTemplate.queryForList("select  * from Exa_OperButton where FSTATUS=1 AND   FTable='"+tableCode+"'");
        for(int i=0;i<buttons.size();i++){
            button.append(buttons.get(i).get("FTEMPLET"));
        }
        return button.toString();
    }

    public static  String getToolBarButtons(String tableCode,int type) throws SQLException {
        StringBuilder button=new StringBuilder("");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DB2Handle.getDataSource());
        List<Map<String, Object>> buttons=jdbcTemplate.queryForList("select  * from Exa_OperButton where FSTATUS=1 AND   FTable='"+tableCode+"' AND FTYPE="+type);
        String event="";
        String fname="";
        String fstyle="";
       if(type==1){
           for(int i=0;i<buttons.size();i++){
               event=buttons.get(i).get("FEVENT").toString();
               fname=buttons.get(i).get("FNAME").toString();
               fstyle=buttons.get(i).get("FSTYLE").toString();

               if("导入".equalsIgnoreCase(fname)||"导出".equalsIgnoreCase(fname)){
                   button=button.append("<button class=\"layui-btn layui-btn-sm\" lay-event=\""+event+"\"><i class=\"layui-icon\">\uE67C</i>"+fname+"</button>");
               }else{
                   button=button.append("<button class=\"layui-btn layui-btn-sm\" lay-event=\""+event+"\">"+fname+"</button>");
               }
           }
        }else if(type==2){
           for(int i=0;i<buttons.size();i++){
               event=buttons.get(i).get("FEVENT").toString();
               fname=buttons.get(i).get("FNAME").toString();
               fstyle=buttons.get(i).get("FSTYLE").toString();
               button=button.append(" <a class=\"layui-btn layui-btn-xs "+fstyle +" \" lay-event=\""+event+"\">"+fname+"</a>");
           }
        }
        return button.toString();
    }


    public static  List<Map<String, Object>> getHeadList(String tableCode) throws SQLException{
        String sql = " select A.*,B.FDATATYPE, B.FCOLSPAN, B.FROWSPAN, B.FALIGN, B.FISEDIT,\n" +
                "       B.FISHIDE, B.FWIDTH, B.FMINWIDTH, B.FUNRESIZE, B.FSORT, B.FTOTALROW, B.FTOTALROWTEXT, B.FCLUMTYPE, B.FLAY_CHECKED, B.FISFIXED,\n" +
                "       B.FFIXEDTYPE, B.FEVENT, B.FSTYLE, B.FTOOLBAR, B.FFLAG from Exa_TableField A  left join  Exa_TableFieldCSS B " +
                "on A.FTABLE=B.FTABLECODE and A.FTABLE||'.'|| A.FFIELDCODE=B.FTableFieldCode  WHERE FTABLE='" +tableCode+"' order by FSEQ ";//F03_BIG_SCREEN_DEPOSIT_INFO
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DB2Handle.getDataSource());
        return jdbcTemplate.queryForList(sql);
    }

    public static  String getCustomFiledStr() throws SQLException{
        /*    customField="[[ \n" +
            "    {field: 'id', title: 'ID', width: 80, rowspan: 2} \n" +
            "    ,{field: 'username', title: '用户名', width: 80, rowspan: 2}\n" +
            "    ,{align: 'center', title: '个人信息', colspan: 3} \n" +
            "  ], [\n" +
            "    {field: 'email', title: '邮箱', width: 80}\n" +
            "    ,{field: 'sex', title: '性别', width: 120}\n" +
            "    ,{field: 'city', title: '城市', width: 300}\n" +
            "  ]]";*/
        return "0";
    }


    public static List<Map<String,Object>> getColumInfos(String tableCode,String[] colums) throws Exception{
        List<Map<String,Object>> columInfos=null;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DB2Handle.getDataSource());
        String queryCondition="select FFieldCode,FFieldType from  Exa_TableField A  left join " +
                "   Exa_TableFieldCSS B on A.FTABLE=B.FTABLECODE and A.FTABLE||'.'|| A.FFIELDCODE=B.FTableFieldCode" +
                " where FISHIDE=0 AND FTABLE='"+tableCode+"' AND UPPER(FFieldCode) in (";
        for(String colum:colums){
            queryCondition=queryCondition+"'"+colum+"',";
        }
        queryCondition=queryCondition.substring(0,queryCondition.lastIndexOf(","));
        queryCondition=queryCondition+")";
        columInfos= jdbcTemplate.queryForList(queryCondition);
        return  columInfos;
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
