
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestCase {
	
	public static void assertReaders(String expectedFile, String actualFile) {

		String expectedLine;
		BufferedReader expected = null;
		BufferedReader result = null;
		try {
			expected = new BufferedReader(new FileReader(new File(expectedFile)));
			result = new BufferedReader(new FileReader(new File(actualFile)));
			
			while ((expectedLine = expected.readLine()) != null) {
				String actualLine = result.readLine();
				assertNotNull("Expected had more lines then the actual.", actualLine);
				assertEquals(expectedLine, actualLine);
			}
			assertNull("Actual had more lines then the expected.", result.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (expected != null)
					expected.close();
				if (result != null)
					result.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}