package by.kozik.quest.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Serge on 27.11.16.
 */
public class CommandMain extends CommandDefault {

    public CommandMain(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {

        currentLink = "/main.page";
        String result = super.execute();
        return result;
    }
}
