% inh database schema

dbase(inh,[A,B,D,C,E,X,Y]).

table(A,[id,a,x]).
table(B,[id,a,x,b]).
table(D,[id,a,x,d,y]).
table(C,[id,a,x,b,c]).
table(E,[id,a,x,d,y,e,f]).
table(X,[g]).
table(Y,[h]).

subtable(A,[B,D]).
subtable(B,[C]).
subtable(D,[E]).
