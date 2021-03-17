package framework.utils;

import org.apache.log4j.PropertyConfigurator;

public class Logger {
    private final static String FILENAME = "src/main/java/test/resources/log4j.properties";
    private final static org.apache.log4j.Logger LOGGER =
            org.apache.log4j.Logger.getLogger(org.apache.log4j.Logger.class.getName());

    private Logger(){
    }
    public static void initLogger(){
        PropertyConfigurator.configure(FILENAME);
    }

    public static void info(String info){
        initLogger();
        LOGGER.info(info);
    }

    public static void logStep(int stepNumber, String info) {
        initLogger();
        LOGGER.info(String.format("--==step %d: %s==--", stepNumber, info));
    }
}
