/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Allegory;

/**
 *
 * @author katrinavanlaan
 */

import MDELite.Marquee1Argument;
import PrologDB.*;
import MDELite.RunningBear;
import MDELite.Marquee2Arguments;

public class Gen extends RunningBear {
    static String q = "\"";
    static DBSchema dbs;
    static DB db;
    static Table dual;
    static String appName;
    static Table cls, fld;
    
    public static void main(String... args) {
        System.out.println("\n\nPart3...\n");

       // Step 1: standard marquee processing

        Marquee2Arguments mark = new Marquee2Arguments(Gen.class, ".vpl.pl", ".java", args);
        RBSetup(mark, args); // opens "X.classes.pl" database, as specified on the command line
                             // variable db is the opened database
                             // file "X.java" is opened (or whatever command-line output is specified
        String inputFileName = mark.getInputFileName();


        // find a way to iterate through database tables
        // write a function to iterate through tables in a database
        DB db_vpl = DB.readDataBase(inputFileName);
        String db_name = db_vpl.getName();
        DB db_schema = DB.readDataBase("test/" + db_name + ".schema.pl");

        Table vBox = db_vpl.getTable("vBox");
        Table vAssociation = db_vpl.getTable("vAssociation");  
        vBox.forEach(t->some_functions(t));

        // Step 3: "generate" code using the runningbear...
        // cls = db.getTableEH("class");
        //cls.forEach(c->genClass(c,false));

    // Step 4: done
    }
    
    private static void some_functions(Tuple t) {
        System.out.println(t.get("name"));
    }
    
    static void genClass(Tuple c, boolean separateFile) {
        // Step 1: print the class header
        String sup = c.get("superName");
        String xtends = (!sup.equals("null"))?"extends "+sup:"";
        if (separateFile) {
            openOut(c.get("name")+".java");
        }
        l("class %s %s {", c.get("name"), xtends);
        
        // Step 2: compute table of c's fields
        Table cfields = c.rightSemiJoin("cid",fld,"cid"); 
        
        // Step 3: declare all fields and their types
        cfields.forEach(f-> l("   %s %s;", f.get("type"), f.get("name")));
        
        // Step 4: print the class footer
        l("}");
        l(1);    
        if (separateFile) {
            closeOut();
        }
    }
}