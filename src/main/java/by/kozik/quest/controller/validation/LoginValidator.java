package by.kozik.quest.controller.validation;

import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.utility.UtilString;

import java.util.Locale;

/**
 * Created by Serge on 24.12.2016.
 */
public class LoginValidator extends AbstractValidator {
    private String nick;
    private String password;

    public LoginValidator(String name, String password) {
        super();
        this.nick = name;
        this.password = password;
    }

    @Override
    public boolean validate(Locale userLocale) {
        flagSuccess = true;
        if (UtilString.isNullEmpty(nick)) {
            flagSuccess = false;
            errorReport.append(ResourceReader.readMessageResource("message.label.error.nick-missing",userLocale));
        }
        if (UtilString.isNullEmpty(password)) {
            flagSuccess = false;
            errorReport.append(" ").append(ResourceReader.readMessageResource("message.label.error.password-missing",userLocale));
        }
        String nameRegExp = ResourceReader.readRegularExpressionSource("regexp.login",userLocale);
        if ((flagSuccess)&&(!nick.matches(nameRegExp))) {
            flagSuccess = false;
        }
        return flagSuccess;
    }
}
