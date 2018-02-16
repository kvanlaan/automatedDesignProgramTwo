package Allegory;

///catdb
import static Allegory.Gen.fld;
import static MDELite.RunningBear.l;
import static MDELite.RunningBear.openOut;
import static MDELite.RunningBear.closeOut;
import PrologDB.DB;
import PrologDB.Table;
import PrologDB.TableSchema;
import PrologDB.Tuple;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class PDD {
    DB db;
    public Table person;
    public Table department;
    public Table division;
    public Table employs_worksin;
    public Table childrenof_parentsof;
    

    public PDD(String fileName) {
        // I'm not sure if we still need this part
        db = DB.readDataBase(fileName);
       
    }
///catdb

    abstract class common <T extends common> {
///commonT
       Table table;

       protected common() { }

       protected common(String tableName, Tuple t) {
          TableSchema ts = t.getSchema();
          if (!ts.getName().equals(tableName)) 
            throw new RuntimeException("assigning non-"+tableName+" table to "+tableName);
          table = new Table(ts).add(t);
       }

       protected common(String tableName, Table tab) {
          if (!tab.getSchema().getName().equals(tableName)) {
             throw new RuntimeException("assigning non-"+tableName+" table to "+tableName);
          }
          table = tab;
        }

       public T select(Predicate<Tuple> p) {
          Table result = table.filter(p);
          return New(result);
        }

       protected abstract T New(Table t);

       public T id() { return New(table); }

       public void print() { table.print(System.out); }
       public void forEach(Consumer<Tuple> action) { table.stream().forEach(t -> action.accept(t)); }

       public T intersect(T tab) { return New(this.table.intersect(tab.table)); }

       public int size() { return this.table.count(); }

       public boolean equals(T tab) { return this.table.equals(tab.table); }
   }
///commonT

}

