
/*
 * This is the class where the information of a reservation is stored aka via logs.
 */
import java.util.ArrayList;
import java.sql.Timestamp;

public class Log {

	// Initializing instance variables
	private String logInfo;
	private static ArrayList<Log> logs = new ArrayList<Log>();

	public Log() {
		logInfo = null;
	}

	/*
	 * This adds the time of when the customer reserves a vehicle
	 * 
	 * @param someLogInfo
	 */
	public Log(String someLogInfo) {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		this.logInfo = (someLogInfo + "\nDate and time of order: " + timeStamp + "\n");
		logs.add(this);
	}

	/*
	 * This method displays all the logs in the system
	 */
	public static void displayLogs() {
		if (logs.isEmpty()) {
			System.out.println("There are no logs in the system.");
			return;
		} else {
			System.out.println("Logs in the system: ");
			for (Log l : logs) {
				System.out.println(l + "\n");
			}
		}
	}

	/*
	 * This returns the info of a log
	 * 
	 * @return String
	 */
	public String toString() {
		return logInfo;
	}

}
