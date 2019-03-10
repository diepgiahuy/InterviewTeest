
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class GenFile {

	private int totalPhoneNumber;
	private String outputName;
	private String outputResultName;
	private ArrayList<Data> listData;
	private ArrayList<Data> listResult;

	private Random generator;

	public GenFile(int totalPhoneNumber, String outputName, String outputResultName) {

		this.totalPhoneNumber = totalPhoneNumber;
		this.outputName = outputName;
		this.outputResultName = outputResultName;
		this.listData = new ArrayList<Data>();
		this.listResult = new ArrayList<Data>();
		this.generator = new Random();

	}

	private String genratePhoneNumber() {
		// phoneNumber start with zero
		String phoneNumber = "";
		// add 100000000 so there will always be 9 numbers
		// 899999999 so it wont succed 999999999 when the 100000000 is added
		long nextNum = generator.nextInt(899999999) + 100000000;
		phoneNumber += String.valueOf(nextNum);
		return phoneNumber;
	}

	public static Date randomDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1996);
		cal.set(Calendar.MONTH, randomRange(1, 12));
		cal.set(Calendar.DAY_OF_MONTH, randomRange(1, 28));
		return cal.getTime();
	}

	public static Date randomDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, randomRange(1, 90));
		return cal.getTime();
	}

	private boolean newUser(int chance) {
		int percent = generator.nextInt(99) + 1;
		// chance to use by new user
		if (percent > chance)
			return true;
		else
			return false;
	}

	public Date randomMonth(Date date, boolean isNewUser) {
		Calendar cal = Calendar.getInstance();
		if (isNewUser) {
			cal.setTime(date);
			cal.add(Calendar.MONTH, randomRange(2, 6));
		} else {
			cal.setTime(date);
		}
		return cal.getTime();
	}

	public static int randomRange(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	public void generate() {
		for (int i = 0; i < totalPhoneNumber; i++) {
			generate(genratePhoneNumber());
		}
		Collections.shuffle(listData);
		writeTestToFile();
		writeResultToFile();
	}

	private void generate(String phoneNumber) {
		Date startDate, endDate = null;
		startDate = randomDate();
		int randomStep = generator.nextInt(4) + 1;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		// Create 3 consecutive blocks
		for (int j = 0; j < 3; j++) {
			// one consecutive block will have random elements
			for (int i = 0; i < randomStep; i++) {
				endDate = randomDate(startDate);
				String activationDate = format.format(startDate);
				String deActivationDate = format.format(endDate);
				Data data = new Data(phoneNumber, activationDate, deActivationDate);
				listData.add(data);
				startDate = randomMonth(endDate, newUser(70));
			}
		}

		// generate result
		startDate = randomMonth(endDate, newUser(0));
		endDate = randomDate(startDate);
		String activationDate = format.format(startDate);
		String deActivationDate = format.format(endDate);
		Data data = new Data(phoneNumber, activationDate, deActivationDate);
		listData.add(data);
		Data resultData = new Data(phoneNumber, activationDate);
		listResult.add(resultData);
	}

	private void writeTestToFile() {
		PrintWriter printer = null;
		try {
			printer = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputName))));
			printer.println("PHONE_NUMBER,ACTIVATION_DATE,DEACTIVATION_DATE");

			for (Data data : listData) {
				printer.println(data.getPhone() + "," + data.getActivationDate() + "," + data.getDeactivationDate());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (printer != null) {
				printer.close();

			}
		}

	}

	private void writeResultToFile() {
		PrintWriter printer = null;
		try {
			printer = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputResultName))));
			printer.println("PHONE_NUMBER,REAL_ACTIVATION_DATE");
			Collections.sort(listResult, (p1, p2) -> p1.getPhone().compareTo(p2.getPhone()));
			for (Data resultData : listResult) {
				printer.println(resultData.getPhone() + "," + resultData.getResultDate());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (printer != null) {
				printer.close();

			}
		}
	}

	public static void main(String[] args) {
		new GenFile(100, "resource/random_1.csv", "resource/random_1_result.csv").generate();
		new GenFile(1000, "resource/random_2.csv", "resource/random_2_result.csv").generate();
	}
}