function [xqm,Node] = connectivity_cal(N,Node,NodeA,Eele,Esense,fs,mp,Areq,link_reliability,r,rsi,xa)
    
        xqm=0;

       %枚举连通矩阵
        EAM=EnumEnergyAvalMatrix(N,Node,Eele,fs,mp,link_reliability,r,rsi)
        NodeA
        EAM2=EAM;
        G=digraph(EAM2);

        %计算连通性
        EN=zeros(1,N+1);
        sumarea=0;
        k=pi*r*r;


         % xqm=1;
         consum = 0;
         
        for i=1:length(xa)
           P=shortestpath(G,NodeA(i).number,N+1)
           if(isempty(P)==0) % 数列非空说明连通
               % xqm=0;
               consum = consum + 1;
           end
        end
        xqm = consum / length(xa)
            %更新节点能量
                            for i=1:N                       
                                if(strcmp(Node(i).state,'ACTIVE'))
                                     Node(i).energy=Node(i).energy-k*Esense-Energy_Rx(k,Eele)-Energy_Tx(Eele,fs,mp,k,rsi);
                                elseif(strcmp(Node(i).state,'RELAY'))
                                    Node(i).energy=Node(i).energy-Energy_Rx(k,Eele)-Energy_Tx(Eele,fs,mp,k,rsi);
                                end
                            end
                    
                                        
                   
end

