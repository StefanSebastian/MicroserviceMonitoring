package sender.load;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Will be focused on scaling one microservice, using the Fibonacci endpoint
 */
public class LoadTest {
	
	private int usersBatch = 100;
	
	private int rampInterval = 30000;
	
	private String url = "http://localhost:8762/api/microservice1/method2?n=30";
	
	public LoadTest() {}
	
	public void performTest() {
		spikeScenario();
		//mscBenchmark();
	}
	
	public void spikeScenario() {
		try {
			startUsers(50);
			Thread.sleep(60000);
			startUsers(150);
			Thread.sleep(120000);
		} catch (Exception ex) {
		}
	}
	
	public void mscBenchmark() {
		usersBatch = 100;
		rampInterval = 30000;
		try {
			while (true) {
				System.out.println("Starting users: " + usersBatch);
				startUsers(usersBatch);
				Thread.sleep(rampInterval);
			}
		} catch (Exception ex) {
		}
	}
	
	public void startUsers(int users) {
		for (int i = 0; i < users; i++) {
			Thread user = new Thread(new User());
			user.setDaemon(true);
			user.start();
		}
	}
	
	class User implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					performActions();
				} catch (Exception e) {	
				}
			}
		}
		
		private void performActions() throws Exception {
			// sleep around a sec
			int sleep = ThreadLocalRandom.current().nextInt(900, 1100 + 1);
			Thread.sleep(sleep);
			
			sendMessage();
		}
		
		private void sendMessage() {
			long start = System.currentTimeMillis();
			try {
				URL obj = new URL(url);
		        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		        con.setRequestMethod("GET");
		        int responseCode = con.getResponseCode();
				printResult(con);
		        System.out.println("Response Code : " + responseCode);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			long elapsed = System.currentTimeMillis() - start;
			System.out.println(Thread.currentThread().getName() + " Request time : " + elapsed);
	    }
		
		private void printResult(HttpURLConnection con) throws IOException {
			BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());
		}
	}

	public int getUsersBatch() {
		return usersBatch;
	}

	public void setUsersBatch(int usersBatch) {
		this.usersBatch = usersBatch;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
