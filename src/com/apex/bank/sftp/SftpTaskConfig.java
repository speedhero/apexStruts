package com.apex.bank.sftp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SftpTaskConfig {

    //Զ�̷�����
    private String password = "apex@2020";// ����
    private int port = 22;// �˿ں�
    private String host = "66.10.61.120";// ��������ַ
    private String ftpuser="mysftp";
    private String cmdLine="";

    //���ݿ������
    private String FServerAddress="66.10.61.122";
    private String FServerAcct="root";
    private String FServerPasswd="tempP@ssw0rd";
    private String FServerCode="";//���������

    private String fName="";//��������
    private int fType=2;//�������� 1|�����ϴ�;2|��������;3|���ݴ���;
    private String FState="";//-1|δ����;1|������;
    private String FCode="";//������
    private String FDataTable="";//���ݱ�
    private List   talbeList=new ArrayList();
    private String FSFTPCode="";//SFTP���
    private String FSFTPFIlePath="";//SFTPԶ�̴���ļ�·��

    private String FDBFilePath="";//���ݿ��ļ�·��  ������������ļ������ݵ������ɵ�DEL�ļ������ݿ�������ϵĴ��·��
    private String FDBName="";//���ݿ�����
    private String FServerFilePath="";//�������ļ�·��



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



            while (rs.next()) { // ��ǰ��¼ָ���ƶ�����һ����¼��
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

                /*������ʼ����ͼ*/
                String sql = "SELECT ID, FCODE, FADDRESS, FACCOUNT, FPASSWORD, FREMARK FROM PUB_SERVERCONFIG WHERE ID="+getFServerCode();//PZ_DEV_APP
                // String sql = "select  ID, FFTPCODE, FADDRESS, FPORT, FUPLOADACCOUNT, FUPLOADPW, FDOWNLOADACCOUNT, FDOWNLOADPW, FREMARK from PUB_FTPConfig where FFTPCODE='"+ftpCode+"'";
                sta= connection.createStatement();
                sta.executeQuery(sql);
                rs = (ResultSet) sta.getResultSet();


            // ��ǰ��¼ָ���ƶ�����һ����¼��
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

                /*������ʼ����ͼ*/
                String sql = "select ID, FFTPCODE, FADDRESS, FPORT, FUPLOADACCOUNT, FUPLOADPW, FDOWNLOADACCOUNT, FDOWNLOADPW, FREMARK from PUB_FTPConfig where ID="+getFSFTPCode();//PZ_DEV_APP
                sta= connection.createStatement();
                sta.executeQuery(sql);
                rs = (ResultSet) sta.getResultSet();


                // ��ǰ��¼ָ���ƶ�����һ����¼��
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
