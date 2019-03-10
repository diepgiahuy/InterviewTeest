import org.junit.Test;

public class FinePhoneNumberTest {

	@Test
	public void test_default() {
		new FindPhoneNumber("resource/sample.csv").process();
		TestCase.assertReaders("resource/sample_result.csv", "resource/output.csv");
	}

	@Test
	public void test_simple_single_entry() {
		new FindPhoneNumber("resource/simple_test_1.csv").process();
		TestCase.assertReaders("resource/simple_test_1_result.csv", "resource/output.csv");
	}

	@Test
	public void test_simple_entry_with_no_deactivation_date() {
		new FindPhoneNumber("resource/simple_test_2.csv").process();
		TestCase.assertReaders("resource/simple_test_2_result.csv", "resource/output.csv");
	}

	@Test
	public void test_1() {
		new FindPhoneNumber("resource/test_1.csv").process();
		TestCase.assertReaders("resource/test_1_result.csv", "resource/output.csv");
	}

	@Test
	public void test_2() {
		new FindPhoneNumber("resource/test_2.csv").process();
		TestCase.assertReaders("resource/test_2_result.csv", "resource/output.csv");
	}

	@Test
	public void test_3() {
		new FindPhoneNumber("resource/test_3.csv").process();
		TestCase.assertReaders("resource/test_3_result.csv", "resource/output.csv");
	}

	@Test
	public void test_random_resource() {
		new FindPhoneNumber("resource/random_1.csv").process();
		TestCase.assertReaders("resource/random_1_result.csv", "resource/output.csv");
	}

	@Test
	public void test_random_resource_2() {
		new FindPhoneNumber("resource/random_2.csv").process();
		TestCase.assertReaders("resource/random_2_result.csv", "resource/output.csv");
	}

}
