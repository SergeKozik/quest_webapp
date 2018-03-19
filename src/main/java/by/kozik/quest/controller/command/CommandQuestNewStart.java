package by.kozik.quest.controller.command;

import by.kozik.quest.controller.validation.AbstractValidator;
import by.kozik.quest.controller.validation.NewQuestValidator;
import by.kozik.quest.entity.QuestBean;
import by.kozik.quest.entity.QuestionBean;
import by.kozik.quest.entity.UserBean;
import by.kozik.quest.service.QuestResultTypeEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Serge on 05.01.2017.
 */
public class CommandQuestNewStart extends CommandDefault {

    public CommandQuestNewStart(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        String language = request.getParameter("quest_lang");
        String checkOwnCategory = request.getParameter("own_categ");
        String category = null;
        if (checkOwnCategory!=null) {
            category = request.getParameter("own_categ_text");
        } else {
            category = request.getParameter("quest_categ");
        }
        String title = request.getParameter("quest_title");
        String description = request.getParameter("quest_descr");
        String typeString = request.getParameter("quest_type");
        QuestResultTypeEnum questType = QuestResultTypeEnum.getTypeByName(typeString);
        AbstractValidator validator = new NewQuestValidator(language,category,title,description,questType);
        if (validator.validate(userLocale)) {
            QuestBean newQuest = new QuestBean();
            newQuest.setLanguage(language);
            newQuest.setCategory(category);
            newQuest.setTitle(title);
            newQuest.setDescription(description);
            newQuest.setTypeResult(questType.getNameEn());
            newQuest.setQuestions(new ArrayList<QuestionBean>());
            Object userObj = extractedSession.getAttribute("user");
            if ((userObj!=null)&&(userObj instanceof UserBean)) {
                UserBean user = (UserBean) userObj;
                newQuest.setAuthor(user.getNick());
            }
            extractedSession.setAttribute("newQuest",newQuest);
        } else {
            request.setAttribute("error_quest_message",validator.getErrorReport());
            return "/quest-title-create.page";
        }
        switch (questType) {
            case VOTING:
                currentLink = "/question-vote.page";
                break;
            case TEST_MARK:
                currentLink = "/question-mark.page";
                break;
            case QUESTIONNAIRE:
                currentLink = "/question-nomark.page";
                break;
            default:
                currentLink = "/main.page";
        }
        return super.execute();
    }
}
