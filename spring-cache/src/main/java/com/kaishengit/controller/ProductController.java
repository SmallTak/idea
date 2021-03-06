package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Product;
import com.kaishengit.entity.ProductType;
import com.kaishengit.exception.NotFountException;
import com.kaishengit.service.ProductService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id:\\d+}/del")
    public String delProduct(@PathVariable Integer id, RedirectAttributes redirectAttributes){

        productService.delProduct(id);
        return "redirect:/product";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String editProduct(@PathVariable Integer id, Model model){

        Product product = productService.findById(id);
        if (product == null){
            throw new NotFountException();
        }

        List<ProductType> productTypeList = productService.findAllProductType();

//        for(ProductType productType : productTypeList){
//            System.out.println(productType);
//        }

        model.addAttribute("product",product);
        model.addAttribute("productTypeList",productTypeList);
        return "product/edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String editProduct(Product product, RedirectAttributes redirectAttributes){

        productService.updateProduct(product);
        redirectAttributes.addFlashAttribute("message","商品修改成功");
        return "redirect:/product";
    }

    @GetMapping("/{id:\\d+}")
    public String viewProduct(@PathVariable Integer id, Model model){

        Product product = productService.findById(id);
        if (product == null){
            throw new NotFountException();
        }
        model.addAttribute("product",product);
        return "product/product";

    }
    @GetMapping
    public String listProduct(@RequestParam(defaultValue = "1",name = "p",required = false) Integer pageNo, Model model,
                              @RequestParam(required = false) String productName,
                              @RequestParam(required = false) String place,
                              @RequestParam(required = false) Float minPrice,
                              @RequestParam(required = false) Float maxPrice,
                              @RequestParam(required = false) Integer typeId
                              ){


        //将搜索内容添加到map集合中
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("productName", productName);
        queryParamMap.put("place", place);
        queryParamMap.put("minPrice", minPrice);
        queryParamMap.put("maxPrice", maxPrice);
        queryParamMap.put("typeId",typeId);

        PageInfo<Product> productPageInfo = productService.findAllPageAndQueryParam(pageNo, queryParamMap);

        model.addAttribute("productPageInfo",productPageInfo);
        model.addAttribute("typeList",productService.findAllByType());
        return "product/list";
    }

    @GetMapping("/new")
    public String newProduct(Model model){
        //查询所有商品类型
        List<ProductType> productTypeList = productService.findAllByType();
        model.addAttribute("typeList",productTypeList);
        return "product/new";
    }

    @PostMapping("/new")
    public String newProduct(Product product, RedirectAttributes redirectAttributes){

        productService.saveProduct(product);
        System.out.println(product.getId());
        redirectAttributes.addFlashAttribute("message","商品保存成功");
        return "redirect:/product";
    }

}
