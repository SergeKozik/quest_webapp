package by.kozik.quest.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Serge on 13.01.2017.
 */
public class CommandQuestSaveCancel extends CommandDefault {

    public CommandQuestSaveCancel(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        extractedSession.setAttribute("newQuest",null);
        return super.execute();
    }
}
