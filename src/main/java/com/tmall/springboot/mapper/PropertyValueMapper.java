package com.tmall.springboot.mapper;

import com.tmall.springboot.pojo.PropertyValue;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PropertyValueMapper {

    /*
    * 根据产品id和属性id获取属性值对象
    * */
    @Select("select * from propertyvalue where pid=#{pid}")
//    @ResultType(PropertyValue.class)
    public List<PropertyValue> get(int pid);

    /*
    * 根据产品id获取所有的属性值
    * */
    @Select("select * from propertyvalue where pid=#{pid}")
    public List<PropertyValue> list(int pid);

    @Insert("insert into propertyvalue (pid, ptid, value) values (#{pid}, #{ptid}, #{value})")
    public void save(PropertyValue propertyValue);

    @Delete("delete from propertyvalue where id = #{id}")
    public void delete(int id);

    @Update("update propertyvalue set pid=#{pid}, ptid=#{ptid}, value=#{value} where id=#{id}")
    public void update(PropertyValue propertyValue);
}
