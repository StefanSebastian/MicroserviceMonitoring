package sender.load;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Will be focused on scaling one microservice, using the Fibonacci endpoint
 */
public class LoadTest {
	
	private int usersBatch;
	
	public LoadTest(int usersBatch) {
		this.usersBatch = usersBatch;
	}
	
	public LoadTest() {
		this.usersBatch = 100;//default
	}
	
	public void performTest() {
		try {
			while (true) {
				System.out.println("Starting users: " + usersBatch);
				startUsers(usersBatch);
				Thread.sleep(60000);
			}
		} catch (Exception ex) {
		}
	}
	
	public void startUsers(int users) {
		for (int i = 0; i < users; i++) {
			new Thread(new User()).start();
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
				URL obj = new URL("http://localhost:8762/api/microservice1/method2?n=30");
		        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		        con.setRequestMethod("GET");
		        int responseCode = con.getResponseCode();
		        System.out.println("Response Code : " + responseCode);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			long elapsed = System.currentTimeMillis() - start;
			System.out.println(Thread.currentThread().getName() + " Request time : " + elapsed);
	    }	
	}
	
}
