package com.apex.bank.sftp;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

    public class SftpDataUtil {

    private String FFTPTask="";
    private Date FDate=null;
    private String FTarName="";
    private String FCode="";
    private String FName="";


    public String getFFTPTask() {
        return FFTPTask;
    }

    public void setFFTPTask(String FFTPTask) {
        this.FFTPTask = FFTPTask;
    }

    public Date getFDate() {
        return FDate;
    }

    public void setFDate(Date FDate) {
        this.FDate = FDate;
    }

    public String getFTarName() {
        return FTarName;
    }

    public void setFTarName(String FTarName) {
        this.FTarName = FTarName;
    }

    public String getFCode() {
        return FCode;
    }

    public void setFCode(String FCode) {
        this.FCode = FCode;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }



    public  java.util.Date  getMinFailSftpDataUtilDate(String fftptask){
        //select * from CPT_GISPOINT where FCODE=(SELECT ID FROM  CPT_GISMAP);
        Connection connection = null;
        ResultSet rs=null;
        ResultSet rs1=null;
        Statement sta=null;
        String failDateStr="";
        java.util.Date failDate=null;
        try {

            connection = DB2Handle.getConnection();

            //0|待下载;1|下载中;2|已下载;3|已解压;4|下载失败;5|解压失败;6|不再下载;
            String sql = "select min(FDATE) AS FAILDATE from PUB_FTPTaskDetail_TarList_H where fftptask="+fftptask;
            sta= connection.createStatement();
            sta.executeQuery(sql);
            rs = (ResultSet) sta.getResultSet();

            while (rs.next()) { // 当前记录指针移动到下一条记录上
                java.sql.Date sqldate= rs.getDate("FAILDATE");
                failDate=new java.util.Date (sqldate.getTime());
                //SimpleDateFormat sy1=new SimpleDateFormat("yyyy-MM-dd");
                // failDateStr=sy1.format(failDate);
                return failDate;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                DB2Handle.close(connection,sta,rs);
            }
        }
        return failDate;

    }




    public List<Map> getAllDownLoadTask(){
        //select * from CPT_GISPOINT where FCODE=(SELECT ID FROM  CPT_GISMAP);
        Connection connection = null;
        ResultSet rs=null;
        ResultSet rs1=null;
        Statement sta=null;
        List downloadTasks=new ArrayList();
        try {

            connection = DB2Handle.getConnection();

            //-1|重跑撤销;0|待执行;1|执行中;2|成功;3|失败;
            String sql = "select A.ID, A.FFTPTASK, A.FPRETASK, A.FTYPE, A.FSTATE, A.FDATE, A.FBEGINTIME, A.FENDTIME, A.FTIMES, A.FFAILNUM, A.FREMARK ,B.FCODE from PUB_FTPTaskDetail A,PUB_FTPTask B\n" +
                    "where A.FFTPTask=B.ID and A.FTYPE=2 and A.FSTATE in (0,1,3)   and (A.FFAILNUM is  null  or A.FFAILNUM <3)";
            sta= connection.createStatement();
            sta.executeQuery(sql);
            rs = (ResultSet) sta.getResultSet();
            while (rs.next()) { // 当前记录指针移动到下一条记录上
                Map task=new HashMap();
                java.sql.Date sqldate= rs.getDate("FDATE");
                Date downloadDate=new java.util.Date (sqldate.getTime());
                SimpleDateFormat sy1=new SimpleDateFormat("yyyy-MM-dd");
                String  failDateStr=sy1.format(downloadDate);
                task.put("FDATE",failDateStr);
                task.put("FFTPDetailID", rs.getInt("ID"));
                task.put("FFTPTASKID", rs.getInt("FFTPTASK"));
                task.put("FCODE", rs.getString("FCODE"));
                downloadTasks.add(task);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                DB2Handle.close(connection,sta,rs);
            }
        }
        return downloadTasks;

    }





     //自动生成调度任务
    public  void   AutoTaskDetail(){
        //select * from CPT_GISPOINT where FCODE=(SELECT ID FROM  CPT_GISMAP);
        Connection connection = null;
        ResultSet rs=null;
        ResultSet rs1=null;
        Statement sta=null;
        String failDateStr="";
        try {

            connection = DB2Handle.getConnection();
            CallableStatement cs = connection.prepareCall("{call sp_PUB_FTPTaskDetail_Auto(?,?)}");
            cs.registerOutParameter(1, Types.INTEGER);           //注册返回类型(sql类型)
            cs.registerOutParameter(2, Types.VARCHAR);           //注册返回类型(sql类型)
            cs.execute();
            Integer objRtn = cs.getInt(1);      //得到返回值
            String objMsg= cs.getString(2);

        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                DB2Handle.close(connection,sta,rs);
            }
        }
    }


    //更新下载详情
    public  void   updateFTPTaskTarList(int  i_Detail,String i_TarName ){
        //select * from CPT_GISPOINT where FCODE=(SELECT ID FROM  CPT_GISMAP);
        Connection connection = null;
        ResultSet rs=null;
        ResultSet rs1=null;
        Statement sta=null;
        String failDateStr="";
        try {

            connection = DB2Handle.getConnection();
            CallableStatement cs = connection.prepareCall("{call sp_PUB_FTPTaskDetail_TarList(?,?,?,?)}");

            cs.registerOutParameter(1, Types.INTEGER);           //注册返回类型(sql类型)
            cs.registerOutParameter(2, Types.VARCHAR);           //注册返回类型(sql类型)
            cs.setInt(3, i_Detail);
            cs.setString(4,i_TarName);
            cs.execute();
            Integer objRtn = cs.getInt(1);      //得到返回值
            String objMsg= cs.getString(2);

        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                DB2Handle.close(connection,sta,rs);
            }
        }
    }
    //更新已经下载详情
    public  void   updateDownloadFTPTaskTarList(int  i_State,String i_TarName,String i_Code,String i_Date ){
        //select * from CPT_GISPOINT where FCODE=(SELECT ID FROM  CPT_GISMAP);
        Connection connection = null;
        ResultSet rs=null;
        ResultSet rs1=null;
        Statement sta=null;
        String failDateStr="";
        try {

            connection = DB2Handle.getConnection();
            CallableStatement cs = connection.prepareCall("{call sp_PUB_FTPTaskDetail_UpdTarState(?,?,?,?,?,?)}");

            cs.registerOutParameter(1, Types.INTEGER);           //注册返回类型(sql类型)
            cs.registerOutParameter(2, Types.VARCHAR);           //注册返回类型(sql类型)
            cs.setInt(3, i_State);
            cs.setString(4,i_Date);
            cs.setString(5,i_Code);
            cs.setString(6,i_TarName);
            cs.execute();
            Integer objRtn = cs.getInt(1);      //得到返回值
            String objMsg= cs.getString(2);

        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                DB2Handle.close(connection,sta,rs);
            }
        }
    }

    //更新已经数据表导入详情详情
    public  void   updateFTPTaskTabList(int  i_State,String i_TarName,String i_TaskCode,String i_TabName ){
        //select * from CPT_GISPOINT where FCODE=(SELECT ID FROM  CPT_GISMAP);
        Connection connection = null;
        ResultSet rs=null;
        ResultSet rs1=null;
        Statement sta=null;
        String failDateStr="";
        try {

            connection = DB2Handle.getConnection();
            CallableStatement cs = connection.prepareCall("{call sp_PUB_FTPTaskDetail_UpdTabState(?,?,?,?,?,?)}");
            cs.registerOutParameter(1, Types.INTEGER);           //注册返回类型(sql类型)
            cs.registerOutParameter(2, Types.VARCHAR);           //注册返回类型(sql类型)
            cs.setInt(3, i_State);
            cs.setString(4,i_TaskCode);
            cs.setString(5,i_TarName);
            cs.setString(6,i_TabName);
            cs.execute();
            Integer objRtn = cs.getInt(1);      //得到返回值
            String objMsg= cs.getString(2);

        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                DB2Handle.close(connection,sta,rs);
            }
        }
    }


    public  void   updateTaskLog(int  i_State,int i_Type,int i_FSID,String  i_Code,String i_Date,String i_Info ){
        //select * from CPT_GISPOINT where FCODE=(SELECT ID FROM  CPT_GISMAP);
        Connection connection = null;
        ResultSet rs=null;
        ResultSet rs1=null;
        Statement sta=null;
        String failDateStr="";
        try {

            connection = DB2Handle.getConnection();
            CallableStatement cs = connection.prepareCall("{call sp_PUB_FTPTaskLog(?,?,?,?,?,?,?,?)}");

            cs.registerOutParameter(1, Types.INTEGER);           //注册返回类型(sql类型)
            cs.registerOutParameter(2, Types.VARCHAR);           //注册返回类型(sql类型)
            cs.setInt(3, i_Type);
            cs.setInt(4, i_FSID);
            cs.setInt(5, i_State);
            cs.setString(6,i_Date);
            cs.setString(7,i_Code);
            cs.setString(8,i_Info);
            cs.execute();
            Integer objRtn = cs.getInt(1);      //得到返回值
            String objMsg= cs.getString(2);

        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                DB2Handle.close(connection,sta,rs);
            }
        }
    }

    ////接口临时表的数据处理到临时表
    public  String   importTempData(String i_path,String i_tablename ){
        //select * from CPT_GISPOINT where FCODE=(SELECT ID FROM  CPT_GISMAP);
        Connection connection = null;
        ResultSet rs=null;
        ResultSet rs1=null;
        Statement sta=null;
        String objMsg="";
        try {

            connection = DB2Handle.getConnection();
            CallableStatement cs = connection.prepareCall("{call sp_PUB_FTPDataImport(?,?,?,?)}");

            cs.registerOutParameter(1, Types.INTEGER);           //注册返回类型(sql类型)
            cs.registerOutParameter(2, Types.VARCHAR);           //注册返回类型(sql类型)
            cs.setString(3, i_path);
            cs.setString(4,i_tablename);
            cs.execute();
            Integer objRtn = cs.getInt(1);      //得到返回值
            objMsg= cs.getString(2);

        }catch (SQLException e) {
            e.printStackTrace();
            objMsg=e.getMessage();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                DB2Handle.close(connection,sta,rs);
            }
        }
        return objMsg;
    }

    //接口临时表的数据处理到正式接口表
    public  String   importData(String i_Date,String i_TableName ){
        //select * from CPT_GISPOINT where FCODE=(SELECT ID FROM  CPT_GISMAP);
        Connection connection = null;
        ResultSet rs=null;
        ResultSet rs1=null;
        Statement sta=null;
        String objMsg="";
        try {

            connection = DB2Handle.getConnection();
            CallableStatement cs = connection.prepareCall("{call sp_PUB_FTPDataImport_Formal(?,?,?,?)}");

            cs.registerOutParameter(1, Types.INTEGER);           //注册返回类型(sql类型)
            cs.registerOutParameter(2, Types.VARCHAR);           //注册返回类型(sql类型)
            cs.setString(3, i_Date);
            cs.setString(4,i_TableName);
            cs.execute();
            Integer objRtn = cs.getInt(1);      //得到返回值
            objMsg= cs.getString(2);

        }catch (SQLException e) {
            e.printStackTrace();
            objMsg=e.getMessage();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                DB2Handle.close(connection,sta,rs);
            }
        }
        return objMsg;
    }

        public  int    getCurrtTaskLogId(int i_taskdetail ){
            //select * from CPT_GISPOINT where FCODE=(SELECT ID FROM  CPT_GISMAP);
            Connection connection = null;
            ResultSet rs=null;
            ResultSet rs1=null;
            Statement sta=null;
            String objMsg="";
            int currentId=0;
            try {

                connection = DB2Handle.getConnection();

                //-1|重跑撤销;0|待执行;1|执行中;2|成功;3|失败;
                String sql = "select  MAX(ID) ID from PUB_FTPTaskLog where FFTPTASKDETAIL="+i_taskdetail;
                sta= connection.createStatement();
                sta.executeQuery(sql);
                rs = (ResultSet) sta.getResultSet();
                while (rs.next()) {
                    currentId=rs.getInt("ID");
                }

            }catch (SQLException e) {
                e.printStackTrace();
                objMsg=e.getMessage();
            }finally{
                if(connection!=null){
                    System.out.println("Connected successfully.");
                    DB2Handle.close(connection,sta,rs);
                }
            }
            return currentId;
        }


}
