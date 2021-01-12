package com.apex.bank.gismap;

import com.alibaba.fastjson.JSON;
import com.apex.bank.sftp.DB2Handle;
import com.apex.bank.gismap.GisPoint;
import org.apache.struts.action.ActionForm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GisForm  extends ActionForm {
    public List getGisMap( String mapcode,  int pointType) {
        GisMap gisMap=new GisMap(mapcode);
        return  gisMap.createPoints(mapcode,pointType);
    }


    public List getGisPoints(Long pointId,String moduel) {
        GisPoint gisPoint=  new GisPoint();
        return  gisPoint.getPointInfos(pointId,moduel);
    }


    public Map getAllProductInfos(){
        Connection connection = null;
        ResultSet rs=null;
        Statement sta=null;
        List productInfos=new ArrayList();//存储坐标信息

        Map outMap=new HashMap();
        List<String> legendData=new ArrayList();
        List<String> axisLabelList=new ArrayList();
        List prdValList=new ArrayList();
        try {

            connection = DB2Handle.getConnection();
            String areaName="";
            String fprdName="";
            Long fval=0L;
            /*创建初始化地图*/
            String sql = "select t.FProductName as FCategory,B.FAREANAME as FLabel,cast(round(sum(t.FCustNum),2) as dec(20,2)) as FValue from BANK_PZ.CPT_Product t,PUB_Area AS B " +
                    " where t.FDataDate = (SELECT max(FDataDate) FROM CPT_Product)   AND t.FArea=B.ID and " +
                    "t.FDept = (SELECT ID FROM LBORGANIZATION WHERE TYPE = 1) and t.FFlag = 1 group by t.FProductName,B.FAREANAME ";
            sta = connection.createStatement();
            sta.executeQuery(sql);
            rs = (ResultSet) sta.getResultSet();
            while (rs.next()) { // 当前记录指针移动到下一条记录上
                //报错时候跳过异常构建下个点
                try {
                    Map productInfo=new HashMap();
                    areaName=rs.getString("FLABEL");
                    fprdName=rs.getString("FCATEGORY");
                    fval=rs.getLong("FValue");
                    if(!legendData.contains(fprdName)){
                        legendData.add(fprdName);
                    }
                    if(!axisLabelList.contains(areaName)){
                        axisLabelList.add(areaName);
                    }
                    productInfo.put("name",areaName);
                    productInfo.put("fprdName",fprdName);
                    productInfo.put("value",fval);
                    productInfos.add(productInfo);
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
        Map prdAreaAxisMap=new HashMap();
        for (int i =0;i<productInfos.size();i++){
            Map prdMap=(Map) productInfos.get(i);
            Map prdListMap=new HashMap();
            String legendLabel=(String)prdMap.get("fprdName");
            Long prdVal=(Long)prdMap.get("value");



            List axisList=new ArrayList();

            if(!prdAreaAxisMap.containsKey(legendLabel)){
                prdAreaAxisMap.put(legendLabel,axisList);
            }else {
                axisList=(List)prdAreaAxisMap.get(legendLabel);
            }

            Map prdAxisMap=new HashMap();
            for (String axisArea: axisLabelList) {
                if(!prdAxisMap.containsKey(legendLabel)){
                    Map areaListMap=new HashMap();
                    prdAxisMap.put(legendLabel,areaListMap);
                }
                Map axisAreaMap=(Map) prdAxisMap.get(legendLabel);

                String areaName= (String)prdMap.get("name");
                if(axisArea.equalsIgnoreCase(areaName)){
                    if(axisAreaMap.containsKey(axisArea)){
                        List  axisPrdList=(List) axisAreaMap.get(axisArea);
                        axisPrdList.add(prdVal);
                    }else{
                        List  axisPrdList=new ArrayList();
                        axisPrdList.add(prdVal);
                        axisAreaMap.put(axisArea,axisPrdList);
                    }
                    axisList.add(axisAreaMap);
                }
            }

            // System.out.println(JSON.toJSONString(axisList));
            System.out.println(JSON.toJSONString(prdAreaAxisMap));


        }


        outMap.put("legendData",legendData);
        outMap.put("axisLabel",axisLabelList);
        outMap.put("productInfos",prdAreaAxisMap);
        System.out.println(JSON.toJSONString(prdValList));
        return  outMap;
    }



    public Map getCountryInfos( String countryName){
        GisMap gisMap=new GisMap("");
        return gisMap.getConutry(countryName);
    }
}
