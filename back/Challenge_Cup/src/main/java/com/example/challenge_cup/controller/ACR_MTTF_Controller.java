package com.example.challenge_cup.controller;

import ACR_MTTF.ACR;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.challenge_cup.utils.R;
import com.mathworks.toolbox.javabuilder.MWException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: ACR_mttf
 * @Author: Ryan
 * @Date: 2023/5/5 13:52
 * @Version: 1.0
 */

@RestController
@RequestMapping("acr")

public class ACR_MTTF_Controller {

    @PostMapping  ("")
    public R  draw(@RequestBody String jsonString ) throws MWException {
        JSONObject data = JSONObject.parseObject(jsonString);
        Integer num =data.getInteger("num");
        System.out.println(num);
        ACR acr= new ACR();
        acr.ACR_MTTF(num);
        HashMap<String,Object> res =new HashMap<>();

        List<byte[]> res1 =new ArrayList<>();
        List<String> res2 =new ArrayList<>();
        String basepath = System.getProperty("user.dir") + "/files/";  // 定于文件上传的根路径

        List<String> filenames= FileUtil.listFileNames(basepath);
//        String fileName= filenames.stream().filter(name -> name.contains("result")).findAny().orElse("");
        for(String fileName:filenames){
            byte[] bytes = new byte[0];
            if(StringUtils.isNotEmpty(fileName)){
                // attachment是以附件的形式下载，inline是浏览器打开
                bytes= FileUtil.readBytes(basepath + fileName);

                if(fileName.contains("png")){
                    res1.add(bytes);
                }
                if(fileName.contains("txt")){
                    String result = new String(bytes).trim();
                    System.out.println(result);
                    res2.add(result);
                }
            }
        }
        res.put("images",res1);
        res.put("areas",res2);
        return new R("计算结果获取成功",res,true);
    }

    @GetMapping()
    public  void get(HttpServletResponse response){
        OutputStream os;
        String basepath = System.getProperty("user.dir") + "/files/";  // 定于文件上传的根路径

        List<String> filenames= FileUtil.listFileNames(basepath);
        String fileName= filenames.stream().filter(name -> name.contains("result")).findAny().orElse("");

        try{
            if(StringUtils.isNotEmpty(fileName)){
                // attachment是以附件的形式下载，inline是浏览器打开
                response.setHeader("Content-Disposition", " attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basepath + fileName);
                os= response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            System.out.println("文件下载失败");
            throw new RuntimeException(e);
        }
    }
}
