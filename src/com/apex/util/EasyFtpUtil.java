package com.apex.util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.util.FtpUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;




public class EasyFtpUtil {


    private static FTPClient ftpClient = new FTPClient();
    private static String encoding = System.getProperty("file.encoding");

    private static EasyFtpUtil instance;
    Logger log = Logger.getLogger("FtpUtil");

    /**
     * ʵ����FtpUtil
     *
     * @return
     */
    public synchronized static EasyFtpUtil getInstance() {
        if (instance == null) {
            instance = new EasyFtpUtil();
        }
        return instance;
    }


    /**
     * ����FTP������
     */
    public boolean login(String url, int port, String username,
                         String password) {
        try {
            int reply;
            ftpClient.connect(url); // �������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������������ ftp.connect(url, port);// ����FTP������
            ftpClient.login(username, password); // ��¼
            ftpClient.setControlEncoding(encoding);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) { // �����Ƿ����ӳɹ�
                log.info("FTP����ʧ��");
                ftpClient.disconnect();
                return false;
            }
            log.info("FTP���ӳɹ�");
        } catch (IOException e) {
            e.printStackTrace();
            closeCon(); //�ر�
        }
        return true;
    }


    /**
     * Description: ��FTP�������ϴ��ļ�
     *
     * @param url      FTP������hostname
     * @param port     FTP�������˿�
     * @param username FTP��¼�˺�
     * @param password FTP��¼����
     * @param path     FTP����������Ŀ¼,����Ǹ�Ŀ¼��Ϊ��/��
     * @param filename �ϴ���FTP�������ϵ��ļ���
     * @param input    �����ļ�������
     * @return �ɹ�����true�����򷵻�false
     * @Version1.0
     */
    public boolean uploadFile(String ftpPath, String filePath, String fileName) {
        boolean result = false;
        try {
            if (ftpClient != null) {
                FileInputStream input = new FileInputStream(new File(filePath + fileName));
                boolean change = ftpClient.changeWorkingDirectory(ftpPath); // ת�ƹ���Ŀ¼��ָ��Ŀ¼��
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                if (change) {
                    log.info("�ɹ��л�Ŀ¼��" + ftpPath);
                    result = ftpClient.storeFile(new String(fileName.getBytes("GBK"), "iso-8859-1"), input);
                    if (result) {
                        log.info(fileName + ",�ϴ��ɹ�!");
                    } else {
                        log.info(fileName + ",�ϴ�ʧ��");
                    }
                }
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * �������ļ��ϴ���FTP��������
     * <p>
     * <p>
     * public static void testUpLoadFromDisk(String url, int port, String username,
     * String password, String path, String filename,String filepath) {
     * try {
     * FileInputStream in = new FileInputStream(new File(filepath));
     * boolean flag = uploadFile(url, port, username,password, path, filename, in);
     * System.out.println(flag);
     * } catch (FileNotFoundException e) {
     * e.printStackTrace();
     * }
     * }
     * /
     * /**
     * ɾ��FTP�ϵ��ļ�
     */

    public static boolean removeFile(String srcFname) {
        boolean flag = false;
        if (ftpClient != null) {
            try {
                flag = ftpClient.deleteFile(srcFname);
            } catch (IOException e) {
                e.printStackTrace();
                closeCon();
            }
        }
        return flag;
    }

    /**
     * ����FTP�ϵ��ļ�
     */

    public static boolean renameFile(String srcFname, String targetFname) {
        boolean flag = false;
        if (ftpClient != null) {
            try {
                flag = ftpClient.rename(srcFname, targetFname);
            } catch (IOException e) {
                e.printStackTrace();
                closeCon();
            }
        }
        return flag;
    }
    private static String getFormatFileName(String str,String regex){

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()){
          return matcher.group(0).toString();
        }
        return null;
    }

    private static List<Map> getRegexFileList(String directroy,  String regex,String srcChar, String targetChar){
        List<Map> fileList=new ArrayList();
        try {
            FTPFile[] fs =  ftpClient.listFiles(directroy);
            for (FTPFile ff : fs) {
                String fileName=ff.getName();
                if(getFormatFileName(fileName,regex)!=null){

                    String formatName=replaceAll(fileName,srcChar,targetChar);
                    if(!fileName.equals(formatName)){//������ֲ�������滻
                        Map fileMap=new HashMap();
                        fileMap.put("srcFileName",fileName);
                        fileMap.put("trgFileName",formatName);
                        fileList.add(fileMap);
                    }
                }

            }
            return  fileList;
        }catch (IOException e ){
            e.printStackTrace();
            closeCon();
            return  null;
        }

    }

    public static boolean renameFormatFile(String directroy,String srcChar, String targetChar) {
        boolean flag = false;
        List<Map> fileList=null;
        if (ftpClient != null) {
            fileList= getRegexFileList(directroy,"[0-9]{4}[-/][0-9]{1,2}[-/][0-9]{1,2}.*.tar.gz",srcChar,targetChar);

            System.out.println(fileList);
            for (Map fileMap:fileList){
                String srcFileName=  (String) fileMap.get("srcFileName");
                //RemoteCommandUtil.execCmd(" cd /root/ftp/daydata ");
               // RemoteCommandUtil.execCmd(" tar zxvf "+srcFileName+"  .");
                //RemoteCommandUtil.execCmd(" cd "+directroy+"/"+srcFileName+"");


              String trgFileName=  (String) fileMap.get("trgFileName");
               // renameFile(srcFileName,trgFileName);
            }
            System.out.println(fileList);
        }
        return flag;
    }

    public static void main(String[] args) {
        EasyFtpUtil easyFtpUtil=new EasyFtpUtil();
       // easyFtpUtil.login("localhost",21,"apex","123456");
        easyFtpUtil.login("66.10.61.120",21,"ftpadmin","278495617");
        EasyFtpUtil.renameFormatFile("/root/ftp/daydata","-","");
    }


    // �滻ȫ��
    public static String replaceAll(String parent,String targetEle,String replaceEle ) {
        if( parent.indexOf(targetEle) == -1 ) {
            return parent;
        }else {
            int beginIndex = parent.indexOf(targetEle);
            int endIndex = beginIndex + targetEle.length();
            return parent.substring(0,beginIndex) + replaceEle + replaceAll(parent.substring(endIndex), targetEle, replaceEle);
        }

    }






    /**
     * <p>����ftp����</p>
     */
    public static void closeCon() {
        if (ftpClient != null) {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * ���ص����ļ�
     */
    public static boolean downloadFile(String ftpPath, String filePath, String fileName) {
        try {
            if (ftpClient != null) {
                FileInputStream input = new FileInputStream(new File(filePath + fileName));
                boolean change = ftpClient.changeWorkingDirectory(ftpPath); // ת�ƹ���Ŀ¼��ָ��Ŀ¼��
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                if (change) {
                    FTPFile[] fs = ftpClient.listFiles();
                    for (FTPFile ff : fs) {
                        if (ff.getName().equals(fileName)) {
                            File localFile = new File(filePath + "/" + ff.getName());
                            OutputStream is = new FileOutputStream(localFile);
                            ftpClient.retrieveFile(ff.getName(), is);
                            is.close();
                        }
                    }
                }
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}

