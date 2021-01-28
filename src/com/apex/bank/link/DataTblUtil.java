package com.apex.bank.link;


import com.apex.bank.util.MapUtil;
import com.apex.form.DataAccess;
import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTblUtil {
    public static JdbcTemplate  jdbcTemplate=null;

    public static void init() throws SQLException{
        if(jdbcTemplate==null){
           //jdbcTemplate  = new JdbcTemplate(DB2Handle.getDataSource());
            jdbcTemplate  = new JdbcTemplate(DataAccess.getDataSource());
        }

    }

    public static void main(String[] args) {
        System.out.println(DataTblUtil.getCloumsHead(0))  ;
    }

    //获得excel输出列
    public static String getCloumsHead(int index){
        String colCode = "";
        char key='A';
        int loop = index / 26;
        if(loop>0){
            colCode += getCloumsHead(loop-1);
        }
        key = (char) (key+index%26);
        colCode += key;
        return colCode;
    }

    //获得excel输出列
    public static Map getCloumsMap(List<Map<String, Object>> fieldList,String filedKey){
        Map filed=new HashMap();
        int cnt=0;
        for(int i=0;i<fieldList.size();i++){
            String fishide=fieldList.get(i).get("FISHIDE")==null?"":fieldList.get(i).get("FISHIDE").toString();
            if("0".equalsIgnoreCase(fishide)){//过滤非隐藏列 0非隐藏1隐藏
                filed.put(fieldList.get(i).get(filedKey),getCloumsHead(cnt));
                cnt++;
            }

        }
        return filed;
    }

    public static  String getTemplate(String tableCode) throws SQLException {
        StringBuilder template=new StringBuilder("");
        init();
        List<Map<String, Object>> templates=jdbcTemplate.queryForList("select FTEMPLET from Exa_TableFieldCSS  where FTEMPLET  IS NOT NULL  AND  FTABLECODE='"+tableCode+"'");
        for(int i=0;i<templates.size();i++){
            template.append(templates.get(i).get("FTEMPLET"));
        }
        return template.toString();
    }

    public static  String getEventFunc(String tableCode) throws SQLException {
        StringBuilder function=new StringBuilder("");
        init();
        List<Map<String, Object>> functions=jdbcTemplate.queryForList("select FEVENT from Exa_TableFieldCSS  where FTEMPLET  IS NOT NULL  AND  FTABLECODE='"+tableCode+"'");
        for(int i=0;i<functions.size();i++){
            function.append(functions.get(i).get("FEVENT"));
        }
        return function.toString();
    }

    public static  String getToolBarButtons(String tableCode) throws SQLException {
        StringBuilder button=new StringBuilder("");
        init();
        List<Map<String, Object>> buttons=jdbcTemplate.queryForList("select  * from Exa_OperButton where FSTATUS=1 AND   FTable='"+tableCode+"'");
        for(int i=0;i<buttons.size();i++){
            button.append(buttons.get(i).get("FTEMPLET"));
        }
        return button.toString();
    }

    public static  String getToolBarButtons(String tableCode,int type) throws SQLException {
        StringBuilder button=new StringBuilder("");
        init();
        List<Map<String, Object>> buttons=jdbcTemplate.queryForList("select  * from Exa_OperButton where FSTATUS=1 AND   FTable='"+tableCode+"' AND FTYPE="+type+" order by FSeq");
        String event="";
        String fname="";
        String fstyle="";
        String iconStyle="";
       if(type==1){
           for(int i=0;i<buttons.size();i++){
               Map<String, Object> buttonMap= buttons.get(i);
               event    =MapUtil.getKeyString(buttonMap,"FEVENT","");
               fname    =MapUtil.getKeyString(buttonMap,"FNAME","");
               fstyle   =MapUtil.getKeyString(buttonMap,"FSTYLE","");
               iconStyle=MapUtil.getKeyString(buttonMap,"FICON","");
               if("导出".equalsIgnoreCase(fname)){
                   button=button.append("<button class=\"layui-btn layui-btn-sm "+fstyle+"\" lay-event=\""+event+"\"><i class=\"layui-icon\">\uE67C</i>"+fname+"</button>");
               }else if("导入".equalsIgnoreCase(fname)){
                   button=button.append("<button class=\"layui-btn layui-btn-sm "+fstyle+"\" lay-event=\""+event+"\"><i class=\"layui-icon layui-icon-download-circle\"></i>"+fname+"</button>");
               }else{
                   button=button.append("<button class=\"layui-btn layui-btn-sm "+fstyle+"\" lay-event=\""+event+"\">"+iconStyle+fname+"</button>");
               }
           }
        }else if(type==2){
           for(int i=0;i<buttons.size();i++){
               Map<String, Object> buttonMap= buttons.get(i);
               event=    MapUtil.getKeyString(buttonMap,"FEVENT","");
               fname=    MapUtil.getKeyString(buttonMap,"FNAME","");
               fstyle=   MapUtil.getKeyString(buttonMap,"FSTYLE","");
               iconStyle=MapUtil.getKeyString(buttonMap,"FICON","");
               button=button.append(" <a class=\"layui-btn layui-btn-xs "+fstyle +" \" lay-event=\""+event+"\">"+fname+"</a>");
           }
        }
        return button.toString();
    }


    public static  String getToolBarButtonsShowMode(String tableCode,int type) throws SQLException {
        StringBuilder button=new StringBuilder("");
        init();
        List<Map<String, Object>> buttons=jdbcTemplate.queryForList("select  * from Exa_OperButton where FSTATUS=1 AND   FTable='"+tableCode+"' AND FTYPE="+type+" order by FSeq");
        String event="";
        String fname="";
        String fstyle="";
        String iconStyle="";
        if(type==1){
            for(int i=0;i<buttons.size();i++){
                Map<String, Object> buttonMap= buttons.get(i);
                event    =MapUtil.getKeyString(buttonMap,"FEVENT","");
                fname    =MapUtil.getKeyString(buttonMap,"FNAME","");
                fstyle   =MapUtil.getKeyString(buttonMap,"FSTYLE","");
                iconStyle=MapUtil.getKeyString(buttonMap,"FICON","");
                if(fname.indexOf("导出")>=0){
                    if("导出".equalsIgnoreCase(fname)){
                        button=button.append("<button class=\"layui-btn layui-btn-sm "+fstyle+"\" lay-event=\""+event+"\"><i class=\"layui-icon\">\uE67C</i>"+fname+"</button>");
                    }else if("导入".equalsIgnoreCase(fname)){
                        button=button.append("<button class=\"layui-btn layui-btn-sm "+fstyle+"\" lay-event=\""+event+"\"><i class=\"layui-icon layui-icon-download-circle\"></i>"+fname+"</button>");
                    }else{
                        button=button.append("<button class=\"layui-btn layui-btn-sm "+fstyle+"\" lay-event=\""+event+"\">"+iconStyle+fname+"</button>");
                    }
                }

            }
        }else if(type==2){
            for(int i=0;i<buttons.size();i++){
                Map<String, Object> buttonMap= buttons.get(i);
                event=    MapUtil.getKeyString(buttonMap,"FEVENT","");
                fname=    MapUtil.getKeyString(buttonMap,"FNAME","");
                fstyle=   MapUtil.getKeyString(buttonMap,"FSTYLE","");
                iconStyle=MapUtil.getKeyString(buttonMap,"FICON","");
                if(fname.indexOf("查看")>=0){
                    button=button.append(" <a class=\"layui-btn layui-btn-xs "+fstyle +" \" lay-event=\""+event+"\">"+fname+"</a>");
                }

            }
        }
        return button.toString();
    }

    public static  Map<String,Integer> getTablePro(String tableCode) throws SQLException{
       // ,int isCheckBox,int isnumber,int isOperate
        init();
        Map<String,Object> map =jdbcTemplate.queryForMap("select * from Exa_Table where FCODE='"+tableCode+"' ");
        Map<String,Integer> pro=new HashMap();
        pro.put("isCheckBox",MapUtil.getKeyInt(map,"FISCHECKBOX",1));
        pro.put("isnumber",MapUtil.getKeyInt(map,"FISOPERATE",1));
        pro.put("isOperate",MapUtil.getKeyInt(map,"FISCOLNUMBER",1));
        return pro;
    }


    public static  List<Map<String, Object>> getHeadList(String tableCode) throws SQLException{
        String sql = " select A.*,B.FDATATYPE, B.FCOLSPAN, B.FROWSPAN, B.FALIGN, B.FISEDIT,\n" +
                "       B.FISHIDE, B.FWIDTH, B.FMINWIDTH, B.FUNRESIZE, B.FSORT, B.FTOTALROW, B.FTOTALROWTEXT, B.FCLUMTYPE, B.FLAY_CHECKED, B.FISFIXED,\n" +
                "       B.FFIXEDTYPE, B.FEVENT, B.FSTYLE, B.FTOOLBAR, B.FFLAG from Exa_TableField A  left join  Exa_TableFieldCSS B " +
                "on A.FTABLE=B.FTABLECODE and A.FTABLE||'.'|| A.FFIELDCODE=B.FTableFieldCode  WHERE FTABLE='" +tableCode+"' order by FSEQ ";//F03_BIG_SCREEN_DEPOSIT_INFO
        init();
        return jdbcTemplate.queryForList(sql);
    }

    public static  String getCustomFiledStr(String tableCode,int isCheckBox,int isnumber,int isOperate) throws SQLException{

        String  customField="0";
        init();
        Integer levels= jdbcTemplate.queryForInt("SELECT MAX(FLEVEL) LEVEL FROM Exa_TableTitle where FTABLE='"+tableCode+"'");
        List customHeadList=new ArrayList();
        if(levels!=null&&levels.intValue()>0){
            for(int i=1;i<=levels;i++){
                List<Map<String,Object>>  list= jdbcTemplate.queryForList("SELECT * FROM Exa_TableTitle where FTABLE='"+tableCode+"' AND FLEVEL="+i+"  ORDER BY FSEQ");
                List listLvel=new ArrayList();
                if(i==1){
                    if(isnumber==1){
                        Map map=new HashMap();
                        map.put("type","numbers");
                        map.put("rowspan",levels);
                        listLvel.add(map);
                    }
                    if(isCheckBox==1){
                        Map map=new HashMap();
                        map.put("type","checkbox");
                        map.put("rowspan",levels);
                        listLvel.add(map);
                    }

                }
                for(int j=0;j<list.size();j++){
                  Map<String,Object> maptemp=list.get(j);
                  String  field   = MapUtil.getKeyString(maptemp,"FTITLECODE","");
                  String  title   = MapUtil.getKeyString(maptemp,"FTITLE","");
                  int  colspan    = MapUtil.getKeyInt(maptemp,"FCOLSPAN",1);
                  int  rowspan    = MapUtil.getKeyInt(maptemp,"FROWSPAN",1);
                  String  width   = MapUtil.getKeyString(maptemp,"FWIDTH","");
                  Boolean  hide   = "1".equalsIgnoreCase(MapUtil.getKeyString(maptemp,"FISHIDE","0")) ?true:false;
                  String  align   = "1".equalsIgnoreCase(MapUtil.getKeyString(maptemp,"FALIGN","1"))?"center":(list.get(j).get("FALIGN")=="2"?"left":"right");//1|center;2|left;3|right
                  Boolean  totalRow=   list.get(j).get("FISTOTALROW")=="1"?true:false;;
                  Map map=new HashMap();
                  if(field!=null&&!"".equalsIgnoreCase(field)){
                      map.put("field",field.toUpperCase());
                  }
                    map.put("title",title);
                    map.put("colspan",colspan);
                    map.put("rowspan",rowspan);
                    map.put("width",width);
                    map.put("hide",hide);
                    map.put("align",align);
                    map.put("totalRow",totalRow);
                    listLvel.add(map);
                }
                if(i==1&&isOperate==1){
                    Map map=new HashMap();
                    map.put("fixed","right");
                    map.put("rowspan",levels);
                    map.put("title","操作");
                    map.put("toolbar","#editBar");
                    map.put("width",180);
                    listLvel.add(map);
                }
                customHeadList.add(listLvel);
            }
             Gson gson=new Gson();
            customField= gson.toJson(customHeadList);

        }
/*             customField="[[ \n" +
            "    {type:  'numbers' ,  rowspan:2} \n" +
            "    ,{type: 'checkbox', rowspan: 2} \n" +
            "    ,{field: 'ID', title: 'ID', width: 80, rowspan: 2} \n" +
            "    ,{field: 'FORG', title: '用户名', width: 80, rowspan: 2}\n" +
            "    ,{field: 'FTASK_KMH', title: '开门红任务', width: 180, rowspan: 2}\n" +
            "    ,{align: 'center', title: '个人信息', colspan: 3} \n" +
            "  ], [\n" +

            "     {field: 'FTASK_1', title: '1月任务', width: 120}\n" +
            "    ,{field: 'FTASK_2', title: '2月任务', width: 120}\n" +
            "    ,{field: 'FTASK_3', title: '3月任务', width: 120}\n" +
            "  ]]";*/
        return customField;
    }


    public static List<Map<String,Object>> getColumInfos(String tableCode,String[] colums) throws Exception{
        List<Map<String,Object>> columInfos=null;
        init();
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

    public static Map execProcedure(String tableCode,String procedure,String cloums) throws Exception{
        Map retMap=new HashMap();
        Integer  o_Ret =-1;
        String o_Msg ="";
        Integer  i_User=0;
        //List<Map<String, Object>> entryList =
  /*      jdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                String storedProc = "{ call sp_ImportData_Deal(?,?,?,?) }";// 调用的sql
                CallableStatement cs = con.prepareCall(storedProc);
                cs.registerOutParameter(1, Types.INTEGER);// 设置输入参数的值
                cs.registerOutParameter(2, Types.VARCHAR);
                cs.setInt(3, i_User);
                cs.setString(4, tableCode);
                return cs;
            }
        }, new CallableStatementCallback() {
            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                List resultsMap = new ArrayList();
                cs.execute();
                ResultSet rs = (ResultSet) cs.getObject(5);// 获取游标一行的值
                while (rs.next()) {// 转换每行的返回值到Map中
                    Map rowMap = new HashMap();
                    rowMap.put("org_code", rs.getString("org_code"));

                    resultsMap.add(rowMap);
                }
                rs.close();
                Integer  o_Ret =-1;
                String o_Msg ="";
                o_Ret=  cs.getInt(1);
                o_Msg= cs.getString(2);
                return resultsMap;
            }
        });*/

        Connection conn =null;
        CallableStatement cs=null;
        try {
            //conn =DB2Handle.getConnection();
            conn =DataAccess.getDataSource().getConnection();
            cs = conn.prepareCall("call "+procedure+"(?,?,?,?,?)");
            cs.registerOutParameter(1, Types.INTEGER);// 设置输入参数的值
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.setInt(3, i_User);
            cs.setString(4, tableCode);
            cs.setString(5,cloums);
            cs.execute();
            o_Ret=  cs.getInt(1);
            o_Msg= cs.getString(2);
            conn.close();

        }catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(cs!=null){
                try {
                    cs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        retMap.put("o_Ret",o_Ret);
        retMap.put("o_Msg",o_Msg);
        return retMap;
    }

}
