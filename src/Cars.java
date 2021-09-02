
/*
 * This is the abstract class for vehicles.
 */
import java.util.Date;

public abstract class Cars {

	// These methods are in every subclass
	public abstract void setDate(Date returnDate);

	public abstract boolean checkAvailable(Date rentDay);

	public abstract String toString();
}
