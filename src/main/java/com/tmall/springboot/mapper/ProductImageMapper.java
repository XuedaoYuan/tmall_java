package com.tmall.springboot.mapper;

import com.tmall.springboot.pojo.ProductImage;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ProductImageMapper {

    @Select("select * from productimage where pid=#{pid}")
    List<ProductImage> findByPid(Integer pid);

    /*
    * 新增
    * */
    @Insert("insert into productimage (pid, type, image) VALUES (#{pid}, #{type}, #{image})")
    public void add(ProductImage productImage);

    /*
    * 批量新增
    * */
    @Insert(
            "<script>insert into productimage (pid, type, image) VALUES" +
                    "<foreach collection=\"items\" index=\"index\" item=\"item\" separator=\",\">" +
                    "(#{item.pid}, #{item.type}, #{item.image})" +
                    "</foreach>" +
                    "</script>"
    )
    public void addList(@Param("items") List<ProductImage> items);

    /*
    * 删除
    * */
    @Delete("delete from productimage where id=#{id}")
    public void delete(Integer id);

}
