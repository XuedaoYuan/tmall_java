package com.tmall.springboot.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.springboot.mapper.ProductImageMapper;
import com.tmall.springboot.pojo.ProductImage;
import com.tmall.springboot.util.Msg;
import com.tmall.springboot.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ProductImageController {
    @Autowired
    ProductImageMapper productImageMapper;

    /*
     * 分页查询
     * */
    @GetMapping("/productimage")
    public Msg list(
            @RequestParam(value = "pid") Integer pid,
            @RequestParam(value = "start", defaultValue = "1") int start,
            @RequestParam(value = "limit", defaultValue = "5") int limit
    ) throws Exception {
        PageHelper.startPage(start, limit, "id desc");
        List<ProductImage> pis = productImageMapper.findByPid(pid);
        PageInfo<ProductImage> page = new PageInfo<>(pis);
        return ResultUtil.success(page);
    }

    /*
     * 文件上传
     * */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Msg uploadImage(HttpServletRequest req, @RequestParam("file") MultipartFile file) {
        String destFileName = null;
        String fileName = null;
        try {
            //根据时间戳和文件名生成新的文件名， 防止覆盖
            fileName = System.currentTimeMillis() + file.getOriginalFilename();
//            destFileName = req.getServletContext().getRealPath("") + "uploaded" + File.separator + fileName;
            destFileName = "/Users/cloud/Desktop/frontEndTestCode/java_upload" + File.separator + fileName;

            File destFile = new File(destFileName);
            System.out.println("图片文件保存目录：" + destFileName);

            destFile.getParentFile().mkdirs();
            //拷贝文件到指定目录
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.error("上传失败");
        }


        return ResultUtil.success(fileName);
    }

    /*
     * 批量文件上传
     * */
    @RequestMapping(value = "/uploads", method = RequestMethod.POST)
    public Msg uploadImages(HttpServletRequest req, @RequestParam("files") MultipartFile[] files) {


        ArrayList<String> fileNames = new ArrayList<>();

        try {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                String fileName = System.currentTimeMillis() + file.getOriginalFilename();
                String destFileName = "/Users/cloud/Desktop/frontEndTestCode/java_upload" + File.separator + fileName;

                File destFile = new File(destFileName);
                System.out.println("图片文件保存目录：" + destFileName);

                destFile.getParentFile().mkdirs();
                //拷贝文件到指定目录
                file.transferTo(destFile);
                fileNames.add(fileName);
            }


        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.error("上传失败");
        }


        return ResultUtil.success(fileNames);
    }


    /*
     * 新增图片
     * */
    @PostMapping("/productimage")
    public Msg addImage(@RequestBody ProductImage productImage) throws Exception {

        productImageMapper.add(productImage);

        return ResultUtil.success();
    }

    /*
     * 批量新增图片
     * */
    @PostMapping("/productimagees")
    public Msg addImages(@RequestBody List<ProductImage> items) throws Exception {
        productImageMapper.addList(items);
        return ResultUtil.success();
    }

    /*
     * 删除
     * */
    @DeleteMapping("/productimage")
    public Msg deleteImage(@RequestParam int id) {

        productImageMapper.delete(id);

        return ResultUtil.success();
    }
}
