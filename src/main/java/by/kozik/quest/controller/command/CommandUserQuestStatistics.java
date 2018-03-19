package by.kozik.quest.controller.command;

import by.kozik.quest.entity.UserBean;
import by.kozik.quest.entity.UserQuestResultView;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.StatisticsService;
import by.kozik.quest.utility.UtilString;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Serge on 26.01.2017.
 */
public class CommandUserQuestStatistics extends CommandDefault {
    public CommandUserQuestStatistics(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        String stringId = request.getParameter("quest_id");
        if (UtilString.isNullEmpty(stringId)) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-id",userLocale));
        }
        int questId;
        try {
            questId = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-id",userLocale)+": "+e.getMessage());
        }
        List<UserQuestResultView> questResults = null;
        try {
            Object userObject = extractedSession.getAttribute("user");
            UserBean user = null;
            if ((userObject!=null)&&(userObject instanceof UserBean)) {
                user = (UserBean)userObject;
            }
            questResults = StatisticsService.getInstance().viewUserResultByDate(questId,user);
        } catch (PoolConnectionException e) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.database-busy",userLocale));
        } catch (GetEntityException e) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.database-sql",userLocale)+e.getMessage());
        }
        extractedSession.setAttribute("users_results",questResults);
        currentLink = "/quest-statistics.page";
        return super.execute();
    }
}
