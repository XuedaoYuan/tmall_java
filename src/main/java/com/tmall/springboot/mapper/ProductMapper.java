package com.tmall.springboot.mapper;

import com.tmall.springboot.pojo.Category;
import com.tmall.springboot.pojo.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface ProductMapper {

    @SelectProvider(type = com.tmall.springboot.provider.SqlProvider.class, method = "findProductByCid")
    @Results({
            @Result(property = "createDate", column = "createDate", jdbcType = JdbcType.TIMESTAMP, javaType = Timestamp.class)
    })
    List<Product> findByCid(HashMap<String, Object> hm);

    /*
    * */
    @Select("select * from product where id = #{id}")
    @Results({
            @Result(property = "category", column = "cid", javaType = Category.class,
                    one=@One(select = "com.tmall.springboot.mapper.CategoryMapper.get")
            )
    })
    public Product get(int id);

    @Insert("insert into product (name, subTitle, originalPrice, promotePrice, stock, cid, createDate) VALUES (#{name},#{subTitle},#{originalPrice},#{promotePrice},#{stock},#{cid},#{createDate, jdbcType=TIMESTAMP})")
    public void add(Product product);

    @Delete("delete from product where id=#{id}")
    public void delete(int id);

    @Update("update product set name=#{name},subTitle=#{subTitle},originalPrice=#{originalPrice},promotePrice=#{promotePrice},stock=#{stock} WHERE id = #{id}")
    public void update(Product product);
}
