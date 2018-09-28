package com.tmall.springboot.mapper;

import com.tmall.springboot.pojo.Property;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PropertyMapper {

    @Select("select * from property")
    List<Property> findAll();

    @Select("select * from property where cid=#{cid}")
    List<Property> findByCid(int cid);

    @Select("select * from property where id=#{id}")
    public Property get(Integer id);

    @Insert("insert into property (cid, name) values (#{cid}, #{name})")
    public void save(Property property);

    @Delete("delete from property where id = #{id}")
    public void delete(Integer id);

    @Update("update property set cid=#{cid}, name=#{name} where id=#{id}")
    public void update(Property property);


}
