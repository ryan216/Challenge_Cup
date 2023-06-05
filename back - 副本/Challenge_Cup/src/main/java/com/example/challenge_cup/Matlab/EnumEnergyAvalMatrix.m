function EAM= EnumEnergyAvalMatrix(N,Node,Eele,fs,mp,link_reliability,r,rsi)
    EAM=zeros(N+1,N+1);
    k=pi*r*r;
    for i=1:N
        if( strcmp(Node(i).state,'ACTIVE')||strcmp(Node(i).state,'RELAY'))
            for j=1:N+1
                if( strcmp(Node(j).state,'ACTIVE')||strcmp(Node(j).state,'RELAY'))     
                      dist=Node(i).distanceCal(Node(j));
                      if(dist<=rsi)
 
                                    energy_Tx=Energy_Tx(Eele,fs,mp,k,dist);
                                    if(energy_Tx<=Node(i).energy)

                                        %检查链接可靠性
                                        testij=rand;
                                        if(testij<=link_reliability)
                                              EAM(i,j)=1;
                                        end

                                    end
                      end
                 end

            end
        end
    end
    for i=1:N
       EAM(N+1,i)=EAM(i,N+1); 
    end
    EAM(N+1,N+1)=1;
end



