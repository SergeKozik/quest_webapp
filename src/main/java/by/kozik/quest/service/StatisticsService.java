package by.kozik.quest.service;

import by.kozik.quest.dao.QuestDao;
import by.kozik.quest.dao.StatisticsDao;
import by.kozik.quest.dao.UserDao;
import by.kozik.quest.entity.*;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.service.comparator.UserQuestResultViewByDate;
import by.kozik.quest.service.comparator.UserQuestResultViewByUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Serge on 25.01.2017.
 */
public class StatisticsService {
    private static StatisticsService instance = new StatisticsService();

    private StatisticsService() {
    }

    public static StatisticsService getInstance() {
        return instance;
    }

    public List<UserQuestResultView> viewUserResult(int questId) throws PoolConnectionException, GetEntityException {
        List<UserQuestResultView> result = null;
        StatisticsDao statisticsDao = StatisticsDao.getInstance();
        List<UserQuestResult> userResults = statisticsDao.findQuestResults(questId);
        if (userResults!=null) {
            result = new ArrayList<>();
            int currentId=0;
            for (UserQuestResult userQuestResult:userResults) {
                UserBean user = UserDao.getInstance().findUserById(userQuestResult.getUserId());
                UserQuestResultView tmpView = new UserQuestResultView();
                tmpView.setQuestId(questId);
                tmpView.setDate(userQuestResult.getDateObject());
                tmpView.setUser(user);
                List<QuestionBean> questions = statisticsDao.returnQuestions(userQuestResult.getAnswers());
                List<UserQuestionView> userQuestions = new ArrayList<>();
                for (QuestionBean question:questions){
                    UserQuestionView userQuestionView = new UserQuestionView();
                    userQuestionView.setFormulation(question.getFormulation());
                    userQuestionView.setAnswers(new ArrayList<UserAnswerView>());
                    for (AnswerVariantBasic answerVariant:question.getVariants()) {
                        UserAnswerView answerView = new UserAnswerView();
                        AnswerTypeEnum answerType = answerVariant.getType();
                        switch (answerType) {
                            case USER_TEXT:
                                AnswerVariantUserText tmpUserAnswer = (AnswerVariantUserText) answerVariant;
                                answerView.setUserAnswer(tmpUserAnswer.getUserText());
                                break;
                            default:
                                answerView.setUserAnswer(answerVariant.getFormulation());
                        }
                        userQuestionView.getAnswers().add(answerView);
                    }
                    userQuestions.add(userQuestionView);
                }
                tmpView.setQuestions(userQuestions);
                tmpView.setQuantityResult(QuestService.getInstance().calculateUserMark(userQuestResult));
                tmpView.setId(currentId++);
                result.add(tmpView);
            }
        }
        return result;
    }

    public List<UserQuestResultView> viewUserResult(int questId,UserBean user) throws PoolConnectionException, GetEntityException {
        List<UserQuestResultView> result = null;
        StatisticsDao statisticsDao = StatisticsDao.getInstance();
        List<UserQuestResult> userResults = statisticsDao.findQuestResults(questId,user.getId());
        if (userResults!=null) {
            result = new ArrayList<>();
            int currentId=0;
            for (UserQuestResult userQuestResult:userResults) {
                UserQuestResultView tmpView = new UserQuestResultView();
                tmpView.setQuestId(questId);
                tmpView.setDate(userQuestResult.getDateObject());
                tmpView.setUser(user);
                List<QuestionBean> questions = statisticsDao.returnQuestions(userQuestResult.getAnswers());
                List<UserQuestionView> userQuestions = new ArrayList<>();
                for (QuestionBean question:questions){
                    UserQuestionView userQuestionView = new UserQuestionView();
                    userQuestionView.setFormulation(question.getFormulation());
                    userQuestionView.setAnswers(new ArrayList<UserAnswerView>());
                    for (AnswerVariantBasic answerVariant:question.getVariants()) {
                        UserAnswerView answerView = new UserAnswerView();
                        AnswerTypeEnum answerType = answerVariant.getType();
                        switch (answerType) {
                            case USER_TEXT:
                                AnswerVariantUserText tmpUserAnswer = (AnswerVariantUserText) answerVariant;
                                answerView.setUserAnswer(tmpUserAnswer.getUserText());
                                break;
                            default:
                                answerView.setUserAnswer(answerVariant.getFormulation());
                        }
                        userQuestionView.getAnswers().add(answerView);
                    }
                    userQuestions.add(userQuestionView);
                }
                tmpView.setQuestions(userQuestions);
                tmpView.setQuantityResult(QuestService.getInstance().calculateUserMark(userQuestResult));
                tmpView.setId(currentId++);
                result.add(tmpView);
            }
        }
        return result;
    }

    public List<UserQuestResultView> viewUserResultByUser(int questId) throws PoolConnectionException, GetEntityException {
        List<UserQuestResultView> listResult = this.viewUserResult(questId);
        if (listResult!=null) {
            Collections.sort(listResult, new UserQuestResultViewByUser());
        }
        return listResult;
    }

    public List<UserQuestResultView> viewUserResultByDate(int questId,UserBean user) throws PoolConnectionException, GetEntityException {
        List<UserQuestResultView> listResult = this.viewUserResult(questId,user);
        if (listResult!=null) {
            Collections.sort(listResult, new UserQuestResultViewByDate());
        }
        return listResult;
    }
}
