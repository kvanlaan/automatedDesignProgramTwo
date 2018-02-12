package p2;

import MDELite.Marquee1Argument;
import MDELite.Marquee2Arguments;
import PrologDB.*;;
import MDELite.RunningBear;

public class Vpl2Schema {
    public static void main(String... args) throws Exception {
        // standard marquee processing, read in the .vpl.pl
        Marquee2Arguments mark = new Marquee2Arguments(P2.class, ".vpl.pl", ".schema.pl", args);
        String inputFileName = mark.getInputFileName();
        DB in = DB.readDataBase(inputFileName);
        
        // when a schema is initially created, and until it is "finished", it is an .ooschema
        DBSchema pddSchema = new DBSchema(in.getName());
        System.out.println("\n\nPart2...");
        System.out.format("%% %s database schema\n\n", pddSchema.getName());
        
        // add tables
        Table vBox = in.getTable("vBox");
        Table vAssociation = in.getTable("vAssociation");
        vBox.forEach(t->addTableSchema_class(t, pddSchema));        
        vAssociation.forEach(t->addTableSchema_assoc(t, pddSchema, vBox));
        
        // output
        pddSchema.finishAndPropagateAttributes();
        pddSchema.print();
    }
    
    private static void addTableSchema_class(Tuple t, DBSchema pddSchema) {
        TableSchema tableSchema = new TableSchema(t.get("name")).addColumns(t.get("fields").split("%"));
        pddSchema.addTableSchema(tableSchema);
    }
    
    private static void addTableSchema_assoc(Tuple t, DBSchema pddSchema, Table vBox) {
        if (t.get("arrow1").equals("BLACK_DIAMOND")) {
            String tableName = vBox.getFirst(d->d.get("id").equals(t.get("cid2"))).get("name");
            String type = vBox.getFirst(d->d.get("id").equals(t.get("cid1"))).get("name");
            String roleName = !t.get("role1").isEmpty() ? t.get("role1") : t.get("middleLabel");
            pddSchema.getTableSchema(tableName).addColumns(roleName + ":" + type);
            
        } else if (t.get("arrow2").equals("BLACK_DIAMOND")) {
            String tableName = vBox.getFirst(d->d.get("id").equals(t.get("cid1"))).get("name");
            String type = vBox.getFirst(d->d.get("id").equals(t.get("cid2"))).get("name");
            String roleName = !t.get("role2").isEmpty() ? t.get("role2") : t.get("middleLabel");
            pddSchema.getTableSchema(tableName).addColumns(roleName + ":" + type);
            
        } else if (t.get("arrow1").equals("DIAMOND")) {
            String tableName = vBox.getFirst(d->d.get("id").equals(t.get("cid2"))).get("name");
            String type = vBox.getFirst(d->d.get("id").equals(t.get("cid1"))).get("name");
            String roleName = !t.get("role1").isEmpty() ? t.get("role1") : t.get("middleLabel");
            pddSchema.getTableSchema(tableName).addColumns(roleName + ":" + type);
            
        } else if (t.get("arrow2").equals("DIAMOND")) {
            String tableName = vBox.getFirst(d->d.get("id").equals(t.get("cid1"))).get("name");
            String type = vBox.getFirst(d->d.get("id").equals(t.get("cid2"))).get("name");
            String roleName = !t.get("role2").isEmpty() ? t.get("role2") : t.get("middleLabel");
            pddSchema.getTableSchema(tableName).addColumns(roleName + ":" + type);
            
        } else if (t.get("arrow1").equals("TRIANGLE") || t.get("arrow2").equals("TRIANGLE")) {
            String suprName;
            String subName;
            if (t.get("arrow1").equals("TRIANGLE")) {
                suprName = vBox.getFirst(d->d.get("id").equals(t.get("cid1"))).get("name");
                subName = vBox.getFirst(d->d.get("id").equals(t.get("cid2"))).get("name");
            } else {
                suprName = vBox.getFirst(d->d.get("id").equals(t.get("cid2"))).get("name");
                subName = vBox.getFirst(d->d.get("id").equals(t.get("cid1"))).get("name");
            }
            SubTableSchema sts = pddSchema.getSubTableSchema(suprName) != null ? pddSchema.getSubTableSchema(suprName) : new SubTableSchema(pddSchema.getTableSchema(suprName));
            sts.addSubTableSchemas(pddSchema.getTableSchema(subName));
            if (pddSchema.getSubTableSchema(suprName) == null) {
                    pddSchema.addSubTableSchema(sts);
            }
            
        } else {
            TableSchema tableSchema = new TableSchema(t.get("role1") + "_" + t.get("role2")).addColumn("id");
            
            if (!t.get("cid1").equals(t.get("cid2"))) {
                tableSchema.addColumn(vBox.getFirst(d->d.get("id").equals(t.get("cid1"))).get("name") + ":" + vBox.getFirst(d->d.get("id").equals(t.get("cid1"))).get("name"));
                tableSchema.addColumn(vBox.getFirst(d->d.get("id").equals(t.get("cid2"))).get("name") + ":" + vBox.getFirst(d->d.get("id").equals(t.get("cid2"))).get("name"));
            } else {
                tableSchema.addColumn(t.get("role1") + ":" + vBox.getFirst(d->d.get("id").equals(t.get("cid1"))).get("name"));
                tableSchema.addColumn(t.get("role2") + ":" + vBox.getFirst(d->d.get("id").equals(t.get("cid2"))).get("name"));
            }
            
            pddSchema.addTableSchema(tableSchema);
        }
    }
}
