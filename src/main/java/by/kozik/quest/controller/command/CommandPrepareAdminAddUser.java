package by.kozik.quest.controller.command;

import by.kozik.quest.entity.TextContentBean;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.RoleEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serge on 26.01.2017.
 */
public class CommandPrepareAdminAddUser extends CommandDefault {
    public CommandPrepareAdminAddUser(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        List<TextContentBean> roleBeans = new ArrayList<>();
        for (RoleEnum role:RoleEnum.values()) {
            if ((role.getPriority()<=RoleEnum.ADMIN.getPriority())&&(role.getPriority()>RoleEnum.ANONYMOUS.getPriority())) {
                roleBeans.add(new TextContentBean(String.valueOf(role.getPriority()), ResourceReader.readMessageResource(role.getMessageKey(),userLocale)));
            }
        }
        extractedSession.setAttribute("roles",roleBeans);
        extractedSession.setAttribute("action_link","/admin/register-user.html");
        currentLink="/register.page";
        return super.execute();
    }
}
