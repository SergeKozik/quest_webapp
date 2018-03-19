package by.kozik.quest.controller.command;

import by.kozik.quest.controller.validation.AbstractValidator;
import by.kozik.quest.controller.validation.AnswerMultipleNoMarkValidator;
import by.kozik.quest.entity.AnswerVariantBasic;
import by.kozik.quest.entity.AnswerVariantCaseMark;
import by.kozik.quest.entity.QuestBean;
import by.kozik.quest.entity.QuestionBean;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.AnswerTypeEnum;
import by.kozik.quest.service.QuestResultTypeEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serge on 08.01.2017.
 */
public class CommandQuestionVote extends CommandDefault {

    public CommandQuestionVote(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        String title = request.getParameter("question_title");
        String[] answersCase = request.getParameterValues("answer_var");
        AbstractValidator validator = new AnswerMultipleNoMarkValidator(title, answersCase,null);
        if (validator.validate(userLocale)) {
            QuestionBean question = new QuestionBean();
            question.setFormulation(title);
            List<AnswerVariantBasic> variants = new ArrayList<>();
            for (String answer:answersCase) {
                AnswerVariantCaseMark variant = new AnswerVariantCaseMark();
                variant.setFormulation(answer);
                variant.setMark(0);
                variant.setType(AnswerTypeEnum.CASE_TEXT);
                variants.add(variant);
            }
            question.setVariants(variants);
            Object questObj = extractedSession.getAttribute("newQuest");
            if ((questObj!=null)&&(questObj instanceof QuestBean)) {
                QuestBean questBean = (QuestBean)questObj;
                if (QuestResultTypeEnum.VOTING.getNameEn().equals(questBean.getTypeResult())) {
                    questBean.getQuestions().add(question);
                } else {
                    return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-type-create",userLocale));
                }
            } else {
                return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-question-create",userLocale));
            }
        } else {
            request.setAttribute("error_question_message",validator.getErrorReport());
            return "/question-vote.page";
        }
        currentLink ="/author/quest-before-save.html";
        return super.execute();
    }
}
