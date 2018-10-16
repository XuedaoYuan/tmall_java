package com.tmall.springboot.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.springboot.mapper.UserMapper;
import com.tmall.springboot.pojo.ProductImage;
import com.tmall.springboot.pojo.User;
import com.tmall.springboot.util.Msg;
import com.tmall.springboot.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired UserMapper userMapper;

//    来自配置文件的配置
    @Value(value="${tmall.secret}")
    public String secret2;

    /*
    * 后台查询用户
    * */
    @GetMapping("/user")
    public Msg list(
            @RequestParam(value = "start", defaultValue = "1") int start,
            @RequestParam(value = "limit", defaultValue = "5") int limit
    ){
        PageHelper.startPage(start, limit, "id desc");
        List<User> us = userMapper.list();
        PageInfo<User> page = new PageInfo<>(us);

        return ResultUtil.success(page);
    }

    @GetMapping("/userid")
    public Msg getById(@RequestParam  int id){
        User u = userMapper.get(id);
        u.setName(secret2);
        return ResultUtil.success(u);
    }




}
