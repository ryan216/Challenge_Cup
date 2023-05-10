package com.example.challenge_cup.domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * @Description: 工具函数
 * @Author: Ryan
 * @Date: 2023/4/28 17:06
 * @Version: 1.0
 */

public class utils
{
    //ACR_MTTF.m
    //CLC.m
    //RMSE.m
//    public double RMSE(double[] sensor_x,double[] sensor_y,double rp_x,double rp_y,double CR){
//        double  a1=CR;
//        ArrayList<Double> Variogram_N00_a1 = new ArrayList<Double>();
//        ArrayList<Double> H_a1 = new ArrayList<Double>();
//        ArrayList<Double> Variance_a1 = new ArrayList<Double>();
//        ArrayList<Double> Variogram_Njj1_a1 = new ArrayList<Double>();
//
//
//        double V_i0_a1;
//        double V_Nj_a1;
//        for (int i4_a1=1;i4_a1<=sensor_x.length;i4_a1++){
//            double H_i0_a1 = Math.sqrt(Math.pow((sensor_x[i4_a1]-rp_x),2) + Math.pow((sensor_y[i4_a1]-rp_y),2);       // 领域内节点与原点之间的距离
//            H_a1.add(H_i0_a1);
//            if (H_i0_a1 == 0){
//                V_i0_a1 = 0;
//            }
//            else{
//                V_i0_a1 = 1 - Math.exp(-Math.pow(H_i0_a1,2)/Math.pow(a1,2));                               //根据高斯变差函数计算变差值
//            }
//            Variogram_N00_a1.add(V_i0_a1);
//        }
//
//
//        for (int  j4_a1=1;j4_a1<=sensor_x.length;j4_a1++){
//            double H_Nj_a1  = Math.sqrt(Math.pow((sensor_x[0]-sensor_x[j4_a1]),2) + Math.pow((sensor_x[0]-sensor_x[j4_a1]),2);       // 领域内节点与原点之间的距离
//            if (H_Nj_a1  == 0){
//                V_Nj_a1 = 0;
//            }
//            else{
//                V_Nj_a1 = 1 - Math.exp(-Math.pow(H_Nj_a1,2)/Math.pow(a1,2));                               //根据高斯变差函数计算变差值
//            }
//            Variogram_Njj1_a1.add(V_Nj_a1);
//        }
//
//        double[] One1_a1=new double[sensor_x.length+1];
//        for(int i =0;i<One1_a1.length;i++){
//            One1_a1[i]=1;
//            Variogram_Njj1_a1.add(One1_a1[i]);
//        }
//
//
////        One_a1= [One1_a1* 0];
////        Variogram_NN_a1.add
////        Variogram_NN_a1 =[ Variogram_Njj_a1;One_a1 ];
//        Weight0_a1 = inv(Variogram_NN_a1) * Variogram_N0_a1;
//        Weight1_a1 = Weight0_a1(1:length(sensor_x));                                    % 取出Weight0中的权系数，得到真正的权系数向量
//                Variance0_a1 = Weight1_a1'*Variogram_N00_a1 +  Weight0_a1(length(sensor_x) + 1) ;
//        rmse = sqrt(abs(Variance0_a1));
//
//
//
//
//
//    }


    //connectivity_val.m

    //stateCal.m
    public String stateCal(double pcomm, double psense, double duty_cycle)
    {
        double test = Math.random();
        double pa = pcomm * psense * duty_cycle;
        double pr = pcomm * (1 - psense) * duty_cycle;
        double pf = (1 - pcomm) * duty_cycle;
        double ps = 1 - duty_cycle;

        String value;
        if (test <= pa)
        {
            value = "ACTIVE";
        } else if (test > pa && test <= pa + pr)
        {
            value = "RELAY";
        } else if (test > pa + pr && test <= pa + pr + pf)
        {
            value = "FAIL";
        } else
        {
            value = "SLEEP";
        }

        return value;
    }

    //StateComsumedEnergy.m
    public double StateComsumedEnergy(String state, double Esense, double Eele, double mp, double fs, double k, double r)
    {
        double comsumedEnergy;
        if ("ACTIVE".equals(state))
        {
            comsumedEnergy = Energy_Tx(Eele, fs, mp, k, r) + k * Esense + Energy_Rx(k, Eele);
        } else if ("RELAY".equals(state))
        {
            comsumedEnergy = Energy_Tx(Eele, fs, mp, k, r);
        } else
        {
            comsumedEnergy = 0d;
        }
        return comsumedEnergy;
    }

