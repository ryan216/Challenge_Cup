%定义节点
classdef Nodes 

    properties    % 定义类成员
        number    % 节点的序号
        x         % 节点的坐标
        y
        state     % 节点的状态ACTIVE RELAY FAIL SLEEP
        energy    % 表示能量
        area
        
    end
    
    methods
        function obj = Nodes(number,x,y,state,energy,area) %初始化结点
            if nargin > 0 %使用nargin来确定调用该函数时在此处提供了多少个输入参数
                obj.number = number;
                obj.x = x;
                obj.y = y;
                obj.state = state;
                obj.energy = energy;
                obj.area = area;
            end
        end
        
        
        function obj = updateLocation(obj,x,y) %更新节点位置
              obj.x = x;
              obj.y = y;
        end
        
        function obj = updateEnergy(obj,energy) % 更新节点能量
              obj.energy = energy;
        end
        
        function obj = updateState(obj,state) % 更新结点状态
              obj.state = state;
        end
    
        function dis=distanceCal(obj,point) %节点到point的距离
            dis=sqrt((obj.x-point.x)*(obj.x-point.x)+(obj.y-point.y)*(obj.y-point.y));
        end
    end
end

