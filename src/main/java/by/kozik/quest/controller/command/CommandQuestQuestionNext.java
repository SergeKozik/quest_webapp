package by.kozik.quest.controller.command;

import by.kozik.quest.controller.validation.AbstractValidator;
import by.kozik.quest.controller.validation.UserAnswerValidator;
import by.kozik.quest.entity.*;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.AnswerTypeEnum;
import by.kozik.quest.service.IteratorSynchronized;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serge on 14.01.2017.
 */
public class CommandQuestQuestionNext extends CommandDefault {

    public CommandQuestQuestionNext(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        String questionTitle = request.getParameter("question_title");
        String[] userAnswer = request.getParameterValues("current_answer");
        String[] userText = request.getParameterValues("current_answer_text");
        Object questionObject = extractedSession.getAttribute("current_question");
        Object userObject = extractedSession.getAttribute("user");
        AbstractValidator validator = new UserAnswerValidator(userObject,questionTitle,userAnswer,userText,questionObject);
        QuestionBean question = null;
        if (validator.validate(userLocale)) {
            Object iteratorObj= extractedSession.getAttribute("currentQuestionsIterator");
            if (iteratorObj==null) {
                return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.question-notfound",userLocale));
            }
            Object userResultObject = extractedSession.getAttribute("userResult");
            if (userResultObject==null) {
                return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-results-notfound",userLocale));
            }
            UserQuestResult userQuestResult = (UserQuestResult)userResultObject;
            List<UserAnswerResult> resultAnswers = new ArrayList<>();
            try {
                IteratorSynchronized<QuestionBean> iterator = (IteratorSynchronized<QuestionBean>)iteratorObj;
                QuestionBean curentQuestion = (QuestionBean)questionObject;
                if (userAnswer!=null) {
                    int numText=0;
                    for (String caseAnswer:userAnswer) {
                        int userAnswerId = Integer.parseInt(caseAnswer);
                        for (AnswerVariantBasic answer:curentQuestion.getVariants()) {
                            if (userAnswerId==answer.getId()) {
                                UserAnswerResult tmpAnswer = new UserAnswerResult();
                                tmpAnswer.setAnswerVariantId(answer.getId());
                                if (answer.getType()==AnswerTypeEnum.USER_TEXT) {
                                    tmpAnswer.setText(userText[numText]);
                                    numText++;
                                    resultAnswers.add(tmpAnswer);
                                } else {
                                    resultAnswers.add(tmpAnswer);
                                }
                                break;
                            }
                        }
                    }
                }
                userQuestResult.getAnswers().addAll(resultAnswers);
                question = iterator.next();
                extractedSession.setAttribute("current_question",question);
            } catch (ClassCastException e1) {
                return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.question-wrong-type", userLocale));
            }
            if (question!=null) {
                currentLink = "/quest-next-question.page";
            } else {
                currentLink = "/user/quest-finish.html";
            }
        } else {
            request.setAttribute("error_question_message",validator.getErrorReport());
            return "/quest-next-question.page";
        }
        String nextLink = request.getParameter("typeLink");
        if ((nextLink!=null)&&("toFinish".equals(nextLink))) {
            currentLink = "/user/quest-finish.html";
        }
        return super.execute();
    }
}
