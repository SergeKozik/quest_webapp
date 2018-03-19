package by.kozik.quest.controller.command;

import by.kozik.quest.controller.validation.AbstractValidator;
import by.kozik.quest.controller.validation.RegisterValidator;
import by.kozik.quest.entity.UserBean;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.exception.AlterEntityException;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.RoleEnum;
import by.kozik.quest.service.UserService;
import by.kozik.quest.utility.UtilString;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Serge on 26.01.2017.
 */
public class CommandAdminAddUser extends CommandDefault {
    public CommandAdminAddUser(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        String errorMessage=null;
        String nick = request.getParameter("user_name");
        String password = request.getParameter("passw");
        String password2 = request.getParameter("passw_double");
        String email = request.getParameter("email");
        String rolePriorityString = request.getParameter("select_role");
        AbstractValidator validator = new RegisterValidator(nick,password,password2,email,rolePriorityString);
        if (validator.validate(userLocale)) {
            UserService userService = UserService.getInstance();
            UserBean inputData = new UserBean();
            if (!(UtilString.isNullEmpty(email))) {
                inputData.setEmail(email);
            }
            inputData.setPassword(password);
            inputData.setNick(nick);
            int rolePriority = Integer.parseInt(rolePriorityString);
            if (rolePriority<= RoleEnum.ADMIN.getPriority()) {
                inputData.setRole(rolePriority);
                try {
                    UserBean user = userService.registerUser(inputData);
                    if (user==null) {
                        errorMessage = ResourceReader.readMessageResource("message.label.error.register-fault",userLocale);
                    }
                } catch (PoolConnectionException e) {
                    errorMessage = ResourceReader.readMessageResource("message.label.error.database-busy",userLocale);
                } catch (AlterEntityException e) {
                    errorMessage = ResourceReader.readMessageResource("message.label.error.database-sql",userLocale)+e.getMessage();
                }
            } else {
                errorMessage = ResourceReader.readMessageResource("message.label.error.role-notallowed",userLocale);
            }
        } else {
            errorMessage = validator.getErrorReport();
        }
        if (errorMessage!=null) {
            currentLink = "/register.page";
            request.setAttribute("error_register_message",errorMessage);
        } else {
            currentLink = "/admin/users.html";
        }
        String result = super.execute();
        return result;
    }
}
