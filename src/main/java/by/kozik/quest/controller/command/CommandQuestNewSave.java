package by.kozik.quest.controller.command;

import by.kozik.quest.entity.QuestBean;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.exception.AlterEntityException;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.QuestService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Serge on 09.01.2017.
 */
public class CommandQuestNewSave extends CommandDefault {

    public CommandQuestNewSave(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        Object questObject = extractedSession.getAttribute("newQuest");
        if ((questObject!=null)&&(questObject instanceof QuestBean)) {
            QuestBean questBean = (QuestBean) questObject;
            try {
                QuestService.getInstance().saveQuest(questBean);
                extractedSession.setAttribute("newQuest",null);
            } catch (PoolConnectionException e1) {
                request.setAttribute("error_quest_message", ResourceReader.readMessageResource("message.label.error.database-busy",userLocale));
                return "/quest-save.page";
            } catch (AlterEntityException e2) {
                return executeExceptionPage(ResourceReader.readMessageResource("message.error.database-save",userLocale)+e2.getMessage());
            }
            return executeReport(ResourceReader.readMessageResource("message.label.report.quest-save-success",userLocale)+" "+questBean.getTitle());
        } else {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-quest-create",userLocale));
        }
    }
}
