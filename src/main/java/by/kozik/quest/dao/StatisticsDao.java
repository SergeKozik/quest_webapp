package by.kozik.quest.dao;

import by.kozik.quest.entity.*;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.exception.AlterEntityException;
import by.kozik.quest.service.AnswerTypeEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Serge on 25.01.2017.
 */
public class StatisticsDao {

    private static String SQL_QUEST_RESULT_READ = "SELECT id,date,user_id FROM user_main_result WHERE quest_list_id=?";
    private static String SQL_QUEST_RESULT_USER_READ = "SELECT id,date FROM user_main_result WHERE quest_list_id=? AND user_id=?";
    private static String SQL_USER_ANSWER_READ = "SELECT id,date,answer_variant_id FROM user_answer WHERE user_main_result_id=?";
    private static String SQL_ANSWER_VARIANT = "SELECT formulation, type, question_item_id, mark, text  FROM" +
            "(SELECT av.id AS id, av.formulation AS formulation, av.type AS type, av.question_item_id AS question_item_id, avm.mark AS mark FROM" +
            " answer_variant av LEFT JOIN answer_variant_mark avm ON av.id=avm.answer_variant_id) AS T1" +
            " RIGHT JOIN" +
            " (SELECT ua.answer_variant_id AS answer_variant_id, uat.text AS text FROM user_answer ua LEFT JOIN user_answer_text uat ON ua.id=uat.user_answer_id WHERE ua.answer_variant_id=? AND ua.id=?) AS T2" +
            " ON T1.id=T2.answer_variant_id;";
    private static String SQL_QUESTION_READ = "SELECT formulation,quest_list_id FROM question_item WHERE id=?";
    private static String SQL_MAIN_RESULT_SAVE = "INSERT INTO user_main_result (date,quest_list_id,user_id) VALUES(now(),?,?)";
    private static String SQL_USER_ANSWER_SAVE = "INSERT INTO user_answer (date,user_main_result_id,answer_variant_id) VALUES(now(),?,?)";
    private static String SQL_USER_TEXT_SAVE = "INSERT INTO user_answer_text (user_answer_id,text) VALUES(?,?)";

    private static StatisticsDao instance = new StatisticsDao();

    private StatisticsDao() {
    }

    public static StatisticsDao getInstance() {
        return instance;
    }

