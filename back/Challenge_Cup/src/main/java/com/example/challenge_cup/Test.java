package com.example.challenge_cup;

import ACR_MTTF.ACR;
import cn.hutool.core.io.FileUtil;
import com.example.challenge_cup.domain.Node;
import com.mathworks.toolbox.javabuilder.MWException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: test
 * @Author: Ryan
 * @Date: 2023/6/4 22:30
 * @Version: 1.0
 */

public class Test {

    public  static  void main(String[] args) throws MWException, IOException {
//        ACR acr= new ACR();
//        acr.ACR_MTTF(150);
        test();
    }

    public  static  void test() throws IOException {
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
        System.out.println(res);
    }
}
