package microservice.scalingmanager.cmdrepo;

public interface CommandsRepository {
    String getStartupCmd(String microserviceName);

    String getShutdownCmd(String microserviceName);
}
