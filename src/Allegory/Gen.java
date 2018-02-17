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
import PrologDB.*;
import MDELite.RunningBear;
import MDELite.Marquee2Arguments;
import java.util.List;

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

        header();

        mainClass();
        common();
        //  classes();
        DB db_vpl = DB.readDataBase(inputFileName);
        String db_name = db_vpl.getName();
        DB db_schema = DB.readDataBase("test/" + db_name + ".schema.pl");

        Table vBox = db_vpl.getTable("vBox");
        Table vAssociation = db_vpl.getTable("vAssociation");
        vBox.forEach(t -> genClass(vBox, t, vAssociation));
//        vAssociation.forEach(t -> genAssociation(vbox, t));
        // we need a gen function for association

        // done with class code

        footer();

        // Step 4: done
    }

    static String findNameByType(Table table, String type) {
        for (Tuple x : table.tuples()) {
            if (x.get("type").equals(type)) {
                return x.get("name");
            }
        }
        return null;
    }

    static String findNameById(Table table, String id) {
        for (Tuple x : table.tuples()) {
            if (x.get("id").equals(id)) {
                return x.get("name");
            }
        }
        return null;
    }

    static void genAssociation(Table table, Tuple c) {
        String name = c.get("id");

        // make sure to put on cid2
        l("public " + findNameById(table, c.get("cid1")) + " " + c.get("role1") + "{\n;"
                + "          Table result1 = table.rightSemiJoin(\"pid\",employs_worksin,\"Person\");\n"
                + "          Table result2 = result1.rightSemiJoin(\"Department\",department,\"did\");\n"
                + "          return new Department(result2);\n"
                + "       }\n"
                + "   ");
    }

    static void footer() {
        l("}");
    }

    static void mainClass() {
        l("public class PDD {\n");
    }

    static void common() {
        l(      "abstract class common <T extends common> {\n"
                + "///commonT\n"
                + "       Table table;\n"
                + "\n"
                + "       protected common() { }\n"
                + "\n"
                + "       protected common(String tableName, Tuple t) {\n"
                + "          TableSchema ts = t.getSchema();\n"
                + "          if (!ts.getName().equals(tableName)) \n"
                + "            throw new RuntimeException(\"assigning non-\"+tableName+\" table to \"+tableName);\n"
                + "          table = new Table(ts).add(t);\n"
                + "       }\n"
                + "\n"
                + "       protected common(String tableName, Table tab) {\n"
                + "          if (!tab.getSchema().getName().equals(tableName)) {\n"
                + "             throw new RuntimeException(\"assigning non-\"+tableName+\" table to \"+tableName);\n"
                + "          }\n"
                + "          table = tab;\n"
                + "        }\n"
                + "\n"
                + "       public T select(Predicate<Tuple> p) {\n"
                + "          Table result = table.filter(p);\n"
                + "          return New(result);\n"
                + "        }\n"
                + "\n"
                + "       protected abstract T New(Table t);\n"
                + "\n"
                + "       public T id() { return New(table); }\n"
                + "\n"
                + "       public void print() { table.print(System.out); }\n"
                + "       public void forEach(Consumer<Tuple> action) { table.stream().forEach(t -> action.accept(t)); }\n"
                + "\n"
                + "       public T intersect(T tab) { return New(this.table.intersect(tab.table)); }\n"
                + "\n"
                + "       public int size() { return this.table.count(); }\n"
                + "\n"
                + "       public boolean equals(T tab) { return this.table.equals(tab.table); }\n"
                + "   }\n"
                + "///commonT\n"
                + "\n");
    }

    public static void header() {

        l("package Allegory;\n"
                + "\n"
                + "///catdb\n"
                + "import PrologDB.DB;\n"
                + "import PrologDB.Table;\n"
                + "import PrologDB.TableSchema;\n"
                + "import PrologDB.Tuple;\n"
                + "\n"
                + "import java.util.function.Consumer;\n"
                + "import java.util.function.Predicate;\n");
    }

    static void genClass(Table table, Tuple c, Table associations) {
        // Step 1: print the class header
        String name = c.get("name");
        String xtends = "extends common<" + name + ">";
        l("public class %s %s {", c.get("name"), xtends);
//        
//        // Step 2: compute table of c's fields
//        //
        List cfields = c.getColumns();

        l("     Table table;\n"
                + "\n"
                + "       protected " + name + "() { }\n"
                + "\n"
                + "       protected " + name + "(String tableName, Tuple t) {\n"
                + "          TableSchema ts = t.getSchema();\n"
                + "          if (!ts.getName().equals(tableName)) \n"
                + "            throw new RuntimeException(\"assigning non-\"+tableName+\" table to \"+tableName);\n"
                + "          table = new Table(ts).add(t);\n"
                + "       }\n"
                + "\n"
                + "       protected " + name + "(String tableName, Table tab) {\n"
                + "          if (!tab.getSchema().getName().equals(tableName)) {\n"
                + "             throw new RuntimeException(\"assigning non-\"+tableName+\" table to \"+tableName);\n"
                + "          }\n"
                + "          table = tab;\n"
                + "        }\n"
                + "\n");
//    }

        for (Tuple a : associations.tuples()) {
            if (c.get("id").equals(a.get("cid2"))) {
             
                l("     public " + findNameById(table, a.get("cid1")) + " " + a.get("role1") + "{\n;"
                           // we need to fill in the values here, everything is hardcoded
                        + "          Table result1 = table.rightSemiJoin(\"pid\",employs_worksin,\"Person\");\n"
                        + "          Table result2 = result1.rightSemiJoin(\"Department\",department,\"did\");\n"
                        + "          return new Department(result2);\n"
                        + "       }\n"
                        + "   ");
            }

        }
        l("}");
        l(1);
    }
}
