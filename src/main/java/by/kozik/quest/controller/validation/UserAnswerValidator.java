package by.kozik.quest.controller.validation;

import by.kozik.quest.entity.AnswerVariantBasic;
import by.kozik.quest.entity.QuestionBean;
import by.kozik.quest.entity.UserBean;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.AnswerTypeEnum;
import by.kozik.quest.utility.UtilString;

import java.util.Locale;

/**
 * Created by Serge on 14.01.2017.
 */
public class UserAnswerValidator extends AbstractValidator {
    private String questionTitle;
    private String[] userAnswer;
    private String[] userText;
    private Object questionFromSession;
    private Object user;

    public UserAnswerValidator(Object userObject,String questionTitle, String[] userAnswer, String[] userText, Object questionFromSession) {
        super();
        this.questionTitle = questionTitle;
        this.userAnswer = userAnswer;
        this.userText = userText;
        this.questionFromSession = questionFromSession;
        this.user = userObject;
    }

    @Override
    public boolean validate(Locale userLocale) {
        flagSuccess = true;
        if ((user==null)||(!(user instanceof UserBean))) {
            flagSuccess = false;
            errorReport.append(ResourceReader.readMessageResource("message.label.error.user-session-missing",userLocale));
        }
        if (UtilString.isNullEmpty(questionTitle)) {
            flagSuccess = false;
            errorReport.append(ResourceReader.readMessageResource("message.label.error.question-title-notfound",userLocale));
        }
        if (questionFromSession==null) {
            flagSuccess = false;
            errorReport.append(ResourceReader.readMessageResource("message.label.error.question-title-notfound",userLocale));
        }
        QuestionBean question=null;
        try {
            question = (QuestionBean)questionFromSession;
            if (!question.getFormulation().equals(questionTitle)) {
                flagSuccess = false;
                errorReport.append(ResourceReader.readMessageResource("message.label.error.question-wrong-order",userLocale));
            }
        } catch (ClassCastException e) {
            flagSuccess = false;
            errorReport.append(ResourceReader.readMessageResource("message.label.error.question-wrong-type",userLocale));
        }
        if ((userAnswer == null)||(userAnswer.length<1)) {
            flagSuccess = false;
            errorReport.append(ResourceReader.readMessageResource("message.label.error.question-answers-notfound",userLocale));
        } else {
            int numText = 0;
            for (AnswerVariantBasic tmpAnswer:question.getVariants()) {
                if (tmpAnswer.getType()== AnswerTypeEnum.USER_TEXT) {
                    numText++;
                }
            }
            if ((numText>0)&&((userText==null)||(userText.length!=numText))) {
                flagSuccess = false;
                errorReport.append(ResourceReader.readMessageResource("message.label.error.question-text-notfound",userLocale));
            }
        }
        return flagSuccess;
    }
}
