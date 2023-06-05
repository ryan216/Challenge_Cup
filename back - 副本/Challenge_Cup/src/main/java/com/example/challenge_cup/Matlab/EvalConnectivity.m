function linkValue = EvalConnectivity(EAM,Node,S,xa,N)


        i=1;
        EAM1=EAM;
       
        EAM1=isCon(EAM1,Node,N); %判断N个节点是否连接？
        
        while(i<=length(xa))
    
            if(EAM1(S(i).number,N+1)==1)
                linkValue=1;
            else
                linkValue=0;
                break;
            end
            
            i=i+1;
                 
        end
end



