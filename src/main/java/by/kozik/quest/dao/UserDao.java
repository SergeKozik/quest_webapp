package by.kozik.quest.dao;


import by.kozik.quest.entity.UserBean;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.exception.AlterEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serge on 06.12.2016.
 */
public class UserDao {
    private static final String SQL_USER_NAME_COUNT = "SELECT COUNT(login) FROM user WHERE login=?";
    private static final String SQL_USER_FIND = "SELECT id,login,role,email FROM user WHERE login=? AND password=? AND deprecated=0";
    private static final String SQL_USER_FIND_ID = "SELECT login,role,email FROM user WHERE id=?";
    private static final String SQL_USER_REGISTER = "INSERT INTO user(login,password,role,email,deprecated) VALUES (?,?,?,?,0)";
    private static final String SQL_USER_EDIT = "UPDATE user SET password=?, role=?, email=? WHERE login=?";
    private static final String SQL_USER_ALL_ACTIVE = "SELECT id,login,role,email,password FROM user WHERE deprecated=0";
    private static final String SQL_USER_ALL_INACTIVE = "SELECT id,login,role,email FROM user WHERE deprecated=1";
    private static final String SQL_USER_SET_INACTIVE = "UPDATE user SET deprecated=1 WHERE id=?";
    private static final String SQL_USER_CHECK_INACTIVE = "SELECT login FROM user WHERE deprecated=1 AND id=?";
    private static final String SQL_ANSWERS_DELETE = "DELETE user_answer,user_answer_text FROM user_answer LEFT JOIN " +
            "user_answer_text ON user_answer.id=user_answer_text.user_answer_id WHERE user_answer.user_main_result_id IN" +
            "(SELECT id FROM user_main_result WHERE user_main_result.user_id=%d)";
    private static final String SQL_USERS_DELETE = "DELETE user, user_main_result FROM user LEFT JOIN user_main_result ON user.id=user_main_result.user_id  WHERE user.id=%d";
    private static UserDao instance = new UserDao();
    private static Logger logger = LogManager.getLogger();

    private UserDao() {
    }

    public static UserDao getInstance() {
        return instance;
    }

