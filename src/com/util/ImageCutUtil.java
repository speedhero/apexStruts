package com.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageCutUtil {
    public static void scale(String srcImageFile, double standardWidth,
                          double standardHeight) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件

            Image p_w_picpath = src.getScaledInstance((int) standardWidth,
                    (int) standardHeight, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage((int) standardWidth,
                    (int) standardHeight, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(p_w_picpath, 0, 0, null);
            g.dispose();
            ImageIO.write(tag, "JPG", new File(srcImageFile + "name.JPG"));// 输出到文件流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cut(String srcImageFile, double standardWidth,
                           double standardHeight) {
        try {
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度
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
     * 缩放后裁剪图片方法
     *
     * @param srcImageFile  源图像地址
     * @param dirImageFile  新图像地址
     * @param x  目标切片起点x坐标
     * @param y  目标切片起点y坐标
     * @param destWidth  目标切片宽度
     * @param destHeight  目标切片高度
     * @param finalWidth   缩放宽度
     * @param finalHeight  缩放高度
     */
    public static int abscut(String srcImageFile, String dirImageFile, int x,
                              int y, int destWidth, int destHeight,int finalWidth,int finalHeight) {
        try {
            Image img;
            ImageFilter cropFilter;
            //FileInputStream is = null;
            //ImageInputStream iis = null;
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度
            System.out.println("srcWidth:"+srcWidth+",srcHeight:"+srcHeight);
            if (srcWidth >= destWidth && srcHeight >= destHeight) {//保证源图高度大于截取高度
                Image image = bi.getScaledInstance(finalWidth, finalHeight, bi.SCALE_SMOOTH);//获取缩放后的图片大小
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
                img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null); // 绘制缩放后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "PNG", new File(dirImageFile));
                return  1;
            }else{//当图片小于目标图片长宽比例则设置为原图
               return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static int abscut(InputStream srcImageInputStream, OutputStream dirImageOutputStream, int x,
                             int y, int destWidth, int destHeight, int finalWidth, int finalHeight) {
        try {
            Image img;
            ImageFilter cropFilter;
            //FileInputStream is = null;
            //ImageInputStream iis = null;
            // 读取源图像
            BufferedImage bi = ImageIO.read(srcImageInputStream);
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度
            System.out.println("srcWidth:"+srcWidth+",srcHeight:"+srcHeight);
            if (srcWidth >= destWidth && srcHeight >= destHeight) {//保证源图高度大于截取高度
                Image image = bi.getScaledInstance(finalWidth, finalHeight, bi.SCALE_SMOOTH);//获取缩放后的图片大小
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
                img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null); // 绘制缩放后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "PNG",dirImageOutputStream);
                return  1;
            }else{//当图片小于目标图片长宽比例则设置为原图
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static void main(String[] args) {
        cut("f:/xxde.jpg", 200, 100);
    }
}
