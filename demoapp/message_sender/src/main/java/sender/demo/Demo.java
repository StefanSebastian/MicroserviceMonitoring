package sender.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import sender.load.RequestLog;

public class Demo {
	public static void main(String[] args) {
		Demo demo = new Demo();
		demo.run();
	}
	
	private String url1 = "http://localhost:8762/api/microservice1/method2?n=35";
	private String url2 = "http://localhost:8762/api/microservice2/method2?n=35";
	private String url3 = "http://localhost:8762/api/microservice3/method2?n=35";
	
	public void run() {
		startUsers(30, url1);
		startUsers(12, url2);
		startUsers(10, url3);
		while(true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			}
		}
		
	}
	
	private List<RequestLog> logs = new LinkedList<>();
	
	private synchronized void addLog(RequestLog log) {
		logs.add(log);
	}
	
	public void startUsers(int users, String url) {
		for (int i = 0; i < users; i++) {
			Thread user = new Thread(new User(url));
			user.setDaemon(true);
			user.start();
		}
	}
	
	class User implements Runnable {
		
		public User(String url) {
			this.url = url;
		}
		
		private String url;

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

}
