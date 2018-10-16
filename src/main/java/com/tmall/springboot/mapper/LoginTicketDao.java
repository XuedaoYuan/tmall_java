package com.tmall.springboot.mapper;

import com.tmall.springboot.pojo.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketDao {
    String TABLE_NAEM = " login_ticket ";
    String INSERT_FIELDS = " userid, ticket, expired, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    @Insert({"insert into", TABLE_NAEM, "(", INSERT_FIELDS, ") values (#{userid},#{ticket},#{expired},#{status})"})
    void insertLoginTicket(LoginTicket loginTicket);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAEM, "where id=#{id}"})
    LoginTicket seletById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAEM, "where ticket=#{ticket}"})
    LoginTicket seletByTicket(String ticket);

    @Update({"update", TABLE_NAEM, "set status = #{status} where ticket = #{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);

    @Delete({"delete from", TABLE_NAEM, "where id=#{id}"})
    void deleteById(int id);

}
