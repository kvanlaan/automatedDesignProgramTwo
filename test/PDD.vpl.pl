dbase(vpl,[vBox,vAssociation]).

table(vBox,[id,type,"name","fields","methods",x,y]).
vBox(ClassNode0,c,'Person','pid%name','',299.29289321881345,193.0918830920368).
vBox(ClassNode1,c,'Department','did%name','',604.0,193.0).
vBox(ClassNode2,c,'Division','vid%name','',885.0,198.0).

table(vAssociation,[id,cid1,type1,"role1","arrow1",cid2,type2,"role2","arrow2","bentStyle","lineStyle","middleLabel"]).
vAssociation(A0,ClassNode0,c,'employs','',ClassNode1,c,'worksin','','','','').
vAssociation(A1,ClassNode1,c,'hasDeps','',ClassNode2,c,'inDiv','BLACK_DIAMOND','HVH','','').
vAssociation(A2,ClassNode0,c,'childrenOf','',ClassNode0,c,'parentsOf','','','','').