    public boolean isUserExist(String nick) throws PoolConnectionException,GetEntityException {
        PreparedStatement statement=null;
        boolean result = false;
        ConnectionUsable connection=null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_USER_NAME_COUNT);
            statement.setString(1,nick);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int userCount = resultSet.getInt(1);
                if (userCount>0) {
                    result = true;
                }
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public UserBean findUser (String login, String password) throws PoolConnectionException,GetEntityException {
        PreparedStatement statement=null;
        UserBean result = null;
        ConnectionUsable connection=null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_USER_FIND);
            statement.setString(1,login);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = new UserBean();
                result.setNick(resultSet.getString("login"));
                result.setEmail(resultSet.getString("email"));
                result.setRole(resultSet.getInt("role"));
                result.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public UserBean findUserById (int userId) throws PoolConnectionException,GetEntityException {
        PreparedStatement statement=null;
        UserBean result = null;
        ConnectionUsable connection=null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_USER_FIND_ID);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = new UserBean();
                result.setNick(resultSet.getString("login"));
                result.setEmail(resultSet.getString("email"));
                result.setRole(resultSet.getInt("role"));
                result.setId(userId);
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public UserBean register (String nick, String password, String email, int role) throws PoolConnectionException, AlterEntityException {
        PreparedStatement statement=null;
        UserBean result = null;
        ConnectionUsable connection=null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(SQL_USER_NAME_COUNT);
            statement.setString(1,nick);
            ResultSet resultSet = statement.executeQuery();
            int userCount = 0;
            if (resultSet.next()) {
                userCount = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
            if (userCount==0) {
                statement = connection.prepareStatement(SQL_USER_REGISTER);
                statement.setString(1,nick);
                statement.setString(2,password);
                statement.setInt(3,role);
                statement.setString(4,email);
                statement.executeUpdate();
                statement.close();
                statement = connection.prepareStatement(SQL_USER_FIND);
                statement.setString(1,nick);
                statement.setString(2,password);
                ResultSet resultSet2 = statement.executeQuery();
                if (resultSet2.next()) {
                    result = new UserBean();
                    result.setNick(resultSet2.getString("login"));
                    result.setEmail(resultSet2.getString("email"));
                    result.setRole(resultSet2.getInt("role"));
                    result.setId(resultSet2.getInt("id"));
                }
            }
        } catch (SQLException e) {
            throw new AlterEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public void editUser(UserBean user) throws AlterEntityException, PoolConnectionException {
        if (user==null) {
            return;
        }
        PreparedStatement statement=null;
        UserBean result = null;
        ConnectionUsable connection=null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(SQL_USER_EDIT);
            statement.setString(1, user.getPassword());
            statement.setInt(2,user.getRole());
            statement.setString(3,user.getEmail());
            statement.setString(4,user.getNick());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new AlterEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
    }

    public List<UserBean> returnAllActive() throws PoolConnectionException, GetEntityException {
        PreparedStatement statement=null;
        List<UserBean> result = null;
        ConnectionUsable connection=null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_USER_ALL_ACTIVE);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                result = new ArrayList<>();
                while (resultSet.next()) {
                    UserBean tmpUser = new UserBean();
                    tmpUser.setNick(resultSet.getString("login"));
                    tmpUser.setEmail(resultSet.getString("email"));
                    tmpUser.setRole(resultSet.getInt("role"));
                    tmpUser.setId(resultSet.getInt("id"));
                    tmpUser.setPassword(resultSet.getString("password"));
                    result.add(tmpUser);
                }
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public List<UserBean> returnAllInactive() throws PoolConnectionException, GetEntityException {
        PreparedStatement statement=null;
        List<UserBean> result = null;
        ConnectionUsable connection=null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_USER_ALL_INACTIVE);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                result = new ArrayList<>();
                while (resultSet.next()) {
                    UserBean tmpUser = new UserBean();
                    tmpUser.setNick(resultSet.getString("login"));
                    tmpUser.setEmail(resultSet.getString("email"));
                    tmpUser.setRole(resultSet.getInt("role"));
                    tmpUser.setId(resultSet.getInt("id"));
                    result.add(tmpUser);
                }
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public void ivalidateUser(int userId) throws PoolConnectionException,AlterEntityException {
        PreparedStatement statement=null;
        ConnectionUsable connection=null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_USER_FIND_ID);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new AlterEntityException("User with id="+userId+" do not exist.");
            }
            resultSet.close();
            statement.close();
            statement = connection.prepareStatement(SQL_USER_SET_INACTIVE);
            statement.setInt(1,userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new AlterEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
    }

    public void eliminateUsers(List<UserBean> users) throws PoolConnectionException, AlterEntityException {
        if (users==null) {
            return;
        }
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ConnectionUsable connection=null;
        try {
            List<UserBean> finalUsers = new ArrayList<>();
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_USER_CHECK_INACTIVE);
            for (UserBean user : users) {
                preparedStatement.setInt(1, user.getId());
                ResultSet tmpRS = preparedStatement.executeQuery();
                if (tmpRS.next()) {
                    finalUsers.add(user);
                }
                tmpRS.close();
            }
            preparedStatement.close();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
            for (UserBean user:finalUsers) {
                statement.addBatch(String.format(SQL_ANSWERS_DELETE,user.getId()));
                statement.addBatch(String.format(SQL_USERS_DELETE,user.getId()));
            }
            statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new AlterEntityException(e);
        } finally {
            try{
                connection.rollback();
            } catch (SQLException e) {
                logger.error("Error rollback eliminateUsers.");
            }
            connection.closeResources(statement);
        }
    }

    public void eliminateUser(int userId) throws PoolConnectionException, AlterEntityException {
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ConnectionUsable connection=null;
        try {
            int finalUserId=0;
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_USER_CHECK_INACTIVE);
            preparedStatement.setInt(1, userId);
            ResultSet tmpRS = preparedStatement.executeQuery();
            if (tmpRS.next()) {
                finalUserId=userId;
            }
            tmpRS.close();
            preparedStatement.close();
            if (finalUserId==0) {
                return;
            }
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
            statement.addBatch(String.format(SQL_ANSWERS_DELETE,finalUserId));
            statement.addBatch(String.format(SQL_USERS_DELETE,finalUserId));
            statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch (SQLException e2) {
                logger.error("Error rollback eliminateUsers."+e2.getMessage());
            }
            throw new AlterEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
    }
}
