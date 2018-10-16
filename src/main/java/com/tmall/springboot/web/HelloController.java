package com.tmall.springboot.web;

import com.github.pagehelper.StringUtil;
import com.tmall.springboot.config.OnlineCount;
import com.tmall.springboot.util.Msg;
import com.tmall.springboot.util.ResultUtil;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class HelloController {


    @GetMapping("/hello")
    public String hello() throws Exception{

        return "hello, world!";
    }

    @GetMapping("/count")
    @ResponseBody
    public Msg count(HttpServletRequest req, HttpServletResponse res){

        HttpSession session = req.getSession(false);
        Object count = session.getServletContext().getAttribute("Count");

        return ResultUtil.success(count);

    }

    @GetMapping("/api/get")
    public Msg apiGrt(@RequestParam String name){

        return ResultUtil.success(name);


    }
}
