import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Entry {
  private String home_address;
  private String work_address;
  private String phone;
  private String email;
  private int id;
  private int contactId;

  public Entry(String home_address, String work_address, String phone, String email, int contactId) {
    this.home_address = home_address;
    this.work_address = work_address;
    this.phone = phone;
    this.email = email;
    this.contactId = contactId;
  }

  @Override
  public boolean equals(Object otherEntry) {
    if (!(otherEntry instanceof Entry)) {
      return false;
    } else {
      Entry newEntry = (Entry) otherEntry;
      return this.getHomeAddress().equals(newEntry.getHomeAddress()) &&
             this.getWorkAddress().equals(newEntry.getWorkAddress()) &&
             this.getPhone().equals(newEntry.getPhone()) &&
             this.getEmail().equals(newEntry.getEmail()) &&
             this.getContactId() == newEntry.getContactId();
    }
  }

  public static List<Entry> all() {
    String sql = "SELECT id, home_address , work_address, phone, email, contactId FROM entries";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Entry.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO entries (home_address, work_address, phone, email, contactId) VALUES (:home_address, :work_address, :phone, :email, :contactId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("home_address", this.home_address)
        .addParameter("work_address", this.work_address)
        .addParameter("phone", this.phone)
        .addParameter("email", this.email)
        .addParameter("contactId", this.contactId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Entry find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where id=:id";
      Entry entry = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Entry.class);
    return entry;
    }
  }

  public String getHomeAddress() {
    return home_address;
  }

  public String getWorkAddress() {
    return work_address;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public int getId() {
  return id;
  }

  public int getContactId() {
    return contactId;
  }

}
