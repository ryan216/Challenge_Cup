package com.example.challenge_cup.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * @Description: Node
 * @Author: Ryan
 * @Date: 2023/4/27 16:58
 * @Version: 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {

    String number;    //节点的序号
    String x;         //节点的坐标
    String y;
    String state;     // 节点的状态ACTIVE RELAY FAIL SLEEP
    String energy;    // 表示能量
    String   area;

//    public void updateState(String state) {
//        this.state = state;
//    }
//
//    public void updateLocation(double x, double y){
//            this.x=x;
//            this.y=y;
//    }
//
//    public  double distanceCal(Node n){
//        double dist=Math.sqrt((this.x-n.x)*(this.x-n.x)+(this.y-n.y)*(this.y-n.y));
//        return dist;
//    }


}
