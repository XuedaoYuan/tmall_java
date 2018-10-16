package com.tmall.springboot.web;

import java.util.Date;

public class TestController {
    public static void main(String[] args) {

        Date d1 = new Date();
        Date d2 = new Date();
        d2.setTime(d2.getTime() + 1000 * 3600 * 10);

        System.out.println(d1.before(d2));

    }
}
