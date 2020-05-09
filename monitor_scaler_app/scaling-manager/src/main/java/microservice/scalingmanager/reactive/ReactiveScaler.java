package microservice.scalingmanager.reactive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import microservice.scalingmanager.ScalingCommandExecutor;

@Service
@EnableScheduling
public class ReactiveScaler {
	
	@Value("${stats.sla.url}")
	private String slaUrl;
	
	@Value("${stats.services.url}")
	private String serviceUrl;
	
	@Value("${stats.sla.threshold}")
	private long slaThreshold;
	
	@Value("${stats.sla.lowerbound}")
	private long slaLowerbound;
	
	@Autowired
    private ScalingCommandExecutor scalingCommandExecutor;
	
	@Scheduled(fixedDelay=60000)
	public void checkSLA() {
		System.out.println("Sending get sla stats message to : " + slaUrl);
		String response = getResource(slaUrl);
		if (response == null) {
			return;
		}

		Gson gson = new Gson();
		SLAStats[] statsList = gson.fromJson(response, SLAStats[].class);
		checkSLA(statsList);
	}
	
	private String getResource(String urlStr) {
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			String response = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
			System.out.println("Got response: " + response);
		return response;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void checkSLA(SLAStats[] statsList) {
		for (SLAStats slaStats : statsList) {
			if (slaStats.getNinetyPercentileResponseTime() > slaThreshold) {
				System.out.println("Threshold reached: " + slaThreshold + " for " + slaStats);
				scalingCommandExecutor.scaleUp(slaStats.getService());
			} else if (slaStats.getNinetyPercentileResponseTime() < slaLowerbound) {
				System.out.println("Lower bound reached: " + slaLowerbound + " for " + slaStats);
				handleScaleDown(slaStats);
			}
		}
	}
	
	private void handleScaleDown(SLAStats slaStats) {
		String response = getResource(serviceUrl);
		Gson gson = new Gson();
		OnlineService[] services = gson.fromJson(response, OnlineService[].class);
		
		// get a random service of given type
		OnlineService scaledServ = null;
		int counter = 0;
		for (OnlineService service : services) {
			if (service.getName().equals(slaStats.getService())) {
				scaledServ = service;
				counter += 1;
			}
		}
		
		if (counter > 1) { // keep at least one service after scale down
			scalingCommandExecutor.scaleDown(scaledServ.getName(), scaledServ.getPid());
		}
	}


}
