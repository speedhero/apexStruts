package  com.apex.bank.sftp;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
/**
 *
 *@auther keven
 *@date 2018/12/7 14:55
 *@desc 远程执行linux服务器
 *@since V1.0
 */
public class RemoteCommandUtil {

    public static void main(String[] args) throws Exception {
        //String userName = "rick";// 用户名
        //String password = "278495617";// 密码
        //String host = "192.168.56.99";// 服务器地址

        String userName = "ftpadmin";// 用户名
        String password = "278495617";// 密码
        //String userName = "root";// 用户名
        //String password = "tempP@ssw0rd";// 密码
        String host = "66.10.61.120";// 服务器地址
        int port = 22;// 端口号
        RemoteCommandUtil remoteCommandUtil=new RemoteCommandUtil();
        remoteCommandUtil.execCmd("cd /home/ftpdata/; sh download.sh 2020-12-08 ","root","tempP@ssw0rd","66.10.61.122",22);
        //execCmd(" ls; ");//
    }





    public   static boolean execCmd(String cmdLine,String userName,String password,String host,int port ) {
        try {
            JSch jsch = new JSch(); // 创建JSch对象

            Session session = jsch.getSession(userName, host, port); // 根据用户名，主机ip，端口获取一个Session对象
            session.setPassword(password); // 设置密码
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config); // 为Session对象设置properties
            int timeout = 60000000;
            session.setTimeout(timeout); // 设置timeout时间
            session.connect(); // 通过Session建立链接
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(cmdLine);
            channelExec.setInputStream(null);
            //用来输出执行linux命令或sh文件时系统错误的输出，这样的话会造成一个问题，就是使用log4j的方法无效，后面的log.info以及返回后其他类中的log都将失效，最终tomcat的catalina.out的日志就永远停止了。。。。所以使用时，一定要注释掉哦~或者不用哦~使用下面reader读取linux中的输出信息就行了哦~
            //channelExec.setErrStream(System.err);
            channelExec.connect();
            InputStream in = channelExec.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
             String buf = null;
            StringBuffer sb = new StringBuffer();
            while ((buf = reader.readLine()) != null) {
                sb.append(buf);
                System.out.println(buf);// 打印控制台输出
            }
            reader.close();
            channelExec.disconnect();
            if (null != session) {
                session.disconnect();
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
