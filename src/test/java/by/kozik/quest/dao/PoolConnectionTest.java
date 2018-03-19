package by.kozik.quest.dao;

import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.watcher.Log4jTestWatcher;
import org.junit.*;
import org.junit.rules.TestWatcher;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serge on 30.01.2017.
 */
public class PoolConnectionTest extends Assert {
    private static ConnectionPool pool;
    private static final int NUM_CONNECTIONS = 20;
    private static final int NUM_WAIT = 100; //Milliseconds
    private static final String ADMIN_LOGIN = "root";
    private static final String ADMIN_PASSWORD = "1234";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/questionnaire3";

    @Rule
    public TestWatcher watcher = new Log4jTestWatcher();

    @BeforeClass
    public static void initPool() {
        ConnectionPool.startPool(NUM_CONNECTIONS,NUM_WAIT,DB_URL,ADMIN_LOGIN,ADMIN_PASSWORD);
        pool = ConnectionPool.getInstance();
    }

    @Test
    public void startedTest() {
        assertFalse("Pool is not started",pool==null);
    }

    @Test
    public void checkTakingConnections() {
        List<ConnectionUsable> connections = new ArrayList<>();
        boolean flag = false;
        String report="";
        int numErroneous=0;
        try {
            for (int i=0;i<NUM_CONNECTIONS; i++) {
                connections.add(pool.takeConnection());
                numErroneous++;
            }
        } catch (PoolConnectionException e) {
            report +="PoolConnectionException at "+String.valueOf(numErroneous)+": "+e.getMessage();
        } catch (SQLException e) {
            report +="SQLException at "+String.valueOf(numErroneous)+": "+e.getMessage();
        } finally {
            try {
                for (ConnectionUsable item:connections) {
                    numErroneous--;
                    item.close();
                }
            } catch (SQLException e) {
                report+="SQLException while closing at "+String.valueOf(numErroneous)+": "+e.getMessage();
            }
        }
        assertTrue(report,"".equals(report));
    }

    @Test
    public void checkAvailableMaxCount() {
        List<ConnectionUsable> connections = new ArrayList<>();
        boolean flag = false;
        String report="";
        int numErroneous=0;
        try {
            for (int i=0;i<NUM_CONNECTIONS; i++) {
                connections.add(pool.takeConnection());
                numErroneous++;
            }
        } catch (PoolConnectionException e) {
            report +="PoolConnectionException at "+String.valueOf(numErroneous)+": "+e.getMessage();
        } catch (SQLException e) {
            report +="SQLException at "+String.valueOf(numErroneous)+": "+e.getMessage();
        }
        assertTrue("Connections remained: "+String.valueOf(pool.getNumAvailable())+report,pool.getNumAvailable()==0);
        try {
            for (ConnectionUsable item:connections) {
                numErroneous--;
                item.close();
            }
        } catch (SQLException e) {
            report+="SQLException while closing at "+String.valueOf(numErroneous)+": "+e.getMessage();
            assertTrue(report,false);
        }
    }

    @Test
    public void checkReturnAllConections() {
        List<ConnectionUsable> connections = new ArrayList<>();
        boolean flag = false;
        String report="";
        int numErroneous=0;
        try {
            for (int i=0;i<NUM_CONNECTIONS; i++) {
                connections.add(pool.takeConnection());
                numErroneous++;
            }
        } catch (PoolConnectionException e) {
            report +="PoolConnectionException at "+String.valueOf(numErroneous)+": "+e.getMessage();
        } catch (SQLException e) {
            report +="SQLException at "+String.valueOf(numErroneous)+": "+e.getMessage();
        } finally {
            try {
                for (ConnectionUsable item:connections) {
                    numErroneous--;
                    item.close();
                }
            } catch (SQLException e) {
                report+="SQLException while closing at "+String.valueOf(numErroneous)+": "+e.getMessage();
            }
            assertTrue("Connections remained: "+String.valueOf(pool.getNumAvailable())+report,pool.getNumAvailable()==NUM_CONNECTIONS);
        }
    }

    @AfterClass
    public static void closePool() {
        ConnectionPool.getInstance().closeAllResources();
    }
}
