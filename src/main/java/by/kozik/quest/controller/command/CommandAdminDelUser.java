package by.kozik.quest.controller.command;

import by.kozik.quest.dao.UserDao;
import by.kozik.quest.entity.UserBean;
import by.kozik.quest.exception.AlterEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.resource_handle.ResourceReader;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Serge on 27.01.2017.
 */
public class CommandAdminDelUser extends CommandDefault {
    public CommandAdminDelUser(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        Object userIdObject = extractedSession.getAttribute("user_id_delete");
        String errorMessage = null;
        if (userIdObject!=null) {
            int userId = Integer.parseInt(String.valueOf(userIdObject));
            extractedSession.setAttribute("user_id_delete",null);
            Object userObject = extractedSession.getAttribute("user");
            UserBean user = (UserBean) userObject;
            if (user.getId()==userId) {
                errorMessage = ResourceReader.readMessageResource("message.label.error.user-selfdelete",userLocale);
            } else{
                try {
                    UserDao.getInstance().ivalidateUser(userId);
                } catch (PoolConnectionException e) {
                    errorMessage = ResourceReader.readMessageResource("message.label.error.database-busy",userLocale);
                } catch (AlterEntityException e) {
                    errorMessage = e.getMessage();
                }
            }
        } else {
            errorMessage = ResourceReader.readMessageResource("message.label.error.user-id",userLocale);
        }
        if (errorMessage==null) {
            currentLink="/admin/users.html";
        } else {
            request.setAttribute("error_admin_message",errorMessage);
            currentLink="/users-view.page";
        }
        return super.execute();
    }
}
