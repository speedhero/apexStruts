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
    String oldImgPath = null;//ͼƬ��׺
    String imgFileExt1 = null;
    String imgRoot = null;
    Integer width1 = 0;
    Integer height1 = 0;

    public ActionForward uploadHeadPhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String imgFileExt = "";
        //ͼƬ�ϴ������·��
        String imgUploadPath = "";
        String imgWebAppPath = "";
        ImgForm fileUploadForm = (ImgForm) form;
        FormFile file = fileUploadForm.getFile();


        //ͼƬ���ƣ���ǰ����
        String imgFileId = DateUtil.dateToStr(new Date(), "yyyyMMddhhmmss");
        //ͼƬ��ʼ���߶�����
        //String width = null;
        //String height = null;



        int imgWidth = 0;
        int imgHeight = 0;


        //ͨ��ʱ���file���ļ���׺,ƴд���ļ���
        java.util.Date date= new java.util.Date();

        String fileName =date.getTime()
                + file.getFileName().substring( file.getFileName().lastIndexOf("."));
        // ȡ�þ���·��

        System.out.println(fileName);
        imgUploadPath=this.getServlet().getServletContext().getRealPath("/") + IMGROOT;
        CommonUtil.checkDirs(imgUploadPath);
        imgWebAppPath=imgUploadPath + fileName;

        //��ƴд�õ��ļ������浽������
        System.out.println(imgWebAppPath);


        try {



            File filetmp=new File(imgWebAppPath);
            // ���������
            FileOutputStream os = new FileOutputStream(imgWebAppPath);
            // ��ʼд�ļ�
            os.write(file.getFileData());
            // �ر���
            os.close();
            filetmp.setReadable(true);
            //���ͼƬ��С
            BufferedImage src = ImageIO.read(filetmp); // �����ļ�
            //int imgSrcWidth = src.getWidth(); // �õ�Դͼ��
            imgWidth  = src.getWidth();
            imgHeight = src.getHeight();
            //����ͼƬ�����ô�С����
            ImageCutUtil.scale(imgWebAppPath,imgWidth,imgHeight);

            if(filetmp.exists()){
                logger.info("����"+imgWidth+"*"+imgHeight+"ͼƬ�ɹ�");
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


      /*  *//*ͼƬ��׺��*//*
        String imgFileExt = "";
        *//*ͼƬ�ϴ������·��*//*
        String imgUploadPath = "";
        String imgWebAppPath = "";
        *//*ͼƬ���ƣ���ǰ����*//*
        String imgFileId = DateUtil.dateToStr(new Date(), "yyyyMMddhhmmss");
        //ͼƬ��ʼ���߶�����
        //String width = null;
        //String height = null;



        int imgWidth = 0;
        int imgHeight = 0;
        try {
            ServletConfig servletConfig=getServlet().getServletConfig();
            com.jspsmart.upload.SmartUpload smartUpload = new com.jspsmart.upload.SmartUpload();
            smartUpload.initialize(servletConfig, request, response);
            smartUpload.upload();

            //�ļ�����
            int fileCounts =  smartUpload.getFiles().getCount();

            for (int i = 0; i <fileCounts; i++) {
                com.jspsmart.upload.File myFile = smartUpload.getFiles().getFile(i);

                if (!myFile.isMissing()) {

                    imgFileExt = myFile.getFileExt();
                    //��װͼƬ����
                    imgFileId += "_" + i + "_" + System.currentTimeMillis() + "." + imgFileExt;

                    //ͼƬ����·��
                    imgWebAppPath = uploadPath + imgFileId;

                    //����ͼƬ�ļ�
                    myFile.saveAs(imgWebAppPath);
                    //ͼƬ�����·��
                    imgUploadPath = IMGROOT + imgFileId;

                    //���ͼƬ��С
                    BufferedImage src = ImageIO.read(new File(imgWebAppPath)); // �����ļ�
                    //int imgSrcWidth = src.getWidth(); // �õ�Դͼ��
                    imgHeight = src.getHeight();
                    //����ͼƬ�����ô�С����
                    ImageCutUtil.scale(imgWebAppPath,imgWidth,imgHeight);
                    File f = new File(imgWebAppPath);
                    if(f.exists()){
                        logger.info("����"+imgWidth+"*"+imgHeight+"ͼƬ�ɹ�");
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
            // �û������������ͼƬ�Ĵ�С
            Integer x = Integer.parseInt(request.getParameter("x"));
            Integer y = Integer.parseInt(request.getParameter("y"));
            Integer w1 = Integer.parseInt(request.getParameter("w1"));
            Integer h1 = Integer.parseInt(request.getParameter("h1"));
            Integer w = Integer.parseInt(request.getParameter("w"));
            Integer h = Integer.parseInt(request.getParameter("h"));

            //WEBӦ�ó����·��
            String rootPath  = request.getSession().getServletContext().getRealPath("/");
            /*ͼƬ����:�Ե�ǰ����*/
            String imgFileId = DateUtil.dateToStr(new Date(), "yyyyMMddhhmmss");
            String imgName = imgRoot + imgFileId + System.currentTimeMillis() +  "." + imgFileExt1;
            /*��װͼƬ��ʵ����*/
            String createImgPath = rootPath + imgName;
            /*֮ǰ�ϴ���ͼƬ·��*/
            rootPath += oldImgPath;
            logger.info("ԭͼƬ·��: " + rootPath + ",��ͼƬ·��: " + createImgPath);

            /*���м���ͼƬ����*/
            ImageCutUtil.abscut(rootPath, createImgPath, x, y, w1, h1, w, h);
            File file = new File(createImgPath);
            if(file.exists()){
                logger.info("����ͼƬ��С: "+w1+"*"+h1+"ͼƬ�ɹ�!");
            }

            Map jsonMap = new HashMap();
            jsonMap.put("url", imgName);
            jsonMap.put("flag", "1");
            //photoImg = createImgPath;//ͷ��ȫ·�����浽���ݿ�
            JSONObject jsonObject = JSONObject.fromObject(jsonMap);
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonObject.toString());//�ϴ��ɹ���
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
