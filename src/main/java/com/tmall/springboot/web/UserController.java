package com.tmall.springboot.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.springboot.mapper.UserMapper;
import com.tmall.springboot.pojo.ProductImage;
import com.tmall.springboot.pojo.User;
import com.tmall.springboot.util.Msg;
import com.tmall.springboot.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired UserMapper userMapper;

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

    /*
    * 查询单个用户
    * */
    @GetMapping("/userById")
    public Msg get(
            @RequestParam int id
    ){
        User user = userMapper.get(id);
        return ResultUtil.success(user);
    }

    /*
    * 注册
    * */
    @PostMapping("/user")
    public Msg signUp(@RequestBody User user){
        userMapper.add(user);
        return ResultUtil.success();
    }
    /*
    * 登录
    * */
    @PostMapping("/login")
    public Msg login(@RequestBody User user){

        return ResultUtil.success();
    }
}
