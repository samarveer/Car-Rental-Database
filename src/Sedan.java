
/*	This is the sedan class, it has the info for a sedan. It is a subclass of Cars
 * 
 */
import java.util.Date;

public class Sedan extends Cars {

	// Initializing instance variables
	private static double pricePerDay = 90.00;
	private Date returnDate;

	// Constructing the date
	@SuppressWarnings("deprecation")
	public Sedan() {
		returnDate = new Date(0, 0, 0, 0, 0);
	}

	/*
	 * Setting the date for the car
	 * 
	 * @param returnDate
	 */
	public void setDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	/*
	 * Getting total price of a vehicle based on how many days it is rented
	 * 
	 * @param numDays
	 * 
	 * @return double
	 */
	public static double getTotPrice(long numDays) {
		Long totDays = new Long(numDays);
		double d = totDays.doubleValue();
		double subTotal = pricePerDay * d;
		return subTotal;
	}

	/*
	 * Checking the availability of a vehicle
	 * 
	 * @param rentDay
	 * 
	 * @return true/false
	 */
	public boolean checkAvailable(Date rentDay) {
		if (rentDay.after(returnDate)) {
			return true;
		}
		return false;
	}

	/*
	 * Returning the info of a sedan
	 * 
	 * @return String
	 */
	public String toString() {
		return "Sedan Price: " + pricePerDay + "\nReturn Date: " + returnDate;
	}

}
