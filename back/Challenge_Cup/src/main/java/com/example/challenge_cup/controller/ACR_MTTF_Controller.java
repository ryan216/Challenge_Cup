package com.example.challenge_cup.controller;

import ACR_MTTF.ACR;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.challenge_cup.domain.Node;
import com.example.challenge_cup.utils.R;
import com.mathworks.toolbox.javabuilder.MWException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
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

    @GetMapping   ("")
    public R draw() throws IOException {
        String basepath = System.getProperty("user.dir") + "/files/";  // 定于文件上传的根路径
        List<String> filenames= FileUtil.listFileNames(basepath);
        System.out.println(filenames);
        HashMap<String,Object> res =new HashMap<>();

        List<List<Node>> res_nodes=new ArrayList<>();
        List<List<Node>> res_rebuild=new ArrayList<>();
        List<String> res_area=new ArrayList<>();
        for(String filanme:filenames){

            if(filanme.contains("ZuoBiao")){
                List<Node> res_tmp =new ArrayList<>();
                String str;

                BufferedReader bufferedReader =new BufferedReader(new FileReader(basepath+filanme));
                while ((str = bufferedReader.readLine()) != null) {
                    Node node=new Node();
                    String[] s=str.split(" ");
                    node.setNumber(s[0]);
                    node.setX(s[1]);
                    node.setY(s[2]);
                    if(s.length>3){
                        node.setState(s[3]);
                    }
                    res_tmp.add(node);
                }
                res_nodes.add(res_tmp);
            }


            if(filanme.contains("rebuild")){
                BufferedReader bufferedReader =new BufferedReader(new FileReader(basepath+filanme));
                String str;
                List<Node> res_tmp =new ArrayList<>();
                while ((str = bufferedReader.readLine()) != null) {
                    Node node=new Node();
                    String[] s=str.split(" ");
                    node.setX(s[0]);
                    node.setY(s[1]);
                    res_tmp.add(node);
                }
                res_rebuild.add(res_tmp);
            }
            if(filanme.contains("area")){
                BufferedReader bufferedReader =new BufferedReader(new FileReader(basepath+filanme));
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    res_area.add(str);
                }
            }
        }
        res.put("Nodes",res_nodes);
        res.put("Areas",res_area);
        res.put("RebulidNodes",res_rebuild);

//        System.out.println(res);
        return new R("计算结果获取成功",res,true);
    }



//    @PostMapping  ("")
//    public R  draw(@RequestBody String jsonString ) throws MWException {
//        JSONObject data = JSONObject.parseObject(jsonString);
//        Integer num =data.getInteger("num");
//        System.out.println(num);
//        ACR acr= new ACR();
//        acr.ACR_MTTF(num);
//        HashMap<String,Object> res =new HashMap<>();
//
//        List<byte[]> res1 =new ArrayList<>();
//        List<String> res2 =new ArrayList<>();
//        String basepath = System.getProperty("user.dir") + "/files/";  // 定于文件上传的根路径
//
//        List<String> filenames= FileUtil.listFileNames(basepath);
////        String fileName= filenames.stream().filter(name -> name.contains("result")).findAny().orElse("");
//        for(String fileName:filenames){
//            byte[] bytes = new byte[0];
//            if(StringUtils.isNotEmpty(fileName)){
//                // attachment是以附件的形式下载，inline是浏览器打开
//                bytes= FileUtil.readBytes(basepath + fileName);
//
//                if(fileName.contains("png")){
//                    res1.add(bytes);
//                }
//                if(fileName.contains("txt")){
//                    String result = new String(bytes).trim();
//                    System.out.println(result);
//                    res2.add(result);
//                }
//            }
//        }
//        res.put("images",res1);
//        res.put("areas",res2);
//        return new R("计算结果获取成功",res,true);
//    }



//    @GetMapping()
//    public  void get(HttpServletResponse response){
//        OutputStream os;
//        String basepath = System.getProperty("user.dir") + "/files/";  // 定于文件上传的根路径
//
//        List<String> filenames= FileUtil.listFileNames(basepath);
//        String fileName= filenames.stream().filter(name -> name.contains("result")).findAny().orElse("");
//
//        try{
//            if(StringUtils.isNotEmpty(fileName)){
//                // attachment是以附件的形式下载，inline是浏览器打开
//                response.setHeader("Content-Disposition", " attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
//                response.setContentType("application/octet-stream");
//                byte[] bytes = FileUtil.readBytes(basepath + fileName);
//                os= response.getOutputStream();
//                os.write(bytes);
//                os.flush();
//                os.close();
//            }
//        } catch (IOException e) {
//            System.out.println("文件下载失败");
//            throw new RuntimeException(e);
//        }
//    }
}
