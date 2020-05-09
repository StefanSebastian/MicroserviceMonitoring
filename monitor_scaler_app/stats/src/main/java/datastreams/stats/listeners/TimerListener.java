package datastreams.stats.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;

import datastream.stats.model.RequestLog;
import datastream.stats.repo.RequestLogRepository;
import datastreams.stats.StatsEngine;
import datastreams.stats.kafkadtos.TimerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author stefansebii@gmail.com
 */
@Component
public class TimerListener {

    @Autowired
    private StatsEngine statsEngine;
    
    @Autowired
    private RequestLogRepository requestRepo;

    @KafkaListener(topics = "timer", groupId = "monitor")
    public void listen(String message) {
        System.out.println("Received message " + message);
        try {
            ObjectMapper mapper = new ObjectMapper();
            TimerDTO timerDTO = mapper.readValue(message, TimerDTO.class);
            statsEngine.addTimer(timerDTO);
            storeRequest(timerDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void storeRequest(TimerDTO dto) {
    	try {
    		RequestLog req = new RequestLog();
    		req.setMicroserviceName(dto.getMicroserviceName());
    		req.setMachine(dto.getMachine());
    		req.setPid(dto.getPid());
    		req.setTimestamp(dto.getTimestamp());
    		req.setDuration(dto.getDuration());
    		req.setMethod(dto.getMethod());
    		requestRepo.save(req);
    	} catch (Exception ex) {
    		System.out.println("Could not store request; " + ex.getMessage());
    	}
    }
}