    public List<UserQuestResult> findQuestResults(int questId) throws PoolConnectionException, GetEntityException {
        PreparedStatement statement=null;
        List<UserQuestResult> result = null;
        ConnectionUsable connection=null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUEST_RESULT_READ);
            statement.setInt(1, questId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                result = new ArrayList<>();
                while (resultSet.next()) {
                    UserQuestResult tmpResult = new UserQuestResult();
                    tmpResult.setId(resultSet.getInt("id"));
                    tmpResult.setUserId(resultSet.getInt("user_id"));
                    tmpResult.setQuestListId(questId);
                    tmpResult.setDate(new Date(resultSet.getTimestamp("date").getTime()));
                    result.add(tmpResult);
                }
            }
            resultSet.close();
            statement.close();
            if (result!=null) {
                statement = connection.prepareStatement(SQL_USER_ANSWER_READ);
                for (UserQuestResult item:result) {
                    statement.setInt(1,item.getId());
                    ResultSet answerRS = statement.executeQuery();
                    List<UserAnswerResult> userAnswers = new ArrayList<>();
                    while (answerRS.next()) {
                        UserAnswerResult tmpAnswer = new UserAnswerResult();
                        tmpAnswer.setId(answerRS.getInt("id"));
                        tmpAnswer.setDate(new Date(answerRS.getTimestamp("date").getTime()));
                        tmpAnswer.setAnswerVariantId(answerRS.getInt("answer_variant_id"));
                        tmpAnswer.setUserMainResultId(item.getId());
                        userAnswers.add(tmpAnswer);
                    }
                    item.setAnswers(userAnswers);
                    answerRS.close();
                }
                statement.close();
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public List<UserQuestResult> findQuestResults(int questId, int userId) throws PoolConnectionException, GetEntityException {
        PreparedStatement statement=null;
        List<UserQuestResult> result = null;
        ConnectionUsable connection=null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUEST_RESULT_USER_READ);
            statement.setInt(1, questId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                result = new ArrayList<>();
                while (resultSet.next()) {
                    UserQuestResult tmpResult = new UserQuestResult();
                    tmpResult.setId(resultSet.getInt("id"));
                    tmpResult.setUserId(userId);
                    tmpResult.setQuestListId(questId);
                    tmpResult.setDate(new Date(resultSet.getTimestamp("date").getTime()));
                    result.add(tmpResult);
                }
            }
            resultSet.close();
            statement.close();
            if (result!=null) {
                statement = connection.prepareStatement(SQL_USER_ANSWER_READ);
                for (UserQuestResult item:result) {
                    statement.setInt(1,item.getId());
                    ResultSet answerRS = statement.executeQuery();
                    List<UserAnswerResult> userAnswers = new ArrayList<>();
                    while (answerRS.next()) {
                        UserAnswerResult tmpAnswer = new UserAnswerResult();
                        tmpAnswer.setId(answerRS.getInt("id"));
                        tmpAnswer.setDate(new Date(answerRS.getTimestamp("date").getTime()));
                        tmpAnswer.setAnswerVariantId(answerRS.getInt("answer_variant_id"));
                        tmpAnswer.setUserMainResultId(item.getId());
                        userAnswers.add(tmpAnswer);
                    }
                    item.setAnswers(userAnswers);
                    answerRS.close();
                }
                statement.close();
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public List<QuestionBean> returnQuestions(List<UserAnswerResult> answerResults) throws PoolConnectionException,GetEntityException {
        PreparedStatement statement=null;
        List<QuestionBean> result = new ArrayList<>();
        ConnectionUsable connection=null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_ANSWER_VARIANT);
            List<AnswerVariantBasic> allVariants = new ArrayList<>();
            for (UserAnswerResult answerResult : answerResults) {
                statement.setInt(1, answerResult.getAnswerVariantId());
                statement.setInt(2, answerResult.getId());
                ResultSet answerRS = statement.executeQuery();
                if (answerRS.next()) {
                    AnswerTypeEnum currentType = AnswerTypeEnum.getByNameLower(answerRS.getString("type"));
                    AnswerVariantBasic tmpAnswer = null;
                    switch (currentType) {
                        case CASE_MARK:
                            AnswerVariantCaseMark tmpAnswer2 = new AnswerVariantCaseMark();
                            tmpAnswer2.setMark(answerRS.getDouble("mark"));
                            tmpAnswer = tmpAnswer2;
                            break;
                        case CASE_TEXT:
                            tmpAnswer = new AnswerVariantBasic();
                            break;
                        case USER_TEXT:
                            AnswerVariantUserText tmpAnswer3 = new AnswerVariantUserText();
                            tmpAnswer3.setUserText(answerRS.getString("text"));
                            tmpAnswer = tmpAnswer3;
                            break;
                    }
                    if (tmpAnswer != null) {
                        tmpAnswer.setQuestionId(answerRS.getInt("question_item_id"));
                        tmpAnswer.setId(answerResult.getAnswerVariantId());
                        tmpAnswer.setFormulation(answerRS.getString("formulation"));
                        allVariants.add(tmpAnswer);
                    }
                }
                answerRS.close();
            }
            statement.close();
            statement = connection.prepareStatement(SQL_QUESTION_READ);
            for (AnswerVariantBasic variant:allVariants) {
                boolean flagContains = false;
                for (QuestionBean question:result) {
                    if (question.getId()==variant.getQuestionId()) {
                        flagContains=true;
                        question.getVariants().add(variant);
                        break;
                    }
                }
                if (!flagContains) {
                    statement.setInt(1,variant.getQuestionId());
                    ResultSet questionRS = statement.executeQuery();
                    if (questionRS.next()) {
                        QuestionBean tmpQuestion = new QuestionBean();
                        tmpQuestion.setQuestId(variant.getQuestionId());
                        tmpQuestion.setQuestId(questionRS.getInt("quest_list_id"));
                        tmpQuestion.setFormulation(questionRS.getString("formulation"));
                        tmpQuestion.setVariants(new ArrayList<AnswerVariantBasic>());
                        tmpQuestion.getVariants().add(variant);
                        result.add(tmpQuestion);
                    }
                }
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public void saveQuestStatistics(UserQuestResult userQuestResult) throws PoolConnectionException,AlterEntityException {
        PreparedStatement statement=null;
        ConnectionUsable connection=null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_MAIN_RESULT_SAVE,PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1,userQuestResult.getQuestListId());
            statement.setInt(2,userQuestResult.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            int resultId=0;
            if (resultSet.next()) {
                resultId = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
            List<UserAnswerResult> userAnswers = userQuestResult.getAnswers();
            if ((resultId>0)&&(userAnswers!=null)&&(!userAnswers.isEmpty())) {
                statement = connection.prepareStatement(SQL_USER_ANSWER_SAVE,PreparedStatement.RETURN_GENERATED_KEYS);
                for (UserAnswerResult answer:userAnswers) {
                    statement.setInt(1,resultId);
                    statement.setInt(2,answer.getAnswerVariantId());
                    statement.executeUpdate();
                    ResultSet answerRS = statement.getGeneratedKeys();
                    if (answerRS.next()) {
                        answer.setId(answerRS.getInt(1));
                    }
                    answerRS.close();
                }
                statement.close();
                statement = connection.prepareStatement(SQL_USER_TEXT_SAVE);
                for (UserAnswerResult answer:userAnswers) {
                    if (answer.getText()!=null) {
                        statement.setInt(1,answer.getId());
                        statement.setString(2,answer.getText());
                        statement.executeUpdate();
                    }
                }
                statement.close();
            }
        } catch (SQLException e) {
            throw new AlterEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
    }
}
