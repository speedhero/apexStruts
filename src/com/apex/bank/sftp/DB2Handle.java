package com.apex.bank.sftp;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class DB2Handle {

    private static String url;
    private static String user;
    private static String password;
    private static String driver;
    private static DataSource dataSource;
    static{
        try {
            String fileName="db2.properties";
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream(new File("F:\\webApp\\apexStruts\\src\\com\\apex\\bank\\sftp\\db2.properties"));

            //InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

            //String path=DB2Handle.class.getResource("/").getPath();//得到工程名WEB-INF/classes/路径
            //path=path.substring(1, path.indexOf("classes"));//从路径字符串中取出工程路径
            //InputStream fis=new FileInputStream(path+fileName);
            properties.load(fis);
            if (fis == null) {
                throw new FileNotFoundException(fileName + " file is not found");
            }
            properties.load(fis);
            url = properties.getProperty("url");
            user = properties.getProperty("username");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");
            Class.forName(driver);

            dataSource= DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {

            e.getMessage();
        }

    }

    public static DataSource getDataSource() throws SQLException{
        return dataSource;
    }




    public static Connection getConnection() throws SQLException{
       // DataSource dataSource= com.apex.form.DataAccess.getDataSource();
         //Connection connection =dataSource.getConnection();

          if(url==null||user==null||password==null){
            try {
                String fileName="db2.properties";
                Properties properties = new Properties();
                //FileInputStream fis = new FileInputStream(new File("F:\\webApp\\GisMap\\src\\main\\java\\com\\apex\\bank\\com.apex.bank.gismap\\db2.properties"));

                //InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

                String path= DB2Handle.class.getResource("/").getPath();//得到工程名WEB-INF/classes/路径
                path=path.substring(1, path.indexOf("classes"));//从路径字符串中取出工程路径
                System.out.println("DB2Handle.getConnection PATH:"+path);
                InputStream fis=new FileInputStream(path+fileName);
                properties.load(fis);
                if (fis == null) {
                    throw new FileNotFoundException(fileName + " file is not found");
                }
                properties.load(fis);
                url = properties.getProperty("url");
                user = properties.getProperty("username");
                password = properties.getProperty("password");
                driver = properties.getProperty("driver");
                Class.forName(driver);
            } catch (Exception e) {

                e.getMessage();
            }
        }
       Connection connection = DriverManager.getConnection(url, user, password);

        return connection;
    }
    public static void close(ResultSet resultSet, PreparedStatement preparedStatement,
                             Connection connection){

        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if(preparedStatement != null ){
                preparedStatement.close();
            }
            if(connection != null ){
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


    public static Map<String,List<String>> handle(ResultSet set) throws SQLException {
        Map<String,List<String>> map = new HashMap<String, List<String>>();

        ResultSetMetaData rsmd =set.getMetaData();
        int count = rsmd.getColumnCount();

        //先生成几个list对象
        @SuppressWarnings("unchecked")
        List<String> [] lists = new List[count];

        for (int i=0;i<lists.length;i++) {
            lists[i] = new ArrayList<String>();
            map.put(rsmd.getColumnName(i+1), lists[i]);
        }
        /**
         * 这里是获取的一条一条
         */
        while(set.next()){
            for(int i=0 ;i<lists.length;i++){
                lists[i].add(set.getString(i+1));
            }
        }

        return map;
    }

    //释放连接回连接池
    public static void close(Connection conn, Statement pst, ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
               // logger.error("Exception in C3p0Utils!", e);
            }
        }
        if(pst!=null){
            try {
                pst.close();
            } catch (SQLException e) {
                //logger.error("Exception in C3p0Utils!", e);
            }
        }

        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
               // logger.error("Exception in C3p0Utils!", e);
            }
        }
    }
}
