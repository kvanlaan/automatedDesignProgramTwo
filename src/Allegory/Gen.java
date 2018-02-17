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
    static String BIGNAME;

    public static void main(String... args) {
        System.out.println("\n\nPart3...\n");

        // Step 1: standard marquee processing
        Marquee2Arguments mark = new Marquee2Arguments(Gen.class, ".vpl.pl", ".java", args);
        RBSetup(mark, args); // opens "X.classes.pl" database, as specified on the command line
        // variable db is the opened database
        String inputFileName = mark.getInputFileName();

        DB db_vpl = DB.readDataBase(inputFileName);
        String db_name = db_vpl.getName();
        Table vBox = db_vpl.getTable("vBox");
        Table vAssociation = db_vpl.getTable("vAssociation");
        
        DBSchema db_schema = DBSchema.readSchema("test/" + db_name + ".schema.pl");
        List<TableSchema> tables = db_schema.getTableSchemas();
        
        BIGNAME = db_name;
        
        header();
        mainClass(tables);
        common();
        
        //  classes();
        vBox.forEach(t -> genClass(vBox, t, vAssociation));
        for (TableSchema table : tables) {
            String className = table.getName();
            Tuple c = vBox.getFirst(d->d.get("name").equals(className));
            if (c == null) {
                l("\tpublic class " + className + " extends common <" + className + "> {\n");
                l("\t\tprotected " + className + " New(Table t) { return new " + className + "(t);}\n");
                l("\t\tpublic " + className + "() {table = " + className.toLowerCase() + ";}\n");
                l("\t\tpublic " + className + "(Table t) { super(\"" + className + "\", t); }\n");
                l("\t\tpublic " + className + "(Tuple t) { super(\"" + className + "\", t); }\n");
                l("\t\tprotected " + className + "(String n, Table t) { super(n,t);}\n");
                l("\t\tprotected " + className + "(String n, Tuple t) { super(n,t);}\n");
                l("\t}\n");
            }
        }
        
//        vAssociation.forEach(t -> genAssociation(vbox, t));
        // we need a gen function for association

        // done with class code
        footer();
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

    static String findFieldNameById(Table table, String id) {
        for (Tuple x : table.tuples()) {
            String tupleId = x.get("id");
            if (tupleId.equals(id)) {
                String fields = x.get("fields");
                return parseFieldsForId(fields);
            }
        }
        return null;
    }

    static String parseFieldsForId(String fields) {
        String fieldId = fields.split("%")[0];
        return fieldId;
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

    static void mainClass(List<TableSchema> tables) {
        l("public class " + BIGNAME + " {\n");
        l("\tDB db;");
        for (TableSchema table : tables) {
            String s = table.getName().toLowerCase();
            l("\tpublic Table " + s + ";");
        }
        l(1);
        
        l("\tpublic " + BIGNAME + "(String fileName) {");
        l("\t\tdb = DB.readDataBase(fileName);");
        for (TableSchema table : tables) {
            String S = table.getName();
            String s = S.toLowerCase();
            l("\t\t" + s + " = db.getTableEH(\"" + S + "\");");
        }
        l("\t}\n");
    }

    static void common() {
        l("abstract class common <T extends common> {\n"
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

        l("package " + BIGNAME + ";\n"
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
        List cfields = c.getColumns();

        l("     protected " + name + " New(Table t) { return new " + name + "(t); }\n"
                + "\n"
                + "      public " + name + "() { table = " + name.toLowerCase() + "; }\n"
                + "\n"
                + "      public " + name + "(Table t) { super(\"" + name + "\",t); }\n"
                + "\n"
                + "      public " + name + "(Tuple t) {  super(\"" + name + "\",t); }\n"
                + "\n"
                + "      protected " + name + "(String n, Table t) { super(n,t); }\n"
                + "\n"
                + "      protected " + name + "(String n, Tuple t) {  super(n,t); }\n");

        for (Tuple a : associations.tuples()) {
            Boolean twoWay = true;
            if (a.get("arrow1").equals("BLACK_DIAMOND") || a.get("arrow2").equals("BLACK_DIAMOND") || a.get("arrow1").equals("DIAMOND") || a.get("arrow2").equals("DIAMOND")) {
                twoWay = false;
            }
            
   
            if (c.get("id").equals(a.get("cid1"))) {
                String retType = findNameById(table, a.get("cid2"));
                String classTypeFieldId = parseFieldsForId(c.get("fields"));
                String retTypeFieldId = findFieldNameById(table, a.get("cid2"));
                String classNodeOne = a.get("role1").toLowerCase() + "_" + a.get("role2").toLowerCase(); 
                if(!twoWay) {
                    classNodeOne =  retType.toLowerCase();
                }
                l("     public " + retType + " " + a.get("role2") + "(){\n"
                        + "         Table result1 = table.rightSemiJoin(\"" + classTypeFieldId + "\"," + classNodeOne + ",\"Person\");");
                if (twoWay) {
                    l("         Table result2 = result1.rightSemiJoin(\"" + retType + "\"," + retType.toLowerCase() + ",\"" + retTypeFieldId + "\");");
                     l("         return new " + retType + "(result2);\n"
                        + "     }\n");
                } else {
                l("         return new " + retType + "(result1);\n"
                        + "     }\n");
                }
            }

            if (c.get("id").equals(a.get("cid2"))) {
                String retType = findNameById(table, a.get("cid1"));
                String classTypeFieldId = parseFieldsForId(c.get("fields"));
                String retTypeFieldId = findFieldNameById(table, a.get("cid1"));
                String classNodeOne = a.get("role1").toLowerCase() + "_" + a.get("role2").toLowerCase(); 
                if(!twoWay) {
                    classNodeOne =  retType.toLowerCase();
                }
                l("     public " + retType + " " + a.get("role1") + "(){\n"
                        + "         Table result1 = table.rightSemiJoin(\"" + classTypeFieldId + "\"," + classNodeOne + ",\"Person\");");
                if (twoWay) {
                    l("         Table result2 = result1.rightSemiJoin(\"" + retType + "\"," + retType.toLowerCase() + ",\"" + retTypeFieldId + "\");");
                     l("         return new " + retType + "(result2);\n"
                        + "     }\n");
                } else {
                l("         return new " + retType + "(result1);\n"
                        + "     }\n");
                }
            }
        }
        l("}");
        l(1);
    }
}
