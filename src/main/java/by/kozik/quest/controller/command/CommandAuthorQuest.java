package by.kozik.quest.controller.command;

import by.kozik.quest.entity.QuestBeanJS;
import by.kozik.quest.entity.TextContentBean;
import by.kozik.quest.entity.UserBean;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.QuestResultTypeEnum;
import by.kozik.quest.service.QuestService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Serge on 24.01.2017.
 */
public class CommandAuthorQuest extends CommandDefault {

    public CommandAuthorQuest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        QuestService questService = QuestService.getInstance();
        List<QuestBeanJS> quests = null;
        Object userObject = extractedSession.getAttribute("user");
        try{
            UserBean user = null;
            if ((userObject!=null)&&(userObject instanceof UserBean)) {
                user = (UserBean)userObject;
            }
            quests = questService.showQuestByTitle(user,userLocale);
            if (quests!=null) {
                for (QuestBeanJS questJS:quests) {
                    questJS.setActionList(returnQuestActions(user,questJS));
                }
            }
        } catch (PoolConnectionException e) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.database-busy",userLocale));
        } catch (GetEntityException e) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.database-sql",userLocale)+e.getMessage());
        }
        extractedSession.setAttribute("quests",quests);
        if ((quests!=null)&&(!quests.isEmpty())) {
            Set<String> languages = questService.showAllLangugages(quests);
            Set<String> types = questService.showAllTypes(quests);
            List<TextContentBean> typesBeans = new ArrayList<>();
            for (String typeName:types) {
                typesBeans.add(new TextContentBean(typeName, ResourceReader.readMessageResource(QuestResultTypeEnum.getTypeByName(typeName).getNameNativeCode(),userLocale)));
            }
            Set<String> categories = questService.showAllCategories(quests);
            extractedSession.setAttribute("languages",languages);
            extractedSession.setAttribute("types",typesBeans);
            extractedSession.setAttribute("categories",categories);
        }
        currentLink = "/quest-list.page";
        return super.execute();
    }
}
