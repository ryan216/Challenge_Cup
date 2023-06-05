function EAM2 = isCon(EAM,Node,N)
    
    EAM2=EAM;
    for i=1:N

        Node(i).state
        if(strcmp(Node(i).state,'ACTIVE')||strcmp(Node(i).state,'RELAY'))
           for j=1:N
               if(EAM2(i,j)==1)
                   for k=1:N+1
                           EAM2(j,k)=EAM2(j,k)||EAM2(i,k);
                   end
               end
               
           end
        end
    end   
end

