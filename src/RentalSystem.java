
/*
 * This is the class where the main options of the program are run. Everything starts from here.
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RentalSystem {

	// Initializing instance variables
	PrintWriter writer;
	String file;
	private Admin a;
	private static int numCustomers;
	private int maxSedan;
	private int maxTruck;
	private int maxVan;
	private Sedan parkedS;
	private Truck parkedT;
	private Van parkedV;
	private ArrayList<Customer> customers;
	private ArrayList<Log> logs;
	private ArrayList<Sedan> sedans;
	private ArrayList<Truck> trucks;
	private ArrayList<Van> vans;

	// Constructor
	public RentalSystem() {

		file = "LogFile.txt";

		a = new Admin();
		numCustomers = 0;
		maxSedan = 6;
		maxTruck = 4;
		maxVan = 4;

		parkedS = new Sedan();
		parkedT = new Truck();
		parkedV = new Van();

		customers = new ArrayList<Customer>();
		logs = new ArrayList<Log>();
		sedans = new ArrayList<Sedan>();
		trucks = new ArrayList<Truck>();
		vans = new ArrayList<Van>();

		// Number of sedans
		Sedan s1 = new Sedan();
		Sedan s2 = new Sedan();
		Sedan s3 = new Sedan();
		Sedan s4 = new Sedan();
		Sedan s5 = new Sedan();
		Sedan s6 = new Sedan();
		sedans.add(s1);
		sedans.add(s2);
		sedans.add(s3);
		sedans.add(s4);
		sedans.add(s5);
		sedans.add(s6);

		// Number of trucks
		Truck t1 = new Truck();
		Truck t2 = new Truck();
		Truck t3 = new Truck();
		Truck t4 = new Truck();
		trucks.add(t1);
		trucks.add(t2);
		trucks.add(t3);
		trucks.add(t4);

		// Number of vans
		Van v1 = new Van();
		Van v2 = new Van();
		Van v3 = new Van();
		Van v4 = new Van();
		vans.add(v1);
		vans.add(v2);
		vans.add(v3);
		vans.add(v4);
	}

	/*
	 * This method adds a customer to the stored customers.
	 */
	public void addCustomer() {
		Customer c = new Customer();
		// If an account is created, it will be stored.
		if (c.createAccount()) {
			numCustomers++;
			customers.add(c);
		}
	}

	/*
	 * This method makes a reservation.
	 */
	@SuppressWarnings("deprecation")
	public void makeReservation() throws FileNotFoundException, IOException {
		Scanner in = new Scanner(System.in);

		// int startYear = 2019;
		// int returnYear = 2019;
		int startMin = 0, returnMin = 0;
		boolean date = false;
		Date posStartDate, posReturnDate;

		do {
			System.out.println("Enter Reservation Info: ");
			System.out.println("Please enter your desired pickup date (MM/DD/YYYY):");
			String pickDate = in.next();
			System.out.println("Please enter the hour of your pickup time (HH): ");
			int startHour = in.nextInt();

			// Getting individual characteristics of a date
			String[] date1 = pickDate.split("/");
			int startMonth = Integer.valueOf(date1[0]);
			int startDay = Integer.valueOf(date1[1]);
			int startYear = Integer.valueOf(date1[2]);

			// Start date
			posStartDate = new Date(startYear - 1900, startMonth - 1, startDay, startHour, startMin);

			System.out.println("Please enter your desired return date (MM/DD/YYYY):");
			String returnDate = in.next();
			System.out.println("Please enter the hour of your return time (HH): ");
			int returnHour = in.nextInt();

			// Getting individual characteristics of a date
			String[] date2 = returnDate.split("/");
			int returnMonth = Integer.valueOf(date2[0]);
			int returnDay = Integer.valueOf(date2[1]);
			int returnYear = Integer.valueOf(date2[2]);

			// Return date
			posReturnDate = new Date(returnYear - 1900, returnMonth - 1, returnDay, returnHour, returnMin);
			date = checkDateOrder(posStartDate, posReturnDate);

		} while (date == false); // Checking to see if dates are valid.

		switch (checkAvailability(posStartDate)) {
		// Reservation for sedan
		case 1:
			int choiceS = 0;
			Customer s = new Customer();
			System.out.println(
					"You need an account to continue. What would you like to do?\n(1)Create Account\n(2)Login");
			choiceS = in.nextInt();
			while (choiceS != 1 || choiceS != 2) {
				if (choiceS == 1) {
					s.createAccount();
					customers.add(s);
					break;
				} else if (choiceS == 2) {
					s = Customer.login();
					break;
				} else {
					System.out.println("Invalid choice. Please try again.");
					choiceS = in.nextInt();
					continue;
				}
			}
			if (s != null) { // If there is a actual customer
				Rental r = new Rental(posStartDate, posReturnDate, parkedS, s);
				System.out.println(r);
				if (r.checkCreditCard(posReturnDate)) { // Checking to see if the payment info is valid
					System.out.println("\nFinal Invoice: \n" + r);
					System.out.println("\nReservation Number: " + r.getResNumber());
					System.out.println(r.displayPayment());

					if (r.correctInvoice()) { // Confirming information
						System.out.println("Thank you for choosing RentACar!");
						Log logInfo = new Log("\r\n" + s.getFirstName() + " " + s.getLastName() + " " + r.toString()
								+ r.displayPayment());
						logs.add(logInfo);
						s.addReservation(r);
						parkedS.setDate(r.getReturnDate());
						printLogToFile(logInfo); // Printing the invoice to the file
					} else { // If information is incorrect
						Log logInfo = new Log("\r\n" + s.getFirstName() + " " + s.getLastName() + " " + r.toString()
								+ "\r\nCancelled Order.\r\n");
						logs.add(logInfo);
						printLogToFile(logInfo);
					}
				} else { // If payment info is not correct
					System.out.println("\nPayment Info Denied! Please restart.");
					Log logInfo = new Log("\r\n" + s.getFirstName() + " " + s.getLastName() + " " + r.toString()
							+ "\r\nDenied Card.\r\n");
					logs.add(logInfo);
					printLogToFile(logInfo);
				}
			}
			break;
		// Same as above but for a truck
		case 2:
			int choiceT = 0;
			Customer t = new Customer();
			System.out.println(
					"You need an account to continue. What would you like to do?\n(1)Create Account\n(2)Login");
			choiceT = in.nextInt();
			while (choiceT == 1 || choiceT == 2) {

				if (choiceT == 1) {
					t.createAccount();
					customers.add(t);
					break;
				} else if (choiceT == 2) {
					t = Customer.login();
					break;
				} else {
					System.out.println("Invalid choice. Please try again.");
					continue;
				}
			}
			if (t != null) {
				Rental r = new Rental(posStartDate, posReturnDate, parkedT, t);
				System.out.println(r);
				if (r.checkCreditCard(posReturnDate)) {
					System.out.println("\nFinal Invoice: \n" + r);
					System.out.println("\nReservation Number: " + r.getResNumber());
					System.out.println(r.displayPayment());

					if (r.correctInvoice()) {
						System.out.println("Thank you for choosing RentACar!");
						Log logInfo = new Log("\r\n" + t.getFirstName() + " " + t.getLastName() + " " + r.toString()
								+ r.displayPayment());
						logs.add(logInfo);
						t.addReservation(r);
						parkedS.setDate(r.getReturnDate());
						printLogToFile(logInfo);
					} else {
						Log logInfo = new Log("\r\n" + t.getFirstName() + " " + t.getLastName() + " " + r.toString()
								+ "\r\nCancelled Order.\r\n");
						logs.add(logInfo);
						printLogToFile(logInfo);
					}
				} else {
					System.out.println("\nPayment Info Denied! Please restart.");
					Log logInfo = new Log("\r\n" + t.getFirstName() + " " + t.getLastName() + " " + r.toString()
							+ "\r\nDenied Card.\r\n");
					logs.add(logInfo);
					printLogToFile(logInfo);
				}
			}
			break;
		// Same as above but for a van
		case 3:
			int choiceV = 0;
			Customer v = new Customer();
			System.out.println(
					"You need an account to continue. What would you like to do?\n(1)Create Account\n(2)Login");
			choiceV = in.nextInt();
			while (choiceV == 1 || choiceV == 2) {
				if (choiceV == 1) {
					v.createAccount();
					customers.add(v);
					break;
				} else if (choiceV == 2) {
					v = Customer.login();
					break;
				} else {
					System.out.println("Invalid choice. Please try again.");
					continue;
				}
			}
			if (v != null) {
				Rental r = new Rental(posStartDate, posReturnDate, parkedV, v);
				System.out.println(r);
				if (r.checkCreditCard(posReturnDate)) {
					System.out.println("\nFinal Invoice: \n" + r);
					System.out.println("\nReservation Number: " + r.getResNumber());
					System.out.println(r.displayPayment());

					if (r.correctInvoice()) {
						System.out.println("Thank you for choosing RentACar!");
						Log logInfo = new Log("\r\n" + v.getFirstName() + " " + v.getLastName() + " " + r.toString()
								+ r.displayPayment());
						logs.add(logInfo);
						v.addReservation(r);
						parkedS.setDate(r.getReturnDate());
						printLogToFile(logInfo);
					} else {
						Log logInfo = new Log("\r\n" + v.getFirstName() + " " + v.getLastName() + " " + r.toString()
								+ "\r\nCancelled Order.\r\n");
						logs.add(logInfo);
						printLogToFile(logInfo);
					}
				} else {
					System.out.println("\nPayment Info Denied! Please restart.");
					Log logInfo = new Log("\r\n" + v.getFirstName() + " " + v.getLastName() + " " + r.toString()
							+ "\r\nDenied Card.\r\n");
					logs.add(logInfo);
					printLogToFile(logInfo);
				}
			}
			break;
		default:
			break;
		}
	}

	/*
	 * This method cancels a reservation.
	 */
	public void cancelReservation() throws FileNotFoundException, IOException {
		Scanner in = new Scanner(System.in);
		String confirmCancel = "n";
		int attempts = 0;

		Customer c;

		String username = "";
		String password = "";

		// To cancel a reservation, you need an account
		while (attempts < 3) {
			System.out.println("Please login to continue: ");

			System.out.println("Username: ");
			username = in.next();

			System.out.println("Password: ");
			password = in.next();

			for (int i = 0; i < customers.size(); i++) {
				if (customers.get(i).loginCancel(username, password)) { // Checks to see if an account is there
					c = customers.get(i);
					c.displayReservations();
					System.out.println("Do you want to cancel a reservation? (y/n)"); // Confirming the reservation
					confirmCancel = in.next();
					if (confirmCancel.equalsIgnoreCase("y")) {
						System.out.print("Enter your reservation number: ");
						c.cancelReservation(in.nextInt());
						attempts = 3;
					} else {
						return;
					}
					break;

				} else { // Login prompt will only run three times.
					System.out
							.println("Account not found. Please try again." + "\n" + (2 - attempts) + " tries left.\n");
					attempts++;
					continue;
				}
			}
		}
	}

	/*
	 * Checking to see if all the vehicles are taken or not
	 * 
	 * @param posStartDate
	 * 
	 * @return int
	 */
	public int checkAvailability(Date posStartDate) {
		Scanner in = new Scanner(System.in);
		int option = 0;
		String confirmOption = "n";

		System.out.println("\nOur Vehicles Include: ");

		// Checking for available sedan
		for (Sedan s : sedans) {
			if (s.checkAvailable(posStartDate)) {
				this.parkedS = s;
				System.out.println("(1) Sedan: $90.00/day.");
				break;
			} else {
				System.out.println("There are no sedans available.");
				break;
			}
		}

		// Checking for available truck
		for (Truck t : trucks) {
			if (t.checkAvailable(posStartDate)) {
				this.parkedT = t;
				System.out.println("(2) Truck: $100.00/day.");
				break;
			} else {
				System.out.println("There are no trucks available.");
				break;
			}
		}

		// Checking for available van
		for (Van v : vans) {
			if (v.checkAvailable(posStartDate)) {
				this.parkedV = v;
				System.out.println("(3) Van: $110.00/day");
				break;
			} else {
				System.out.println("There are no vans available.");
				break;
			}
		}

		System.out.println("(4) Exit to main menu.");

		// Choosing what vehicles are available
		while (confirmOption.equalsIgnoreCase("n")) {
			System.out.println("\nPlease make your selection: ");
			option = in.nextInt();

			switch (option) {
			case 1:
				System.out.println("You have chosen the sedan.\nCorrect? (y/n)");
				confirmOption = in.next();
				break;
			case 2:
				System.out.println("You have chosen the truck.\nCorrect? (y/n)");
				confirmOption = in.next();
				break;
			case 3:
				System.out.println("You have chosen the van.\nCorrect? (y/n)");
				confirmOption = in.next();
				break;
			case 4:
				return 0;
			default:
				System.out.println("Incorrect option. Please try again.");
				break;
			}
		}
		return option;
	}

	/*
	 * Checking to see if the entered dates are valid or not
	 * 
	 * @param posStartDate, posReturnDate
	 * 
	 * @return true/false
	 */
	@SuppressWarnings("deprecation")
	public boolean checkDateOrder(Date posStartDate, Date posReturnDate) {
		if (posReturnDate.after(posStartDate)) {
			// Can only make a reservation for 2019, but I have to use 119 because the year
			// in date in java is (actual year - 1900)
			if (posStartDate.getYear() != 119 || posReturnDate.getYear() != 119) {
				System.out
						.println("You can only make rentals for the this year. Please re-enter your desired dates: \n");
				return false;
			} else if (posStartDate.getDay() == posReturnDate.getDay()) {
				System.out.println("The rental must be of at least one day. Please re-enter your desired dates: \n");
				return false;
			} else {
				return true;
			}
		} else { // If the return date is not after start date
			System.out.println("These dates are not sequential.  Please re-enter your desired dates: \n");
			return false;
		}
	}

	/*
	 * Getting number of customers
	 * 
	 * @return int
	 */
	public static int getNumCustomers() {
		return numCustomers;
	}

	/*
	 * This method prints a log to the file
	 * 
	 * @param log
	 */
	public void printLogToFile(Log log) throws FileNotFoundException, java.io.IOException {

		// Prints to file
		PrintWriter pw = new PrintWriter(new FileWriter(file, true));
		pw.println("\n" + log);
		pw.close();
	}

	/*
	 * toString Method for the rental system
	 * 
	 * @return String
	 */
	public String toString() {
		return "Admin Info: " + a + "\nNumber of Customers:" + numCustomers + "\nSedan Count: " + maxSedan
				+ "\nTruck Count: " + maxTruck + "\nVan Count: " + maxVan;
	}

}
