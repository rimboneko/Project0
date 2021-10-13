package com.revature;

import java.sql.SQLException;
import java.util.Scanner;

import com.project.dao.AccountDao;
import com.project.dao.AccountDaoDB;
import com.project.dao.UserDao;
import com.project.dao.UserDaoDB;
import com.project.exceptions.InvalidCredentialsException;
import com.project.exceptions.UserDoesNotExistException;
import com.project.exceptions.UsernameAlreadyExistsException;
import com.project.logging.Logging;
import com.project.models.Account;
import com.project.models.User;
import com.project.services.AccountService;
import com.project.services.UserService;

public class ProjectDriver {
	
	private static UserDao uDao = new UserDaoDB();
	private static AccountDao aDao = new AccountDaoDB();
	private static UserService uServ = new UserService(uDao);
	

	public static void main(String[] args) throws SQLException, UserDoesNotExistException, InvalidCredentialsException {
		
		Scanner in = new Scanner(System.in);
		
		boolean done = false;
		boolean logout = false;
		boolean donec = false;
		char choice;
		User loggedIn = null;
		
	    System.out.println("WELCOME TO MY BANK");
		
		while(!done) {
			logout = false;
			donec = false;
			System.out.println("For ADMIN press 1");
			System.out.println("For EMPLOYEE press 2");
			System.out.println("For CUSTOMER press 3");
			System.out.println("To EXIT press 4");
			choice = in.next().charAt(0);
		
			switch(choice) {
			
			default :
				System.out.println("Invalid Entry, Please try again");
				break;
			case '4':
				done = true;
				System.out.println("GOODBYE");
				break;
				
			case '1': 
				System.out.print("Please enter your username: ");
				String username = in.next();
				System.out.print("Please enter your password: ");
				String password = in.next();
				try {
				loggedIn = uServ.signIn(username, password, "admin");
				System.out.println("Welcome " + loggedIn.getUsername());
				
				while(!logout) {
					
					System.out.println("To Register new Employee press 1 ");
					System.out.println("To Approve an Account Application press 2");
					System.out.println("To Withdraw from an Account press 3");
					System.out.println("To Deposit to an Account press 4");
					System.out.println("To Transfert press 5");
					System.out.println("To Delete an Account press 6");
					System.out.println("To View all Accounts press 7");
					System.out.println("To Log out press 8");
					choice = in.next().charAt(0);
					switch(choice) {
					
					default :
						System.out.println("Invalid Entry, Please try again");
						break;
						
					case '8':
						logout = true;
						System.out.println("======================================");
						Logging.logger.info("User loged out");
						break;
					
					case '1':
						System.out.print("Please enter the first name: ");
						String first = in.next();
						System.out.print("Please enter the last name: ");
						String last = in.next();
						System.out.print("Please enter the username: ");
						username = in.next();
						System.out.print("Please enter the password: ");
						password = in.next();
						System.out.print("Please enter the email: ");
						String email = in.next();
						try {
						loggedIn = uServ.signUp(first, last, username, password, email, "employee");
						System.out.println("New Employee created");
						loggedIn = null;
						System.out.println("======================================");
						} catch(UsernameAlreadyExistsException e) {
							System.out.println("This username already exists");
							continue;
						}
						
						break;
						
						
					case '2':
						System.out.println("Please enter the username on the Application : ");
						username = in.next();
						aDao.createAccount(uDao.getUserByUsername(username));
						System.out.println("New account created");
						System.out.println("======================================");
						break;
						
					case '3':
						System.out.print("Please enter the username for the Account : ");
						username = in.next();
						System.out.println("Enter the amount to withdraw");
						Double amount = Double.parseDouble(in.next());
						if(amount>0.0 && amount<=aDao.getBalance(username)) {
						aDao.withdraw(amount, username);
						aDao.setTransaction("Withdraw: "+ amount, aDao.getAccountByusername(username).getAccount_id(), "Approved");
						System.out.println("======================================");
						} else if(amount > aDao.getBalance(username)) {
							aDao.setTransaction("Withdraw: "+ amount,  aDao.getAccountByusername(username).getAccount_id(), "Denied");
							System.out.println("Insufficient balance");
							System.out.println("======================================");
						} else {
							aDao.setTransaction("Withdraw: "+ amount,  aDao.getAccountByusername(username).getAccount_id(), "Denied");
							System.out.println("The amount must be at least greater than 0.0 ");
							System.out.println("======================================");
						}
						break;
						
					case '4':
						System.out.print("Please enter the username for the Account : ");
						username = in.next();
						System.out.println("Enter the amount to deposit");
						amount = Double.parseDouble(in.next());
						if(amount>0.0) {
						aDao.deposit(amount, username);
						aDao.setTransaction("Deposit: "+ amount, aDao.getAccountByusername(username).getAccount_id(), "Approved");
						System.out.println("======================================");
						} else {
							aDao.setTransaction("Deposit: "+ amount, aDao.getAccountByusername(username).getAccount_id(), "Denied");
							System.out.println("The amount must be at least greater than 0.0 ");
							System.out.println("======================================");
						}
						break;
						
					case '5':
						System.out.println("Enter the username of the sender: ");
						String susername = in.next();
						System.out.println("Enter the username of the receiver: ");
						String rusername = in.next();
						System.out.println("Enter the amount to transfert: ");
						amount = Double.parseDouble(in.next());
						if(amount>0.0 && amount<=aDao.getBalance(susername)) {
				        aDao.transfert(amount, susername, rusername);
				        aDao.setTransaction("Transfert: "+ amount, aDao.getAccountByusername(susername).getAccount_id(), "Approved");
				        System.out.println("======================================");
						} else if(amount > aDao.getBalance(susername)) {
							aDao.setTransaction("Transfert: "+ amount, aDao.getAccountByusername(susername).getAccount_id(), "Denied");
							System.out.println("Insufficient balance");
							System.out.println("======================================");
						} else {
							aDao.setTransaction("Transfert: "+ amount, aDao.getAccountByusername(susername).getAccount_id(), "Denied");
							System.out.println("The amount must be at least greater than 0.0 ");
							System.out.println("======================================");
						}
						break;
					
					case '6':
						System.out.println("Enter the Account_ID: ");
						int acc_id = Integer.parseInt(in.next());
						aDao.cancelAccount(acc_id);
						System.out.println("======================================");
						break;
						
					case '7':
						System.out.println(aDao.getAllAccount());
						System.out.println("======================================");
						break;
					}
				}
				} catch(UserDoesNotExistException | InvalidCredentialsException | SQLException e) {
					System.out.println("Username or password incorrect");
					System.out.println("======================================");
					Logging.logger.info("Log in failed");
					continue;
				}
				break;
								
			case '2':
				System.out.print("Please enter your username: ");
				username = in.next();
				System.out.print("Please enter your password: ");
				password = in.next();
				try {
				loggedIn = uServ.signIn(username, password, "employee");
				System.out.println("Welcome " + loggedIn.getUsername());
				
				while(!logout) {
					
				System.out.println("For User Information press 1");
				System.out.println("For Account Information press 2");
				System.out.println("To Approve an Account Application press 3");
				System.out.println("To Log out press 4");
				choice = in.next().charAt(0);
						
				switch(choice) {
				 
				default :
					System.out.println("Invalid Entry, Please try again");
					break;
					
				case '4':
					logout = true;
					System.out.println("======================================");
					Logging.logger.info("User loged out");
					break;
				
				case '1':
					System.out.println("Please enter the User username : ");
					username = in.next();
					System.out.println(uDao.getUserByUsername(username));
					System.out.println("======================================");
					break;
					
				case '2':
					System.out.println("Please enter the User username : ");
					username = in.next();
					System.out.println(aDao.getAccountByusername(username));
					System.out.println("======================================");
					break;
					
				case '3':
					System.out.println("Please enter the username on the Application : ");
					username = in.next();
					aDao.createAccount(uDao.getUserByUsername(username));
					System.out.println("New account created");
					System.out.println("======================================");
					break;
					
					}
				}
				} catch(InvalidCredentialsException | SQLException e) {
					System.out.println("Username or password incorrect");
					System.out.println("======================================");
					Logging.logger.info("log in failed");
					continue;
				}
				break;
				
			case '3':
			
			while(!donec) {
				logout = false;
				System.out.println("To LOGIN press 1");
				System.out.println("To SIGNUP press 2");
				System.out.println("To EXIT press 3");
				choice = in.next().charAt(0);
				switch(choice) {
				
				default: 
					System.out.println("Invalid Entry, Please try again");
					break;
					
				case '3':
					donec = true;
					System.out.println("======================================");
					break;
				
				case '1':
					System.out.print("Please enter your username: ");
					username = in.next();
					System.out.print("Please enter your password: ");
					password = in.next();
					try {
						loggedIn = uServ.signIn(username, password, "customer");
						System.out.println("Welcome " + loggedIn.getUsername());
						double amount = 0;
						Account acc = aDao.getAccountByusername(loggedIn.getUsername());
						while(!logout) {
						if(acc.getUsername() != null) {	
						System.out.println("To WITHDRAW press 1");
						System.out.println("To DEPOSIT press 2");
						System.out.println("To TRANSFERT press 3");
						System.out.println("To Log out press 4");
						choice = in.next().charAt(0);
						
						switch(choice) {
						
						default: 
							System.out.println("Invalid Entry, Please try again");
							System.out.println("======================================");
							break;
							
						case '4':
							logout = true;
							//done = true;
							System.out.println("======================================");
							Logging.logger.info("User loged out");
							break;
						
						case '1':
							System.out.println("Enter the amount to withdraw");
							amount = Double.parseDouble(in.next());
							if(amount > 0.0 && amount <= aDao.getBalance(loggedIn.getUsername())) {
							aDao.withdraw(amount, loggedIn.getUsername());
							aDao.setTransaction("Withdraw: "+ amount, acc.getAccount_id(), "Approved");
							System.out.println("Your new balance is : " + aDao.getBalance(loggedIn.getUsername()));
							System.out.println("======================================");
							} else if(amount > aDao.getBalance(loggedIn.getUsername())) {
								aDao.setTransaction("Withdraw: "+ amount, acc.getAccount_id(), "Denied");
								System.out.println("Insufficient balance");
								System.out.println("======================================");
							} else {
								aDao.setTransaction("Withdraw: "+ amount, acc.getAccount_id(), "Denied");
								System.out.println("The amount must be at least greater than 0.0 ");
								System.out.println("======================================");
							}
							
							break;
							
						case '2':
							System.out.println("Enter the amount to deposit");
							amount = Double.parseDouble(in.next());
							if(amount>0.0) {
							aDao.deposit(amount, loggedIn.getUsername());
							aDao.setTransaction("Deposit: "+amount, acc.getAccount_id(), "Approved");
							System.out.println("Your new balance is : " + aDao.getBalance(loggedIn.getUsername()));
							System.out.println("======================================");
							} else {
								aDao.setTransaction("Deposit: "+ amount, acc.getAccount_id(), "Denied");
								System.out.println("The amount must be at least greater than 0.0 ");
								System.out.println("======================================");
							}
							break;
							
						case '3':
							System.out.println("Enter the username of the recipient: ");
							username = in.next();
							System.out.println("Enter the amount to transfert: ");
							amount = Double.parseDouble(in.next());
							if(amount > 0.0 && amount <= aDao.getBalance(loggedIn.getUsername())) {
					        aDao.transfert(amount, loggedIn.getUsername(), username);
					        aDao.setTransaction("Transfert :"+amount, acc.getAccount_id(), "Approved");
							System.out.println("Your new balance is : " + aDao.getBalance(loggedIn.getUsername()));
							System.out.println("======================================");
							} else if(amount > aDao.getBalance(loggedIn.getUsername())) {
								aDao.setTransaction("Transfert: "+ amount, acc.getAccount_id(), "Denied");
								System.out.println("Insufficient balance");
								System.out.println("======================================");
							} else {
								aDao.setTransaction("Transfert: "+ amount, acc.getAccount_id(), "Denied");
								System.out.println("The amount must be at least greater than 0.0 ");
								System.out.println("======================================");
							}
							break;
						}
						
						} else {
							System.out.println("Your application still pending");
							System.out.println("======================================");
							Logging.logger.info("Application pending");
							break;
						}
					  }
						
					}catch(InvalidCredentialsException | SQLException e) {
						System.out.println("Username or password incorrect");
						Logging.logger.info("Log in failed");
						continue;
						//done = true;
					}
			
				break;
				
				case '2':
					System.out.print("Please enter your first name: ");
					String first = in.next();
					System.out.print("Please enter your last name: ");
					String last = in.next();
					System.out.print("Please enter your username: ");
					username = in.next();
					System.out.print("Please enter your password: ");
					password = in.next();
					System.out.print("Please enter your email: ");
					String email = in.next();
					try {
						loggedIn = uServ.signUp(first, last, username, password,email, "customer");
						System.out.println("You may now login with the username: " + loggedIn.getUsername());
						break;
						
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
	

}
