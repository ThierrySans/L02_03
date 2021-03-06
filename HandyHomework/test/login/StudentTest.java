package login;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class StudentTest {

	private StudentLogin stud;
	@Before
	public void setUp() throws Exception {
		stud = new StudentLogin(1, "bestStud", "userIsFalse");
	}

	@Test
	public void testIsProf() {
		assertEquals(false, stud.isProf());
	}

	@Test
	public void testGetUserName() {
		String user = stud.getUserName();
		assertTrue(user.equals("bestStud"));
	}


	@Test
	public void testGetId() {
		int id = stud.getId();
		assertEquals(1, id);
	}

}
