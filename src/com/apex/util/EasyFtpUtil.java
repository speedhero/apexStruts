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
     * 实例化FtpUtil
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
     * 连接FTP服务器
     */
    public boolean login(String url, int port, String username,
                         String password) {
        try {
            int reply;
            ftpClient.connect(url); // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器，否则 ftp.connect(url, port);// 连接FTP服务器
            ftpClient.login(username, password); // 登录
            ftpClient.setControlEncoding(encoding);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) { // 检验是否连接成功
                log.info("FTP连接失败");
                ftpClient.disconnect();
                return false;
            }
            log.info("FTP连接成功");
        } catch (IOException e) {
            e.printStackTrace();
            closeCon(); //关闭
        }
        return true;
    }


    /**
     * Description: 向FTP服务器上传文件
     *
     * @param url      FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param path     FTP服务器保存目录,如果是根目录则为“/”
     * @param filename 上传到FTP服务器上的文件名
     * @param input    本地文件输入流
     * @return 成功返回true，否则返回false
     * @Version1.0
     */
    public boolean uploadFile(String ftpPath, String filePath, String fileName) {
        boolean result = false;
        try {
            if (ftpClient != null) {
                FileInputStream input = new FileInputStream(new File(filePath + fileName));
                boolean change = ftpClient.changeWorkingDirectory(ftpPath); // 转移工作目录至指定目录下
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                if (change) {
                    log.info("成功切换目录到" + ftpPath);
                    result = ftpClient.storeFile(new String(fileName.getBytes("GBK"), "iso-8859-1"), input);
                    if (result) {
                        log.info(fileName + ",上传成功!");
                    } else {
                        log.info(fileName + ",上传失败");
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
     * 将本地文件上传到FTP服务器上
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
     * 删除FTP上的文件
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
     * 改名FTP上的文件
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
                    if(!fileName.equals(formatName)){//如果名字不相等则替换
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


    // 替换全部
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
     * <p>销毁ftp连接</p>
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
     * 下载单个文件
     */
    public static boolean downloadFile(String ftpPath, String filePath, String fileName) {
        try {
            if (ftpClient != null) {
                FileInputStream input = new FileInputStream(new File(filePath + fileName));
                boolean change = ftpClient.changeWorkingDirectory(ftpPath); // 转移工作目录至指定目录下
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

