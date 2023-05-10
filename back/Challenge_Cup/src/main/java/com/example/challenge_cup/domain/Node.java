package com.example.challenge_cup.domain;

import java.awt.*;

/**
 * @Description: Node
 * @Author: Ryan
 * @Date: 2023/4/27 16:58
 * @Version: 1.0
 */

public class Node {

    double number;    //节点的序号
    double x;         //节点的坐标
    double y;
    String state;     // 节点的状态ACTIVE RELAY FAIL SLEEP
    double energy;    // 表示能量
    double   area;

    public Node(double number, double x, double y, String state, double energy, double area) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.state = state;
        this.energy = energy;
        this.area = area;
    }

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void updateState(String state) {
        this.state = state;
    }

    public void updateLocation(double x, double y){
            this.x=x;
            this.y=y;
    }

    public  double distanceCal(Node n){
        double dist=Math.sqrt((this.x-n.x)*(this.x-n.x)+(this.y-n.y)*(this.y-n.y));
        return dist;
    }


}
