/* Main class where all the main options of the program are run from
 * 
 */

//imports
import java.util.Scanner;

/*
 * Main method (heart of program)
 * 
 * @param args
 */
public class Main {
	public static void main(String[] args) throws java.io.FileNotFoundException, java.io.IOException {

		Scanner in = new Scanner(System.in);

		// Initializing object on which most of main the program methods run
		RentalSystem RentACar = new RentalSystem();

		int choice = 0;

		// Welcome message
		System.out.println("Hello. Welcome to RentACar Rental Agency");
		System.out.println("\nSelect the following options accordingly: ");
		System.outn 
				.println("(1)Admin Login\n(2)Create Account\n(3)Make a Reservation\n(4)Cancel a Reservation\n(5)Exit");
		choice = in.nextInt();

		// Different pathways to the program
		while (choice != 5) {
			switch (choice) {
			case 1:
				Admin.login();
				break;
			case 2:
				RentACar.addCustomer();
				break;
			case 3:
				RentACar.makeReservation();
				break;
			case 4:
				RentACar.cancelReservation();
				break;
			case 5:
				break;
			default:
				System.out.println("Your choice is not a valid option.  Please try again, or exit");
				break;
			}
			System.out.println("\nMake a selection from the following options: ");
			System.out.println(
					"(1)Admin Login\n(2)Create Account\n(3)Make a Reservation\n(4)Cancel a Reservation\n(5)Exit");
			choice = in.nextInt();

		}

		// Exiting the program
		System.exit(0);
	}
}
