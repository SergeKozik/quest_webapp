package by.kozik.quest.controller.command;

import by.kozik.quest.entity.UserQuestResultView;
import by.kozik.quest.resource_handle.ResourceReader;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Serge on 25.01.2017.
 */
public class CommandAuthorUserStatistics extends CommandDefault {
    public CommandAuthorUserStatistics(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        Object usersResultsObject = extractedSession.getAttribute("users_results");
        List<UserQuestResultView> usersResults;
        if (usersResultsObject!=null) {
            usersResults = (List<UserQuestResultView>)usersResultsObject;
        } else {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.users-results-notfound",userLocale));
        }
        String resultNumberString = request.getParameter("result_id");
        int resultNumber = 0;
        if (resultNumberString!=null) {
            resultNumber = Integer.parseInt(resultNumberString);
        } else {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.users-results-id",userLocale));
        }
        UserQuestResultView resultView = null;
        for (UserQuestResultView view:usersResults) {
            if (view.getId()==resultNumber) {
                resultView = view;
            }
        }
        extractedSession.setAttribute("users_result",resultView);
        currentLink = "/user-quest-statistics.page";
        return super.execute();
    }
}
