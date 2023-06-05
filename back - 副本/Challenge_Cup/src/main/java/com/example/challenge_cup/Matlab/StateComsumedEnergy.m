function comsumedEnergy = StateComsumedEnergy(state,Esense,Eele,mp,fs,k,r)
   
    if(strcmp(state,'ACTIVE'))
        %comsumedEnergy= Energy_Rx(k,Eele)+Esense+Energy_Tx(Eele,fs,mp,k,r);
        comsumedEnergy= Energy_Tx(Eele,fs,mp,k,r)+k*Esense+Energy_Rx(k,Eele);
    elseif(strcmp(state,'RELAY'))
        %comsumedEnergy=Energy_Rx(k,Eele)+Energy_Tx(Eele,fs,mp,k,r);
        comsumedEnergy= Energy_Tx(Eele,fs,mp,k,r);
%     elseif(strcmp(state,'SLEEP'))
%          comsumedEnergy= 0.70588*(Energy_Rx(k,Eele)+k*Esense+Energy_Tx(Eele,fs,mp,k,r));
    else
        comsumedEnergy=0;
end

