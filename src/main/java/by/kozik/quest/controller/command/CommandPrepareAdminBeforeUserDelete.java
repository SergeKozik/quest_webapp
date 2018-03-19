package by.kozik.quest.controller.command;

import by.kozik.quest.entity.UserBean;
import by.kozik.quest.resource_handle.ResourceReader;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Serge on 27.01.2017.
 */
public class CommandPrepareAdminBeforeUserDelete extends CommandDefault {
    public CommandPrepareAdminBeforeUserDelete(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        UserBean userDelete = null;
        String userIdString = request.getParameter("user_id");
        int userId=0;
        if (userIdString!=null) {
            userId = Integer.parseInt(userIdString);
        }
        Object listUsersObject = extractedSession.getAttribute("user_list");
        if ((listUsersObject!=null)&&(userId>0)) {
            List<UserBean> listUsers = (List<UserBean>)listUsersObject;
            for (UserBean user:listUsers) {
                if (user.getId()==userId) {
                    userDelete=user;
                    break;
                }
            }
        }
        if (userDelete!=null) {
            extractedSession.setAttribute("user_id_delete",userIdString);
            return executeConfirm(ResourceReader.readMessageResource("message.text.ask.user-delete",userLocale)+" "+ userDelete.getNick(),"/users-view.page","/admin/user-id-delete.html");
        } else {
            String errorMessage = ResourceReader.readMessageResource("message.label.error.user-id",userLocale);
            request.setAttribute("error_admin_message",errorMessage);
            currentLink = "/users-view.page";
        }
        return super.execute();
    }
}
