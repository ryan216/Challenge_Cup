package com.example.challenge_cup.Controller;

import ACR_MTTF.ACR;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.mathworks.toolbox.javabuilder.MWException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 画图测试
 * @Author: Ryan
 * @Date: 2023/5/5 14:22
 * @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ImageTest {

    @Test
    public void test() throws MWException {
        ACR class1 = new ACR();
        class1.ACR_MTTF(100);

    }

    @Test
    public void images() throws UnsupportedEncodingException {
        String basepath = System.getProperty("user.dir") + "/files/";  // 定于文件上传的根路径

        List<String> filenames= FileUtil.listFileNames(basepath);
        System.out.println(filenames);
        for(String fileName:filenames){
            if(fileName.contains("png")){
                System.out.println(fileName);
            }
            if(fileName.contains("txt")){

                System.out.println("txt:"+fileName);
                byte[] bytes = FileUtil.readBytes(basepath + fileName);
                String result = new String(bytes).trim();
                System.out.println(result);
            }

//            System.out.println(bytes);
        }
    }

}
