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
      protected Person New(Table t) { return new Person(t); }

       public Person() { table = person; }

       public Person(Table t) { super("Person",t); }

       public Person(Tuple t) {  super("Person",t); }

       protected Person(String n, Table t) { super(n,t); }

       protected Person(String n, Tuple t) {  super(n,t); }


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
      protected Department New(Table t) { return new Department(t); }

       public Department() { table = department; }

       public Department(Table t) { super("Department",t); }

       public Department(Tuple t) {  super("Department",t); }

       protected Department(String n, Table t) { super(n,t); }

       protected Department(String n, Tuple t) {  super(n,t); }


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
      protected Division New(Table t) { return new Division(t); }

       public Division() { table = division; }

       public Division(Table t) { super("Division",t); }

       public Division(Tuple t) {  super("Division",t); }

       protected Division(String n, Table t) { super(n,t); }

       protected Division(String n, Tuple t) {  super(n,t); }


     public Department hasDeps(){
         Table result1 = table.rightSemiJoin("vid",hasdeps_indiv,"Person");
         return new Department(result2);
     }

}

}
