package com.tmall.springboot.mapper;

import com.tmall.springboot.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select id,name from user")
    List<User> list();

    @Select("select * from user where id=#{id}")
    public User get(Integer id);
     /*

    @Insert("insert into user (name, password) VALUES (#{name, #{password}})")
    public void add(User user);


    @Update("update user set name=#{name}, password=#{password} where id=#{id}")
    public void update(User user);*/


}
