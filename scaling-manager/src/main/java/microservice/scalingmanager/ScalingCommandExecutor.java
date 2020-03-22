package microservice.scalingmanager;

import microservice.scalingmanager.cmdrepo.CommandsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScalingCommandExecutor {

    private Logger log = LoggerFactory.getLogger(ScalingCommandExecutor.class);

    private static final String PID_ID = "$pid$";

    @Autowired
    private CommandsRepository commandsRepository;

    public void scaleUp(String microserviceName) {
        String cmd = commandsRepository.getStartupCmd(microserviceName);
    }

    public void scaleDown(String microserviceName, long pid) {
        String cmd = commandsRepository.getShutdownCmd(microserviceName);
        cmd = cmd.replace(PID_ID, String.valueOf(pid));
    }

    protected void executeCmd(String cmd) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", cmd);
        try {
            Process process = processBuilder.start();
        } catch (IOException e) {
            log.warn("Could not start process", e);
        }

    }
}
