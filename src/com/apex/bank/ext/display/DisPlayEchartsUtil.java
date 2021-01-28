package com.apex.bank.ext.display;

import com.apex.bank.sftp.DB2Handle;
import com.apex.bank.util.MapUtil;
import com.apex.form.DataAccess;
import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DisPlayEchartsUtil {
    public static JdbcTemplate jdbcTemplate=null;

    public static void init() throws SQLException {
        if(jdbcTemplate==null){
            jdbcTemplate  = new JdbcTemplate(DB2Handle.getDataSource());
            //jdbcTemplate  = new JdbcTemplate(DataAccess.getDataSource());
        }

    }

    public static Map<String,String> getPageListStr(String fmodulecode,String date,int type) throws SQLException{//1实际值比应达，2实际值比任务值
        init();
        Map<String,String> jsonStrMap=new HashMap();

        String  jsonNameArr="";
        String  jsonTrgArr="";
        String  jsonActArr="";
        String pageArr="";
        Gson gson=new Gson();

        String sql="select FORGNAME, FTARGET, FACTUAL,FTASK   from Exa_QuotaConf where FMODULECODE='"+fmodulecode+"' " +
                "   and FDATADATE='"+date+"'";//(select TO_CHAR(MAX(TO_DATE(FDATADATE,'YYYY-MM')),'YYYY-MM') from Exa_QuotaConf)
        List<Map<String,Object>> items=  jdbcTemplate.queryForList(sql);

        List targetList=new ArrayList();
        List actualList=new ArrayList();
        List nameList=new ArrayList();
        List pageList=new ArrayList();
         int limit=10;

             for(int i=0;i<=(items.size()/limit);i++){
                 List<String> subNamelist=new ArrayList();
                 List<Double> subTrglist=new ArrayList();
                 List<Double> subActlist=new ArrayList();
                 pageList.add(i+1);
                 int subSize=0;
                 if(i<(items.size()/limit)){
                     subSize=limit;
                 }else {
                     subSize= items.size()%limit;
                 }
                 for(int j=0;j<subSize;j++){
                     Map item=items.get(i*10+j);
                     String forgname =MapUtil.getKeyString(item,"FORGNAME","");
                     Double ftarget = MapUtil.getKeyDouble(item,"FTARGET",0d);
                     Double factual=MapUtil.getKeyDouble(item,"FACTUAL",0d);
                     Double ftask=MapUtil.getKeyDouble(item,"FTASK",0d);
                     Double percent=0d;
                     if(type==1){
                         if(factual==0){
                             percent=0d;
                         }else if(factual>0&&ftarget>0){
                             percent =formatDouble2(factual/ftarget*100);
                         }else{
                             percent=0d;
                         }
                     }else if((type==2)) {
                         if(factual==0){
                             percent=0d;
                         }else if(factual>0&&ftask>0){
                             percent =formatDouble2(factual/ftask*100);
                         }else{
                             percent=0d;
                         }
                     }
                     subNamelist.add(forgname);
                     subTrglist.add(ftarget);
                     subActlist.add(factual);
                 }
                 targetList.add(subTrglist);
                 actualList.add(subActlist);
                 nameList.add(subNamelist);
             }

        jsonNameArr=gson.toJson(nameList);
        jsonActArr=gson.toJson(actualList);
        jsonTrgArr=gson.toJson(targetList);
        pageArr=gson.toJson(pageList);

 /*       for(int i=0;i<=(48/10);i++){
            List<String> subNamelist=new ArrayList();
            List<Integer> subTrglist=new ArrayList();
            List<Integer> subActlist=new ArrayList();
            pageList.add(i+1);
            int subSize=0;
            if(i<4){
                subSize=10;
            }else {
                subSize= 48%10;
            }
            for(int j=0;j<subSize;j++){
                subNamelist.add("支行"+(i*10+j));
                subTrglist.add(80000);
                subActlist.add(70000);
            }
            targetList.add(subTrglist);
            actualList.add(subActlist);
            nameList.add(subNamelist);
        }
        jsonNameArr=gson.toJson(nameList);
        jsonActArr=gson.toJson(actualList);
        jsonTrgArr=gson.toJson(targetList);
        pageArr=gson.toJson(pageList);*/
        jsonStrMap.put("jsonNameArr",jsonNameArr);
        jsonStrMap.put("jsonActArr",jsonActArr);
        jsonStrMap.put("jsonTrgArr",jsonTrgArr);
        jsonStrMap.put("pageArr",pageArr);
        return jsonStrMap;
    }


    public static Map<String,String> getSliderPercentListStr(String fmodulecode,String date,int type) throws SQLException{
        init();
        Map<String,String> jsonStrMap=new HashMap();

        String  jsonNameArr="";
        String  percentArr="";
        Gson gson=new Gson();

        String sql="select FORGNAME, FTARGET, FACTUAL,FTASK  from Exa_QuotaConf where FMODULECODE='"+fmodulecode+"' " +
                "   and FDATADATE='"+date+"'";//(select TO_CHAR(MAX(TO_DATE(FDATADATE,'YYYY-MM')),'YYYY-MM') from Exa_QuotaConf)
        List<Map<String,Object>> items=  jdbcTemplate.queryForList(sql);

        List nameList=new ArrayList();
        List<Map> percentList=new ArrayList();
        int limit=10;

        for(int i=0;i<items.size();i++){

            Map item=items.get(i);
            String forgname =MapUtil.getKeyString(item,"FORGNAME","");
            Double ftarget = MapUtil.getKeyDouble(item,"FTARGET",0d);
            Double factual=MapUtil.getKeyDouble(item,"FACTUAL",0d);
            Double ftask=MapUtil.getKeyDouble(item,"FTASK",0d);
            Double percent=0d;
            if(type==1){
                if(factual==0){
                    percent=0d;
                }else if(factual>0&&ftarget>0){
                    percent =formatDouble2(factual/ftarget*100);
                }else if(factual>0&&ftask==0){
                    percent =factual;
                }else{
                    percent=0d;
                }
            }else if((type==2)) {
                if(factual==0){
                    percent=0d;
                }else if(factual>0&&ftask>0){
                    percent =formatDouble2(factual/ftask*100);
                }else if(factual>0&&ftask==0){
                    percent =factual;
                }else{
                    percent=0d;
                }
            }
            Map valMap=new HashMap();
            valMap.put("value",percent);
            valMap.put("ftarget",ftarget);
            valMap.put("factual",factual);
            valMap.put("ftask",ftask);
            percentList.add(valMap);
            nameList.add(forgname);
        }

        jsonNameArr=gson.toJson(nameList);
        percentArr=gson.toJson(percentList);

/*        for(int i=0;i<=48;i++){
            Double percent=formatDouble2(500d/600d*100);
            percentList.add(percent);
            nameList.add("支行"+i);
        }*/
        jsonNameArr=gson.toJson(nameList);
        percentArr=gson.toJson(percentList);
        jsonStrMap.put("jsonNameArr",jsonNameArr);
        jsonStrMap.put("percentArr",percentArr);
        return jsonStrMap;
    }



    public static Map<String,String> getGroupChartListStr(String fmodulecode,String date,int type) throws SQLException{
        init();
        Map<String,String> jsonStrMap=new HashMap();
//(select TO_CHAR(MAX(TO_DATE(FDATADATE,'YYYY-MM')),'YYYY-MM') from Exa_QuotaConf)
        String  jsonNameArr="";
        String  percentArr="";
        String  actualtArr="";
        String groupArr="";
        Gson gson=new Gson();
        String groupSql="SELECT FGROUPCODE,FNAME FROM Exa_QuotaConf WHERE FMODULECODE = '"+fmodulecode+"' group by  FGROUPCODE,FNAME";
        List<Map<String,Object>> grpItems =jdbcTemplate.queryForList(groupSql);
        List<String> grpItemName=new ArrayList();
        List<String>nameList     =new ArrayList();
        List<List> sumpercentList=new ArrayList();
        List<List> sumtrgList    =new ArrayList();
        List<List> sumactualList =new ArrayList();
        List<List> sumtaskList   =new ArrayList();
        for(int j=0;j<grpItems.size();j++){
          //  grpItem.add(MapUtil.getKeyString(grpItems.get(i),"FGROUPCODE",""));
          String grpCode=  MapUtil.getKeyString(grpItems.get(j),"FGROUPCODE","");
          String grpName=  MapUtil.getKeyString(grpItems.get(j),"FNAME","");
          if(!"".equalsIgnoreCase(grpCode)){
              String sql="select FORGNAME, FTARGET, FACTUAL,FTASK  from Exa_QuotaConf where FMODULECODE='"+fmodulecode+"' " +
                      "   and FDATADATE='"+date+"' AND FGROUPCODE='"+grpCode+"' ORDER BY FORGNAME";
              grpItemName.add("应达"+grpName);
              List<Map<String,Object>> items=  jdbcTemplate.queryForList(sql);

              List subnameList=new ArrayList();
              List<Double> percentList=new ArrayList();
              List<Double> trgList=new ArrayList();
              List<Double> actualList=new ArrayList();
              List<Double> taskList=new ArrayList();
              int limit=12;

              for(int i=0;i<items.size();i++){

                  Map item=items.get(i);
                  String forgname =MapUtil.getKeyString(item,"FORGNAME","");
                  Double ftarget = MapUtil.getKeyDouble(item,"FTARGET",0d);
                  Double factual=MapUtil.getKeyDouble(item,"FACTUAL",0d);
                  Double ftask=MapUtil.getKeyDouble(item,"FTASK",0d);
                  Double percent=0d;
                  if(type==1){
                      if(factual==0){
                          percent=0d;
                      }else if(factual>0&&ftarget>0){
                          percent =formatDouble2(factual/ftarget*100);
                      }else{
                          percent=0d;
                      }
                  }else if((type==2)) {
                      if(factual==0){
                          percent=0d;
                      }else if(factual>0&&ftask>0){
                          percent =formatDouble2(factual/ftask*100);
                      }else{
                          percent=0d;
                      }
                  }

                  percentList.add(percent);
                  trgList.add(ftarget);
                  actualList.add(factual);
                  taskList.add(ftask);
                  subnameList.add(forgname);
              }
              nameList=subnameList;
              sumpercentList.add(percentList);
              sumtrgList.add(trgList);
              sumactualList.add(actualList);
              sumtaskList.add(taskList);
          }

        }

        jsonNameArr=gson.toJson(nameList);
        percentArr=gson.toJson(sumpercentList);
        actualtArr=gson.toJson(sumactualList);
        groupArr=gson.toJson(grpItemName);

        jsonStrMap.put("jsonNameArr",jsonNameArr);
        jsonStrMap.put("percentArr",percentArr);
        jsonStrMap.put("actualtArr",actualtArr);
        jsonStrMap.put("groupArr",groupArr);
        return jsonStrMap;
    }


    //1|前10名;2|后10名;1|前5名;2|后5名
    public static Map<String,String> getSortChartListStr(String fmodulecode,String date,int type,int sort) throws SQLException{
        init();
        int FSORT=sort;
        String orderBy="";
        switch(sort){
            case 1 :
                FSORT=10;orderBy=" DESC ";
                break;
            case 2 :
                FSORT=10;orderBy=" ASC ";
                break;
            case 3 :
                FSORT=5;orderBy=" DESC ";
                break;
            case 4 :
                FSORT=5;orderBy=" ASC ";
                break;
            case 5 :
                FSORT=100;orderBy=" DESC ";
                break;
        }

        Map<String,String> jsonStrMap=new HashMap();

        String  jsonNameArr="";
        String  percentArr="";
        String  extpercentArr="";
        Gson gson=new Gson();
        //SELECT  cast(substr(NVL(FRATE,'0%'), 1, locate('%', NVL(FRATE,'0%'))-1) as decimal(8,2))  FRATE FROM Exa_QuotaConf WHERE
                //      FMODULECODE = 'KMH_BLYJ_2021' ORDER BY FRATE DESC FETCH FIRST  10 ROWS  ONLY;
      //  String sql="select cast(substr(NVL(FRATE,'0%'), 1, locate('%', NVL(FRATE,'0%'))-1) as decimal(8,2)) FRATE,FORGNAME,FTARGET,FACTUAL,FTASK " +
        //        "  from Exa_QuotaConf where FMODULECODE='"+fmodulecode+"' " +
          //      "   and FDATADATE='"+date+"'  ORDER BY FRATE "+orderBy+" FETCH FIRST "+FSORT +" ROWS  ONLY";
        String sql="select  FRATE,FORGNAME,FTARGET,FACTUAL,FTASK " +
                "  from Exa_QuotaConf where FMODULECODE='"+fmodulecode+"' " +
                "   and FDATADATE='"+date+"'  ORDER BY FRATE "+orderBy+" FETCH FIRST "+FSORT +" ROWS  ONLY";
        List<Map<String,Object>> items=  jdbcTemplate.queryForList(sql);

        List nameList=new ArrayList();
        List<Map> percentList=new ArrayList();
        List<Double> extpercentList=new ArrayList();
        int limit=10;

        Double maxvalue=0d;
        for(int i=0;i<items.size();i++){

            Map item=items.get(i);
            String forgname =MapUtil.getKeyString(item,"FORGNAME","");
            Double ftarget = MapUtil.getKeyDouble(item,"FTARGET",0d);
            Double factual=MapUtil.getKeyDouble(item,"FACTUAL",0d);
            Double ftask=MapUtil.getKeyDouble(item,"FTASK",0d);
            Double frate=MapUtil.getKeyDouble(item,"FRATE",0d);
            if(frate>maxvalue){
                maxvalue=frate;
            }
            Map valMap=new HashMap();
            valMap.put("value",frate);
            valMap.put("ftarget",ftarget);
            valMap.put("factual",factual);
            valMap.put("ftask",ftask);
            percentList.add(valMap);
          //  percentList.add(frate);
            nameList.add(forgname);
        }

        for(Map percent: percentList){
            extpercentList.add(maxvalue+50-MapUtil.getKeyDouble(percent,"value",0d) );
        }

        jsonNameArr=gson.toJson(nameList);
        percentArr=gson.toJson(percentList);
        extpercentArr=gson.toJson(extpercentList);
        jsonStrMap.put("jsonNameArr",jsonNameArr);
        jsonStrMap.put("percentArr",percentArr);
        jsonStrMap.put("extpercentArr",extpercentArr);
        return jsonStrMap;
    }


    //折线图
    public static Map<String,String> getZlineChartListStr(String fmodulecode,String date,int type) throws SQLException{
        init();
        Map<String,String> jsonStrMap=new HashMap();
//(select TO_CHAR(MAX(TO_DATE(FDATADATE,'YYYY-MM')),'YYYY-MM') from Exa_QuotaConf)
        String  jsonNameArr="";
        String  percentArr="";
        String  actualtArr="";
        String  groupArr="";
        String  pageArr="";
        String  jsonTrgArr="";
        Gson gson=new Gson();
        String groupSql="SELECT FGROUPCODE,FNAME FROM Exa_QuotaConf WHERE FMODULECODE = '"+fmodulecode+"' group by  FGROUPCODE,FNAME";
        List<Map<String,Object>> grpItems =jdbcTemplate.queryForList(groupSql);
        List<String> grpItemName=new ArrayList();
        List<String>nameList     =new ArrayList();
        List<List> sumpercentList=new ArrayList();
        List<List> sumtrgList    =new ArrayList();
        List<List> sumactualList =new ArrayList();
        List<List> sumtaskList   =new ArrayList();
        List pageList=new ArrayList();
        for(int k=0;k<grpItems.size();k++){
            //  grpItem.add(MapUtil.getKeyString(grpItems.get(i),"FGROUPCODE",""));
            String grpCode=  MapUtil.getKeyString(grpItems.get(k),"FGROUPCODE","");
            String grpName=  MapUtil.getKeyString(grpItems.get(k),"FNAME","");
            if(!"".equalsIgnoreCase(grpCode)){
                String sql="select FORGNAME, FTARGET, FACTUAL,FTASK  from Exa_QuotaConf where FMODULECODE='"+fmodulecode+"' " +
                        "   and FDATADATE='"+date+"' AND FGROUPCODE='"+grpCode+"' ORDER BY FORGNAME";

                List<Map<String,Object>> items=  jdbcTemplate.queryForList(sql);
                grpItemName.add(grpName);
                int limit=10;
            /*    for(int i=0;i<(items.size()/limit);i++){
                    if(k==0){
                        pageList.add(i+1);
                    }
                }*/
                List<Double> subTrglist=new ArrayList();
                List<Double> subActlist=new ArrayList();
                List<Double> subpercentList=new ArrayList();
                List<Double> subTasklist=new ArrayList();
                List<String> subNamelist=new ArrayList<>();
                for(int i=0;i<items.size();i++){
                    Map item=items.get(i);
                    String forgname =MapUtil.getKeyString(item,"FORGNAME","");
                    Double ftarget = MapUtil.getKeyDouble(item,"FTARGET",0d);
                    Double factual=MapUtil.getKeyDouble(item,"FACTUAL",0d);
                    Double ftask=MapUtil.getKeyDouble(item,"FTASK",0d);
                    Double percent=0d;
                    if(type==1){
                        if(factual==0){
                            percent=0d;
                        }else if(factual>0&&ftarget>0){
                            percent =formatDouble2(factual/ftarget*100);
                        }else{
                            percent=0d;
                        }
                    }else if((type==2)) {
                        if(factual==0){
                            percent=0d;
                        }else if(factual>0&&ftask>0){
                            percent =formatDouble2(factual/ftask*100);
                        }else{
                            percent=0d;
                        }
                    }
                    subNamelist.add(forgname);
                    subTrglist.add(ftarget);
                    subActlist.add(factual);
                    subpercentList.add(percent);
                    subTasklist.add(ftask);
                }

                nameList=subNamelist;
                sumpercentList.add(subpercentList);
                sumtrgList.add(subTrglist);
                sumactualList.add(subActlist);
                sumtaskList.add(subTasklist);
            }

        }

        jsonNameArr=gson.toJson(nameList);
        percentArr=gson.toJson(sumpercentList);
        actualtArr=gson.toJson(sumactualList);
        groupArr=gson.toJson(grpItemName);
        pageArr=gson.toJson(pageList);
        jsonTrgArr=gson.toJson(sumtrgList);
        jsonStrMap.put("jsonNameArr",jsonNameArr);
        jsonStrMap.put("percentArr",percentArr);
        jsonStrMap.put("actualtArr",actualtArr);
        jsonStrMap.put("jsonTrgArr",jsonTrgArr);
        jsonStrMap.put("groupArr",groupArr);
        jsonStrMap.put("pageArr",pageArr);
        return jsonStrMap;
    }

    public static double formatDouble2(double d) {
        // 旧方法，已经不再推荐使用
//        BigDecimal bg = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);

        return bg.doubleValue();
    }


    public static String getMaxDataDate(String fmodulecode) throws SQLException{
        init();
        String maxdate="";
        String groupSql="select TO_CHAR(MAX(TO_DATE(FDATADATE,'YYYY-MM')),'YYYY-MM') DATE from Exa_QuotaConf WHERE  FMODULECODE = '"+fmodulecode+"'";
        Map<String,Object> dateMap =jdbcTemplate.queryForMap(groupSql);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
       // System.out.println("把当前时间转换成字符串：" + sdf.format(new Date()));
        maxdate=MapUtil.getKeyString(dateMap,"DATE",sdf.format(new Date()));
        return maxdate;
    }

    public static String getColor(String fmodulecode,String ftitle)throws SQLException{
        String groupSql="select FColor from DC_Echarts_Ext WHERE  FModuleCode = '"+fmodulecode+"'  and FTITLE='"+ftitle+"'";
        init();
        Map<String,Object> dateMap =jdbcTemplate.queryForMap(groupSql);
        String color=MapUtil.getKeyString(dateMap,"FCOLOR","#C1232B,#B5C334,#fc877d,#e81c50,#27727B");
        Gson gson=new Gson();
        String[] colors=null;
        if(color!=null&&color!="");{
            colors=color.split(",");
        }
        String colorlist= gson.toJson(colors);
        return colorlist;

    }

    public static String getIsSlider(String fmodulecode,String ftitle)throws SQLException{
        String groupSql="select FISlider from DC_Echarts_Ext WHERE  FModuleCode = '"+fmodulecode+"'  and FTITLE='"+ftitle+"'";
        init();
        Map<String,Object> dateMap =jdbcTemplate.queryForMap(groupSql);
        String fislider=MapUtil.getKeyString(dateMap,"FISLIDER","1");
        return fislider;

    }


}
