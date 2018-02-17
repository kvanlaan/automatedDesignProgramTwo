package Allegory;

///catdb
import PrologDB.DB;
import PrologDB.Table;
import PrologDB.TableSchema;
import PrologDB.Tuple;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class PDD {

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
