package com.kaishengit;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.misc.Cleaner;


@RunWith(SpringJUnit4ClassRunner.class)   //
//@ContextConfiguration(locations = "classpath:spring.xml")//找到spring.xml配置文件并读取 创建spring容器
@ContextConfiguration(classes = Application.class)
public class baseTestCase {
}