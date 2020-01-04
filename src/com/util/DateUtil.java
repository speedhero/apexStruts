package com.util;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * <b>
 *    ��������   ������
 * </b>
 * @author kangxu
 *
 */
public class DateUtil {

    /**
     * �ַ�������ת���ڸ�ʽ����
     * @param strDate �ַ�������
     * @param dateFormat �ַ������ڸ�ʽ
     * @return
     */
    public static Date strToDate(String strDate,String dateFormat) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (Exception e) {
            throw new Exception("���ڸ�ʽת������");
        }
        return date;
    }

    /**
     * ������ת��Ϊ�ַ�������
     * @param date ����
     * @param tarDateFormat ���ڸ�ʽ
     * @return
     */
    public static String dateToStr(Date date,String tarDateFormat){

        return new SimpleDateFormat(tarDateFormat).format(date);
    }

    /**
     * ת�����ڸ�ʽ
     * @param strDate �ַ�������
     * @param srcFormat ԭʼ��ʽ
     * @param tarFormat Ŀ���ʽ
     * @return
     */
    public static String strToStr(String strDate,String srcFormat,String tarFormat) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(srcFormat);
        try {
            Date date = sdf.parse(strDate);
            sdf = new SimpleDateFormat(tarFormat);
            strDate = sdf.format(date);
        } catch (Exception e) {
            throw new Exception("���ڸ�ʽת������");
        }
        return strDate;
    }

}