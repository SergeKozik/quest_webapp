package by.kozik.quest.controller.validation;

import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.utility.UtilString;

import java.util.Locale;

/**
 * Created by Serge on 24.12.2016.
 */
public class RegisterValidator extends AbstractValidator {
    private String nick;
    private String password;
    private String passwordDouble;
    private String email;
    private String roleCodeString;

    public RegisterValidator(String nick, String password, String passwordDouble, String email, String roleCodeString) {
        super();
        this.nick = nick;
        this.password = password;
        this.passwordDouble = passwordDouble;
        this.email = email;
        this.roleCodeString = roleCodeString;
    }

    @Override
    public boolean validate(Locale userLocale) {
        flagSuccess = true;
        String regEmail = ResourceReader.readRegularExpressionSource("regexp.email",userLocale);
        String regNick = ResourceReader.readRegularExpressionSource("regexp.login",userLocale);

        if (UtilString.isNullEmpty(roleCodeString)) {
            flagSuccess = false;
            errorReport.append(ResourceReader.readMessageResource("message.label.error.role-notfound",userLocale));
        }
        if (UtilString.isNullEmpty(password)) {
            flagSuccess = false;
            errorReport.append(" ").append(ResourceReader.readMessageResource("message.label.error.password-missing",userLocale));
        } else {
            if (!password.equals(passwordDouble)) {
                flagSuccess = false;
                errorReport.append(" ").append(ResourceReader.readMessageResource("message.label.error.password-compare",userLocale));
            }
        }
        if (UtilString.isNullEmpty(nick)) {
            flagSuccess = false;
            errorReport.append(" ").append(ResourceReader.readMessageResource("message.label.error.nick-fault",userLocale));
        }
        if ((!UtilString.isNullEmpty(email))&&(!email.matches(regEmail))) {
            flagSuccess = false;
            errorReport.append(" ").append(ResourceReader.readMessageResource("message.label.error.email-nonvalid",userLocale));
        }
        if ((flagSuccess)&&(!nick.matches(regNick))) {
            flagSuccess = false;
            errorReport.append(" ").append(ResourceReader.readMessageResource("message.label.error.nick-nonvalid",userLocale));
        }
        return flagSuccess;
    }
}
