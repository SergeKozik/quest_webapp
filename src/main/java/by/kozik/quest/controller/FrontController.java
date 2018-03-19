package by.kozik.quest.controller;

import by.kozik.quest.controller.command.CommandExecutable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roldo on 23.11.16.
 */

public class FrontController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         processResource(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       processResource(req,resp);

    }

    private void processResource(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {

        CommandExecutable command = ActionFactory.defineCommand(req);
        String page = command.execute();
        if (page == null) {
            resp.sendError(503);
        } else {
            if (page.endsWith("page")) {
                req.getSession().setAttribute("last_view",page);
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        }

    }
}
