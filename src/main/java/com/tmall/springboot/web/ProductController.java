package com.tmall.springboot.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.springboot.mapper.CategoryMapper;
import com.tmall.springboot.mapper.ProductImageMapper;
import com.tmall.springboot.mapper.ProductMapper;
import com.tmall.springboot.pojo.Category;
import com.tmall.springboot.pojo.Product;
import com.tmall.springboot.pojo.ProductImage;
import com.tmall.springboot.util.Msg;
import com.tmall.springboot.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ProductImageMapper productImageMapper;

    /*
     * 分页查询
     * */
    @GetMapping("/product")
    public Msg listProduct(
            @RequestParam(value = "cid", required = false) Integer cid,
            @RequestParam(value = "start", defaultValue = "1") int start,
            @RequestParam(value = "limit", defaultValue = "5") int limit
    ) throws Exception {
        PageHelper.startPage(start, limit, "id desc");
        HashMap<String, Object> hm = null;
        System.out.println("cid=" + cid);
        if (null != cid) {
            hm = new HashMap<>();
            hm.put("cid", cid);
        }

        List<Product> ps = productMapper.findByCid(hm);
        setFirstProductImage(ps);
        PageInfo<Product> page = new PageInfo<>(ps);

        return ResultUtil.success(page);

    }

    /*
     * 根据id获取单个产品
     * */
    @GetMapping("/getProduct")
    public Msg getProduct(@RequestParam(value = "id") int id) throws Exception {

        Product c = productMapper.get(id);
        int cid = c.getCid();
        Category cate = categoryMapper.get(cid);
        c.setCategory(cate);
        return ResultUtil.success(c);
    }


    /*
     * 新增 修改
     * */
    @PostMapping("/product")
    public Msg addProduct(@RequestBody Product product) throws Exception {

        System.out.println(product.getId());
        if (null != product.getId()) {
            //修改
            productMapper.update(product);

        } else {
            //新增
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            product.setCreateDate(ts);
            productMapper.add(product);
        }


        return ResultUtil.success();
    }

    /*
     * 删除
     * */
    @DeleteMapping("/product")
    public Msg deleteProduct(@RequestParam(value = "id") int id) throws Exception {

        productMapper.delete(id);

        return ResultUtil.success();
    }

    /*
     * 修改
     * */
    @PutMapping("/product")
    public Msg updateProduct(@RequestBody Product p) throws Exception {

        productMapper.update(p);
        return ResultUtil.success();
    }

    /*设置第一个图*/
    public void setFirstProductImage(Product p) {
        List<ProductImage> pi =  productImageMapper.findSingleByPid(p.getId());
        if(!pi.isEmpty()){
            p.setFirstProductImage(pi.get(0));
        }

    }
    public void setFirstProductImage(List<Product> ps){
        for(Product p : ps){
            setFirstProductImage(p);
        }
    }

}
