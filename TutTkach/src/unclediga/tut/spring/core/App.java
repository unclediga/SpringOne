package unclediga.tut.spring.core;

import unclediga.tut.spring.beans.Client;
import unclediga.tut.spring.loggers.ConsoleEventLogger;

public class App {
    private Client client;
    private ConsoleEventLogger eventLogger;


    public void logEvent(String msg) {
        String message = msg.replaceAll(client.getId(), client.getFullName());
        eventLogger.logEvent(message);
    }

    public static void main(String[] args) {


        App app = new App();
        app.client = new Client("1","John Smith");
        app.eventLogger = new ConsoleEventLogger();
        app.logEvent("Some message for user 1");



    }

}
