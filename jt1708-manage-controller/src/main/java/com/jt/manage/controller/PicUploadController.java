package com.jt.manage.controller;

import com.jt.common.vo.PicUploadResult;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PicUploadController {

    //用于记录日志
    private static final Logger log = Logger.getLogger(PicUploadController.class);

    @RequestMapping("pic/upload")
    @ResponseBody
    public PicUploadResult picUpload(MultipartFile uploadFile) {
        /**
         * 文件上次的步骤
         * 1 拿到这个文件名,拿到扩展名,判断是否为合法的图片文件后缀
         * .jpg .png .gif .jpeg
         * 2 判断是否为木马
         * 3 生成两个路径:图片的真实存放路径 , 网络访问的相对路径
         * 4 图片存放目录 , 一个文件太多 , yyyy/mm/dd
         * 5 不能使用原文件名 , 重命名 ; 计算方法(不唯一)
         *   currentTime + 3位随机数
         * 6 保存图片
         * 7 生成1个picUpload的对象来封装数据
         */
        PicUploadResult result = new PicUploadResult();
        String oldFileName = uploadFile.getOriginalFilename();  //原文件名
        String extFileName = oldFileName.substring(oldFileName.lastIndexOf("."));   //文件扩展名
        //文件后缀判断 , 正则来判断
        if (!extFileName.matches("^.*(jpg|png|gif|jpeg)$")) {
            result.setError(1);
            return result;
        }
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(uploadFile.getInputStream());
            result.setHeight(bufferedImage.getHeight() + "");
            result.setWidth(bufferedImage.getWidth() + "");
            //生成有格式的路径
            String dir = "/images/" + new SimpleDateFormat("yyyy/MM/dd").format(new Date()) + "/";
            String urlPrefix = "http://image.jt.com" + dir;//相对路径 , 属性注入的解耦操作(后期再修改)
            //绝对路径"c:/jt-upload/images/"+dir
            String path = "c:/jt-upload/images/" + dir;
            File _dir = new File(path);
            //判断是否存在目录
            if (!_dir.exists()) {   //不存在路径直接创建
                _dir.mkdirs();  //创建多级目录
            }
            //生成根据计算方法完成文件名称的重命名 , 防止文件重名问题
            String fileName = System.currentTimeMillis() + "" + RandomUtils.nextInt(100, 999) + extFileName;
            //保存虚拟路径
            result.setUrl(urlPrefix + fileName);
            //保存到磁盘操作
            uploadFile.transferTo(new File(path + fileName));
        } catch (IOException e) {
            log.error(e.getMessage());
            result.setError(1);
        }
        return result;
    }
}
