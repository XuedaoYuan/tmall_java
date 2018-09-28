package com.tmall.springboot.mapper;

import org.apache.ibatis.jdbc.SQL;

public class PropertySqlProvider {

    public String list(int cid){
        SQL sql = new SQL().SELECT("*").FROM("property");
        if(0 != cid){
            sql.WHERE("cid=#{cid}");
        }

        return sql.toString();
    }
}
