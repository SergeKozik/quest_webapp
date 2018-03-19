package by.kozik.quest.controller.command;

import by.kozik.quest.dao.QuestDao;
import by.kozik.quest.entity.FormActionBean;
import by.kozik.quest.entity.QuestBean;
import by.kozik.quest.entity.QuestBeanJS;
import by.kozik.quest.entity.UserBean;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.LanguageEnum;
import by.kozik.quest.service.QuestService;
import by.kozik.quest.service.RoleEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Serge on 27.11.16.
 */
public class CommandDefault implements CommandExecutable {

    protected String currentLink = "/main.page";
    protected HttpServletRequest request;
    protected Locale userLocale;
    protected HttpSession extractedSession;
    private static Logger logger = LogManager.getLogger();

    public CommandDefault(HttpServletRequest request) {
        this.request = request;
        this.extractedSession = request.getSession();
        Object tmpLanguage = this.extractedSession.getAttribute("language");
        if ((tmpLanguage!=null)) {
            try {
                String language = (String)tmpLanguage;
                userLocale = LanguageEnum.localeByStringLocale(language);
            } catch (ClassCastException e) {
                logger.error("'language' attribute is overriden in session."+e.getMessage());
            }
        } else {
            userLocale = request.getLocale();
        }
        if (userLocale==null) {
            userLocale = Locale.ENGLISH;
        }
    }


    @Override
    public String execute() {
        return currentLink;
    }

    @Override
    public String executeExceptionPage(String message) {
        request.setAttribute("error_message",message);
        currentLink = "/custom_exception.page";
        return currentLink;
    }

    @Override
    public String executeReport(String message) {
        request.setAttribute("report_message",message);
        currentLink = "/custom_report.page";
        return currentLink;
    }

    @Override
    public String executeConfirm(String message, String returnPage, String proceedPage) {
        extractedSession.setAttribute("cancel_link",returnPage);
        extractedSession.setAttribute("proceed_link",proceedPage);
        extractedSession.setAttribute("ask_message",message);
        currentLink = "/custom_confirm.page";
        return currentLink;
    }

    protected List<FormActionBean> returnQuestActions(UserBean user, QuestBeanJS quest) throws PoolConnectionException, GetEntityException {
        List<FormActionBean> result = new ArrayList<>();
        if ((user==null)||(quest==null)) {
            return result;
        }
        if (user.getRole()> RoleEnum.ANONYMOUS.getPriority()) {
            result.add(new FormActionBean("/user/quest-start.html", ResourceReader.readMessageResource("message.button.quest-start",userLocale)));
        }
        if (QuestService.getInstance().calculateUserAttempts(quest,user)>0) {
            result.add(new FormActionBean("/user/quest-statistics.html",ResourceReader.readMessageResource("message.button.user-quest-statistics",userLocale)));
        }
        if ((quest.getAuthor().equals(user.getNick()))||(user.getRole()>RoleEnum.AUTHOR.getPriority())) {
            result.add(new FormActionBean("/author/quest-statistics.html",ResourceReader.readMessageResource("message.button.quest-statistics",userLocale)));
        }
        return result;
    }

}
