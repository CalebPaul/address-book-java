import java.util.Arrays;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ContactTest {

  @Before
  public void setUp() {
  DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/address_book_test", null, null);
}

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteEntriesQuery = "DELETE FROM entries *;";
      String deleteContactsQuery = "DELETE FROM contacts *;";
      con.createQuery(deleteEntriesQuery).executeUpdate();
      con.createQuery(deleteContactsQuery).executeUpdate();
    }
  }

  @Test
  public void contact_instantiatesCorrectly_true() {
    Contact testContact = new Contact("Caleb");
    assertTrue(testContact instanceof Contact);
  }

  @Test
  public void getName_contactInstantiatesWithName_Home() {
    Contact testContact = new Contact("Caleb");
    assertEquals("Caleb", testContact.getName());
  }
}
