package com.dim.server;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.dim.server.net.Server;
/**
 * Hello world!
 *
 */
public class Start {

    private static final Logger logger = LogManager.getLogger(Start.class);

    public static void main(String[] args) {
        try {
            logger.info("server will start at "+9600);
            new Server(9600).start();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
