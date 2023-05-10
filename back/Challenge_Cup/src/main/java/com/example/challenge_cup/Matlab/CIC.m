function [Area,Node]= CIC(N,Node,WL,WB,rs,rmsemax)
 % 划分网格
 
CR = 2 * rs;
x1 = 0 : CR : WL;
y1 = 0 : CR : WB;
rebuild = 0;
rebuild_x=[];
rebuild_y=[];

[X,Y] = meshgrid(x1,y1);       
hold on
plot(X,Y,':',Y,X,':'); %横线&竖线。

for xpoint = 1 : length(X)-1
    for ypoint = 1 : length(Y)-1
        % 重建点坐标
        rp_x = X(1,xpoint) + CR/2;
        rp_y = Y(ypoint,1) + CR/2;
        % 重建区域坐标
        polyx = [X(1,xpoint); X(1,xpoint)+CR; X(1,xpoint)+CR; X(1,xpoint); X(1,xpoint)];
        polyy = [Y(ypoint,1); Y(ypoint,1); Y(ypoint,1)+CR; Y(ypoint,1)+CR; Y(ypoint,1)];
        % 在区域内的传感器节点的数量
        sensor_x=[];
        sensor_y=[];
        sensork = 0;
        for nodei = 1 : N       % 判断哪些节点在该区域内
            if(strcmp(Node(nodei).state,'ACTIVE'))
                xq = Node(nodei).x;
                yq = Node(nodei).y;
                [in,on] = inpolygon(xq,yq,polyx,polyy);
                if(in||on) % nodei节点在该区域内
                    sensork = sensork + 1;
                    sensor_x(sensork) = xq;
                    sensor_y(sensork) = yq;
                end  
            end
        end
        % 找到所有在区域内的点
        if(sensork) % 该区域至少包含一个点
            rmse = RMSE(sensor_x, sensor_y, rp_x, rp_y,CR)
            if(rmse <= rmsemax) % 重建点满足要求
                rebuild = rebuild + 1;
                rebuild_x(rebuild) = rp_x; % 保存满足要求的重建点
                rebuild_y(rebuild) = rp_y;
            end 
        end
        
    end
end
 hold on 
 plot(rebuild_x,rebuild_y,'*');

Area = rebuild * CR * CR

end

