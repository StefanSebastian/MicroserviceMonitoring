package datastreams.stats.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;

import datastream.stats.model.Heartbeat;
import datastream.stats.repo.HeartbeatRepository;
import datastreams.stats.StatsEngine;
import datastreams.stats.kafkadtos.HeartbeatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author stefansebii@gmail.com
 */
@Component
public class HeartbeatListener {

    @Autowired
    private StatsEngine statsEngine;
    
    @Autowired
    private HeartbeatRepository heartbeatRepo;

    @KafkaListener(topics = "heartbeat", groupId="monitor")
    public void listen(String message) {
        System.out.println("Received message " + message);

        try {
            ObjectMapper mapper = new ObjectMapper();
            HeartbeatDTO heartbeatDTO = mapper.readValue(message, HeartbeatDTO.class);
            statsEngine.addHeartbeat(heartbeatDTO);
            storeHeartbeat(heartbeatDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void storeHeartbeat(HeartbeatDTO dto) {
    	try {
	    	Heartbeat heartbeat = new Heartbeat();
	    	heartbeat.setMicroserviceName(dto.getMicroserviceName());
	    	heartbeat.setPid(dto.getPid());
	    	heartbeat.setMachine(dto.getMachine());
	    	heartbeat.setTimestamp(dto.getTimestamp());
	    	heartbeatRepo.save(heartbeat);
    	} catch (Exception ex) {
    		System.out.println("Could not store heartbeat; " + ex.getMessage());
    	}
    }

}
