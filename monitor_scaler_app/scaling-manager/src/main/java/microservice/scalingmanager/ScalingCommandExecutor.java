package microservice.scalingmanager;

import microservice.scalingmanager.cmdrepo.CommandsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;

@Component
public class ScalingCommandExecutor {

    private Logger log = LoggerFactory.getLogger(ScalingCommandExecutor.class);

    private static final String PID_ID = "$pid$";
    private static final String PORT_ID = "$port$";

    @Autowired
    private CommandsRepository commandsRepository;

    public void scaleUp(String microserviceName) {
        String cmd = commandsRepository.getStartupCmd(microserviceName);
        cmd = cmd.replace(PORT_ID, String.valueOf(findFreePort()));
        executeCmd(cmd);
    }

    private long findFreePort() {
        try {
            ServerSocket socket = new ServerSocket(0);
            int port = socket.getLocalPort();
            socket.close();
            return port;
        } catch (IOException e) {
            throw new RuntimeException("No available ports");
        }
    }


    public void scaleDown(String microserviceName, long pid) {
        String cmd = commandsRepository.getShutdownCmd(microserviceName);
        cmd = cmd.replace(PID_ID, String.valueOf(pid));
        executeCmd(cmd);
    }

    private void executeCmd(String cmd) {
        new Thread(() -> {
            try {
                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", cmd);
                pb.redirectErrorStream(true);
                Process proc = pb.start();

                proc.getInputStream().close();
                proc.getOutputStream().close();
            } catch (IOException e) {
                log.warn("Could not start process", e);
            }
        }).start();
    }
}
