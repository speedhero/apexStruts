package com.apex.bank.sftp;

import com.jcraft.jsch.ChannelSftp;

import java.util.*;

public class SftpTaskExecuter implements Runnable {


    String o_Ret="";
    String o_Msg="";


    public static void main(String[] args) {
        SftpTaskExecuter executer=new SftpTaskExecuter();
        executer.dealRemoteData();
        System.out.println("Complete!");
    }

    public String getO_Ret() {
        return o_Ret;
    }

    public void setO_Ret(String o_Ret) {
        this.o_Ret = o_Ret;
    }

    public String getO_Msg() {
        return o_Msg;
    }

    public void setO_Msg(String o_Msg) {
        this.o_Msg = o_Msg;
    }

    @Override
    public void run() {


        int count=0;
        int failcont=0;
        SftpDataUtil sftpDataUtil=new SftpDataUtil();
        //�Զ����ɵ�������
        sftpDataUtil.AutoTaskDetail();

        //��ȡ�������ɵĵ�����ϸ�µ��������� ����ʧ�ܵĺʹ�����
        List<Map> taskList= sftpDataUtil.getAllDownLoadTask();

        for (int i=0;i<taskList.size();i++){
            try {
                Map task= taskList.get(i);
                System.out.println("---------start---------");
                System.out.println(task.get("FDATE").toString());
                System.out.println(task.get("FFTPDetailID"));
                System.out.println(task.get("FCODE").toString());
                System.out.println(task.get("FFTPTASKID"));
                System.out.println("---------end---------");
                this.downloadTar(task.get("FDATE").toString(),(Integer)task.get("FFTPDetailID"),task.get("FCODE").toString(),(Integer)task.get("FFTPTASKID"));//
                count++;
            }catch (Exception e){
                e.printStackTrace();
                failcont++;
            }

        }

    }

    public Map dealRemoteData(){
        Map resultMap=new HashMap();
        resultMap.put("o_Ret",0);
        resultMap.put("o_Msg","���ȳɹ�");
        SftpTaskExecuter demo3 = new SftpTaskExecuter();

        try {
            Thread t = new Thread(demo3);
            t.start();
        }catch (Exception e){
            resultMap.put("o_Ret",1);
            resultMap.put("o_Msg","�����߳�����ʧ�ܣ�");
        }

        return resultMap;

    }





    public void downloadTar(String dateString,int taskDetailId,String FCODE,int taskId){
        SftpTaskConfig sftpTaskConfig=new SftpTaskConfig();
        sftpTaskConfig.initLoginInfo(taskId);
        List<String>   remoteOKFiles=new ArrayList();
        SftpDataUtil sftpDataUtil=new SftpDataUtil();
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
                for(String remoteTargz:remoteOKFiles){
                    for(int i=0;i<sftpTaskConfig.getTalbeList().size();i++){
                        //������������
                        String tableName= sftpTaskConfig.getTalbeList().get(i).toString();
                        if(remoteTargz.indexOf(tableName)>=0){
                            sftpDataUtil.updateFTPTaskTarList(taskDetailId,remoteTargz+".del.tar.gz");
                        }
                    }
                }
            }else{
                sftpDataUtil.updateTaskLog(3,1,0,FCODE,dateString,"������"+remoteDataDir);
            }
        }catch (Exception e){
            sftpDataUtil.updateTaskLog(3,1,0,FCODE,dateString,"���ݰ����ؽ�ѹʧ�ܣ�");
        }finally {
            sftpUtils.disconnect();
        }


        System.out.println("remoteOKFiles:"+remoteOKFiles.toString());
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
                    System.out.println("localFileNames:"+localFileNames.toString());
                    //��һ��
                    for (String strOkfile : localFileNames) {

                        for(int i=0;i<sftpTaskConfig.getTalbeList().size();i++){
                            //������������
                            String tableName= sftpTaskConfig.getTalbeList().get(i).toString();
                            if(strOkfile.indexOf(tableName)>=0){
                                System.err.print(strOkfile);
                                String tableDel = strOkfile.substring(0, strOkfile.indexOf(".del"));
                                String varTarName = dateString + "_FRPT_" + tableDel + ".del.tar.gz";
                                //��del�ļ��������ؽ�ѹ�ɹ�
                                if (strOkfile.indexOf(".del") > 0&&sftpTaskConfig.getTalbeList().contains(tableDel)) {

                                    sftpDataUtil.updateDownloadFTPTaskTarList(3, varTarName, FCODE, dateString);//�����Ѿ���������
                                    sftpDataUtil.updateTaskLog(2, 1, 0, FCODE, dateString, "���ݰ�" + varTarName + "���ؽ�ѹ");
                                    sftpDataUtil.updateTaskLog(2, 2, sftpDataUtil.getCurrtTaskLogId(taskDetailId), FCODE, dateString, "���ݰ����ؽ�ѹ�ɹ�");
                                }
                            }
                        }

                    }
                    for (String strOkfile : localFileNames) {
                        for(int i=0;i<sftpTaskConfig.getTalbeList().size();i++){
                            //������������
                            String tableName= sftpTaskConfig.getTalbeList().get(i).toString();
                            if(strOkfile.indexOf(tableName)>=0){
                                System.err.print(strOkfile);
                                String tableDel = strOkfile.substring(0, strOkfile.indexOf(".del"));
                                String varTarName = dateString + "_FRPT_" + tableDel + ".del.tar.gz";
                                //��������,ֻ������������еı�����
                                if (strOkfile.indexOf(".del") > 0&&sftpTaskConfig.getTalbeList().contains(tableDel)) {//
                                    sftpDataUtil.updateFTPTaskTabList(1, varTarName, FCODE, tableDel);
                                    this.importData(locadataPath + "/" + strOkfile, dateString, FCODE, varTarName, tableDel,taskDetailId);
                                }
                            }
                        }

                    }
                }else{
                    sftpDataUtil.updateTaskLog(3,1,0,FCODE,dateString,"������"+locadataPath);
                }
            }catch (Exception e){
                    sftpDataUtil.updateTaskLog(3,1,0,FCODE,dateString,"���ݰ����ؽ�ѹʧ�ܣ�");
            }finally {
                sftpUtilLocal.disconnect();
            }

        }



    }


    public void importData(String i_path,String i_Date,String FCode,String varTarName,String i_tablename,int taskDetailId){
        SftpDataUtil sftpDataUtil=new SftpDataUtil();
        try{

            sftpDataUtil.updateTaskLog(2,1,0,FCode,i_Date,"["+i_tablename+"]���ݵ�����ʱ��");
            String importMsg= sftpDataUtil.importTempData(i_path,i_tablename);
            sftpDataUtil.updateTaskLog(2,2,sftpDataUtil.getCurrtTaskLogId(taskDetailId),FCode,i_Date,importMsg);


            sftpDataUtil.updateTaskLog(2,1,0,FCode,i_Date,"["+i_tablename+"]���ݾ������������ʽ��");
            importMsg= sftpDataUtil.importData(i_Date,i_tablename);
            sftpDataUtil.updateFTPTaskTabList(2,varTarName,FCode,i_tablename);
            sftpDataUtil.updateTaskLog(2,2,sftpDataUtil.getCurrtTaskLogId(taskDetailId),FCode,i_Date,importMsg);
        }catch (Exception e){
            sftpDataUtil.updateTaskLog(3,2,0,FCode,i_Date,"���ݱ�"+i_tablename+"ʧ��");
        }

    }


}
