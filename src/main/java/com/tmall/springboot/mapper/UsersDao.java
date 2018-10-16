package com.tmall.springboot.mapper;
import com.tmall.springboot.pojo.Users;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UsersDao {
    String TABLE_NAEM = " users ";
    String INSERT_FIELDS = " name, password, salt, headurl, role ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAEM,"(",INSERT_FIELDS,") values (#{name},#{password},#{salt},#{headurl},#{role})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public void insertUser(Users users);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where id=#{id}"})
    public Users seletById(int id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where name=#{name}"})
    public Users seletByName(@Param("name") String name);

    @Delete({"delete from",TABLE_NAEM,"where id=#{id}"})
    public void deleteById(int id);

}
