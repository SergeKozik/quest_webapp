package by.kozik.quest.controller.command;

import by.kozik.quest.utility.UtilString;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Serge on 11.12.2016.
 */
public class CommandLocale extends CommandDefault {

    public CommandLocale(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        String language = request.getParameter("lang");
        if (!UtilString.isNullEmpty(language)) {
            extractedSession.setAttribute("language",language);
        }
        Object lastView = extractedSession.getAttribute("last_view");
        if ((lastView!=null)&&(lastView instanceof String)) {
            currentLink = (String)lastView;
        }
        return super.execute();
    }
}