    //Energy_Rx.m
    public double Energy_Rx(double k, double Eelec)
    {
        double energy = k * Eelec;
        return energy;
    }

    //Energy_Tx.m
    public double Energy_Tx(double Eele, double fs, double mp, double k, double d)
    {
        double energy;
        double d0 = Math.sqrt(fs / mp);
        if (d < d0)
            energy = k * Eele + k * fs * d * d;
        else
            energy = k * Eele + k * mp * Math.pow(d, 4);
        return energy;
    }

    //connectivity_cal.m
    public void connectivity_cal(int N, Node[] node, Node[] nodeA, double Eele, double Esense, double fs,
                                 double mp, double Area, double link_reliability, double r, double rsi, double[] xa) {
        double xqm = 0;
        //枚举连通矩阵
        int[][] EAM = EnumEnergyAvalMatrix(N, node, Eele, fs, mp, link_reliability, r, rsi);
        int[][] EAM2 = EAM;
        //画图逻辑
    }

    //EnumEnergyAvalMatrix.m
    public int[][] EnumEnergyAvalMatrix(int N, Node[] node, double Eele, double fs, double mp, double link_reliability, double r, double rsi)
    {
        //注意MatLab下标从1开始
        int[][] EAM = new int[N + 2][N + 2];
        double k = Math.PI * r * r;
        double dist;
        for (int i = 1; i <= N; i++)
        {
            //node 1xN+1
            if ("ACTIVE".equals(node[i].state) || "RELAY".equals(node[i].state))
            {
                for (int j = 1; j <= N + 1; j++)
                {
                    if ("ACTIVE".equals(node[j].state) || "RELAY".equals(node[j].state))
                    {
                        dist = node[i].distanceCal(node[j]);
                        if (dist <= rsi)
                        {
                            double energy_Tx = Energy_Tx(Eele, fs, mp, k, dist);
                            if (energy_Tx <= node[i].energy)
                            {
                                double testij = Math.random();
                                if (testij <= link_reliability)
                                {
                                    EAM[i][j] = 1;
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i = 1; i <= N; i++)
        {
            EAM[N + 1][i] = EAM[i][N + 1];
        }
        EAM[N + 1][N + 1] = 1;
        return EAM;
    }

    //distance.m
    public double[] distance(int k, Node[] node, int m, int N) {
        Point point = new Point();
        double[][] alldis = new double[N+1][3];
        Node n = new Node(node[k].x, node[k].y);
        for (int i = 1; i <= N; i++) {
            if (i == k) {
                alldis[i][1] = node[i].number;
                alldis[i][2] = 1000000;
            }else if ("EVICTED".equals(node[i].state)) {
                alldis[i][1] = node[i].number;
                alldis[i][2] = 1000000;
            }else {
                alldis[i][1] = node[i].number;
                alldis[i][2] = node[i].distanceCal(n);
            }
        }
        //对距离排序，返回最近的m个结点，升序排列
        Comparator<double[]> comparator = new Comparator<double[]>() {
            @Override
            public int compare(double[] o1, double[] o2)
            {
                if ((o1[1] - o2[1]) > 0)
                    return 1;
                else
                    return -1;
            }
        };
        Arrays.sort(alldis, comparator);
        double[] index = new double[m+1];
        for (int i = 1; i <= m; i++)
        {
            index[i] = alldis[i][1];
        }
        return index;
    }

    //EvalConnectivity.m
    public void EvalConnectivity(int[][] EAM, Node[] node, Node[] S, double[] xa, int N) {
        int i = 1;
        int[][] EAM1 = EAM;
        int linkValue;
        EAM1 = isCon(EAM, node, N);
        while (i < xa.length) {
            if (EAM1[(int) S[i].number][N+1] == 1) {
                linkValue = 1;
            }else {
                linkValue = 0;
                break;
            }
            i += 1;
        }
    }

    //isCon.m
    public int[][] isCon(int[][] EAM, Node[] node, int N) {
        int[][] EAM2 = EAM;
        for (int i = 1; i <= N; i++) {
            System.out.println(node[i].state);
            if ("ACTIVE".equals(node[i].state) || "RELAY".equals(node[i].state)) {
                for (int j = 1; j <= N; j++)
                {
                    if (EAM2[i][j] == 1) {
                        for (int k = 1; k <= N+1; k++)
                        {
                            if (EAM2[j][k] == 1 || EAM2[i][k] == 1)
                                EAM2[j][k] = 1;
                            else
                                EAM2[j][k] = 0;
                        }
                    }
                }
            }
        }
        return EAM2;
    }
}