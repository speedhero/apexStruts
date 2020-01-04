package com.action;

import com.util.CommonUtil;
import com.util.ImageCutUtil;
import com.form.ImgForm;
import com.util.DateUtil;
import net.sf.json.JSONObject;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CustImgAction extends DispatchAction {

    Logger logger=Logger.getLogger("CustImgAction");

    private static final String IMGROOT ="/upload/" ;
    String oldImgPath = null;//图片后缀
    String imgFileExt1 = null;
    String imgRoot = null;
    Integer width1 = 0;
    Integer height1 = 0;

    public ActionForward uploadHeadPhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String imgFileExt = "";
        //图片上传的相对路径
        String imgUploadPath = "";
        String imgWebAppPath = "";
        ImgForm fileUploadForm = (ImgForm) form;
        FormFile file = fileUploadForm.getFile();


        //图片名称：当前日期
        String imgFileId = DateUtil.dateToStr(new Date(), "yyyyMMddhhmmss");
        //图片初始化高度与宽度
        //String width = null;
        //String height = null;



        int imgWidth = 0;
        int imgHeight = 0;


        //通过时间和file的文件后缀,拼写出文件名
        java.util.Date date= new java.util.Date();

        String fileName =date.getTime()
                + file.getFileName().substring( file.getFileName().lastIndexOf("."));
        // 取得绝对路径

        System.out.println(fileName);
        imgUploadPath=this.getServlet().getServletContext().getRealPath("/") + IMGROOT;
        CommonUtil.checkDirs(imgUploadPath);
        imgWebAppPath=imgUploadPath + fileName;

        //将拼写好的文件名保存到对象中
        System.out.println(imgWebAppPath);


        try {



            File filetmp=new File(imgWebAppPath);
            // 定义输出流
            FileOutputStream os = new FileOutputStream(imgWebAppPath);
            // 开始写文件
            os.write(file.getFileData());
            // 关闭流
            os.close();
            filetmp.setReadable(true);
            //检查图片大小
            BufferedImage src = ImageIO.read(filetmp); // 读入文件
            //int imgSrcWidth = src.getWidth(); // 得到源图宽
            imgWidth  = src.getWidth();
            imgHeight = src.getHeight();
            //按照图片的设置大小生成
            ImageCutUtil.scale(imgWebAppPath,imgWidth,imgHeight);

            if(filetmp.exists()){
                logger.info("创建"+imgWidth+"*"+imgHeight+"图片成功");
            }
            imgFileExt=CommonUtil.ext(imgWebAppPath);
            oldImgPath=IMGROOT+fileName;
            imgFileExt1=imgFileExt;
            imgRoot=IMGROOT;
            width1=imgWidth;
            height1=imgHeight;


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
        }


      /*  *//*图片后缀名*//*
        String imgFileExt = "";
        *//*图片上传的相对路径*//*
        String imgUploadPath = "";
        String imgWebAppPath = "";
        *//*图片名称：当前日期*//*
        String imgFileId = DateUtil.dateToStr(new Date(), "yyyyMMddhhmmss");
        //图片初始化高度与宽度
        //String width = null;
        //String height = null;



        int imgWidth = 0;
        int imgHeight = 0;
        try {
            ServletConfig servletConfig=getServlet().getServletConfig();
            com.jspsmart.upload.SmartUpload smartUpload = new com.jspsmart.upload.SmartUpload();
            smartUpload.initialize(servletConfig, request, response);
            smartUpload.upload();

            //文件个数
            int fileCounts =  smartUpload.getFiles().getCount();

            for (int i = 0; i <fileCounts; i++) {
                com.jspsmart.upload.File myFile = smartUpload.getFiles().getFile(i);

                if (!myFile.isMissing()) {

                    imgFileExt = myFile.getFileExt();
                    //组装图片名称
                    imgFileId += "_" + i + "_" + System.currentTimeMillis() + "." + imgFileExt;

                    //图片生成路径
                    imgWebAppPath = uploadPath + imgFileId;

                    //生成图片文件
                    myFile.saveAs(imgWebAppPath);
                    //图片的相对路径
                    imgUploadPath = IMGROOT + imgFileId;

                    //检查图片大小
                    BufferedImage src = ImageIO.read(new File(imgWebAppPath)); // 读入文件
                    //int imgSrcWidth = src.getWidth(); // 得到源图宽
                    imgHeight = src.getHeight();
                    //按照图片的设置大小生成
                    ImageCutUtil.scale(imgWebAppPath,imgWidth,imgHeight);
                    File f = new File(imgWebAppPath);
                    if(f.exists()){
                        logger.info("创建"+imgWidth+"*"+imgHeight+"图片成功");
                    }
                    oldImgPath=imgUploadPath;
                    imgFileExt1=imgFileExt;
                    imgRoot=IMGROOT;
                    width1=imgWidth;
                    height1=imgHeight;
                    request.setCharacterEncoding("UTF-8");
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().print("1");
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }*/

        //return null;
    }

    public ActionForward uploadImageCut(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
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

            //WEB应用程序根路径
            String rootPath  = request.getSession().getServletContext().getRealPath("/");
            /*图片名称:以当前日期*/
            String imgFileId = DateUtil.dateToStr(new Date(), "yyyyMMddhhmmss");
            String imgName = imgRoot + imgFileId + System.currentTimeMillis() +  "." + imgFileExt1;
            /*组装图片真实名称*/
            String createImgPath = rootPath + imgName;
            /*之前上传的图片路径*/
            rootPath += oldImgPath;
            logger.info("原图片路径: " + rootPath + ",新图片路径: " + createImgPath);

            /*进行剪切图片操作*/
            ImageCutUtil.abscut(rootPath, createImgPath, x, y, w1, h1, w, h);
            File file = new File(createImgPath);
            if(file.exists()){
                logger.info("剪切图片大小: "+w1+"*"+h1+"图片成功!");
            }

            Map jsonMap = new HashMap();
            jsonMap.put("url", imgName);
            jsonMap.put("flag", "1");
            //photoImg = createImgPath;//头像全路径保存到数据库
            JSONObject jsonObject = JSONObject.fromObject(jsonMap);
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonObject.toString());//上传成功！
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
