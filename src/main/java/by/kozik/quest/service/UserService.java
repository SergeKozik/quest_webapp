package by.kozik.quest.service;

import by.kozik.quest.dao.UserDao;
import by.kozik.quest.entity.UserBean;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.exception.AlterEntityException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Serge on 06.12.2016.
 */
public class UserService {

    private static UserService instance = new UserService();

    private String codingSHA512(String passwordToHash, String   salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException|UnsupportedEncodingException e){ //JDK 7
            generatedPassword = passwordToHash;
        }
        return generatedPassword;
    }

    private UserService() {
    }

    public static UserService getInstance() {
        return instance;
    }

    public UserBean loginUser(UserBean inputData) throws PoolConnectionException, GetEntityException {
        UserDao dao = UserDao.getInstance();
        if (dao!=null) {
            UserBean user = dao.findUser(inputData.getNick(),codingSHA512(inputData.getPassword(),""));
            //UserBean user = dao.findUser(inputData.getNick(),inputData.getPassword());
            return user;
        }
        return null;
    }

    public UserBean registerUser(UserBean inputData) throws PoolConnectionException, AlterEntityException {
        UserDao dao = UserDao.getInstance();
        if (dao==null) {
            return null;
        } else {
            UserBean user = dao.register(inputData.getNick(),codingSHA512(inputData.getPassword(),""),inputData.getEmail(),inputData.getRole());
            //UserBean user = dao.register(inputData.getNick(),inputData.getPassword(),inputData.getEmail(),inputData.getRole());
            return user;
        }
    }

    public List<UserBean> showAllActiveUsers() throws PoolConnectionException, GetEntityException {
        UserDao dao = UserDao.getInstance();
        if (dao==null) {
            return null;
        } else {
            return dao.returnAllActive();
        }
    }

    public List<UserBean> showAllInactiveUsers() throws PoolConnectionException, GetEntityException {
        UserDao dao = UserDao.getInstance();
        if (dao==null) {
            return null;
        } else {
            return dao.returnAllInactive();
        }
    }

    public void ivalidateUser(int userId) throws PoolConnectionException,AlterEntityException {
        UserDao dao = UserDao.getInstance();
        if (dao!=null) {
            dao.ivalidateUser(userId);
        } else {
            throw new PoolConnectionException("Null user DAO.");
        }
    }

    public void eliminateUsers(List<UserBean> users) throws PoolConnectionException, AlterEntityException {
        UserDao dao = UserDao.getInstance();
        if (dao!=null) {
            dao.eliminateUsers(users);
        } else {
            throw new PoolConnectionException("Null user DAO.");
        }
    }

    public void eliminateUser(int userId) throws PoolConnectionException, AlterEntityException {
        UserDao dao = UserDao.getInstance();
        if (dao!=null) {
            dao.eliminateUser(userId);
        } else {
            throw new PoolConnectionException("Null user DAO.");
        }
    }

}
