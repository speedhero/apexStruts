package com.action;

import com.util.CommonUtil;
import com.util.FtpUtil;
import com.util.ImageCutUtil;
import com.form.ImgForm;
import com.util.DateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class CustImgAction extends DispatchAction {

    Logger logger=Logger.getLogger("CustImgAction");


    public ActionForward uploadHeadPhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String imgFileExt = "";
        //图片上传的相对路径
        String imgUploadPath = "";
        String imgWebAppPath = "";
        ImgForm fileUploadForm = (ImgForm) form;
        FormFile file = fileUploadForm.getFile();


        //图片名称：当前日期
        //String imgFileId = DateUtil.dateToStr(new Date(), "yyyyMMddhhmmss");
        //图片名称：客户号
        String custId=request.getParameter("custId");
        String imgFileId = custId;


        int imgWidth = 0;
        int imgHeight = 0;


        //通过时间和file的文件后缀,拼写出文件名
        java.util.Date date= new java.util.Date();

        String fileName =date.getTime() + file.getFileName().substring( file.getFileName().lastIndexOf("."));
        InputStream in=null;
        FTPClient ftpClient=null;
        try {

            //=========web目录方式start=========
           /*
            // 取得绝对路径
            imgUploadPath=this.getServlet().getServletContext().getRealPath("/") + IMGROOT; //web
            imgWebAppPath=imgUploadPath + fileName;
            //将拼写好的文件名保存到对象中
            System.out.println(imgWebAppPath);
            File filetmp=new File(imgWebAppPath);
            // 定义输出流
            FileOutputStream os = new FileOutputStream(imgWebAppPath);
            // 开始写文件
            os.write(file.getFileData());
            // 关闭流
            os.close();
            filetmp.setReadable(true);
            //检查图片大小
            BufferedImage src = ImageIO.read(filetmp); // 读入文件*/
            //int imgSrcWidth = src.getWidth(); // 得到源图宽
            //=========web目录方式end=========



            //=========FTPClient目录方式start=========
            String basePath="/";
            String filePath=FtpUtil.getPropertyMsg("ftp_uploadpath");
            String addr = FtpUtil.getPropertyMsg("ftp_url");
            //端口号
            int port=Integer.valueOf(FtpUtil.getPropertyMsg("ftp_port")).intValue();
            //用户名密码
            String username=FtpUtil.getPropertyMsg("ftp_username");
            String password=FtpUtil.getPropertyMsg("ftp_password");

            FtpUtil.uploadFile(addr,port,username,password,basePath,filePath,fileName,file.getInputStream());

            ftpClient = FtpUtil.initFTP(ftpClient);
            String imgPath=basePath+filePath+fileName;
             in = ftpClient.retrieveFileStream(new String(imgPath.getBytes("UTF-8"), "iso-8859-1"));
            BufferedImage src  = ImageIO.read(in);

            //=========FTPClient目录方式end=========
            imgWidth  = src.getWidth();
            imgHeight = src.getHeight();
            //按照图片的设置大小生成
           // ImageCutUtil.scale(imgWebAppPath,imgWidth,imgHeight);

          /*  if(filetmp.exists()){
                logger.info("创建"+imgWidth+"*"+imgHeight+"图片成功");
            }*/
            imgFileExt=CommonUtil.ext(fileName);

            request.getSession().setAttribute("oldImgPath",basePath+filePath+fileName);
            request.getSession().setAttribute("imgFileExt1",imgFileExt);
            request.getSession().setAttribute("imgRoot",basePath+filePath);
            request.getSession().setAttribute("width1",imgWidth);
            request.getSession().setAttribute("height1",imgHeight);

            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("1");
            return null;

        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return mapping.findForward("fal");

        } catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
            return mapping.findForward("fal");
        }finally {
            if(in!=null) {
                try {
                    in.close();
                    FtpUtil.destroy(ftpClient);
                } catch (IOException e) {
                }
            }
        }

    }

    public ActionForward uploadImageCut(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
        InputStream in=null;
        OutputStream out=null;
        FTPClient ftpClient=null;
        try {
            logger.info("x:"+request.getParameter("x")+",y:"+request.getParameter("y")+
                    ",w1:"+request.getParameter("w1")+",h1:"+request.getParameter("h1")+
                    ",w:"+request.getParameter("w")+",h:"+request.getParameter("h"));
            // 用户经过剪辑后的图片的大小
            Integer x = Integer.parseInt(request.getParameter("x"));
            Integer y = Integer.parseInt(request.getParameter("y"));
            Integer w1 = Integer.parseInt(request.getParameter("w1"));
            Integer h1 = Integer.parseInt(request.getParameter("h1"));
            Integer w = Integer.parseInt(request.getParameter("w"));
            Integer h = Integer.parseInt(request.getParameter("h"));

            String  oldImgPath = request.getSession().getAttribute("oldImgPath").toString();
            String  imgFileExt1 =request.getSession().getAttribute("imgFileExt1").toString();
            String  imgRoot=   request.getSession().getAttribute("imgRoot").toString();
            Integer width1 =  (Integer)request.getSession().getAttribute("width1");
            Integer height1 = (Integer)request.getSession().getAttribute("height1");



            //WEB应用程序根路径
            //String rootPath  = request.getSession().getServletContext().getRealPath("/");
            //=========FTPClient目录方式start=========
            String rootPath="/";
            String filePath=FtpUtil.getPropertyMsg("ftp_uploadpath");
            String imgPath=FtpUtil.getPropertyMsg("ftp_imgpath");
            String addr = FtpUtil.getPropertyMsg("ftp_url");
            //端口号
            int port=Integer.valueOf(FtpUtil.getPropertyMsg("ftp_port")).intValue();
            //用户名密码
            String username=FtpUtil.getPropertyMsg("ftp_username");
            String password=FtpUtil.getPropertyMsg("ftp_password");

            ftpClient = FtpUtil.initFTP(ftpClient);



            /*图片名称:以当前日期*/
            String imgFileId = DateUtil.dateToStr(new Date(), "yyyyMMddhhmmss");
            String imgName = imgFileId + System.currentTimeMillis() +  "." + imgFileExt1;
            /*组装图片真实名称*/
            String createImgPath = rootPath + imgPath+imgName;
            logger.info("原图片路径: " + oldImgPath + ",新图片路径: " + createImgPath);
            ftpClient.changeWorkingDirectory(oldImgPath);
            in = ftpClient.retrieveFileStream(new String(oldImgPath.getBytes("UTF-8"), "iso-8859-1"));
            ftpClient=FtpUtil.initFTP(ftpClient);
            OutputStream outputStream= ftpClient.storeFileStream(createImgPath);

            /*进行剪切图片操作*/
            ftpClient.changeWorkingDirectory(createImgPath);
            int flag= ImageCutUtil.abscut(in, outputStream , x, y, w1, h1, w, h);
            outputStream.close();
            //FtpUtil.uploadFile(addr,port,username,password,rootPath,imgPath,imgName,file.getInputStream());
            String desc="保存成功";
           /* if(file.exists()){
                logger.info("剪切图片大小: "+w1+"*"+h1+"图片成功!");
            }*/
            if (flag<0){
                logger.info("剪切图片大小: "+w1+"*"+h1+"图片失败!");
                desc="剪切图片大小: "+w1+"*"+h1+"图片失败!";
            }

            Map jsonMap = new HashMap();
            jsonMap.put("url", "uploadImage.do?method=getImage&custId="+imgName.substring(0,imgName.indexOf(".")));
            jsonMap.put("flag", String.valueOf(flag));
            jsonMap.put("desc", desc);
            //photoImg = createImgPath;//头像全路径保存到数据库
            JSONObject jsonObject = JSONObject.fromObject(jsonMap);
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonObject.toString());//上传成功！
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            if(in!=null) {
                try {
                    in.close();
                    FtpUtil.destroy(ftpClient);
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public ActionForward getImage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception{
        FTPClient ftp=null;
        InputStream in = null;
        OutputStream os = null;
        String imgPath="/";
        ImgForm custfileUpForm = (ImgForm) form;
        String custId = request.getParameter("custId");
        imgPath=FtpUtil.getPropertyMsg("ftp_imgpath");
        imgPath=imgPath+custId+".JPG";
        try {
            ftp= FtpUtil.initFTP(ftp);

            //下载文件 FTP协议里面，规定文件名编码为iso-8859-1，所以读取时要将文件名转码为iso-8859-1
            //如果没有设置按照UTF-8读，获取的流是空值null
            in = ftp.retrieveFileStream(new String(imgPath.getBytes("UTF-8"), "iso-8859-1"));
            String picType = imgPath.split("\\.")[1];
            BufferedImage bufImg = null;
            bufImg = ImageIO.read(in);
            os = response.getOutputStream();
            ImageIO.write(bufImg, picType, os);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in!=null) {
                try {
                    in.close();
                    FtpUtil.destroy(ftp);
                } catch (IOException e) {
                }
            }
        }
        return  null;
    }







}
