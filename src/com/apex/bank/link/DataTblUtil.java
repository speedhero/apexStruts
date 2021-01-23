package com.apex.bank.link;


import com.apex.bank.sftp.DB2Handle;
import com.apex.form.DataAccess;
import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTblUtil {
    public static JdbcTemplate  jdbcTemplate=null;

    public static void init() throws SQLException{
        if(jdbcTemplate==null){
           jdbcTemplate  = new JdbcTemplate(DB2Handle.getDataSource());
           // jdbcTemplate  = new JdbcTemplate(DataAccess.getDataSource());
        }

    }

    public static void main(String[] args) {
        System.out.println(DataTblUtil.getCloumsHead(0))  ;
    }

    //获得excel输出列
/*    public static String getCloumsHead(int num){
        String[] array = new String[] { "A", "B", "C", "D", "E", "F", "G", "H","I", "J", "K", "L", "M", "N", "O", "P", "Q",
                "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
        int count = 26;
        System.out.println("num/count=" + num/count);
        String out = "";
        if (num/count != 0) {
            if(num%count==0){
                out = array[25];
            }else {
                out = array[num/count-1];
            }

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
    }*/

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
               event=buttons.get(i).get("FEVENT")==null?"":buttons.get(i).get("FEVENT").toString();
               fname=buttons.get(i).get("FNAME")==null?"":buttons.get(i).get("FNAME").toString();
               fstyle=buttons.get(i).get("FSTYLE")==null?"":buttons.get(i).get("FSTYLE").toString();
               iconStyle=buttons.get(i).get("FICON")==null?"":buttons.get(i).get("FICON").toString();
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
               event=buttons.get(i).get("FEVENT")==null?"":buttons.get(i).get("FEVENT").toString();
               fname=buttons.get(i).get("FNAME")==null?"":buttons.get(i).get("FNAME").toString();
               fstyle=buttons.get(i).get("FSTYLE")==null?"":buttons.get(i).get("FSTYLE").toString();
               iconStyle=buttons.get(i).get("FICON")==null?"":buttons.get(i).get("FICON").toString();
               button=button.append(" <a class=\"layui-btn layui-btn-xs "+fstyle +" \" lay-event=\""+event+"\">"+fname+"</a>");
           }
        }
        return button.toString();
    }

    public static  Map<String,Integer> getTablePro(String tableCode) throws SQLException{
       // ,int isCheckBox,int isnumber,int isOperate
        init();
        Map<String,Object> map =jdbcTemplate.queryForMap("select * from Exa_Table where FCODE='"+tableCode+"' ");
        Map<String,Integer> pro=new HashMap();
        pro.put("isCheckBox",map.get("FISCHECKBOX")==null?1:Integer.parseInt(map.get("FISCHECKBOX").toString()));
        pro.put("isnumber",map.get("FISOPERATE")==null?1:Integer.parseInt(map.get("FISOPERATE").toString()));
        pro.put("isOperate",map.get("FISCOLNUMBER")==null?1:Integer.parseInt(map.get("FISCOLNUMBER").toString()));
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
                  String  field   =   list.get(j).get("FTITLECODE")==null?"":list.get(j).get("FTITLECODE").toString();
                  String  title   =   list.get(j).get("FTITLE")==null?"":list.get(j).get("FTITLE").toString();
                  int  colspan =   list.get(j).get("FCOLSPAN")==null?1:Integer.parseInt(list.get(j).get("FCOLSPAN").toString());
                  int  rowspan =   list.get(j).get("FROWSPAN")==null?1:Integer.parseInt(list.get(j).get("FROWSPAN").toString());
                  String  width   =   list.get(j).get("FWIDTH")==null?"":list.get(j).get("FWIDTH").toString();
                  Boolean  hide    =  "1".equalsIgnoreCase(list.get(j).get("FISHIDE").toString()) ?true:false;
                  String  align   =   "1".equalsIgnoreCase(list.get(j).get("FALIGN").toString())?"center":(list.get(j).get("FALIGN")=="2"?"left":"right");//1|center;2|left;3|right
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

}
