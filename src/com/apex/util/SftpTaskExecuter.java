package com.apex.util;

import com.jcraft.jsch.ChannelSftp;

import java.text.SimpleDateFormat;
import java.util.*;

public class SftpTaskExecuter {

    SftpTaskConfig sftpTaskConfig=null;


    public static void main(String[] args) {
        SftpTaskExecuter executer=new SftpTaskExecuter();
        executer.dealRemoteData();
        System.out.println("Complete!");
    }


    public void dealRemoteData(){

        //Calendar calendar = new GregorianCalendar();
       // SimpleDateFormat sy1=new SimpleDateFormat("yyyy-MM-dd");
        //  failDateStr=sy1.format(failDate);
        sftpTaskConfig=new SftpTaskConfig();
        sftpTaskConfig.initLoginInfo();

        DownloadLog downloadLog=new DownloadLog();
        //�Զ����ɵ�������
        downloadLog.AutoTaskDetail();

        //��ȡ�������ɵĵ�����ϸ�µ��������� ����ʧ�ܵĺʹ�����
        List<Map> taskList= downloadLog.getAllDownLoadTask();
        for (int i=0;i<taskList.size();i++){
           Map task= taskList.get(i);
            this.downloadTar(task.get("FDATE").toString(),(Integer)task.get("FFTPTASKID"),task.get("FCODE").toString());//
        }

    }





    public void downloadTar(String dateString,int taskDetailId,String FCODE){
        List<String>   remoteOKFiles=new ArrayList();
        DownloadLog downloadLog=new DownloadLog();
        String cmdline="";
        String remotePath=sftpTaskConfig.getFSFTPFIlePath();

        String localftpPath=sftpTaskConfig.getFDBFilePath();
        String remoteDataDir=remotePath;
        String localDataDir=localftpPath;
        if(remotePath.indexOf("${date}")>0){
            remoteDataDir=remotePath.replace("${date}",dateString.replace("-",""));
        }
        if(remotePath.indexOf("${date2}")>0){
            remoteDataDir=remotePath.replace("${date2}",dateString);
        }

        if(localDataDir.indexOf("${date}")>0){
            localDataDir=localDataDir.replace("${date}",dateString.replace("-",""));
        }
        if(localDataDir.indexOf("${date2}")>0){
            localDataDir=localDataDir.replace("${date2}",dateString);
        }
        //Զ������sftp
        SFTPUtils sftpUtils=new SFTPUtils(sftpTaskConfig.getHost(),sftpTaskConfig.getFtpuser(),sftpTaskConfig.getPassword());

        try {
            sftpUtils.connect();
            //�ж�Զ��Ŀ¼�Ƿ����
            if(sftpUtils.isDirExist(remoteDataDir)){
                Vector<String> files= sftpUtils.listFiles(remoteDataDir);
                List<String> fileNames=new ArrayList();
                for (int i = 0; i < files.size(); i++)
                {
                    Object obj = files.elementAt(i);
                    if (obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry)
                    {
                        ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) obj;
                        if (true && !entry.getAttrs().isDir())
                        {
                            fileNames.add(entry.getFilename());
                        }
                        if (true && entry.getAttrs().isDir())
                        {
                            if (!entry.getFilename().equals(".") && !entry.getFilename().equals(".."))
                            {
                                fileNames.add(entry.getFilename());
                            }
                        }
                    }
                }
                //��һ��
                for (String strOkfile : fileNames) {
                    System.err.print(strOkfile);
                    //�������ok�ļ��ſ�������
                    if(strOkfile.indexOf(".OK")>0){

                        remoteOKFiles.add(strOkfile.substring(0,strOkfile.indexOf(".OK")));
                    }
                    if(strOkfile.indexOf(".ok")>0){
                        remoteOKFiles.add(strOkfile.substring(0,strOkfile.indexOf(".ok")));
                    }

                }


                //����Ҫ���ص��ļ�
                for(String remotTargz:remoteOKFiles){
                    //������������
                    downloadLog.updateFTPTaskTarList(taskDetailId,remotTargz+".del.tar.gz");
                }
            }
        }catch (Exception e){
            downloadLog.updateTaskLog(3,1,0,FCODE,dateString,"���ݰ����ؽ�ѹʧ�ܣ�");
        }finally {
            sftpUtils.disconnect();
        }



