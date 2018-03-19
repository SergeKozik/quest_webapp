package by.kozik.quest.listener;

import by.kozik.quest.dao.ConnectionPool;
import by.kozik.quest.dao.QuestDao;
import by.kozik.quest.dao.UserDao;
import by.kozik.quest.exception.AlterEntityException;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.service.QuestService;
import by.kozik.quest.service.UserService;
import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Serge on 20.12.2016.
 */
public class ServletContextListenerImpl implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String dbAdmin = servletContext.getInitParameter("db_admin_login");
        String dbPassword = servletContext.getInitParameter("db_admin_password");
        String dbUrl = servletContext.getInitParameter("database_url");
        String connectionNum = servletContext.getInitParameter("number_connections");
        String connectionWait = servletContext.getInitParameter("wait_connection_millisec");
        ConnectionPool.startPool(Integer.parseInt(connectionNum),Integer.parseInt(connectionWait),dbUrl,dbAdmin,dbPassword);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance().closeAllResources();
    }
}
