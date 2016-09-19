import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Contact {
  private String name;
  private int id;

  public Contact(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object otherContact) {
    if (!(otherContact instanceof Contact)) {
      return false;
    } else {
      Contact newContact = (Contact) otherContact;
      return this.getName().equals(newContact.getName()) &&
             this.getId() == newContact.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO contacts (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Contact> all() {
    String sql = "SELECT id, name FROM contacts";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Contact.class);
    }
  }

  public List<Entry> getEntries() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where contactID=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Entry.class);
    }
  }

  public static Contact find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM contacts where id=:id";
      Contact contact = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Contact.class);
      return contact;
    }
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }
}
