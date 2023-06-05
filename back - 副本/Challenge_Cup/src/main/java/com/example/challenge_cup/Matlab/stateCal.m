function value = stateCal(pcomm,psense,duty_cycle)
%返回值为节点的状态 
test=rand; %随机产生test
pa=pcomm*psense*duty_cycle;
pr=pcomm*(1-psense)*duty_cycle;
pf=(1-pcomm)*duty_cycle;
ps=1-duty_cycle;
if(test<=pa)
    value='ACTIVE';
elseif(test>pa&&test<=pa+pr)
    value='RELAY';
elseif(test>pa+pr && test<=pa+pr+pf)
    value='FAIL';
else
    value='SLEEP';
end

end

