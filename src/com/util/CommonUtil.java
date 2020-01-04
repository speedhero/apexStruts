package com.util;

import java.io.File;

public class CommonUtil {

    /*
     * 判断目录是否存在，不存在就新建,多级父目录
     * @param path
     */
    public static void checkDirs(String path) {
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 获取文件扩展名
     * @return
     */
    public static String ext(String filename) {
        int index = filename.lastIndexOf(".");

        if (index == -1) {
            return null;
        }
        String result = filename.substring(index + 1);
        return result;
    }

}
