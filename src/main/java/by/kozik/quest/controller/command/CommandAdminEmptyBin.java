package by.kozik.quest.controller.command;

import by.kozik.quest.exception.AlterEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

/**
 * Created by Serge on 27.01.2017.
 */
public class CommandAdminEmptyBin extends CommandDefault {
    public CommandAdminEmptyBin(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        String[] usersIdString = request.getParameterValues("user_id");
        String errorMessage = null;
        if (usersIdString!=null) {
            for (String idString:usersIdString) {
                int userId = Integer.parseInt(idString);
                try {
                    UserService.getInstance().eliminateUser(userId);
                } catch (AlterEntityException|PoolConnectionException e) {
                    errorMessage+= ResourceReader.readMessageResource("message.label.error.user-delete",userLocale)+e.getMessage()+"\n";
                }
            }
        }
        request.setAttribute("error_admin_message",errorMessage);
        currentLink="/admin/users.html";
        return super.execute();
    }
}
