package microservice.scalingmanager.cmdrepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileCommandsRepository implements CommandsRepository {

    private Logger log = LoggerFactory.getLogger(FileCommandsRepository.class);

    private static final String startupCmdsFile = "startup_commands_docker.txt";
    private static final String shutdownCmdsFile = "shutdown_commands.txt";
    private static final String delimiter = ":";

    private Map<String, String> startupCmds = new HashMap<>();
    private Map<String, String> shutdownCmds = new HashMap<>();

    public String getStartupCmd(String microserviceName) {
        return startupCmds.get(microserviceName);
    }

    public String getShutdownCmd(String microserviceName) {
        return shutdownCmds.get(microserviceName);
    }

    @EventListener(ApplicationReadyEvent.class)
    private void initCommands() {
        File startupCmdFile = getFileFromResources(startupCmdsFile);
        startupCmds = readFileIntoMap(startupCmdFile);

        File shutdownCmdFile = getFileFromResources(shutdownCmdsFile);
        shutdownCmds = readFileIntoMap(shutdownCmdFile);

        log.info(startupCmds.toString());
        log.info(shutdownCmds.toString());
    }

    private File getFileFromResources(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("File not found!");
        }
        return new File(resource.getFile());
    }

    private Map<String, String> readFileIntoMap(File file) {
        Map<String, String> map = new HashMap<>();
        try(FileReader reader = new FileReader(file)) {
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(delimiter, 2);
                if (parts.length >= 2) {
                    map.put(parts[0], parts[1]);
                } else {
                    log.info("Ignoring line : " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize control commands");
        }
        return map;
    }
}
