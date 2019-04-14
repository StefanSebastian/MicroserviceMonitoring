package com.example.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

@Configuration
@ComponentScan(basePackages = "com.example.client")
public class TaskExecutor {

    @Value("${kafka.topic.name}")
    private String topicName;

    @Value("${spring.application.name}")
    private String appName;

    private long pid;
    private String machine;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public TaskExecutor(){
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();

        String jvmName = bean.getName();
        pid = Long.valueOf(jvmName.split("@")[0]);
        machine = jvmName.split("@")[1];
    }
	
	@Scheduled(fixedRate = 2000)
    public void heartbeat() {
	    long timestamp = System.currentTimeMillis();
        HeartbeatDTO heartbeatDTO = new HeartbeatDTO();
        heartbeatDTO.setMachine(machine);
        heartbeatDTO.setPid(pid);
        heartbeatDTO.setMicroserviceName(appName);
        heartbeatDTO.setTimestamp(timestamp);

        System.out.println(heartbeatDTO);
        kafkaTemplate.send(topicName, heartbeatDTO.toString());
    }

}
