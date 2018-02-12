% PDD database schema

dbase(PDD,[Person,Department,Division,employs_worksin,childrenOf_parentsOf]).

table(Person,[pid,name]).
table(Department,[did,name,inDiv:Division]).
table(Division,[vid,name]).
table(employs_worksin,[id,Person:Person,Department:Department]).
table(childrenOf_parentsOf,[id,childrenOf:Person,parentsOf:Person]).
