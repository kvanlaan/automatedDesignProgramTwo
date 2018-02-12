dbase(vpl,[vBox,vAssociation]).

table(vBox,[id,type,"name","fields","methods",x,y]).
vBox(ClassNode0,c,'A','id%a','',754.0,113.0).
vBox(ClassNode1,c,'B','b','',624.0,275.0).
vBox(ClassNode2,c,'D','d','',936.0,278.0).
vBox(ClassNode3,c,'C','c','',619.0,397.0).
vBox(ClassNode4,c,'E','e%f','',937.0,408.0).
vBox(ClassNode5,c,'X','g','',1109.0,111.0).
vBox(ClassNode6,c,'Y','h','',1170.0,273.0).

table(vAssociation,[id,cid1,type1,"role1","arrow1",cid2,type2,"role2","arrow2","bentStyle","lineStyle","middleLabel"]).
vAssociation(A0,ClassNode1,c,'','',ClassNode0,c,'','TRIANGLE','VHV','','').
vAssociation(A1,ClassNode2,c,'','',ClassNode0,c,'','TRIANGLE','VHV','','').
vAssociation(A2,ClassNode3,c,'','',ClassNode1,c,'','TRIANGLE','VHV','','').
vAssociation(A3,ClassNode4,c,'','',ClassNode2,c,'','TRIANGLE','VHV','','').
vAssociation(A4,ClassNode0,c,'','',ClassNode5,c,'','BLACK_DIAMOND','HVH','','x').
vAssociation(A5,ClassNode6,c,'','DIAMOND',ClassNode2,c,'','','HVH','','y').

