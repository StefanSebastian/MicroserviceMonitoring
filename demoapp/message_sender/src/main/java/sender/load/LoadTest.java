package sender.load;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
		//spike();
		constantFlow();
	}
	
	public void constantFlow() {
		startUsers(10);
		while(true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void spike() {
		try {
			startUsers(10);
			Thread.sleep(60000);
			startUsers(60);
			Thread.sleep(180000);
			
			synchronized (this) {
				System.out.println("Storing logs");
				storeLogs();
				System.out.println("Stored logs");
				System.out.println("Printing stats");
				printStats();
				System.out.println("Pritned stats");
			}
		} catch (Exception ex) {
		}
	}
	
	private void storeLogs() {
		String filename = System.currentTimeMillis() + ".csv";
		File csvFile = new File(filename);
		try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFile));){
			csvWriter.println("timestamp,duration,respCode");
			for(RequestLog item : logs){
				csvWriter.println(item);
			}
		} catch (IOException e) {
		    //Handle exception
		    e.printStackTrace();
		}
	}
	
	private void printStats() {
		System.out.println("printing stats");
		List<Long> durations = new LinkedList<>();
		for (RequestLog req : logs) {
			durations.add(req.getDuration());
		}
		long sum = 0;
		for (Long d : durations) {
			sum += d;
		}
		double avg = sum / durations.size();
		
		Collections.sort(durations);
		int idx = (int) (0.9 * durations.size());	
		double p90rt = durations.get(idx);
		
		System.out.println("Avg : " + avg);
		System.out.println("P90RT : " + p90rt);
		
		String filename = System.currentTimeMillis() + ".stats";
		File csvFile = new File(filename);
		try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFile));){
			csvWriter.println("avg,p90rt");
			csvWriter.println(avg + "," + p90rt);
		} catch (IOException e) {
		    //Handle exception
		    e.printStackTrace();
		}
	}
	
	
	private List<RequestLog> logs = new LinkedList<>();
	
	private synchronized void addLog(RequestLog log) {
		logs.add(log);
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
			int responseCode = -1;
			try {
				URL obj = new URL(url);
		        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		        con.setRequestMethod("GET");
		        responseCode = con.getResponseCode();
				printResult(con);
		        System.out.println("Response Code : " + responseCode);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			long elapsed = System.currentTimeMillis() - start;
			System.out.println(Thread.currentThread().getName() + " Request time : " + elapsed);
			addLog(new RequestLog(start, elapsed, responseCode));
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
