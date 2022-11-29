package com.capitalone.dashboard.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.capitalone.dashboard.model.Authentication;

@ExtendWith(MockitoExtension.class)
public class AuthenticationRepositoryTest  {

	private static int testNumber = 0;

	private static String username;

	@BeforeEach
	public void updateUsername(){
		username = "usernameTest" + testNumber;
		testNumber++;
	}

	private AuthenticationRepository authenticationRepository = Mockito.mock(AuthenticationRepository.class);


    /*
     * This test checks that adding a duplicate username will create an exception
     */
    @Test
    public void createDuplicateUserTest() {

    	String username = "username";

    	Authentication user1 = new Authentication(username, "pass1");

    	authenticationRepository.save(user1);

    	Authentication user2 = new Authentication(username, "pass2");

    	// This line should throw a DuplicateKeyException
    	authenticationRepository.save(user2);
    }

	@Test
	public void verifyExistingPasswords() throws Exception {
		String username = "username1";

		Authentication user1 = new Authentication(username, "pass1");
		// bypass hasher..
		Field pwField = user1.getClass().getDeclaredField("password");
		pwField.setAccessible(true);
		pwField.set(user1, "pass1");


		authenticationRepository.save(user1);

		when(authenticationRepository.findByUsername(username)).thenReturn(user1);

		Authentication u = authenticationRepository.findByUsername(username);
		assertTrue(u.checkPassword("pass1"));
		// try against a new object
		Authentication hashedUser1 = new Authentication(username, "pass1");

		when(authenticationRepository.findByUsername(username)).thenReturn(hashedUser1);
		Field pwFieldHashed = hashedUser1.getClass().getDeclaredField("password");
		pwFieldHashed.setAccessible(true);
		pwFieldHashed.set(hashedUser1, "pass1");

		assertEquals(u.getPassword(), hashedUser1.getPassword());
	}

	@Test
	public void verifyExistingWithNewPasswords() throws Exception {
		String username = "username" + System.currentTimeMillis();

		Authentication user1 = new Authentication(username, "pass1");

		authenticationRepository.save(user1);

		when(authenticationRepository.findByUsername(username)).thenReturn(user1);

		Authentication u = authenticationRepository.findByUsername(username);
		assertTrue(u.checkPassword("pass1"));
	}

	@Test
	public void verifyWithBadPasswords() throws Exception {
		String username = "username" + System.currentTimeMillis();

		Authentication user1 = new Authentication(username, "pass2");

		authenticationRepository.save(user1);

		when(authenticationRepository.findByUsername(username)).thenReturn(user1);

		Authentication u = authenticationRepository.findByUsername(username);
		assertFalse(u.checkPassword("pass1"));
	}

    /*
     * This test checks that we ge a null when getting a user which does not exist
     */
    @Test
    public void testGetUserDoesNotExist() {
    	assertNull(authenticationRepository.findByUsername(username));
    }

}