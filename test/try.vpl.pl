dbase(vpl,[vBox,vAssociation]).

table(vBox,[id,type,"name","fields","methods",x,y]).
vBox(ClassNode0,c,'Person','pid%name','',86.0,130.0).
vBox(ClassNode1,c,'Department','did%name','',312.0,216.0).
vBox(ClassNode2,c,'Division','vid%name','',592.0,216.0).

table(vAssociation,[id,cid1,type1,"role1","arrow1",cid2,type2,"role2","arrow2","bentStyle","lineStyle","middleLabel"]).
vAssociation(A0,ClassNode0,c,'employs','',ClassNode1,c,'worksin','TRIANGLE','HVH','','').
vAssociation(A1,ClassNode2,c,'inDiv','BLACK_DIAMOND',ClassNode1,c,'hasDepts','','HVH','','').
vAssociation(A2,ClassNode0,c,'','',ClassNode0,c,'','','HVH','','parent,child').

