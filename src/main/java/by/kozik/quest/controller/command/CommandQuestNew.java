package by.kozik.quest.controller.command;

import by.kozik.quest.entity.TextContentBean;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.service.LanguageEnum;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.QuestService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * Created by Serge on 05.01.2017.
 */
public class CommandQuestNew extends CommandDefault {
    public CommandQuestNew(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        List<String> languages = LanguageEnum.listTitlesNative();
        extractedSession.setAttribute("languages",languages);
        try {
            String language = null;
            Set<String> categories = QuestService.getInstance().showAllCategories(language);//if language is null then show all possible categories
            extractedSession.setAttribute("categories",categories);
            List<TextContentBean> typeBeans = QuestService.getInstance().listQuestTypeBeans(userLocale);
            extractedSession.setAttribute("type_beans",typeBeans);
        } catch (PoolConnectionException e) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.database-busy",userLocale));
        } catch (GetEntityException e) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.database-sql",userLocale)+e.getMessage());
        }
        currentLink ="/quest-title-create.page";
        return super.execute();
    }
}
