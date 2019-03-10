
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class ReadFile {
	private BufferedReader reader = null;
	private StringTokenizer tokenizer;

	public ReadFile(InputStream stream) {
		reader = new BufferedReader(new InputStreamReader(stream));
		tokenizer = null;
	}

	public void close() {
		try {
			if (reader != null)
				reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String next() {
		while ((tokenizer == null || !tokenizer.hasMoreTokens())) {
			try {
				String line = reader.readLine();
				if (line == null)
					return null;
				tokenizer = new StringTokenizer(line);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return tokenizer.nextToken();
	}

}