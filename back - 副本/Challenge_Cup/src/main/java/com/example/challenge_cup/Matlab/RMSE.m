function  rmse=RMSE(sensor_x, sensor_y,rp_x,rp_y,CR)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%用以计算均方根误差的函数
%
%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Gaussian Model
%a1 = CR/sqrt(3);   % 变差函数模型中的变程参数，根据高斯模型，真正物理意义上的变程是:a*sqrt(3)，设为100m
a1=CR;
Variogram_N00_a1 = [];
H_a1 = [];
Variance_a1=[];
for i4_a1 = 1:length(sensor_x)
    H_i0_a1 = sqrt((sensor_x(i4_a1)-rp_x).^2 + (sensor_y(i4_a1)-rp_y).^2);       % 领域内节点与原点之间的距离
    H_a1 = [H_a1;H_i0_a1];
    if H_i0_a1 == 0
        V_i0_a1 = 0;
    else
        V_i0_a1 = 1 - exp(-H_i0_a1.^2/a1.^2);                               %  根据高斯变差函数计算变差值
    end
    Variogram_N00_a1 =[Variogram_N00_a1;V_i0_a1];
end
Variogram_N0_a1 = [Variogram_N00_a1;1];                                   % 得到变差函数 列向量

Variogram_Njj1_a1 = [];

for j4_a1 = 1:length(sensor_x) 
    H_Nj_a1 = sqrt((sensor_x(:)-sensor_x(j4_a1)).^2 + (sensor_y(:)-sensor_y(j4_a1)).^2);
    if H_Nj_a1 == 0
        V_Nj_a1 = 0;
    else
        V_Nj_a1 = 1 - exp(-H_Nj_a1.^2/a1.^2);                               %  根据高斯变差函数计算变差值
    end
    Variogram_Njj1_a1 = [Variogram_Njj1_a1 V_Nj_a1];
end

         One1_a1 = ones(length(sensor_x),1);
         Variogram_Njj_a1 = [Variogram_Njj1_a1 One1_a1];
         One_a1= [One1_a1' 0];
         Variogram_NN_a1 =[ Variogram_Njj_a1;One_a1 ];
         Weight0_a1 = inv(Variogram_NN_a1) * Variogram_N0_a1;
         Weight1_a1 = Weight0_a1(1:length(sensor_x));                                    % 取出Weight0中的权系数，得到真正的权系数向量     
         Variance0_a1 = Weight1_a1'*Variogram_N00_a1 +  Weight0_a1(length(sensor_x) + 1) ;
         rmse = sqrt(abs(Variance0_a1));
end
