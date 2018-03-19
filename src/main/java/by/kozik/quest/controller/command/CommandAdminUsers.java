package by.kozik.quest.controller.command;

import by.kozik.quest.entity.UserBean;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Serge on 26.01.2017.
 */
public class CommandAdminUsers extends CommandDefault {
    public CommandAdminUsers(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        List<UserBean> users;
        List<UserBean> usersBanned;
        try {
            users = UserService.getInstance().showAllActiveUsers();
            usersBanned = UserService.getInstance().showAllInactiveUsers();
        } catch (PoolConnectionException e) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.database-busy",userLocale));
        } catch (GetEntityException e) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.database-sql",userLocale)+e.getMessage());
        }
        extractedSession.setAttribute("user_list",users);
        extractedSession.setAttribute("user_list_bin",usersBanned);
        currentLink="/users-view.page";
        return super.execute();
    }
}
