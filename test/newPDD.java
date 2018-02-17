package Allegory;

///catdb
import PrologDB.DB;
import PrologDB.Table;
import PrologDB.TableSchema;
import PrologDB.Tuple;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class PDD {

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


public class Person extends common<Person> {
     Table table;

       protected Person() { }

       protected Person(String tableName, Tuple t) {
          TableSchema ts = t.getSchema();
          if (!ts.getName().equals(tableName)) 
            throw new RuntimeException("assigning non-"+tableName+" table to "+tableName);
          table = new Table(ts).add(t);
       }

       protected Person(String tableName, Table tab) {
          if (!tab.getSchema().getName().equals(tableName)) {
             throw new RuntimeException("assigning non-"+tableName+" table to "+tableName);
          }
          table = tab;
        }


     public Department worksin(){
         Table result1 = table.rightSemiJoin("pid",employs_worksin,"Person");
         Table result2 = result1.rightSemiJoin("Department",department,"did");
         return new Department(result2);
}

     public Person parentsOf(){
         Table result1 = table.rightSemiJoin("pid",childrenof_parentsof,"Person");
         Table result2 = result1.rightSemiJoin("Person",person,"pid");
         return new Person(result2);
}

     public Person childrenOf(){
         Table result1 = table.rightSemiJoin("pid",childrenof_parentsof,"Person");
         Table result2 = result1.rightSemiJoin("Person",person,"pid");
         return new Person(result2);
     }

}

public class Department extends common<Department> {
     Table table;

       protected Department() { }

       protected Department(String tableName, Tuple t) {
          TableSchema ts = t.getSchema();
          if (!ts.getName().equals(tableName)) 
            throw new RuntimeException("assigning non-"+tableName+" table to "+tableName);
          table = new Table(ts).add(t);
       }

       protected Department(String tableName, Table tab) {
          if (!tab.getSchema().getName().equals(tableName)) {
             throw new RuntimeException("assigning non-"+tableName+" table to "+tableName);
          }
          table = tab;
        }


     public Person employs(){
         Table result1 = table.rightSemiJoin("did",employs_worksin,"Person");
         Table result2 = result1.rightSemiJoin("Person",person,"pid");
         return new Person(result2);
     }

     public Division inDiv(){
         Table result1 = table.rightSemiJoin("did",hasdeps_indiv,"Person");
         return new Division(result2);
}

}

public class Division extends common<Division> {
     Table table;

       protected Division() { }

       protected Division(String tableName, Tuple t) {
          TableSchema ts = t.getSchema();
          if (!ts.getName().equals(tableName)) 
            throw new RuntimeException("assigning non-"+tableName+" table to "+tableName);
          table = new Table(ts).add(t);
       }

       protected Division(String tableName, Table tab) {
          if (!tab.getSchema().getName().equals(tableName)) {
             throw new RuntimeException("assigning non-"+tableName+" table to "+tableName);
          }
          table = tab;
        }


     public Department hasDeps(){
         Table result1 = table.rightSemiJoin("vid",hasdeps_indiv,"Person");
         return new Department(result2);
     }

}

}
