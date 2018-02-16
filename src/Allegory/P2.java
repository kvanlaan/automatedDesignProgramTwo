package Allegory;

import MDELite.Marquee1Argument;
import java.io.PrintStream;

public class P2 {
    static String fileName = "x.PDD.pl";
    
    public static void main(String[] args) throws Exception {
        Vpl2Schema.main("test/PDD.vpl.pl", "test/PDD.schema.pl");
        Vpl2Schema.main("test/inh.vpl.pl", "test/inh.schema.pl");
        
        
//        // Step 0: initialize database tables -- only person is needed
//        Marquee1Argument mark = new Marquee1Argument(P2.class, ".PDD.pl", args);
//        String inputFileName = mark.getInputFileName();
//        PDD db = new PDD(inputFileName);
//        PDD.Person person = db.new Person();
//        PDD.Division division = db.new Division();
//        PrintStream out = System.out;
//        
//        PDD.Person res = person.id();
////        Table resTable = res.table;
////        resTable.print(out);
////        System.out.println(   person.size());
//        
//        String result = res.id().equals(person)?"works!":"fails!";
//        out.format("1) id arrow test %s\n\n", result);
//
//        out.println("2) print all persons whose name begins with 'd' or 'p'");
/////two
//        person.select(t-> t.get("name").startsWith("d") || t.get("name").startsWith("p")).print();
/////two        
////        out.println("all persons who work in division v1");
////        division.select(d->d.is("vid","v1")).hasDeps().employs().print();
//        
//        out.println("3) print all divisions in which don works");
/////three
//        person.select(t->t.is("name","don")).worksin().inDiv().print();
/////three
//        
//        out.println("4) print all persons that work in the same department as priscila");
/////four
//        person.select(t->t.is("name","priscila")).worksin().employs().print();
/////four
//        
//        out.println("5) who are the division colleagues of priscila");
/////five
//        person.select(t->t.is("name","priscila")).worksin().inDiv().hasDeps().employs().print();
/////five
//        
//        out.println("6) print the children of employees that work in this company");
/////six
//        person.childrenOf().print();
/////six
//        
//        //Step 7: are there children who work in the same department as their parent?
/////seven
//        System.out.println("7) are there children who work in the same department as their parent?\n");
//        person.forEach(p->{   
//            PDD.Department pdepts = db.new Person(p).worksin();
//            PDD.Department cdepts = db.new Person(p).childrenOf().worksin();
//            PDD.Department inter = pdepts.intersect(cdepts);
//            if (inter.size()!=0) {
//                out.format("%s and at least one of his/her children work in the following departments\n", p.get("name"));
//                inter.print();
//                out.println();
//            }
//        });
/////seven


    }
}
