package com.util;

import java.io.File;

public class CommonUtil {

    /*
     * �ж�Ŀ¼�Ƿ���ڣ������ھ��½�,�༶��Ŀ¼
     * @param path
     */
    public static void checkDirs(String path) {
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * ��ȡ�ļ���չ��
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
