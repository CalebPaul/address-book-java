import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class EntryTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/address_book_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteContactsQuery = "DELETE FROM contacts *;";
      String deleteEntriesQuery = "DELETE FROM entries *;";
      con.createQuery(deleteContactsQuery).executeUpdate();
      con.createQuery(deleteEntriesQuery).executeUpdate();
    }
  }

  @Test
  public void entry_instantiatesCorrectly_true() {
    Entry testEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    assertTrue(testEntry instanceof Entry);
  }

  @Test
  public void entry_instantiatesWithHomeAddress_String() {
    Entry testEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    assertEquals("123 Sesame St", testEntry.getHomeAddress());
  }

  @Test
  public void entry_instantiatesWithWorkAddress_String() {
    Entry testEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    assertEquals("90210 Beverly Hills", testEntry.getWorkAddress());
  }

  @Test
  public void entry_instantiatesWithPhone_String() {
    Entry testEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    assertEquals("503-555-5555", testEntry.getPhone());
  }

  @Test
  public void entry_instantiatesWithEmail_String() {
    Entry testEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    assertEquals("email@address.org", testEntry.getEmail());
  }

  @Test
  public void all_returnsAllInstancesOfEntry_true() {
    Entry firstEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    firstEntry.save();
    Entry secondEntry = new Entry("12 Sesame St", "9021 Beverly Hills", "503-555-55565", "email1@address.org", 2);
    firstEntry.save();
    assertTrue(Entry.all().get(0).equals(firstEntry));
    assertTrue(Entry.all().get(1).equals(secondEntry));
  }

  @Test
  public void getId_entriesInstantiateWithAnId() {
    Entry testEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    testEntry.save();
    assertTrue(testEntry.getId() > 0);
  }

  @Test
  public void find_returnsEntryWithSameId_secondEntry() {
    Entry firstEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    firstEntry.save();
    Entry secondEntry = new Entry("12 Sesame St", "9021 Beverly Hills", "503-555-55565", "email1@address.org", 2);
    firstEntry.save();
    assertEquals(Entry.find(secondEntry.getId()), secondEntry);
  }

  @Test
  public void equals_returnsTrueIfHomeAddressesAreTheSame() {
    Entry firstEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    Entry secondEntry = new Entry("12 Sesame St", "9021 Beverly Hills", "503-555-55565", "email1@address.org", 2);
    assertTrue(firstEntry.equals(secondEntry));
  }

  @Test
  public void save_returnsTrueIfHomeAddressesAreTheSame() {
    Entry testEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    testEntry.save();
    assertTrue(Entry.all().get(0).equals(testEntry));
  }

  @Test
  public void equals_returnsTrueIfWorkAddressesAreTheSame() {
    Entry firstEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    Entry secondEntry = new Entry("12 Sesame St", "9021 Beverly Hills", "503-555-55565", "email1@address.org", 2);
    assertTrue(firstEntry.equals(secondEntry));
  }

  @Test
  public void save_returnsTrueIfWorkAddressesAreTheSame() {
    Entry testEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    testEntry.save();
    assertTrue(Entry.all().get(0).equals(testEntry));
  }

  @Test
  public void equals_returnsTrueIfPhonesAreTheSame() {
    Entry firstEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    Entry secondEntry = new Entry("12 Sesame St", "9021 Beverly Hills", "503-555-55565", "email1@address.org", 2);
    assertTrue(firstEntry.equals(secondEntry));
  }

  @Test
  public void save_returnsTrueIfPhonesAreTheSame() {
    Entry testEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    testEntry.save();
    assertTrue(Entry.all().get(0).equals(testEntry));
  }

  @Test
  public void equals_returnsTrueIfEmailsAreTheSame() {
    Entry firstEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    Entry secondEntry = new Entry("12 Sesame St", "9021 Beverly Hills", "503-555-55565", "email1@address.org", 2);
    assertTrue(firstEntry.equals(secondEntry));
  }

  @Test
  public void save_returnsTrueIfEmailsAreTheSame() {
    Entry testEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    testEntry.save();
    assertTrue(Entry.all().get(0).equals(testEntry));
  }

  @Test
  public void save_assignsIdToObject() {
    Entry testEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", 1);
    testEntry.save();
    Entry savedEntry = Entry.all().get(0);
    assertEquals(testEntry.getId(), savedEntry.getId());
  }

  @Test
  public void save_savesContactIdIntoDB_true() {
    Contact myContact = new Contact("Caleb");
    myContact.save();
    Entry testEntry = new Entry("123 Sesame St", "90210 Beverly Hills", "503-555-5555", "email@address.org", myContact.getId());
    testEntry.save();
    Entry savedEntry = Entry.find(testEntry.getId());
    assertEquals(savedEntry.getContactId(), myContact.getId());
  }


}
