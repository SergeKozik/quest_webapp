package by.kozik.quest.controller.validation;

import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.utility.UtilString;

import java.util.Locale;

/**
 * Created by Serge on 08.01.2017.
 */
public class AnswerMultipleMarkValidator extends AbstractValidator {
    private String title;
    private String[] marks;
    private String[] answersCase;
    private String[] answersText;

    public AnswerMultipleMarkValidator(String title, String[] marks, String[] answersCase, String[] answersText) {
        super();
        this.title = title;
        this.marks = marks;
        this.answersCase = answersCase;
        this.answersText = answersText;
    }

    @Override
    public boolean validate(Locale userLocale) {
        flagSuccess = true;
        if (UtilString.isNullEmpty(title)) {
            flagSuccess=false;
            errorReport.append(ResourceReader.readMessageResource("message.label.error.question-title",userLocale));
        }
        if ((answersCase ==null)&&(answersText ==null)) {
            flagSuccess = false;
            errorReport.append(ResourceReader.readMessageResource("message.label.error.question.variant-empty",userLocale));
        }
        if ((answersCase !=null)&&((marks==null)||(answersCase.length!=marks.length))) {
            flagSuccess = false;
            errorReport.append(ResourceReader.readMessageResource("message.label.error.question-variants",userLocale));

        }
        if (answersCase !=null) {
            for (int i = 0; i< answersCase.length; i++) {
                if (UtilString.isNullEmpty(answersCase[i])) {
                    flagSuccess = false;
                    errorReport.append(ResourceReader.readMessageResource("message.label.error.question.variant-alert-string",userLocale)).append(i+1).append(" :");
                    errorReport.append(ResourceReader.readMessageResource("message.label.error.question.variant-empty",userLocale));
                }
            }
        }
        if (answersText !=null) {
            for (int i = 0; i< answersText.length; i++) {
                if (UtilString.isNullEmpty(answersText[i])) {
                    flagSuccess = false;
                    errorReport.append(ResourceReader.readMessageResource("message.label.error.question.variant-alert-string",userLocale)).append(i+1).append(" :");
                    errorReport.append(ResourceReader.readMessageResource("message.label.error.question.variant-text-empty",userLocale));
                }
            }
        }
        return flagSuccess;
    }
}
