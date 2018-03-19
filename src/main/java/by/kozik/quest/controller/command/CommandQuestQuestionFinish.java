package by.kozik.quest.controller.command;

import by.kozik.quest.entity.*;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.exception.AlterEntityException;
import by.kozik.quest.resource_handle.ResourceReader;
import by.kozik.quest.service.QuestResultTypeEnum;
import by.kozik.quest.service.QuestService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serge on 15.01.2017.
 */
public class CommandQuestQuestionFinish extends CommandDefault {

    public CommandQuestQuestionFinish(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        Object questObject = extractedSession.getAttribute("startQuest");
        Object userResultObject = extractedSession.getAttribute("userResult");
        if (userResultObject==null) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-results-notfound",userLocale));
        }
        UserQuestResult userQuestResult = null;
        try {
            userQuestResult = (UserQuestResult)userResultObject;
            QuestService.getInstance().saveQuestStatistics(userQuestResult);
        } catch (PoolConnectionException e) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.database-busy",userLocale));
        } catch (AlterEntityException e) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.error.database-save",userLocale));
        }
        QuestBean quest = (QuestBean)questObject;
        QuestResultTypeEnum questType = QuestResultTypeEnum.getTypeByName(quest.getTypeResult());
        if (questType==null) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.quest-type-notfound",userLocale));
        }
        try {
            switch (questType) {
                case VOTING:
                    String questionTitle = quest.getTitle();
                    extractedSession.setAttribute("question_title", questionTitle);
                    QuestionBean currentQuestion = quest.getQuestions().get(0);
                    List<TextContentBean> answerResults = new ArrayList<>();
                    int questPassed = quest.getPassed()+1;
                    double procentCount=0;
                    for (AnswerVariantBasic answer : currentQuestion.getVariants()) {
                        int answerPassed=QuestService.getInstance().usersForAnswer(answer);
                        procentCount = 100.0 *answerPassed/questPassed ;
                        answerResults.add(new TextContentBean(answer.getFormulation(), String.format("%.1f%% (%d)",procentCount,answerPassed)));
                    }
                    extractedSession.setAttribute("answer_results",answerResults);
                    currentLink = "/quest-finish-voting.page";
                    break;
                case TEST_MARK:
                    double totalMark = QuestService.getInstance().calculateMaxMark(quest);
                    double userMark = QuestService.getInstance().calculateUserMark(userQuestResult);
                    extractedSession.setAttribute("test_score",String.format("%.2f",userMark));
                    extractedSession.setAttribute("test_total",String.format("%.2f",totalMark));
                    currentLink = "/quest-finish-mark.page";
                    break;
                case QUESTIONNAIRE:
                    currentLink = "/quest-finish-nomark.page";
                    break;
                default:
                    currentLink = "/main.page";
            }
        } catch (PoolConnectionException e) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.label.error.database-busy",userLocale));
        } catch (GetEntityException e) {
            return executeExceptionPage(ResourceReader.readMessageResource("message.error.quest-not-found",userLocale)+e.getMessage());
        } finally {
            extractedSession.setAttribute("startQuest",null);
            extractedSession.setAttribute("currentQuestionsIterator",null);
            extractedSession.setAttribute("current_question",null);
            extractedSession.setAttribute("userResult",null);
        }
        return super.execute();
    }
}
