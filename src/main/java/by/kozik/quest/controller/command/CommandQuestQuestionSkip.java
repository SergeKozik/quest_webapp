package by.kozik.quest.controller.command;

import by.kozik.quest.entity.QuestionBean;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.IteratorSynchronized;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Serge on 14.01.2017.
 */
public class CommandQuestQuestionSkip extends CommandDefault {

    public CommandQuestQuestionSkip(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        Object iteratorObj= extractedSession.getAttribute("currentQuestionsIterator");
        if (iteratorObj==null) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.question-notfound",userLocale));
        }
        QuestionBean question = null;
        try {
            IteratorSynchronized<QuestionBean> iterator = (IteratorSynchronized<QuestionBean>)iteratorObj;
            question = iterator.next();
            extractedSession.setAttribute("current_question",question);
        } catch (ClassCastException e1) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.question-wrong-type",userLocale));
        }
        if (question!=null) {
            currentLink = "/quest-next-question.page";
        } else {
            currentLink = "/user/quest-finish.html";
        }
        return super.execute();
    }
}
