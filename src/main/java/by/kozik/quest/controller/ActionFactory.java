package by.kozik.quest.controller;

import by.kozik.quest.controller.command.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Roldo on 23.11.16.
 */

public class ActionFactory {

    public static CommandExecutable defineCommand(HttpServletRequest request) {

        String uriString = request.getRequestURI();
        CommandExecutable result=null;
        switch (uriString) { //JDK 7
            case "/main.html":
                result = new CommandMain(request);
                break;
            case "/login-user.html":
                result = new CommandLogin(request);
                break;
            case "/language.html":
                result = new CommandLocale(request);
                break;
            case "/register.html":
                result = new CommandPrepareUserRegister(request);
                break;
            case "/register-user.html":
                result = new CommandRegister(request);
                break;
            case "/quests.html":
                result = new CommandQuest(request);
                break;
            case "/author/quest-create.html":
                result = new CommandQuestNew(request);
                break;
            case "/author/quest-title-create.html":
                result = new CommandQuestNewStart(request);
                break;
            case "/author/next-question-mark.html":
                result = new CommandQuestionMark(request);
                break;
            case "/author/next-question-nomark.html":
                result = new CommandQuestionNoMark(request);
                break;
            case "/author/next-question-vote.html":
                result = new CommandQuestionVote(request);
                break;
            case "/author/quest-save-cancel.html":
                result = new CommandQuestSaveCancel(request);
                break;
            case "/author/quest-before-save.html":
                result = new CommandQuestBeforeSave(request);
                break;
            case "/author/quest-save.html":
                result = new CommandQuestNewSave(request);
                break;
            case "/user/quest-start.html":
                result = new CommandQuestBeforeStart(request);
                break;
            case "/user/quest-run.html":
                result = new CommandQuestQuestionStart(request);
                break;
            case "/user/quest-next-question.html":
                result = new CommandQuestQuestionNext(request);
                break;
            case "/user/quest-skip-question.html":
                result = new CommandQuestQuestionSkip(request);
                break;
            case "/user/quest-finish.html":
                result = new CommandQuestQuestionFinish(request);
                break;
            case "/user/quest-own.html":
                result = new CommandUserQuest(request);
                break;
            case "/user/quest-statistics.html":
                result = new CommandUserQuestStatistics(request);
                break;
            case "/user/user-result.html":
                result = new CommandUserStatisticsResult(request);
                break;
            case "/author/quest-own.html":
                result = new CommandAuthorQuest(request);
                break;
            case "/author/quest-statistics.html":
                result= new CommandAuthorQuestStatistics(request);
                break;
            case "/author/user-result.html":
                result = new CommandAuthorUserStatistics(request);
                break;
            case "/admin/users.html":
                result = new CommandAdminUsers(request);
                break;
            case "/admin/user-add.html":
                result = new CommandPrepareAdminAddUser(request);
                break;
            case "/admin/register-user.html":
                result = new CommandAdminAddUser(request);
                break;
            case "/admin/user-delete.html":
                result = new CommandPrepareAdminBeforeUserDelete(request);
                break;
            case "/admin/user-id-delete.html":
                result = new CommandAdminDelUser(request);
                break;
            case "/admin/user-bin-clear.html":
                result = new CommandAdminEmptyBin(request);
                break;
            case "/logout.html":
                result = new CommandLogout(request);
                break;
            default:
                result = new CommandDefault(request);
        }
        if (result==null){
            result = new CommandDefault(request);
        }
        return result;
    }
}
