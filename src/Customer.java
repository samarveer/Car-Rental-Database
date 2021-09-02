
/* This class has all the methods for the customer use of the program
 * 
 */
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer {

	// Initializing instance variables
	String file;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private static ArrayList<String> usernames = new ArrayList<String>();
	private static ArrayList<String> passwords = new ArrayList<String>();
	private static ArrayList<Customer> customers = new ArrayList<Customer>();
	private ArrayList<Rental> reservations;

	// Constructor for variables in class
	public Customer() {
		file = "LogFile.txt";
		firstName = null;
		lastName = null;
		username = null;
		password = null;
		reservations = new ArrayList<Rental>();
	}

	// Constructor for a customer
	public Customer(String firstName, String lastName, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		reservations = new ArrayList<Rental>();
	}

	/*
	 * Setting a new customer
	 * 
	 * @param aCustomer
	 */
	public static void setCustomers(Customer aCustomer) {
		customers.add(aCustomer);
	}

	/*
	 * Setting customer first name
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/*
	 * Getting the first name of a customer
	 * 
	 * @return String
	 */
	public String getFirstName() {
		return firstName;
	}

	/*
	 * Setting customer last name
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/*
	 * Getting the first name of a customer
	 * 
	 * @return String
	 */
	public String getLastName() {
		return lastName;
	}

	/*
	 * Setting username for customer
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
		usernames.add(username);
	}

	/*
	 * Setting password for customer
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
		passwords.add(password);
	}

	/*
	 * Creating customer account
	 * 
	 * @return true/false
	 */
	public boolean createAccount() {
		Scanner in = new Scanner(System.in);
		boolean success = false;
		String posUsername;
		String posPassword;
		String firstName;
		String lastName;

		int attempts = 0;

		System.out.println("Username/Password Requirements: Minimum 8 letters & 2 numbers.");
		while (attempts < 3) {
			if (attempts == 3) {
				System.out.println("Maximum tries reached.");
				break;
			}
			System.out.print("Enter a username: ");
			posUsername = in.nextLine();

			// Checking to see if username is already used or not
			if (usernames.contains(posUsername) || posUsername.equals("Samarveer123")) {
				System.out.println(
						"'" + posUsername + "' is in use. Please try again.\n" + (2 - attempts) + " tries left.\n");
				attempts++;
				continue;
			}

			System.out.print("Enter a password: ");
			posPassword = in.nextLine();
			System.out.print("Enter your first name: ");
			firstName = in.nextLine();
			System.out.print("Enter your last name: ");
			lastName = in.nextLine();

			// Checking the format for username and password
			if (checkUsername(posUsername) == true && checkPassword(posPassword) == true) {
				setUsername(posUsername);
				setPassword(posPassword);
				setFirstName(firstName);
				setLastName(lastName);
				customers.add(this);
				System.out.println("\nYou were successfully added to the system. Here are your details: \nName:"
						+ this.firstName + " " + this.lastName + "\nUsername: " + this.username + "\nPassword: "
						+ this.password);
				success = true;
				break;
			} else if (checkUsername(posUsername) == false) {
				System.out.println(
						"Username format is incorrect. Please try again." + "\n" + (2 - attempts) + " tries left.\n");
				attempts++;
				continue;
			} else {
				System.out.println(
						"Password format is incorrect. Please try again." + "\n" + (2 - attempts) + " tries left.\n");
				attempts++;
				continue;
			}

		}
		return success;
	}

	/*
	 * Checking username format
	 * 
	 * @param username
	 * 
	 * @return true/false
	 */
	public boolean checkUsername(String username) {
		boolean correct = false;
		int letters = 0;
		int numbers = 0;

		for (int i = 0; i < username.length(); i++) {
			Character place = new Character(username.charAt(i));

			if (Character.isLetter(place)) {
				letters++;
			} else if (Character.isDigit(place)) {
				numbers++;
			}
		}
		if (letters > 7 && numbers > 1) {
			correct = true;
		}
		return correct;
	}

	/*
	 * Checking password format
	 * 
	 * @param password
	 * 
	 * @return true/false
	 */
	public boolean checkPassword(String password) {
		boolean correct = false;
		int letters = 0;
		int numbers = 0;

		for (int i = 0; i < password.length(); i++) {
			Character place = new Character(password.charAt(i));

			if (Character.isLetter(place)) {
				letters++;
			} else if (Character.isDigit(place)) {
				numbers++;
			}
		}
		if (letters > 7 && numbers > 1) {
			correct = true;
		}
		return correct;
	}

	/*
	 * Login method for customer
	 * 
	 */
	public static Customer login() {
		Scanner in = new Scanner(System.in);
		int attempts = 0;
		String posUsername = "";
		String posPassword = "";

		System.out.println("Please login to continue: ");
		while (attempts < 3) {
			System.out.println("Username: ");
			posUsername = in.next();

			System.out.println("Password: ");
			posPassword = in.next();

			// Checking to see if account previously exists or not
			if (usernames.contains(posUsername)) {
				if (passwords.get(usernames.indexOf(posUsername)).equals(posPassword)) {
					return customers.get(usernames.indexOf(posUsername));
				} else {
					System.out.println("Incorrect username or password.  You have " + (2 - attempts) + " more tries.");
					attempts++;
				}
			} else {
				System.out.println("Incorrect username or password.  You have " + (2 - attempts) + " more tries.");
				attempts++;
			}
		}
		System.out.println("Account could not be found.\n");
		return null;
	}

	/*
	 * Method that checks to see if username and password that are entered for login
	 * are correct. Used when cancelling reservation
	 * 
	 * @param username, password
	 * 
	 * @return true/false
	 */
	public boolean loginCancel(String username, String password) {
		if (this.username.equals(username) && this.password.equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Adding a reservation
	 * 
	 * @param rental
	 */
	public void addReservation(Rental rental) {
		reservations.add(rental);
	}

	/*
	 * This method displays the customers in the system.
	 * 
	 */
	public static void displayCustomers() {
		if (customers.isEmpty()) {
			System.out.println("No customers in the system.");
		} else {
			System.out.println("Customers in the system: ");
			for (Customer c : customers) {
				System.out.println(c + "\n");
			}
		}
	}

	/*
	 * This method displays the reservations in the system.
	 * 
	 */
	public void displayReservations() {
		if (reservations.isEmpty()) {
			System.out.println("\nYou do not have any reservations.");
		} else {
			System.out.println("Your Reservations: ");
			for (Rental r : reservations) {
				System.out.println(r + "\nReservation Number: " + r.getResNumber() + r.displayPayment());
			}
		}
	}

	/*
	 * This method confirms the cancellation of a reservation.
	 * 
	 * @param resNum
	 */
	public void cancelReservation(int resNum) throws FileNotFoundException, IOException {
		for (Rental r : reservations) {
			if (resNum == r.getResNumber()) {
				Log logInfo = new Log(r.toString() + "\nReservation Canceled.");
				System.out.println("Your reservation of the number :(" + r.getResNumber() + ") was cancelled.");

				r.resetVehicle();
				reservations.remove(reservations.indexOf(r));
				printLogToFile(logInfo);

				r = null;
				return;
			}
		}
	}

	/*
	 * This method prints a log to the file
	 * 
	 * @param log
	 */
	public void printLogToFile(Log log) throws FileNotFoundException, java.io.IOException {

		PrintWriter pw = new PrintWriter(new FileWriter(file, true));

		pw.println("\n" + log);

		pw.close();
	}

	/*
	 * toString method
	 * 
	 * @return String
	 */
	public String toString() {
		String s = firstName + " " + lastName + "\n" + "Username: " + username + "\nPassword: " + password + "\n";
		for (Rental r : reservations) {
			s = s + r.toString();
		}
		return s;
	}

}
