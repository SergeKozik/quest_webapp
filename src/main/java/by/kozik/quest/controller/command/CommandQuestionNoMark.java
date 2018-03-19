package by.kozik.quest.controller.command;

import by.kozik.quest.controller.validation.AbstractValidator;
import by.kozik.quest.controller.validation.AnswerMultipleNoMarkValidator;
import by.kozik.quest.entity.*;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.AnswerTypeEnum;
import by.kozik.quest.service.QuestResultTypeEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serge on 09.01.2017.
 */
public class CommandQuestionNoMark extends CommandDefault {

    public CommandQuestionNoMark(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        String nextLink = request.getParameter("typeLink");
        if ((nextLink != null) && ("toNew".equals(nextLink))) {
            return "/question-nomark.page";
        }
        String title = request.getParameter("question_title");
        String[] answersCase = request.getParameterValues("answer_var");
        String[] answersText = request.getParameterValues("answer_text");
        AbstractValidator validator = new AnswerMultipleNoMarkValidator(title, answersCase,answersText);
        if (validator.validate(userLocale)) {
            QuestionBean question = new QuestionBean();
            question.setFormulation(title);
            List<AnswerVariantBasic> variants = new ArrayList<>();
            if (answersCase != null) {
                for (String answer:answersCase) {
                    AnswerVariantBasic variant = new AnswerVariantBasic();
                    variant.setFormulation(answer);
                    variant.setType(AnswerTypeEnum.CASE_TEXT);
                    variants.add(variant);
                }
            }
            if (answersText != null) {
                for (String answer:answersText) {
                    AnswerVariantUserText variant = new AnswerVariantUserText();
                    variant.setFormulation(answer);
                    variant.setType(AnswerTypeEnum.USER_TEXT);
                    variants.add(variant);
                }
            }
            question.setVariants(variants);
            Object questObj = extractedSession.getAttribute("newQuest");
            if ((questObj != null) && (questObj instanceof QuestBean)) {
                QuestBean questBean = (QuestBean) questObj;
                if (QuestResultTypeEnum.QUESTIONNAIRE.getNameEn().equals(questBean.getTypeResult())) {
                    questBean.getQuestions().add(question);
                } else {
                    return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-type-create", userLocale));
                }
            } else {
                return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-question-create", userLocale));
            }
        } else {
            request.setAttribute("error_question_message", validator.getErrorReport());
            return "/question-nomark.page";
        }
        if ((nextLink!=null)&&("toFinish".equals(nextLink))) {
            currentLink ="/author/quest-before-save.html";
        } else {
            currentLink ="/question-nomark.page";
        }
        return super.execute();
    }
}
