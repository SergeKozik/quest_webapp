package by.kozik.quest.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Serge on 21.01.2017.
 */
public class CommandLogout extends CommandDefault {
    public CommandLogout(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        extractedSession.invalidate();
        return super.execute();
    }
}
