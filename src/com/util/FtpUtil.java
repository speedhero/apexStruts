package com.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.Properties;

public class FtpUtil {
    /**
     * Description: ��FTP�������ϴ��ļ�
     * @param host FTP������hostname
     * @param port FTP�������˿�
     * @param username FTP��¼�˺�
     * @param password FTP��¼����
     * @param basePath FTP����������Ŀ¼
     * @param filePath FTP�������ļ����·������������ڴ�ţ�/2015/01/01���ļ���·��ΪbasePath+filePath
     * @param filename �ϴ���FTP�������ϵ��ļ���
     * @param input ������
     * @return �ɹ�����true�����򷵻�false
     */
    public static boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// ����FTP������
            // �������Ĭ�϶˿ڣ�����ʹ��ftp.connect(host)�ķ�ʽֱ������FTP������
            ftp.login(username, password);// ��¼
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //�л����ϴ�Ŀ¼
            if (!ftp.changeWorkingDirectory(basePath+filePath)) {
                //���Ŀ¼�����ڴ���Ŀ¼
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir))
                    {
                        continue;
                    }
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //�����ϴ��ļ�������Ϊ����������
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //�ϴ��ļ�
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * Description: ��FTP�����������ļ�
     * @param host FTP������hostname
     * @param port FTP�������˿�
     * @param username FTP��¼�˺�
     * @param password FTP��¼����
     * @param remotePath FTP�������ϵ����·��
     * @param fileName Ҫ���ص��ļ���
     * @param localPath ���غ󱣴浽���ص�·��
     * @return
     */
    public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
                                       String fileName, String localPath) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // �������Ĭ�϶˿ڣ�����ʹ��ftp.connect(host)�ķ�ʽֱ������FTP������
            ftp.login(username, password);// ��¼
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            ftp.changeWorkingDirectory(remotePath);// ת�Ƶ�FTP������Ŀ¼
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());

                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }

            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    //�ر�FTP�ͻ���
    public static void destroy(FTPClient ftp) throws IOException {
        if(ftp != null){
            ftp.disconnect();
            ftp = null;
        }
    }

    public  static   String getPropertyMsg(String key) throws IOException{
        //InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("ftp.properties");
        //InputStream in = CustImgAction.class.getResourceAsStream("/com/ftp.properties");
        InputStream in = FtpUtil.class.getClassLoader().getResourceAsStream("ftp.properties");
        Properties properties=new Properties();
        properties.load(in);
        return properties.getProperty(key);
    }



    //ͨ����ȡftp.properties�ļ�����ʼ��ftp�Ĳ���
    public static FTPClient initFTP(FTPClient ftp) throws IOException {
        //url
        String addr = getPropertyMsg("ftp_url");
        //�˿ں�
        int port=Integer.valueOf(getPropertyMsg("ftp_port")).intValue();
        //�û�������
        String username=getPropertyMsg("ftp_username");
        String password=getPropertyMsg("ftp_password");
        ftp = new FTPClient();
        ftp.connect(addr,port);
        ftp.login(username,password);
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftp.setControlEncoding("GBK");
        ftp.setBufferSize(1024*1024*10); //���û������ϴ��ٶ�Ϊ10M��Ĭ��Ϊ1K
        ftp.setFileType(FTP.BINARY_FILE_TYPE);//�����ϴ���ʽλ�ֽ�
        ftp.enterLocalPassiveMode();//Switch to passive mode
        return ftp;
    }

    public static void main(String[] args) {
        try {
            FileInputStream in=new FileInputStream(new File("D:\\temp\\image\\gaigeming.jpg"));
            boolean flag = uploadFile("localhost", 21, "anonymous", "123", "","upload", "gaigeming.jpg", in);
            System.out.println(flag);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
