package com.tmall.springboot.web;

import com.tmall.springboot.mapper.LoginTicketDao;
import com.tmall.springboot.mapper.UsersDao;
import com.tmall.springboot.pojo.LoginTicket;
import com.tmall.springboot.pojo.Users;
import com.tmall.springboot.util.Log;
import com.tmall.springboot.util.Msg;
import com.tmall.springboot.util.ResultUtil;
import com.tmall.springboot.util.XdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
public class UsersController {

    @Autowired
    UsersDao usersDao;

    @Autowired
    LoginTicketDao loginTicketDao;
    /*
    * 注册
    * */
    public Map<String, String> register(String username, String password) {
        Map<String, String> map = new HashMap<>();

        if (StringUtils.isEmpty(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        Users u = usersDao.seletByName(username);
        if(null != u){
            map.put("msg", "用户名已经被占用");
            return map;
        }

        Users user = new Users();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHeadurl("暂时不用");
        user.setPassword(XdUtil.MD5(password + user.getSalt()));
        user.setRole("user");
        usersDao.insertUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    /*
    * 登录
    *
    * */

    public Map<String, String> login(String username, String password){


        Map<String, String> map = new HashMap<>();
        Random random = new Random();
        if (StringUtils.isEmpty(username)){
            map.put("msg","用户名不能为空");
            return map;
        }

        if (StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        Users u = usersDao.seletByName(username);
        /*验证用户是否存在*/
        if(null == u){
            map.put("msg", "用户名不存在");
            return map;
        }
        /*验证秘密是否正确*/
        if(!XdUtil.MD5(password + u.getSalt()).equals(u.getPassword())){
            map.put("msg", "密码错误");
            return map;
        }

        String ticket = addLoginTicket(u.getId());
        map.put("ticket", ticket);
        return map;

    }

    /*
    * 免密登录
    * */
    public String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 30);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        //生成凭证
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDao.insertLoginTicket(loginTicket);
        return loginTicket.getTicket();
    }


    /*
    * 登录接口
    * */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Msg loginApi(HttpServletResponse httpResponse, @RequestBody Users users){
        Map<String, String> map = this.login(users.getName(), users.getPassword());
        //如果包含凭证， 就存入cookie
        if(map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket", map.get("ticket"));
            cookie.setPath("/");
            httpResponse.addCookie(cookie);
            return ResultUtil.success();
        }else {
            Log.logger.error(map.get("msg"));
            return ResultUtil.error(map.get("msg"));
        }
    }

    /*
    * 注册接口
    * */
    @PostMapping("/register")
    public Msg registerApi(HttpServletResponse httpServletResponse, @RequestBody Users users){
        Log.logger.info(users.getName());

        Map<String, String> map = this.register(users.getName(), users.getPassword());

        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket"));
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
            return ResultUtil.success(map.get("ticket"));

        }else {
            Log.logger.error(map.get("msg"));
            return ResultUtil.error(map.get("msg"));
        }

    }

}
