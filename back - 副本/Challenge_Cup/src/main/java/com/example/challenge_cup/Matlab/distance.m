% 计算距离节点Node最近的m个节点
function index = distance(k,Node,m,N)  % 返回值为距离最近的m个节点

    point.x = Node(k).x;
    point.y = Node(k).y;
    for i = 1 : N      % 计算当前节点k与其他所有点的距离
        
        if(i == k)     % 将自己与自己的距离设置为最大值
            alldis(i,1) = Node(i).number;
            alldis(i,2)= 1000000;
        
        elseif(strcmp(Node(i).attribute,'EVICTED')) % 如果当前节点已经被去除（FAIL）
            alldis(i,1) = Node(i).number;
            alldis(i,2) = 1000000;
        else
            alldis(i,1) = Node(i).number;
            alldis(i,2)=distanceCal(Node(i),point);
        end
    end
    % 对距离进行排序
    b = alldis(:,2); % 取所有距离保存在b中
    [c,pos] = sort(b); % pos为排序后的下标,c为第一行的排序结果
    alldis(:,1) = pos;
    alldis(:,2) = c;
    
    % 取最近的m个节点
    for i = 1 : m
        index(i) = alldis(i,1); %取第一列
    end 
end
