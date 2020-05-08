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
	
	@Value("${stats.sla.threshold}")
	private long slaThreshold;
	
	@Autowired
    private ScalingCommandExecutor scalingCommandExecutor;
	
	@Scheduled(fixedDelay=120000)
	public void checkSLA() {
		System.out.println("Sending get sla stats message to : " + slaUrl);
		try {
			URL url = new URL(slaUrl);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			String response = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
			System.out.println("Got response: " + response);

			Gson gson = new Gson();
			SLAStats[] statsList = gson.fromJson(response, SLAStats[].class);
			checkSLA(statsList);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void checkSLA(SLAStats[] statsList) {
		for (SLAStats slaStats : statsList) {
			if (slaStats.getNinetyPercentileResponseTime() > slaThreshold) {
				System.out.println("Threshold reached: " + slaThreshold + " for " + slaStats);
				scalingCommandExecutor.scaleUp(slaStats.getService());
			}
		}
	}


}
