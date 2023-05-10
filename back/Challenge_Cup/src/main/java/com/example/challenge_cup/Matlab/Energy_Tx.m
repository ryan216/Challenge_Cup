function energy= Energy_Tx(Eele,fs,mp,k,d)
    d0=sqrt(fs/mp);
    if(d<d0)
        energy=k*Eele+k*fs*d*d;
    else
        energy=k*Eele+k*mp*d^4;
    end
end

