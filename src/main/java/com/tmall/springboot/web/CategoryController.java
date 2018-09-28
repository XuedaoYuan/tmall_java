package com.tmall.springboot.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.springboot.mapper.CategoryMapper;
import com.tmall.springboot.pojo.Category;
import com.tmall.springboot.util.Msg;
import com.tmall.springboot.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired CategoryMapper categoryMapper;

    /*
    * 分页查询
    * */
    @GetMapping("/categories")
    public String listCategory(
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "limit", defaultValue = "5") int limit
    ) throws Exception {

        PageHelper.startPage(start, limit, "id desc");

        List<Category> cs = categoryMapper.findAll();

        PageInfo<Category> page = new PageInfo<>(cs);

        HashMap<String, Object> hm = new HashMap<>();
//        System.out.println(start + " : " + limit);
        hm.put("list", page.getList());
        hm.put("total", page.getTotal());

        return JSON.toJSONString(hm);
    }

    /*
    * 新增
    * */
    @PostMapping("/categories")
    public Msg addCategory(@RequestBody Category c) throws Exception{
        System.out.println(c);
        //不存在id， 就是新增
        if(c.getId() == 0){
            categoryMapper.save(c);

        }else {
            //存在id是修改
            categoryMapper.update(c);
        }

        return ResultUtil.success();
    }
    /*
    * 删除
    * */
    @DeleteMapping("/categories")
    public Msg deleteCategory(
            @RequestParam(value = "id") int id
    ) throws Exception {
        categoryMapper.delete(id);
        return ResultUtil.success();
    }

    /*
    * 根据id获取Category
    * */
    @GetMapping("/getCategory")
    public Msg getCategory(
        @RequestParam(value = "id") int id
    ) throws Exception {
        //获取对应的id
        Category c = categoryMapper.get(id);
        return ResultUtil.success(c, "", true);
    }



}
