package by.kozik.quest.controller.command;

import by.kozik.quest.controller.validation.AbstractValidator;
import by.kozik.quest.controller.validation.NewQuestValidator;
import by.kozik.quest.entity.QuestBean;
import by.kozik.quest.entity.QuestionBean;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.QuestService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Serge on 09.01.2017.
 */
public class CommandQuestBeforeSave extends CommandDefault {

    public CommandQuestBeforeSave(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        Object questObj = extractedSession.getAttribute("newQuest");
        if ((questObj!=null)&&(questObj instanceof QuestBean)) {
            QuestBean questBean = (QuestBean)questObj;
            AbstractValidator validator = new NewQuestValidator(questBean);
            if (validator.validate(userLocale)) {
                QuestService questService = QuestService.getInstance();
                String questType = questService.questTypeI18n(questBean.getTypeResult(),userLocale);
                if (questType!=null) {
                    extractedSession.setAttribute("quest_type",questType);
                } else {
                    extractedSession.setAttribute("quest_type","?ERROR?");
                }
                List<QuestionBean> questions = questBean.getQuestions();
                int numQuestions = 0;
                double maxMark = 0;
                if ((questions!=null)&&(!questions.isEmpty())) {
                    numQuestions = questions.size();
                    maxMark = questService.calculateMaxMark(questBean);
                }
                extractedSession.setAttribute("quest_questions",numQuestions);
                extractedSession.setAttribute("quest_marks",maxMark);
            } else {
                extractedSession.setAttribute("newQuest",null);
                return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-question-create",userLocale));
            }
        } else {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-quest-create",userLocale));
        }
        currentLink = "/quest-save.page";
        return super.execute();
    }
}
