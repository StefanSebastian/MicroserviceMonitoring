package sender.load;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Will be focused on scaling one microservice, using the Fibonacci endpoint
 */
public class LoadTest {
	
	public static void main(String[] args) {
		LoadTest tester = new LoadTest();
		if (args.length > 0) {
			String url = args[0];
			tester.setUrl(url);
		}
		tester.performTest();
	}
	
	private String url = "http://localhost:8762/api/microservice1/method2?n=35";
	
	
	public void performTest() {
		spikeScenario();
		//mscBenchmark();
	}
	
	public void spikeScenario() {
		try {
			startUsers(40);
			Thread.sleep(120000);
			System.out.println("Avg: " + times.stream().mapToDouble(a -> a).average().getAsDouble());
			Collections.sort(times);
			int index = (int)Math.ceil(((double)90 / (double)100) * (double)times.size());
			System.out.println("90 prt: " + times.get(index));
			//startUsers(150);
			//Thread.sleep(120000);
		} catch (Exception ex) {
		}
	}
	
	public void mscBenchmark() {
		int usersBatch = 100;
		int rampInterval = 30000;
		try {
			while (true) {
				System.out.println("Starting users: " + usersBatch);
				startUsers(usersBatch);
				Thread.sleep(rampInterval);
			}
		} catch (Exception ex) {
		}
	}
	
	private List<Long> times = new LinkedList<>();
	
	private synchronized void addTime(Long time) {
		times.add(time);
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
			addTime(elapsed);
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
