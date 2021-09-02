
/*
 * This is the same class as customer just for the admin.
 */
import java.util.Scanner;

public class Admin extends Customer {

	// Initializing instance variables
	private String username = "Samarveer123";
	private String password = "Samarveer123";

	public Admin() {
		username = null;
		password = null;
	}

	/*
	 * Getting the username for admin
	 * 
	 * @return String
	 */
	public String getUsername() {
		return username;
	}

	/*
	 * Getting the password for admin
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/*
	 * Login method for the admin
	 */
	public static Admin login() {
		Scanner in = new Scanner(System.in);
		int attempts = 0;

		String posUsername, posPassword;
		while (attempts < 3) {
			System.out.println("Enter username: ");
			posUsername = in.next();
			System.out.println("Enter password: ");
			posPassword = in.next();

			// Checking to see if the username and password are correct
			if (posUsername.equals("Samarveer123") && posPassword.equals("Samarveer123")) {
				Admin.begin();
				return null;
			} else { // Login prompt will only run three times.
				System.out.println("Incorrect username or password. Try Again.\n" + (2 - attempts) + " tries left.");
				attempts++;
			}
		}
		System.out.println("You have been locked out. Restart program.");
		System.exit(0);
		return null;

	}

	/*
	 * This method is run when the admin logs in to the system.
	 */
	public static void begin() {
		Scanner in = new Scanner(System.in);
		int option = 0;

		while (option != 3) {
			System.out.println(
					"\nChoose from the following options:\n (1) Display Customers \n (2) Display Logs \n (3) Exit\n");
			option = in.nextInt();

			switch (option) {
			case 1:
				displayCustomers();
				break;
			case 2:
				displayLogs();
				break;
			case 3:
				break;
			default:
				System.out.println("Incorrect option. Try again.\n");
				break;
			}
		}
	}

	/*
	 * Displaying the customers for the admin
	 */
	public static void displayCustomers() {
		Customer.displayCustomers();
	}

	/*
	 * Displaying the logs for the admin
	 */
	public static void displayLogs() {
		Log.displayLogs();
	}

	/*
	 * Returning admin info
	 * 
	 * @return String
	 */
	public String toString() {
		return "Admin Username: " + username + "\nAdmin Password: " + password;
	}
}
