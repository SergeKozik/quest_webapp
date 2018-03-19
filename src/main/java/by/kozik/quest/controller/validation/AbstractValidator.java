package by.kozik.quest.controller.validation;

import java.util.Locale;
import java.util.MissingResourceException;

/**
 * Created by Serge on 24.12.2016.
 */
public abstract class AbstractValidator {

    protected StringBuilder errorReport;
    protected boolean flagSuccess;

    public AbstractValidator() {
        errorReport = new StringBuilder();
    }

    public abstract boolean validate(Locale userLocale);

    public String getErrorReport() {
        if (errorReport!=null) {
            return errorReport.toString();
        } else {
            return "";
        }
    }
}
