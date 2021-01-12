package com.apex.bank.sftp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SftpTaskConfig {

    //远程服务器
    private String password = "apex@2020";// 密码
    private int port = 22;// 端口号
    private String host = "66.10.61.120";// 服务器地址
    private String ftpuser="mysftp";
    private String cmdLine="";

    //数据库服务器
    private String FServerAddress="66.10.61.122";
    private String FServerAcct="root";
    private String FServerPasswd="tempP@ssw0rd";
    private String FServerCode="";//服务器编号

    private String fName="";//任务名称
    private int fType=2;//任务类型 1|数据上传;2|数据下载;3|数据处理;
    private String FState="";//-1|未启用;1|已启用;
    private String FCode="";//任务编号
    private String FDataTable="";//数据表
    private List   talbeList=new ArrayList();
    private String FSFTPCode="";//SFTP编号
    private String FSFTPFIlePath="";//SFTP远程存放文件路径

    private String FDBFilePath="";//数据库文件路径  待导入的数据文件或数据导出生成的DEL文件在数据库服务器上的存放路径
    private String FDBName="";//数据库名称
    private String FServerFilePath="";//服务器文件路径



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFtpuser() {
        return ftpuser;
    }

    public void setFtpuser(String ftpuser) {
        this.ftpuser = ftpuser;
    }

    public String getCmdLine() {
        return cmdLine;
    }

    public void setCmdLine(String cmdLine) {
        this.cmdLine = cmdLine;
    }


    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public int getfType() {
        return fType;
    }

    public void setfType(int fType) {
        this.fType = fType;
    }

    public String getFState() {
        return FState;
    }

    public void setFState(String FState) {
        this.FState = FState;
    }

    public String getFCode() {
        return FCode;
    }

    public void setFCode(String FCode) {
        this.FCode = FCode;
    }

    public String getFDataTable() {
        return FDataTable;
    }

    public void setFDataTable(String FDataTable) {
        this.FDataTable = FDataTable;
    }

    public String getFSFTPCode() {
        return FSFTPCode;
    }

    public void setFSFTPCode(String FSFTPCode) {
        this.FSFTPCode = FSFTPCode;
    }

    public String getFSFTPFIlePath() {
        return FSFTPFIlePath;
    }

    public void setFSFTPFIlePath(String FSFTPFIlePath) {
        this.FSFTPFIlePath = FSFTPFIlePath;
    }

    public String getFServerCode() {
        return FServerCode;
    }

    public void setFServerCode(String FServerCode) {
        this.FServerCode = FServerCode;
    }

    public String getFDBFilePath() {
        return FDBFilePath;
    }

    public void setFDBFilePath(String FDBFilePath) {
        this.FDBFilePath = FDBFilePath;
    }

    public String getFDBName() {
        return FDBName;
    }

    public void setFDBName(String FDBName) {
        this.FDBName = FDBName;
    }

    public List getTalbeList() {
        return talbeList;
    }

    public void setTalbeList(List talbeList) {
        this.talbeList = talbeList;
    }


    public String getFServerFilePath() {
        return FServerFilePath;
    }

    public void setFServerFilePath(String FServerFilePath) {
        this.FServerFilePath = FServerFilePath;
    }

    public String getFServerAddress() {
        return FServerAddress;
    }

    public void setFServerAddress(String FServerAddress) {
        this.FServerAddress = FServerAddress;
    }

    public String getFServerAcct() {
        return FServerAcct;
    }

    public void setFServerAcct(String FServerAcct) {
        this.FServerAcct = FServerAcct;
    }

    public String getFServerPasswd() {
        return FServerPasswd;
    }

    public void setFServerPasswd(String FServerPasswd) {
        this.FServerPasswd = FServerPasswd;
    }

    public  boolean initLoginInfo(int taskId){
        //select * from CPT_GISPOINT where FCODE=(SELECT ID FROM  CPT_GISMAP);
        Connection connection = null;
        ResultSet rs=null;
        ResultSet rs1=null;
        Statement sta=null;
        boolean flag=true;
        try {

            connection = DB2Handle.getConnection();

            String sql = "select ID, FCODE, FNAME, FTYPE, FPRETASK, FSTATE, FREMARK, FTARNAME, FDATATABLE,\n" +
                    "       FFTPCODE, FFTPFILEPATH, FSERVERFILEPATH, FISMAC, FSERVERCODE, FDBFILEPATH, FDBNAME, FCOMMAND, FIMPDEAL, FSPERIOD from PUB_FTPTask WHERE FTYPE=2 and FSTATE=1 and ID="+taskId;//PZ_DEV_APP
            // String sql = "select  ID, FFTPCODE, FADDRESS, FPORT, FUPLOADACCOUNT, FUPLOADPW, FDOWNLOADACCOUNT, FDOWNLOADPW, FREMARK from PUB_FTPConfig where FFTPCODE='"+ftpCode+"'";
            sta= connection.createStatement();
            sta.executeQuery(sql);
            rs = (ResultSet) sta.getResultSet();



            while (rs.next()) { // 当前记录指针移动到下一条记录上
                setFCode(rs.getString("FCODE"));
                setfName(rs.getString("FNAME"));
                setFDataTable(rs.getString("FDATATABLE"));
                setFSFTPCode(rs.getString("FFTPCode"));
                setFSFTPFIlePath(rs.getString("FFTPFIlePath"));
                setFDBFilePath(rs.getString("FDBFILEPATH"));
                setFServerCode(rs.getString("FServerCode"));
                setFServerFilePath(rs.getString("FServerFilePath"));
                if(getFDataTable()!=null&&!"".equalsIgnoreCase(getFDataTable())){
                  List tables=  Arrays.asList(getFDataTable().split(";"));
                    setTalbeList(tables);
                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
            flag=false;
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                DB2Handle.close(connection,sta,rs);
            }
        }

        if(getFServerCode()!=null&&!"".equalsIgnoreCase(getFServerCode())){
            try {

                connection = DB2Handle.getConnection();

                /*创建初始化地图*/
                String sql = "SELECT ID, FCODE, FADDRESS, FACCOUNT, FPASSWORD, FREMARK FROM PUB_SERVERCONFIG WHERE ID="+getFServerCode();//PZ_DEV_APP
                // String sql = "select  ID, FFTPCODE, FADDRESS, FPORT, FUPLOADACCOUNT, FUPLOADPW, FDOWNLOADACCOUNT, FDOWNLOADPW, FREMARK from PUB_FTPConfig where FFTPCODE='"+ftpCode+"'";
                sta= connection.createStatement();
                sta.executeQuery(sql);
                rs = (ResultSet) sta.getResultSet();


            // 当前记录指针移动到下一条记录上
                while (rs.next()) {
                    setFServerAcct(rs.getString("FACCOUNT"));
                    setFServerAddress(rs.getString("FADDRESS"));
                    setFServerPasswd(rs.getString("FPASSWORD"));
                }

            }catch (SQLException e) {
                e.printStackTrace();
            }finally{
                if(connection!=null){
                    System.out.println("Connected successfully.");
                    DB2Handle.close(connection,sta,rs);
                }
            }
        }else{
            flag=false;
        }

        if(getFSFTPCode()!=null&&!"".equalsIgnoreCase(getFSFTPCode())){
            try {

                connection = DB2Handle.getConnection();

                /*创建初始化地图*/
                String sql = "select ID, FFTPCODE, FADDRESS, FPORT, FUPLOADACCOUNT, FUPLOADPW, FDOWNLOADACCOUNT, FDOWNLOADPW, FREMARK from PUB_FTPConfig where ID="+getFSFTPCode();//PZ_DEV_APP
                sta= connection.createStatement();
                sta.executeQuery(sql);
                rs = (ResultSet) sta.getResultSet();


                // 当前记录指针移动到下一条记录上
                while (rs.next()) {
                    setFtpuser(rs.getString("FDOWNLOADACCOUNT"));
                    setPassword(rs.getString("FDOWNLOADPW"));
                    setHost(rs.getString("FADDRESS"));
                }

            }catch (SQLException e) {
                e.printStackTrace();
            }finally{
                if(connection!=null){
                    System.out.println("Connected successfully.");
                    DB2Handle.close(connection,sta,rs);
                }
            }
        }else{
            flag=false;
        }

         return flag;

    }


}
