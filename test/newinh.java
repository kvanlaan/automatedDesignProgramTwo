package inh;

///catdb
import PrologDB.DB;
import PrologDB.Table;
import PrologDB.TableSchema;
import PrologDB.Tuple;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class inh {

	DB db;
	public Table a;
	public Table b;
	public Table d;
	public Table c;
	public Table e;
	public Table x;
	public Table y;

	public inh(String fileName) {
		db = DB.readDataBase(fileName);
		a = db.getTableEH("A");
		b = db.getTableEH("B");
		d = db.getTableEH("D");
		c = db.getTableEH("C");
		e = db.getTableEH("E");
		x = db.getTableEH("X");
		y = db.getTableEH("Y");
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


public class A extends common<A> {
     protected A New(Table t) { return new A(t); }

      public A() { table = a; }

      public A(Table t) { super("A",t); }

      public A(Tuple t) {  super("A",t); }

      protected A(String n, Table t) { super(n,t); }

      protected A(String n, Tuple t) {  super(n,t); }

     public B (){
         Table result1 = table.rightSemiJoin("id",_,"Person");
         Table result2 = result1.rightSemiJoin("B",b,"b");
         return new B(result2);
     }

     public D (){
         Table result1 = table.rightSemiJoin("id",_,"Person");
         Table result2 = result1.rightSemiJoin("D",d,"d");
         return new D(result2);
     }

     public X (){
         Table result1 = table.rightSemiJoin("id",x,"Person");
         return new X(result1);
     }

}

public class B extends common<B> {
     protected B New(Table t) { return new B(t); }

      public B() { table = b; }

      public B(Table t) { super("B",t); }

      public B(Tuple t) {  super("B",t); }

      protected B(String n, Table t) { super(n,t); }

      protected B(String n, Tuple t) {  super(n,t); }

     public A (){
         Table result1 = table.rightSemiJoin("b",_,"Person");
         Table result2 = result1.rightSemiJoin("A",a,"id");
         return new A(result2);
     }

     public C (){
         Table result1 = table.rightSemiJoin("b",_,"Person");
         Table result2 = result1.rightSemiJoin("C",c,"c");
         return new C(result2);
     }

}

public class D extends common<D> {
     protected D New(Table t) { return new D(t); }

      public D() { table = d; }

      public D(Table t) { super("D",t); }

      public D(Tuple t) {  super("D",t); }

      protected D(String n, Table t) { super(n,t); }

      protected D(String n, Tuple t) {  super(n,t); }

     public A (){
         Table result1 = table.rightSemiJoin("d",_,"Person");
         Table result2 = result1.rightSemiJoin("A",a,"id");
         return new A(result2);
     }

     public E (){
         Table result1 = table.rightSemiJoin("d",_,"Person");
         Table result2 = result1.rightSemiJoin("E",e,"e");
         return new E(result2);
     }

     public Y (){
         Table result1 = table.rightSemiJoin("d",y,"Person");
         return new Y(result1);
     }

}

public class C extends common<C> {
     protected C New(Table t) { return new C(t); }

      public C() { table = c; }

      public C(Table t) { super("C",t); }

      public C(Tuple t) {  super("C",t); }

      protected C(String n, Table t) { super(n,t); }

      protected C(String n, Tuple t) {  super(n,t); }

     public B (){
         Table result1 = table.rightSemiJoin("c",_,"Person");
         Table result2 = result1.rightSemiJoin("B",b,"b");
         return new B(result2);
     }

}

public class E extends common<E> {
     protected E New(Table t) { return new E(t); }

      public E() { table = e; }

      public E(Table t) { super("E",t); }

      public E(Tuple t) {  super("E",t); }

      protected E(String n, Table t) { super(n,t); }

      protected E(String n, Tuple t) {  super(n,t); }

     public D (){
         Table result1 = table.rightSemiJoin("e",_,"Person");
         Table result2 = result1.rightSemiJoin("D",d,"d");
         return new D(result2);
     }

}

public class X extends common<X> {
     protected X New(Table t) { return new X(t); }

      public X() { table = x; }

      public X(Table t) { super("X",t); }

      public X(Tuple t) {  super("X",t); }

      protected X(String n, Table t) { super(n,t); }

      protected X(String n, Tuple t) {  super(n,t); }

     public A (){
         Table result1 = table.rightSemiJoin("g",a,"Person");
         return new A(result1);
     }

}

public class Y extends common<Y> {
     protected Y New(Table t) { return new Y(t); }

      public Y() { table = y; }

      public Y(Table t) { super("Y",t); }

      public Y(Tuple t) {  super("Y",t); }

      protected Y(String n, Table t) { super(n,t); }

      protected Y(String n, Tuple t) {  super(n,t); }

     public D (){
         Table result1 = table.rightSemiJoin("h",d,"Person");
         return new D(result1);
     }

}

}
