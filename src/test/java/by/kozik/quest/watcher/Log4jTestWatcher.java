package by.kozik.quest.watcher;

/**
 * Created by Roldo on 11.10.16.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.TestWatcher;


/**
 * Created by Roldo on 05.10.16.
 */
public class Log4jTestWatcher extends TestWatcher {
    private static Logger logger;

    public Log4jTestWatcher() {
        logger = LogManager.getLogger();
    }

    public Log4jTestWatcher(String loggerName) {
        logger = LogManager.getLogger(loggerName);
    }

    @Override
    protected void failed(Throwable e, org.junit.runner.Description description) {
        logger.error(description, e);
    }

    @Override
    protected void succeeded(org.junit.runner.Description description) {
        logger.info(description);
    }
}