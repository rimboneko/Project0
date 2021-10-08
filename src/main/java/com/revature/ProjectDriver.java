package com.revature;

import java.sql.SQLException;
import java.util.Scanner;

import com.project.dao.AccountDao;
import com.project.dao.AccountDaoDB;
import com.project.dao.UserDao;
import com.project.dao.UserDaoDB;
import com.project.exceptions.InvalidCredentialsException;
import com.project.models.Account;
import com.project.models.User;
import com.project.services.AccountService;
import com.project.services.UserService;

public class ProjectDriver {
	
	private static UserDao uDao = new UserDaoDB();
	private static AccountDao aDao = new AccountDaoDB();
	private static UserService uServ = new UserService(uDao);
	

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		boolean done = false;
		
		User loggedIn = null;
		
	    System.out.println("WELCOME TO MY BANK");
		System.out.println("For ADMIN press A---For EMPLOYEE press E---For CUSTOMER press C");
		char choice1 =  in.next().charAt(0);
		
		
		
		while(!done) {
			
			if(loggedIn == null) {
				System.out.println("Login or Signup? Press 1 to Login, Press 2 to SignUp");
				int choice = Integer.parseInt(in.nextLine());
				if(choice == 1) {
					System.out.print("Please enter your username: ");
					String username = in.nextLine();
					System.out.print("Please enter your password: ");
					String password = in.nextLine();
					try {
						loggedIn = uServ.signIn(username, password);
						System.out.println("Welcome " + loggedIn.getUsername());
						
	                   
						System.out.println("For WITHDRAWING press W---For DEPOSIT press D---For TRANSFERT press T");
						char choice2 = in.next().charAt(0);
						double amount = 0;
						switch(choice2) {
						case 'W':
							System.out.println("Enter the amount to withdraw");
							amount = Double.parseDouble(in.next());
							aDao.withdraw(amount, loggedIn.getUsername());
							System.out.println("Your new balance is : " + aDao.getBalance(loggedIn.getUsername()));
							break;
						case 'D':
							System.out.println("Enter the amount to deposit");
							amount = Double.parseDouble(in.next());
							aDao.deposit(amount, loggedIn.getUsername());
							System.out.println("Your new balance is : " + aDao.getBalance(loggedIn.getUsername()));
							break;
						case 'T':
							System.out.println("Enter the username of the recipient: ");
							username = in.next();
							System.out.println("Enter the amount to transfert: ");
							amount = Double.parseDouble(in.next());
					        aDao.transfert(amount, loggedIn.getUsername(), username);
							System.out.println("Your new balance is : " + aDao.getBalance(loggedIn.getUsername()));
							break;
						}
					}catch(InvalidCredentialsException | SQLException e) {
						System.out.println("Username or password incorrect. Goodbye");
						done = true;
					}
					continue;
				}
				else {
					System.out.print("Please enter your first name: ");
					String first = in.nextLine();
					System.out.print("Please enter your last name: ");
					String last = in.nextLine();
					System.out.print("Please enter your username: ");
					String username = in.nextLine();
					System.out.print("Please enter your password: ");
					String password = in.nextLine();
					System.out.print("Please enter your email: ");
					String email = in.nextLine();
					try {
						loggedIn = uServ.signUp(first, last, username, password,email);
						aDao.createAccount(loggedIn);
						System.out.println("You may now login with the username: " + loggedIn.getUsername());
						loggedIn = null;
						continue;
					} catch (Exception e) {
						System.out.println("Sorry the username is already taken");
						System.out.println("Please try signing up with a different one");
						continue;
					}
				}
			}
			
		}

	}
	

}
