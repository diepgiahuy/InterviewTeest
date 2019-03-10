import java.io.IOException;

public class Solution {

	public static void main(String[] args) throws IOException {
		FindPhoneNumber getPhone = new FindPhoneNumber("resource/random_1.csv");
		long startTime = System.nanoTime();
		getPhone.process();
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println((double)totalTime / 1000000000);
	}

}
