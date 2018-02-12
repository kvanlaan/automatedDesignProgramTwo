package p2;

import MDELite.Marquee4Conform;
import PrologDB.Constraints;
import PrologDB.DB;
import PrologDB.ErrorReport;
import PrologDB.Table;
import PrologDB.Tuple;
import java.util.function.Predicate;

public class ClassConform extends Constraints {
    /** conformance program for Violet .vpl.pl databases
     * @param args X.vpl.pl
     */
    public static void main(String... args) {
        String expl;
        
        // Step 1: Standard marquee processing
        Marquee4Conform mark = new Marquee4Conform(ClassConform.class, ".vpl.pl", args);
        String inputFileName = mark.getInputFileName();

        // Step 2: read database and create error handler
        DB db = DB.readDataBase(inputFileName);
        Table vBox = db.getTableEH("vBox");
        Table vAssociation = db.getTableEH("vAssociation");
        ErrorReport er = new ErrorReport();

        // Step 3: apply constraints
        // C1: all vBox ids are unique
        // C2: all vAssociation ids are unique
        isUnique(vBox, "id", er);
        isUnique(vAssociation, "id", er);

        // C3: All cid1 references are legit
        // C4: All cid2 references are legit
        isLegit(vAssociation, "cid1", vBox, "id", er);
        isLegit(vAssociation, "cid2", vBox, "id", er);

        // vBox constraints 
        // % type = c(class),i(nterface),n(ote).  packages are skipped   
        // table(vBox,[id,type,"name","fields","methods",x,y]).
        // C5: if type = i, then fields are empty
        // C6: if type = n, then fields and methods are empty
        Predicate<Tuple> typeisi = r->r.is("type","i");
        Predicate<Tuple> typeisn = r->r.is("type","n");
        Predicate<Tuple> fieldsIsEmpty = r->r.is("fields","");
        Predicate<Tuple> methodsIsEmpty = r->r.is("methods","");
        
        expl = "interfaces should have no fields";
        implies(vBox, typeisi, fieldsIsEmpty,expl,er);
        
        expl = "notes should have no fields or methods";
        implies(vBox, typeisn, fieldsIsEmpty.and(methodsIsEmpty),expl,er);

        // % type1,type2 = c(lass) or i(nterface)
        //% bentStyle = "-"(solid) or "-.-"
        //% lineStyle = ""(solid) or "DOTTED"
        //% arrow1,2 = V, TRIANGLE, DIAMOND, BLACK_DIAMOND
        //% bentStyle = "", HV, VH, HVH, VHV
        //table(vAssociation,[id,cid1,type1,"role1","arrow1",cid2,type2,"role2","arrow2","bentStyle","lineStyle","middleLabel"]).
        // C7,8: if arrow1==TRIANGLE implies arrow2isEmpty and role1isEmpty and role2isEmpty (symmetrical)
        Predicate<Tuple> a1isTriangle = t -> t.is("arrow1", "TRIANGLE");
        Predicate<Tuple> a2isTriangle = t -> t.is("arrow2", "TRIANGLE");
        Predicate<Tuple> a1isEmpty = t -> t.is("arrow1", "");
        Predicate<Tuple> a2isEmpty = t -> t.is("arrow2", "");
        Predicate<Tuple> r1isEmpty = t -> t.is("role1", "");
        Predicate<Tuple> r2isEmpty = t -> t.is("role2", "");

        expl = "arrow1=TRIANGLE implies (arrow2 is empty and role1 is empty and role2 is empty)";
        implies(vAssociation, a1isTriangle, a2isEmpty.and(r1isEmpty.and(r2isEmpty)), expl, er);
        implies(vAssociation, a2isTriangle, a1isEmpty.and(r1isEmpty.and(r2isEmpty)), expl, er);

        // C11 if arrow1==TRIANGLE and type1=c and type2=i, then lineStyle== dashed, symmetrically
        Predicate<Tuple> candi = r -> r.is("type1", "c") && r.is("type2", "i");
        Predicate<Tuple> iandc = r -> r.is("type1", "i") && r.is("type2", "c");
        Predicate<Tuple> isDashed = r -> r.is("lineStyle", "DOTTED");
        expl = "linestyle should be dashed)";
        implies(vAssociation, a1isTriangle.and(candi).or(a2isTriangle.and(iandc)),
                isDashed, expl, er);

        // if middleLabel != nothing error
        Predicate<Tuple> misEmpty = r->!r.is("middleLabel","");
        expl = "association should not have a middleLabel";
        iftest(vAssociation,misEmpty,expl,er);
        
        // See if there are extends cycles
        Predicate<Tuple> candc = y -> y.is("type1", "c") && y.is("type2", "c");
        Table ct = Constraints.makeCycleTable();
        vAssociation.stream().filter(a1isTriangle.and(candc)).forEach(t->addTuple(t,ct,true));
        vAssociation.stream().filter(a2isTriangle.and(candc)).forEach(t->addTuple(t,ct,false));
        Constraints.cycleCheck(ct,er);
        
        // Finally, see if there are implements cycles 
        Table ct2 = Constraints.makeCycleTable();
        vAssociation.stream().filter(a1isTriangle.and(candc.negate())).forEach(t->addTuple(t,ct2,true));
        vAssociation.stream().filter(a2isTriangle.and(candc.negate())).forEach(t->addTuple(t,ct2,false));
        Constraints.cycleCheck(ct2, er);
        
        // End report all errors
        er.printReportEH(System.out);
    }
                
    private static void addTuple(Tuple t, Table ct, boolean direction) {
        Tuple cycle = new Tuple(ct);
        if (direction) {
            cycle.setValues(t.get("cid1"), t.get("cid2"));
        } else {
            cycle.setValues(t.get("cid2"), t.get("cid1"));
        }
        ct.add(cycle);
    }
}
