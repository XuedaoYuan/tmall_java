package com.tmall.springboot;

import com.tmall.springboot.pojo.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

    @Test
    public void contextLoads() {
        Category c = new Category();
        System.out.println(c.getId());
        System.out.println(c.getName());
    }

}
