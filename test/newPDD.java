package Allegory;

///catdb
import PrologDB.DB;
import PrologDB.Table;
import PrologDB.TableSchema;
import PrologDB.Tuple;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class PDD {

	DB db;
	public Table Person;
	public Table Department;
	public Table Division;
	public Table employs_worksin;
	public Table childrenOf_parentsOf;

	public PDD(String fileName) {
		db = DB.readDataBase(fileName);
		person = db.getTableEH("Person");
		department = db.getTableEH("Department");
		division = db.getTableEH("Division");
		employs_worksin = db.getTableEH("employs_worksin");
		childrenof_parentsof = db.getTableEH("childrenOf_parentsOf");
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


	public class employs_worksin extends common <employs_worksin> {

		protected employs_worksin New(Table t) { return new employs_worksin(t);}

		public employs_worksin() {table = employs_worksin;}

		public employs_worksin(Table t) { super("employs_worksin", t); }

		public employs_worksin(Tuple t) { super("employs_worksin", t); }

		protected employs_worksin(String n, Table t) { super(n,t);}

		protected employs_worksin(String n, Tuple t) { super(n,t);}

	}

	public class childrenOf_parentsOf extends common <childrenOf_parentsOf> {

		protected childrenOf_parentsOf New(Table t) { return new childrenOf_parentsOf(t);}

		public childrenOf_parentsOf() {table = childrenOf_parentsOf;}

		public childrenOf_parentsOf(Table t) { super("childrenOf_parentsOf", t); }

		public childrenOf_parentsOf(Tuple t) { super("childrenOf_parentsOf", t); }

		protected childrenOf_parentsOf(String n, Table t) { super(n,t);}

		protected childrenOf_parentsOf(String n, Tuple t) { super(n,t);}

	}

}
