package com.tmall.springboot.mapper;

import com.tmall.springboot.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("select * from category")
    public List<Category> findAll();

    @Insert("insert into category (name) values (#{name})")
    public void save(Category category);

    @Delete("delete from category where id = #{id}")
    public void delete(int id);

    @Select("select * from category where id = #{id}")
    public Category get(int id);

    @Update("update category set name=#{name} where id = #{id}")
    public void update(Category category);

}