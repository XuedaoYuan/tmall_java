package com.tmall.springboot.web;

import com.tmall.springboot.mapper.CategoryMapper;
import com.tmall.springboot.mapper.ProductMapper;
import com.tmall.springboot.mapper.PropertyMapper;
import com.tmall.springboot.mapper.PropertyValueMapper;
import com.tmall.springboot.pojo.Product;
import com.tmall.springboot.pojo.Property;
import com.tmall.springboot.pojo.PropertyValue;
import com.tmall.springboot.util.Msg;
import com.tmall.springboot.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PropertyValueController {
    @Autowired
    PropertyValueMapper propertyValueMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    PropertyMapper propertyMapper;

    @Autowired
    ProductMapper productMapper;

    /*
     * 初始化属性值
     * 属性值只能修改， 不能新增删除
     * 所以需要为一个产品初始化
     * 根据产品查询分类， 根据分类或者所有的属性集合
     * 然后用属性id和产品id去查看这个属性值是否存在，
     * 不存在就创建一个， 同时插入到数据库。 完成初始化
     * */
    @PostMapping("/propertyvalue/init")
    public Msg init(@RequestBody Product p) {
        int categoryId = p.getCid();
//
        List<Property> pts = propertyMapper.findAllWithCid(categoryId);
        for (Property pt : pts) {
            List<PropertyValue> pvs = propertyValueMapper.get(p.getId());
            if (pvs.isEmpty()) {
                PropertyValue pv = new PropertyValue();
                pv.setPid(p.getId());
                pv.setPtid(pt.getId());
                pv.setValue("");
                // 插入到数据库
                propertyValueMapper.save(pv);
            }else {
                boolean flag = true;
                for(PropertyValue pv : pvs){
                    if(pv.getPtid() == pt.getId()){
                        flag = false;
                        break;
                    }
                }
                if(flag == true){
                    PropertyValue pv2 = new PropertyValue();
                    pv2.setPid(p.getId());
                    pv2.setPtid(pt.getId());
                    pv2.setValue("");
                    propertyValueMapper.save(pv2);
                }
            }
        }

        return ResultUtil.success();

       /* // 得到分类id

        // 获取所有的属性
        List<Property> pts = propertyMapper.findAllWithCid(categoryId);
        for (Property pt : pts) {
            PropertyValue pv = get2(p.getId(), pt.getId());
            if (null == pv) {

            }
        }
        return ResultUtil.success();*/
    }

    @PutMapping("/propertyvalue")
    public Msg update(@RequestBody PropertyValue propertyValue) throws Exception {
        propertyValueMapper.update(propertyValue);
        return ResultUtil.success();
    }


   /* @GetMapping("/propertyvalue")
    public Msg get(@RequestParam int pid, @RequestParam int ptid) {
        PropertyValue pv = propertyValueMapper.get(pid);
        if (null == pv) {
            return ResultUtil.error("不存在");
        }
        return ResultUtil.success(pv);
    }*/

   /* public PropertyValue get2(int pid, int ptid) {
        PropertyValue pv = propertyValueMapper.get(pid);
        if (null == pv) {
            return null;
        }
        return pv;
    }*/

    /*
     * 根据产品id获取所有的属性值
     * */
    @GetMapping("/propertyvalue/list")
    public Msg getList(@RequestParam int pid) {
        List<PropertyValue> list = propertyValueMapper.list(pid);
        for (PropertyValue pv : list) {
            Property property = propertyMapper.get(pv.getPtid());
            pv.setProperty(property);
        }
        return ResultUtil.success(list);
    }
}
