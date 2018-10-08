package com.tmall.springboot.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;

public class SqlProvider {
    /*
    * 根据cid查找产品
    *
    * */
    public String findProductByCid(HashMap<String, Object> hm){
        SQL sql = new SQL();
        sql.SELECT("*").FROM("product");
        if(hm!= null && hm.containsKey("cid")){
            sql.WHERE("cid=#{cid}");
        }

        return sql.toString();
    }
}
