package LectureExamples.allegory;

///catdb
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
        db = DB.readDataBase(fileName);
        person = db.getTableEH("Person");
        department = db.getTableEH("Department");
        division = db.getTableEH("Division");
        employs_worksin = db.getTableEH("employs_worksin");
        childrenof_parentsof = db.getTableEH("childrenOf_parentsOf");
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

   public class Person extends common<Person> {
///Person
       protected Person New(Table t) { return new Person(t); }

       public Person() { table = person; }

       public Person(Table t) { super("Person",t); }

       public Person(Tuple t) {  super("Person",t); }

       protected Person(String n, Table t) { super(n,t); }

       protected Person(String n, Tuple t) {  super(n,t); }

       public Department worksin() {
          Table result1 = table.rightSemiJoin("pid",employs_worksin,"Person");
          Table result2 = result1.rightSemiJoin("Department",department,"did");
          return new Department(result2);
       }

       public Person childrenOf() {
          Table result1 = table.rightSemiJoin("pid",childrenof_parentsof,"parentsOf");
          Table result2 = result1.rightSemiJoin("childrenOf",person,"pid");
          return new Person(result2);
       }

       public Person parentsOf() {
          Table result1 = table.rightSemiJoin("pid",childrenof_parentsof,"childrenOf");
          Table result2 = result1.rightSemiJoin("parentsOf",person,"pid");
          return new Person(result2);
       }
   }
///Person

   public class Department extends common<Department> {
///Department
       protected Department New(Table t) { return new Department(t); }

       public Department() { table = department; }

       public Department(Table t) { super("Department",t); }

       public Department(Tuple t) {  super("Department",t); }

       protected Department(String n, Table t) { super(n,t); }

       protected Department(String n, Tuple t) {  super(n,t); }

       public Person employs() {
          Table result1 = table.rightSemiJoin("did",employs_worksin,"Department");
          Table result2 = result1.rightSemiJoin("Person",person,"pid");
          return new Person(result2);
       }

       public Division inDiv() {
          Table result = table.rightSemiJoin("inDiv",division,"vid");
          return new Division(result);
       }
   }
///Department


   public class Division extends common<Division> {
///Division
       protected Division New(Table t) { return new Division(t); }

       public Division() { table = division; }

       public Division(Table t) { super("Division",t); }

       public Division(Tuple t) {  super("Division",t); }

       protected Division(String n, Table t) { super(n,t); }

       protected Division(String n, Tuple t) {  super(n,t); }

       public Department hasDeps() {
          Table result = table.rightSemiJoin("vid",department,"inDiv");
          return new Department(result);
       }
   }
///Division

   public class employs_worksin extends common<employs_worksin> {
///EW

       protected employs_worksin New(Table t) { return new employs_worksin(t); }

       public employs_worksin() { table = employs_worksin; }

       public employs_worksin(Table t) { super("employs_worksin",t); }

       public employs_worksin(Tuple t) {  super("employs_worksin",t); }

       protected employs_worksin(String n, Table t) { super(n,t); }

       protected employs_worksin(String n, Tuple t) {  super(n,t); }
   }
///EW

   public class childrenOf_parentsOf extends common<childrenOf_parentsOf> {
///CP
       protected childrenOf_parentsOf New(Table t) { return new childrenOf_parentsOf(t); }

       public childrenOf_parentsOf() { table = childrenof_parentsof; }

       public childrenOf_parentsOf(Table t) { super("childrenOf_parentsOf",t); }

       public childrenOf_parentsOf(Tuple t) {  super("childrenOf_parentsOf",t); }

       protected childrenOf_parentsOf(String n, Table t) { super(n,t); }

       protected childrenOf_parentsOf(String n, Tuple t) {  super(n,t); }
   }
///CP

}