        if(remoteOKFiles.size()>0){
            String fileName="";
      /*      for(int i=0;i<remoteOKFiles.size();i++){
                fileName=remoteOKFiles.get(i);
                //����ָ�����ļ�����ѹ
                cmdline="cd "+localDataDir+"; sh download.sh  "+dateString;//+"   "+fileName;
                RemoteCommandUtil.execCmd(cmdline,sftpTaskConfig.getFtpuser(),sftpTaskConfig.getPassword(),sftpTaskConfig.getHost(),22);
            }*/
            cmdline="cd "+localftpPath+"; sh download.sh  "+dateString;
            RemoteCommandUtil.execCmd(cmdline,sftpTaskConfig.getFServerAcct(),sftpTaskConfig.getFServerPasswd(),sftpTaskConfig.getFServerAddress(),22);
            String locadataPath=localftpPath+"/"+dateString;
            SFTPUtils sftpUtilLocal=new SFTPUtils(sftpTaskConfig.getFServerAddress(),sftpTaskConfig.getFServerAcct(),sftpTaskConfig.getFServerPasswd());
            try{
                sftpUtilLocal.connect();
                if(sftpUtilLocal.isDirExist(locadataPath)) {
                    //�������ݿ����
                    Vector<String> vectorLocal = sftpUtilLocal.listFiles(locadataPath);
                    List<String> localFileNames = new ArrayList();
                    for (int i = 0; i < vectorLocal.size(); i++) {
                        Object obj = vectorLocal.elementAt(i);
                        if (obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
                            ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) obj;
                            if (true && !entry.getAttrs().isDir()) {
                                localFileNames.add(entry.getFilename());
                            }
                            if (true && entry.getAttrs().isDir()) {
                                if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                                    localFileNames.add(entry.getFilename());
                                }
                            }
                        }
                    }

                    //��һ��
                    for (String strOkfile : localFileNames) {
                        System.err.print(strOkfile);
                        String tableDel = strOkfile.substring(0, strOkfile.indexOf(".del"));
                        String varTarName = dateString + "_FRPT_" + tableDel + ".del.tar.gz";
                        //��del�ļ��������ؽ�ѹ�ɹ�
                        if (strOkfile.indexOf(".del") > 0) {

                            downloadLog.updateDownloadFTPTaskTarList(3, varTarName, FCODE, dateString);//�����Ѿ���������
                            downloadLog.updateTaskLog(2, 1, 0, FCODE, dateString, "���ݰ�" + varTarName + "���ؽ�ѹ�ɹ�");
                            downloadLog.updateFTPTaskTabList(1, varTarName, FCODE, tableDel);
                            this.importData(locadataPath + "/" + strOkfile, dateString, FCODE, varTarName, tableDel);
                        }
                    }
                }
            }catch (Exception e){
                    downloadLog.updateTaskLog(3,1,0,FCODE,dateString,"���ݰ����ؽ�ѹʧ�ܣ�");
            }finally {
                sftpUtilLocal.disconnect();
            }

        }



    }


    public void importData(String i_path,String i_Date,String FCode,String varTarName,String i_tablename){
        DownloadLog downloadLog=new DownloadLog();
        try{

            downloadLog.importTempData(i_path,i_tablename);
            downloadLog.importData(i_Date,i_tablename);
            downloadLog.updateFTPTaskTabList(2,varTarName,FCode,i_tablename);
            downloadLog.updateTaskLog(2,2,0,FCode,i_Date,"���ݱ�"+i_tablename+"����ɹ�");
        }catch (Exception e){
            downloadLog.updateTaskLog(3,2,0,FCode,i_Date,"���ݱ�"+i_tablename+"ʧ��");
        }

    }


}
