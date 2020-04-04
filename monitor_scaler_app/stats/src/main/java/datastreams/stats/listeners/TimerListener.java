package datastreams.stats.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @KafkaListener(topics = "timer", groupId = "monitor")
    public void listen(String message) {
        System.out.println("Received message " + message);
        try {
            ObjectMapper mapper = new ObjectMapper();
            TimerDTO timerDTO = mapper.readValue(message, TimerDTO.class);
            statsEngine.addTimer(timerDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
