package by.kozik.quest.controller.command;

import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.controller.validation.AbstractValidator;
import by.kozik.quest.controller.validation.LoginValidator;
import by.kozik.quest.entity.UserBean;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.service.UserService;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Serge on 27.11.16.
 */
public class CommandLogin extends CommandDefault {


    public CommandLogin(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        String errorMessage=null;
        String nick = request.getParameter("user_name");
        String password = request.getParameter("passw");
        AbstractValidator validator = new LoginValidator(nick,password);
        if (validator.validate(userLocale)) {
            UserService userService = UserService.getInstance();
            UserBean inputData = new UserBean();
            inputData.setNick(nick);
            inputData.setPassword(password);
            try {
                UserBean user = userService.loginUser(inputData);
                if (user==null) {
                    errorMessage = ResourceReader.readMessageResource("message.label.error.login-fault",userLocale);
                } else {
                    extractedSession.setAttribute("user",user);
                }
            } catch (PoolConnectionException e) {
                errorMessage = ResourceReader.readMessageResource("message.label.error.database-busy",userLocale);
            } catch (GetEntityException e) {
                errorMessage = ResourceReader.readMessageResource("message.label.error.database-sql",userLocale)+e.getMessage();
            }
        } else {
            errorMessage = validator.getErrorReport();
        }
        if (errorMessage!=null) {
            currentLink = "/login.page";
            request.setAttribute("error_login_message",errorMessage);
        } else {
            currentLink = "/main.page";
        }
        String result = super.execute();
        return result;
    }
}