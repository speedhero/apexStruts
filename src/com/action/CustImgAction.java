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
        //ͼƬ�ϴ������·��
        String imgUploadPath = "";
        String imgWebAppPath = "";
        ImgForm fileUploadForm = (ImgForm) form;
        FormFile file = fileUploadForm.getFile();


        //ͼƬ���ƣ���ǰ����
        //String imgFileId = DateUtil.dateToStr(new Date(), "yyyyMMddhhmmss");
        //ͼƬ���ƣ��ͻ���
        String custId=request.getParameter("custId");
        String imgFileId = custId;


        int imgWidth = 0;
        int imgHeight = 0;


        //ͨ��ʱ���file���ļ���׺,ƴд���ļ���
        java.util.Date date= new java.util.Date();

        String fileName =date.getTime() + file.getFileName().substring( file.getFileName().lastIndexOf("."));
        InputStream in=null;
        FTPClient ftpClient=null;
        try {

            //=========webĿ¼��ʽstart=========
           /*
            // ȡ�þ���·��
            imgUploadPath=this.getServlet().getServletContext().getRealPath("/") + IMGROOT; //web
            imgWebAppPath=imgUploadPath + fileName;
            //��ƴд�õ��ļ������浽������
            System.out.println(imgWebAppPath);
            File filetmp=new File(imgWebAppPath);
            // ���������
            FileOutputStream os = new FileOutputStream(imgWebAppPath);
            // ��ʼд�ļ�
            os.write(file.getFileData());
            // �ر���
            os.close();
            filetmp.setReadable(true);
            //���ͼƬ��С
            BufferedImage src = ImageIO.read(filetmp); // �����ļ�*/
            //int imgSrcWidth = src.getWidth(); // �õ�Դͼ��
            //=========webĿ¼��ʽend=========



            //=========FTPClientĿ¼��ʽstart=========
            String basePath="/";
            String filePath=FtpUtil.getPropertyMsg("ftp_uploadpath");
            String addr = FtpUtil.getPropertyMsg("ftp_url");
            //�˿ں�
            int port=Integer.valueOf(FtpUtil.getPropertyMsg("ftp_port")).intValue();
            //�û�������
            String username=FtpUtil.getPropertyMsg("ftp_username");
            String password=FtpUtil.getPropertyMsg("ftp_password");

            FtpUtil.uploadFile(addr,port,username,password,basePath,filePath,fileName,file.getInputStream());

            ftpClient = FtpUtil.initFTP(ftpClient);
            String imgPath=basePath+filePath+fileName;
             in = ftpClient.retrieveFileStream(new String(imgPath.getBytes("UTF-8"), "iso-8859-1"));
            BufferedImage src  = ImageIO.read(in);

            //=========FTPClientĿ¼��ʽend=========
            imgWidth  = src.getWidth();
            imgHeight = src.getHeight();
            //����ͼƬ�����ô�С����
           // ImageCutUtil.scale(imgWebAppPath,imgWidth,imgHeight);

          /*  if(filetmp.exists()){
                logger.info("����"+imgWidth+"*"+imgHeight+"ͼƬ�ɹ�");
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
            // �û������������ͼƬ�Ĵ�С
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



            //WEBӦ�ó����·��
            //String rootPath  = request.getSession().getServletContext().getRealPath("/");
            //=========FTPClientĿ¼��ʽstart=========
            String rootPath="/";
            String filePath=FtpUtil.getPropertyMsg("ftp_uploadpath");
            String imgPath=FtpUtil.getPropertyMsg("ftp_imgpath");
            String addr = FtpUtil.getPropertyMsg("ftp_url");
            //�˿ں�
            int port=Integer.valueOf(FtpUtil.getPropertyMsg("ftp_port")).intValue();
            //�û�������
            String username=FtpUtil.getPropertyMsg("ftp_username");
            String password=FtpUtil.getPropertyMsg("ftp_password");

            ftpClient = FtpUtil.initFTP(ftpClient);



            /*ͼƬ����:�Ե�ǰ����*/
            String imgFileId = DateUtil.dateToStr(new Date(), "yyyyMMddhhmmss");
            String imgName = imgFileId + System.currentTimeMillis() +  "." + imgFileExt1;
            /*��װͼƬ��ʵ����*/
            String createImgPath = rootPath + imgPath+imgName;
            logger.info("ԭͼƬ·��: " + oldImgPath + ",��ͼƬ·��: " + createImgPath);
            ftpClient.changeWorkingDirectory(oldImgPath);
            in = ftpClient.retrieveFileStream(new String(oldImgPath.getBytes("UTF-8"), "iso-8859-1"));
            ftpClient=FtpUtil.initFTP(ftpClient);
            OutputStream outputStream= ftpClient.storeFileStream(createImgPath);

            /*���м���ͼƬ����*/
            ftpClient.changeWorkingDirectory(createImgPath);
            int flag= ImageCutUtil.abscut(in, outputStream , x, y, w1, h1, w, h);
            outputStream.close();
            //FtpUtil.uploadFile(addr,port,username,password,rootPath,imgPath,imgName,file.getInputStream());
            String desc="����ɹ�";
           /* if(file.exists()){
                logger.info("����ͼƬ��С: "+w1+"*"+h1+"ͼƬ�ɹ�!");
            }*/
            if (flag<0){
                logger.info("����ͼƬ��С: "+w1+"*"+h1+"ͼƬʧ��!");
                desc="����ͼƬ��С: "+w1+"*"+h1+"ͼƬʧ��!";
            }

            Map jsonMap = new HashMap();
            jsonMap.put("url", "uploadImage.do?method=getImage&custId="+imgName.substring(0,imgName.indexOf(".")));
            jsonMap.put("flag", String.valueOf(flag));
            jsonMap.put("desc", desc);
            //photoImg = createImgPath;//ͷ��ȫ·�����浽���ݿ�
            JSONObject jsonObject = JSONObject.fromObject(jsonMap);
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonObject.toString());//�ϴ��ɹ���
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

            //�����ļ� FTPЭ�����棬�涨�ļ�������Ϊiso-8859-1�����Զ�ȡʱҪ���ļ���ת��Ϊiso-8859-1
            //���û�����ð���UTF-8������ȡ�����ǿ�ֵnull
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
