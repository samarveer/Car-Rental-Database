
/*
 * This class includes all the necessary methods for a reservation to be made
 */
import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.Date;

public class Rental {

	// Initializing instance variables
	private Date startDate;
	private Date returnDate;
	private long numDays;
	private double subTotal;
	private String payName, cNum, ccExp;
	private String carType;
	private int resNumber;
	private static int resNumbers = 0;
	private boolean isSedan;
	private boolean isVan;
	private boolean isTruck;
	Customer renter;
	Sedan s;
	Truck t;
	Van v;

	public Rental() {
		startDate = null;
		returnDate = null;
		numDays = 0;
		resNumber = 0;
		isSedan = false;
		isVan = false;
		isTruck = false;

	}

	// Constructor for the reservation of a sedan
	public Rental(Date startDate, Date returnDate, Sedan s, Customer renter) {
		this.startDate = startDate;
		this.returnDate = returnDate;
		this.numDays = getNumDays();
		resNumbers++;
		this.resNumber = resNumbers;
		carType = "Sedan";
		this.s = s;
		this.isSedan = true;
		this.isTruck = false;
		this.isVan = false;
		this.renter = renter;
		setSubtotal();
	}

	// Constructor for the reservation of a truck
	public Rental(Date startDate, Date returnDate, Truck t, Customer renter) {
		this.startDate = startDate;
		this.returnDate = returnDate;
		this.numDays = getNumDays();
		resNumbers++;
		this.resNumber = resNumbers;
		carType = "Truck";
		this.t = t;
		this.isSedan = false;
		this.isTruck = true;
		this.isVan = false;
		this.renter = renter;
		setSubtotal();
	}

	// Constructor for the reservation of a van
	public Rental(Date startDate, Date returnDate, Van v, Customer renter) {
		this.startDate = startDate;
		this.returnDate = returnDate;
		this.numDays = getNumDays();
		resNumbers++;
		this.resNumber = resNumbers;
		carType = "Van";
		this.v = v;
		this.isSedan = false;
		this.isTruck = false;
		this.isVan = true;
		this.renter = renter;
		setSubtotal();
	}

	/*
	 * This methods gives the number of days of a reservations
	 * 
	 * @return long
	 */
	public long getNumDays() {
		long diff = returnDate.getTime() - startDate.getTime();
		return ((diff / (1000 * 60 * 60 * 24)));
	}

	/*
	 * This methods gives the price of the reservation before tax
	 */
	public void setSubtotal() {
		if (isSedan == true) {
			subTotal = Sedan.getTotPrice(numDays);
		} else if (isTruck == true) {
			subTotal = Truck.getTotPrice(numDays);
		} else if (isVan == true) {
			subTotal = Van.getTotPrice(numDays);
		}
	}

	/*
	 * Getting the reservation number
	 * 
	 * @return int
	 */
	public int getResNumber() {
		return resNumber;
	}

	/*
	 * Getting the return date
	 * 
	 * @return date
	 */
	public Date getReturnDate() {
		return returnDate;
	}

	/*
	 * This method rounds doubles to two decimal places.
	 * 
	 * @param d
	 * 
	 * @return double
	 */
	public double roundMoney(double d) {
		DecimalFormat twoDec = new DecimalFormat("#.##");
		return Double.valueOf(twoDec.format(d));
	}

	/*
	 * This method sets up the payment for the reservation
	 * 
	 * @param name, number, expDate
	 */
	public void setPayment(String name, String number, String expDate) {
		payName = name;
		cNum = number;
		ccExp = expDate;
	}

	/*
	 * This method validates the entered credit card information
	 * 
	 * @param posReturnDate
	 * 
	 * @return true/false
	 */
	@SuppressWarnings("deprecation")
	public boolean checkCreditCard(Date posReturnDate) {
		Scanner in = new Scanner(System.in);

		String number, expDate, payName;
		System.out.print(" Please enter the name on the card: ");
		payName = in.nextLine();
		System.out.print(" Please enter your credit card code(xxxx-xxxx-xxxx-xxxx): ");
		number = in.next();
		System.out.print(" Expiration date (MM/YYYY): ");
		expDate = in.next();

		// gets individual numbers from entered string
		String[] numDash = number.split("-");
		String finalNum = numDash[0] + numDash[1] + numDash[2] + numDash[3];

		// gets individual characteristics of a date from entered string
		String[] date = expDate.split("/");
		int expMonth = Integer.parseInt(date[0]);
		int expDay = Integer.parseInt(date[1]);
		int expYear = Integer.parseInt(date[2]);

		Date posExpDate = new Date(expMonth - 1, expDay, expYear);

		int retMonth = posReturnDate.getMonth();
		int retDay = posReturnDate.getDay();
		int retYear = posReturnDate.getYear();

		Date posRetDate = new Date(retMonth - 1, retDay, retYear);

		if (finalNum.length() == 16) { // If credit card number is valid
			if (expDate.matches("[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]")
					&& checkExpDate(posExpDate, posRetDate) == true) { // Validates expiry date

				System.out.println("...\n...\n...");
				System.out.println("Your card has been authorized.");
				setPayment(payName, number, expDate);
				return true;
			} else {
				System.out.println("...\n...\n...");
				System.out.println("Your card was denied.");
				return false;
			}

		} else {
			System.out.println("Your card was denied."); // If payment info is incorrect
			return false;
		}
	}

	/*
	 * This method is the customer's final confirmation of their invoice.
	 * 
	 * @return true/false
	 */
	public boolean correctInvoice() {
		Scanner in = new Scanner(System.in);

		System.out.println("Is the information correct (y/n)?");
		String finalize = in.next();

		// Finalization
		if (finalize.equalsIgnoreCase("y")) {
			if (isSedan == true) {
				subTotal = Sedan.getTotPrice(numDays);
			} else if (isTruck == true) {
				subTotal = (Truck.getTotPrice(numDays));
			} else if (isVan == true) {
				subTotal = (Van.getTotPrice(numDays));
			}
			return true;
		} else {
			return false;
		}
	}

	/*
	 * This method resets the vehicle's return date.
	 */
	@SuppressWarnings("deprecation")
	public void resetVehicle() {
		Date defaultDate = new Date(0, 0, 0, 0, 0);
		if (isSedan == true) {
			s.setDate(defaultDate);
		} else if (isTruck == true) {
			t.setDate(defaultDate);
		} else if (isVan == true) {
			v.setDate(defaultDate);
		}
	}

	/*
	 * This method displays the payment info of the entered info
	 * 
	 * @return String
	 */
	public String displayPayment() {
		return "\r\nPayment Info: \r\n\tName: " + payName + "\r\n\tCard Number: " + cNum + "\r\n\tExpiry: " + ccExp
				+ "\r\n";
	}

	/*
	 * This method validates the expiry date of the credit card
	 * 
	 * @param posExpDate, posRetDate
	 * 
	 * @return true/false
	 */
	public boolean checkExpDate(Date posExpDate, Date posRetDate) {
		if (posExpDate.after(posRetDate)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Final Invoice of the reservation
	 * 
	 * @return String
	 */
	public String toString() {
		String str = "";
		str += "\r\nReservation Details:\r\n\tCar Type: " + carType + "\r\n\tPickup: " + startDate + "\r\n\tReturn: "
				+ returnDate + "\r\n\tNumber of days: " + numDays + "\r\nSubtotal: $" + roundMoney(subTotal)
				+ "\r\nTotal: $" + roundMoney(subTotal * 1.13);
		return str;
	}

}
