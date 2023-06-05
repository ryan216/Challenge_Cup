% 相关参数定义

WL=120;             % 模拟区域的长度（100m）
WB=120;             % 模拟区域的宽度（100m）
r=20;               % 节点的感知半径（80m）
rc = r * 2;         % 通信半径
N = 100;            % 节点的数量（50-100）
t=5;                % ACR时间间隔 
pcomm=0.95;         % Comm正常工作的概率
psense=0.8;         % Sense正常工作的概率
duty_cycle= 0.6;    % 占空比（0.4-0.7的随机数）
link_reliability=1; % 链路可靠性
rmsemax = 0.7;      % CIC阈值
areq=0.6;           % 区域覆盖要求
Areq=areq*WL*WB;
connectivity = 0.6; % 连通率要求
fs=0.1*10^(-12);    % 自由空间信道模型 单位joules / bit
mp=0.0013*10^(-12); % 多径衰落信道模型
u=1;                % sink节点移动的速度
M=WL/(u*t); 
initial_energy=2;   % 初始能量
Eele=0.937*10^(-6); % 收发器消耗的能量
Esense=50*10^(-9); 

sum1=0;
sumenergy=0;

%随机产生点位置
x=WL*rand(N,1);
y=WB*rand(N,1);
plot(x,y,'.');

for average = 1 : 1 %蒙特卡洛模拟，可以暂时不用管
% 初始化节点
    for i=1:N

        node=Nodes(i,x(i),y(i),'NONE',initial_energy,pi*r*r);
        Node(i)=node;

    end

    sum1=0;
    sum2=0;
    zerocal=true;
    %随机产生N个节点状态
            xa=[];
            ya=[];
            index=0;

            for i=1:N
                %枚举每个随机节点状态
                state=stateCal(pcomm,psense,duty_cycle);
                Node(i).state=state;

                %寻找ACTIVE节点
                if(strcmp(Node(i).state,'ACTIVE')) %找到ACTIVE节点并保存下来
                    index=index+1; %NodeA中保存所有ACTIVE节点，index为节点数量
                    NodeA(index)=Node(i);
                    xa(index)=Node(i).x;
                    ya(index)=Node(i).y;
                end
            end

    for time = 1 :  100  

                % 确定sink的位置
                sinkx=mod(u*time,WL); %sink沿x轴以u速度运动，时间间隔是time
                sinky=0;

                % 随机产生N个节点状态
                xa=[];
                ya=[];
                index=0;
                for i=1:N
                     %枚举每个随机节点状态
                     if(strcmp(Node(i).state,'FAIL'))
                         Node(i).state = 'FAIL';
                     else
                        state=stateCal(pcomm,psense,duty_cycle);
                        Node(i).state=state;
                     end

                        %寻找ACTIVE节点
                        if(strcmp(Node(i).state,'ACTIVE')) %找到ACTIVE节点并保存下来
                            index=index+1; %NodeA中保存所有ACTIVE节点，index为节点数量
                            NodeA(index)=Node(i);
                            xa(index)=Node(i).x;
                            ya(index)=Node(i).y;
                        end
                end

                % 储存sink点 因为能量矩阵为N*N+1
                Node(N+1)=Nodes(N+1,sinkx,sinky,'ACTIVE',Inf,pi*r*r);
                % sink节点的能量是无限的

                % 计算覆盖面积
                area=0;
                if(index~=0) %存在ACTIVE节点

                     %计算ACTIVE节点感知面积
                     [area,Node1]=CIC(N,Node,WL,WB,r,rmsemax);% 保存每个节点的覆盖面积
                     Node=Node1;

                     if(area>=Areq)   %感知面积满足需求      
                          %枚举连通矩阵
                          %EAM1=EnumEnergyAvalMatrix1(N,Node,link_reliability,r);  %没有能量限制

                            %计算连通性
                             [xqm1,Node1] = connectivity_cal(N,Node,NodeA,Eele,Esense,fs,mp,Areq,link_reliability,r,rc,xa);
                             Node = Node1;
                             if(xqm1<connectivity) % 连通性不满足要求 
                                  break;
                             end

                     else % 感知面积不满足需求
                         break;
                     end
                end

              % 判断能量为0的节点
              for j = 1 : N

                  if(strcmp(Node(j).state,'ACTIVE')||strcmp(Node(j).state,'RELAY'))
                        if(Node(j).energy <= 0) % 如果节点能能量<0,则直接被去除
                            Node(j).state = 'FAIL';
                        end
                  end
               end

    end
end

     
     
     
     








