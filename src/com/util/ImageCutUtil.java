package com.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

public class ImageCutUtil {
    public static void scale(String srcImageFile, double standardWidth,
                          double standardHeight) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile)); // �����ļ�

            Image p_w_picpath = src.getScaledInstance((int) standardWidth,
                    (int) standardHeight, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage((int) standardWidth,
                    (int) standardHeight, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(p_w_picpath, 0, 0, null);
            g.dispose();
            ImageIO.write(tag, "JPG", new File(srcImageFile + "name.JPG"));// ������ļ���
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cut(String srcImageFile, double standardWidth,
                           double standardHeight) {
        try {
            Image img;
            ImageFilter cropFilter;
            // ��ȡԴͼ��
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getWidth(); // Դͼ���
            int srcHeight = bi.getHeight(); // Դͼ�߶�
            if (srcWidth > standardWidth && srcHeight > standardHeight) {
                Image p_w_picpath = bi.getScaledInstance(srcWidth, srcHeight,
                        Image.SCALE_DEFAULT);
                int w = 0;
                int h = 0;

                double wScale = srcWidth / standardWidth;
                double hScale = srcHeight / standardHeight;
                int srcWidth2;
                int srcHeight2;
                if (wScale > hScale) {

                    srcWidth2 = (int) (standardWidth * hScale);
                    w = (srcWidth - srcWidth2) / 2;
                    srcWidth = srcWidth2;
                    h = 0;
                } else {
                    srcHeight2 = (int) (standardHeight * wScale);
                    h = (srcHeight - srcHeight2) / 2;
                    srcHeight = srcHeight2;
                    w = 0;
                }

                cropFilter = new CropImageFilter(w, h, srcWidth, srcHeight);
                img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(p_w_picpath.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(srcWidth, srcHeight,
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null);
                g.dispose();
                ImageIO.write(tag, "JPEG", new File(srcImageFile));
                scale(srcImageFile, standardWidth, standardHeight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ���ź�ü�ͼƬ����
     *
     * @param srcImageFile  Դͼ���ַ
     * @param dirImageFile  ��ͼ���ַ
     * @param x  Ŀ����Ƭ���x����
     * @param y  Ŀ����Ƭ���y����
     * @param destWidth  Ŀ����Ƭ���
     * @param destHeight  Ŀ����Ƭ�߶�
     * @param finalWidth   ���ſ��
     * @param finalHeight  ���Ÿ߶�
     */
    public static void abscut(String srcImageFile, String dirImageFile, int x,
                              int y, int destWidth, int destHeight,int finalWidth,int finalHeight) {
        try {
            Image img;
            ImageFilter cropFilter;
            //FileInputStream is = null;
            //ImageInputStream iis = null;
            // ��ȡԴͼ��
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getWidth(); // Դͼ���
            int srcHeight = bi.getHeight(); // Դͼ�߶�
            System.out.println("srcWidth:"+srcWidth+",srcHeight:"+srcHeight);
            if (srcWidth >= destWidth && srcHeight >= destHeight) {//��֤Դͼ�߶ȴ��ڽ�ȡ�߶�
                Image image = bi.getScaledInstance(finalWidth, finalHeight, bi.SCALE_SMOOTH);//��ȡ���ź��ͼƬ��С
                // �ĸ������ֱ�Ϊͼ���������Ϳ��
                // ��: CropImageFilter(int x,int y,int width,int height)
                cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
                img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null); // �������ź��ͼ
                g.dispose();
                // ���Ϊ�ļ�
                ImageIO.write(tag, "PNG", new File(dirImageFile));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        cut("f:/xxde.jpg", 200, 100);
    }
}
