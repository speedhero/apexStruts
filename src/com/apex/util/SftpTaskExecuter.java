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
        //自动生成调度日期
        downloadLog.AutoTaskDetail();

        //获取所有生成的调度明细下的下载任务 调度失败的和待调度
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
        //远程连接sftp
        SFTPUtils sftpUtils=new SFTPUtils(sftpTaskConfig.getHost(),sftpTaskConfig.getFtpuser(),sftpTaskConfig.getPassword());

        try {
            sftpUtils.connect();
            //判断远程目录是否存在
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
                //第一种
                for (String strOkfile : fileNames) {
                    System.err.print(strOkfile);
                    //如果存在ok文件才可以下载
                    if(strOkfile.indexOf(".OK")>0){

                        remoteOKFiles.add(strOkfile.substring(0,strOkfile.indexOf(".OK")));
                    }
                    if(strOkfile.indexOf(".ok")>0){
                        remoteOKFiles.add(strOkfile.substring(0,strOkfile.indexOf(".ok")));
                    }

                }


                //生成要下载的文件
                for(String remotTargz:remoteOKFiles){
                    //更新下载详情
                    downloadLog.updateFTPTaskTarList(taskDetailId,remotTargz+".del.tar.gz");
                }
            }
        }catch (Exception e){
            downloadLog.updateTaskLog(3,1,0,FCODE,dateString,"数据包下载解压失败！");
        }finally {
            sftpUtils.disconnect();
        }



        if(remoteOKFiles.size()>0){
            String fileName="";
      /*      for(int i=0;i<remoteOKFiles.size();i++){
                fileName=remoteOKFiles.get(i);
                //下载指定的文件并解压
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
                    //连接数据库服务
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

                    //第一种
                    for (String strOkfile : localFileNames) {
                        System.err.print(strOkfile);
                        String tableDel = strOkfile.substring(0, strOkfile.indexOf(".del"));
                        String varTarName = dateString + "_FRPT_" + tableDel + ".del.tar.gz";
                        //有del文件代表下载解压成功
                        if (strOkfile.indexOf(".del") > 0) {

                            downloadLog.updateDownloadFTPTaskTarList(3, varTarName, FCODE, dateString);//更新已经下载详情
                            downloadLog.updateTaskLog(2, 1, 0, FCODE, dateString, "数据包" + varTarName + "下载解压成功");
                            downloadLog.updateFTPTaskTabList(1, varTarName, FCODE, tableDel);
                            this.importData(locadataPath + "/" + strOkfile, dateString, FCODE, varTarName, tableDel);
                        }
                    }
                }
            }catch (Exception e){
                    downloadLog.updateTaskLog(3,1,0,FCODE,dateString,"数据包下载解压失败！");
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
            downloadLog.updateTaskLog(2,2,0,FCode,i_Date,"数据表"+i_tablename+"导入成功");
        }catch (Exception e){
            downloadLog.updateTaskLog(3,2,0,FCode,i_Date,"数据表"+i_tablename+"失败");
        }

    }


}
