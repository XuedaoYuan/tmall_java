package com.tmall.springboot.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.springboot.mapper.CategoryMapper;
import com.tmall.springboot.mapper.PropertyMapper;
import com.tmall.springboot.pojo.Category;
import com.tmall.springboot.pojo.Property;
import com.tmall.springboot.util.Msg;
import com.tmall.springboot.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class PropertyController {
    @Autowired
    PropertyMapper propertyMapper;
    @Autowired
    CategoryMapper categoryMapper;

    /*
     * 分页查询
     * */
    @GetMapping("/properties")
    public Msg listCategory(
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "cid") int cid
    ) throws Exception {

        PageHelper.startPage(start, limit, "id desc");
        List<Property> ps = null;
        if (cid == 0) {
            ps = propertyMapper.findAll();
        } else {
            ps = propertyMapper.findByCid(cid);
        }

        PageInfo<Property> page = new PageInfo<>(ps);

        HashMap<String, Object> hm = new HashMap<>();
//        System.out.println(start + " : " + limit);
        hm.put("list", page.getList());
        hm.put("total", page.getTotal());

        return ResultUtil.success(hm, "", true);
    }

    /*
     * 新增
     * */
    @PostMapping("/properties")
    public Msg addProperty(@RequestBody Property property) throws Exception {
        propertyMapper.save(property);

        return ResultUtil.success();
    }

    /*
     * 删除
     * */
    @DeleteMapping("/properties")
    public Msg deleteProperty(@RequestParam(value = "id") int id) throws Exception {
        propertyMapper.delete(id);
        return ResultUtil.success();
    }

    /*
     * 获取
     * */
    @GetMapping("/properties/cid")
    public Msg propertiesCid(@RequestParam(value = "cid") int cid) throws Exception {
        List<Property> ps = propertyMapper.findAllWithCid(cid);

        return ResultUtil.success(ps);
    }

    /*
     * 根据id获取属性
     * */
    @GetMapping("/properties/id")
    public Msg getPropertiyById(@RequestParam(value = "id") int id) throws Exception {
        Property property = propertyMapper.get(id);
        Category category = categoryMapper.get(property.getCid());
        property.setCategory(category);
        return ResultUtil.success(property);
    }

    /*
     * 修改
     * */
    @PutMapping("/properties")
    public Msg updateProperty(@RequestBody Property property) throws Exception {
        propertyMapper.update(property);
        return ResultUtil.success();
    }
}
