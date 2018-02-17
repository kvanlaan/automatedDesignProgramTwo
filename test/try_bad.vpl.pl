dbase(vpl,[vBox,vAssociation]).

table(vBox,[id,type,"name","fields","methods",x,y]).
vBox(ClassNode0,c,'Department','did%name','',564.0,345.0).
vBox(ClassNode1,c,'Person','pid%name','',311.0,163.0).
vBox(ClassNode2,c,'Division','vid%name','',823.0,353.0).

table(vAssociation,[id,cid1,type1,"role1","arrow1",cid2,type2,"role2","arrow2","bentStyle","lineStyle","middleLabel"]).
vAssociation(A0,ClassNode1,c,'employs','',ClassNode0,c,'worksin','TRIANGLE','HVH','','employs_worksin').
vAssociation(A1,ClassNode2,c,'5','BLACK_DIAMOND',ClassNode0,c,'hasDepts','','HVH','','').
vAssociation(A2,ClassNode1,c,'parentsOf','',ClassNode1,c,'childrenOf','V','HVH','','').

