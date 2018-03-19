package by.kozik.quest.controller.command;

import by.kozik.quest.entity.QuestBean;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.QuestService;
import by.kozik.quest.utility.UtilString;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Serge on 14.01.2017.
 */
public class CommandQuestBeforeStart extends CommandDefault {

    public CommandQuestBeforeStart(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        String stringId = request.getParameter("quest_id");
        if (UtilString.isNullEmpty(stringId)) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-id",userLocale));
        }
        int questId=-1;
        try {
            questId = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-id",userLocale)+": "+e.getMessage());
        }
        Object currentQuestionsObject = extractedSession.getAttribute("currentQuestionsIterator");
        if (currentQuestionsObject!=null) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-nonfinished",userLocale));
        }
        QuestBean currentQuest = null;
        try {
            currentQuest = QuestService.getInstance().findQuestById(questId);
        } catch (PoolConnectionException e1) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.database-busy",userLocale));
        } catch (GetEntityException e2) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-id-database",userLocale));
        }
        if (currentQuest == null) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.database-sql",userLocale));
        }
        extractedSession.setAttribute("startQuest",currentQuest);
        currentLink = "/quest-before-start.page";
        return super.execute();
    }
}
