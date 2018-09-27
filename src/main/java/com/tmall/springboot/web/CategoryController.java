package com.tmall.springboot.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.springboot.mapper.CategoryMapper;
import com.tmall.springboot.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired CategoryMapper categoryMapper;

    @GetMapping("/categories")
    public String listCategory(
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "limit", defaultValue = "5") int limit
    ) throws Exception {

        PageHelper.startPage(start, limit, "id desc");
        List<Category> cs = categoryMapper.findAll();
        PageInfo<Category> page = new PageInfo<>(cs);

        HashMap<String, Object> hm = new HashMap<>();
        System.out.println(start + " : " + limit);
        hm.put("list", page.getList());
        hm.put("total", page.getTotal());

        return JSON.toJSONString(hm);
    }
}
