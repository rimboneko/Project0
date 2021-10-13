package com.project.usertest;

import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project.dao.UserDao;
import com.project.exceptions.InvalidCredentialsException;
import com.project.exceptions.UserDoesNotExistException;
import com.project.models.User;
import com.project.services.UserService;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {

		@InjectMocks
		public UserService uServ;
		
		
		@Mock
		public UserDao uDao;
		
		@Before
		public void initMocks() {
			MockitoAnnotations.initMocks(this);
		}
		
		@Test
		public void testValidLogin() throws UserDoesNotExistException, InvalidCredentialsException {
			User u1 = new User("testf", "testl", "testf@mail.com", "testu", "testp", "testt");
			
			when(uDao.getUserByUsername(anyString())).thenReturn(u1);
			
			User loggedIn = uServ.signIn("testu", "testp", "testt");
			
			assertEquals(u1.getUsername(), loggedIn.getUsername());
		}
		
		@Test(expected = UserDoesNotExistException.class)
		public void testInvalidUsername() throws UserDoesNotExistException, InvalidCredentialsException {
			User not = new User("test", "user", "test@mail.com", "testuser", "testpass", "testtype");
			
			when(uDao.getUserByUsername(anyString())).thenReturn(not);
			
			User loggedIn = uServ.signIn("test", "testpass", "testtype");
		}
		
		@Test(expected = InvalidCredentialsException.class)
		public void testInvalidPassword() throws UserDoesNotExistException, InvalidCredentialsException {
			User not = new User("test", "user", "test@mail.com", "testuser", "wrongpass", "testtype");
			
			when(uDao.getUserByUsername(anyString())).thenReturn(not);
			
			uServ.signIn("testuser", "testpass", "testtype");
		}

}
