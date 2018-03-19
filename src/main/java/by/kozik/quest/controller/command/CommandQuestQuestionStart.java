package by.kozik.quest.controller.command;

import by.kozik.quest.entity.QuestBean;
import by.kozik.quest.entity.QuestionBean;
import by.kozik.quest.entity.UserBean;
import by.kozik.quest.entity.UserQuestResult;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.IteratorSynchronized;
import by.kozik.quest.utility.UtilString;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Serge on 14.01.2017.
 */
public class CommandQuestQuestionStart extends CommandDefault {

    public CommandQuestQuestionStart(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        Object startObject = extractedSession.getAttribute("startQuest");
        if ((startObject==null)||(!(startObject instanceof QuestBean))) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-start",userLocale));
        }
        QuestBean startQuest = (QuestBean)startObject;
        if (UtilString.isNullEmpty(startQuest.getTitle())) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-start",userLocale));
        }
        Object currentQuestionsObject = extractedSession.getAttribute("currentQuestionsIterator");
        if (currentQuestionsObject!=null) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-nonfinished",userLocale));
        }
        List<QuestionBean> questions = startQuest.getQuestions();
        if ((questions==null)||(questions.isEmpty())) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-start-questions",userLocale));
        }
        Object userObject = extractedSession.getAttribute("user");
        if ((userObject==null)||(!(userObject instanceof UserBean))) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.user-session-missing",userLocale));
        }
        Object resultObject = extractedSession.getAttribute("userResult");
        if (resultObject!=null) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-simultaneous",userLocale));
        }
        UserBean userBean = (UserBean)userObject;
        UserQuestResult userResult = new UserQuestResult(startQuest.getId(),userBean.getId());
        extractedSession.setAttribute("userResult",userResult);
        IteratorSynchronized<QuestionBean> iterator = new IteratorSynchronized<>(questions.iterator()); // CopyOnWriteArrayList
        QuestionBean question = iterator.next();
        extractedSession.setAttribute("currentQuestionsIterator",iterator);
        extractedSession.setAttribute("current_question",question);
        currentLink = "/quest-next-question.page";
        return super.execute();
    }
}
