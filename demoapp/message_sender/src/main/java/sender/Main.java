package sender;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        String url = args[0];
        Integer iterations = Integer.valueOf(args[1]);
        if (args.length == 3) {
        	Integer fib_n = Integer.valueOf(args[2]);
        	url = url + "?n=" + fib_n;
        }
        
        for (int i = 0; i < iterations; i++) {
            try {
                simulateClient(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void simulateClient(String url) throws Exception {
    	new Thread(() -> {
        	try {
        		long start = System.currentTimeMillis();
				sendMessage(url);
				long elapsed = System.currentTimeMillis() - start;
				System.out.println(Thread.currentThread().getName() + " Request time : " + elapsed);
			} catch (Exception e) {
				System.out.println("Couldn't send message; " + e.getMessage());
			}
    	}).start();
    }

    public static void sendMessage(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

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
