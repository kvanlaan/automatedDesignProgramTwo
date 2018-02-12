///column1
dbase(PDD,[Person,Department,Division,employs_worksin,childrenOf_parentsOf]).

table(Person,[pid,name,age]).
Person(p1,don,64).
Person(p2,karen,57).
Person(p3,hanna,23).
Person(p4,alex,25).
Person(p5,steve,53).
Person(p6,priscila,28).

table(Department,[did,name,inDiv:Division]).
Department(d1,mens,v1).
Department(d2,womens,v1).
Department(d3,appliances,v2).
Department(d4,hardware,v2).

table(Division,[vid,name]).
Division(v1,clothing).
Division(v2,goods).
///column1

///column2
table(childrenOf_parentsOf,[id,childrenOf:Person,parentsOf:Person]).
childrenOf_parentsOf(c1,p3,p1).
childrenOf_parentsOf(c2,p3,p2).
childrenOf_parentsOf(c3,p4,p1).
childrenOf_parentsOf(c4,p4,p2).

table(employs_worksin,[id,Person:Person,Department:Department]).
employs_worksin(w1,p1,d1).
employs_worksin(w2,p2,d2).
employs_worksin(w3,p3,d2).
employs_worksin(w4,p4,d4).
employs_worksin(w5,p5,d3).
employs_worksin(w6,p6,d2).
employs_worksin(w7,p1,d3).
///column2
