import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.exceptions.JedisException;

public class FindPhoneNumber {
	// Config for Redis server on localhost
	private final String REDISHOST = "localhost";
	private final Integer REDISPORT = 6379;

	// the jedis connection pool..
	private JedisPool pool = null;
	private Jedis jedis = null;
	private String fileName = "";

	public FindPhoneNumber(String fileName) {
		this.fileName = fileName;
		pool = new JedisPool(REDISHOST, REDISPORT);
		jedis = pool.getResource();
		jedis.flushAll();
	}

	public void process() {
		readFile();
		writeFile();
		jedis.close();
	}

	private void readFile() {
		ReadFile readFile = null;
		try {
			readFile = new ReadFile(new FileInputStream(new File(fileName)));
			String line;
			readFile.next();
			while ((line = readFile.next()) != null) {
				try {
					String[] tokens = line.split(",");
					if (tokens.length == 3) {
						// Add data into redis , return 0 when data is duplicate
						Long res = jedis.sadd(tokens[0], tokens[1]);
						if (res == 0) {
							// remove duplicate data
							jedis.srem(tokens[0], tokens[1]);
						}
						res = jedis.sadd(tokens[0], tokens[2]);
						if (res == 0) {
							jedis.srem(tokens[0], tokens[2]);
						}

					} else if (tokens.length == 2) {
						Long res = jedis.sadd(tokens[0], tokens[1], " ");
						if (res == 1) {
							jedis.srem(tokens[0], tokens[1]);
						}

					}
				} catch (JedisException e) {
					// if something wrong happen, return it back to the pool
					if (null != jedis) {
						pool.returnBrokenResource(jedis);
						jedis = null;
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			readFile.close();
		}
	}

	private void writeFile() {
		// Get all phone number store in redis
		Set<String> keys = jedis.keys("*");

		ArrayList<String> list = new ArrayList<String>(keys);
		// Sort phone number for test purpose
		Collections.sort(list, (p1, p2) -> (p1).compareTo(p2));

		PrintWriter printer = null;
		try {
			printer = new PrintWriter(new BufferedWriter(new FileWriter(new File("resource/output.csv"))));
			printer.println("PHONE_NUMBER,REAL_ACTIVATION_DATE");
			for (String key : list) {
				// Sort value by key (incr) for get result
				List<String> members = jedis.sort(key, new SortingParams().alpha());
				// Add 0 at begin in csv file
				//String convertKey = "\t" + key;
				if (" ".equals(members.get(0))) {
					// if current user is active so result is the largest date
					printer.println(key + "," + members.get(members.size() - 1));
				} else {
					// if current user is deactive so result is the second
					// largest date
					printer.println(key + "," + members.get(members.size() - 2));
				}
			}
			printer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
