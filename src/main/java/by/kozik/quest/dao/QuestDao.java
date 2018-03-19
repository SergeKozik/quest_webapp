package by.kozik.quest.dao;

import by.kozik.quest.entity.*;
import by.kozik.quest.exception.GetEntityException;
import by.kozik.quest.exception.PoolConnectionException;
import by.kozik.quest.exception.AlterEntityException;
import by.kozik.quest.service.AnswerTypeEnum;
import by.kozik.quest.utility.UtilString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by Serge on 14.12.2016.
 */
public class QuestDao {
    private static final String SQL_QUEST_NAME_CATEGORY_COUNT = "SELECT COUNT(name) FROM quest_list WHERE name=? AND type_result=?";
    private static final String SQL_QUEST_ALL = "SELECT id,name,language,field,type_result,author,creation_date, COUNT(user_id) AS passed FROM (" +
            "SELECT quest_list.id,name,language,field,type_result,author,creation_date, user_id FROM quest_list" +
            " LEFT JOIN user_main_result ON quest_list.id=user_main_result.quest_list_id) AS T1" +
            " WHERE not(name is NULL) GROUP BY id";
    private static final String SQL_QUEST_ALL_AUTHOR = "SELECT id,name,language,field,type_result,author,creation_date, COUNT(user_id) AS passed FROM (" +
            "SELECT quest_list.id,name,language,field,type_result,author,creation_date, user_id FROM quest_list" +
            " LEFT JOIN user_main_result ON quest_list.id=user_main_result.quest_list_id) AS T1" +
            " WHERE author=? GROUP BY id";
    private static final String SQL_QUEST_PARTICIPATED = "SELECT DISTINCT ql.id AS id,ql.name AS name,ql.language AS language,ql.field AS field,ql.type_result AS type_result,ql.author AS author,ql.creation_date AS creation_date" +
            " FROM quest_list ql RIGHT JOIN user_main_result umr ON ql.id=umr.quest_list_id WHERE umr.user_id=?";
    private static final String SQL_PASSED_QUEST_ID = "SELECT COUNT(ql.id) FROM quest_list ql RIGHT JOIN user_main_result umr ON ql.id=umr.quest_list_id WHERE ql.id=?";
    private static final String SQL_QUEST_ID = "SELECT id,name,language,field,type_result,author,creation_date, COUNT(user_id) AS passed FROM (" +
            "SELECT quest_list.id,name,language,field,type_result,author,creation_date, user_id FROM quest_list" +
            " LEFT JOIN user_main_result ON quest_list.id=user_main_result.quest_list_id) AS T1" +
            " WHERE id=? GROUP BY id";
    private static final String SQL_QUEST_TEMPLATE_BEGIN = "SELECT id,name,language,field,type_result,author,creation_date, COUNT(user_id) AS passed FROM (" +
            "SELECT quest_list.id,name,language,field,type_result,author,creation_date, user_id FROM quest_list" +
            " LEFT JOIN user_main_result ON quest_list.id=user_main_result.quest_list_id) AS T1";
    private static final String SQL_QUEST_TEMPLATE_END =" GROUP BY id";
    private static final String SQL_CATEGORIES_LANG = "SELECT DISTINCT field FROM quest_list WHERE language=?";
    private static final String SQL_CATEGORIES_ALL = "SELECT DISTINCT field FROM quest_list";
    private static final String SQL_QUEST_SAVE = "INSERT INTO quest_list(name,language,field,type_result,author,creation_date,description) VALUES(?,?,?,?,?,now(),?)";
    private static String SQL_QUESTION_SAVE = "INSERT INTO question_item (formulation,quest_list_id) VALUES(?,?)";
    private static String SQL_QUESTION_READ = "SELECT id, formulation FROM question_item WHERE quest_list_id=?";
    private static String SQL_ANSWER_VARIANT_INSERT = "INSERT INTO answer_variant (formulation,question_item_id,type) VALUES(?,?,?)";
    private static String SQL_ANSWER_BASIC_READ = "SELECT av.id AS id, av.formulation AS formulation, av.type AS type, avm.mark AS mark FROM answer_variant av LEFT JOIN answer_variant_mark avm ON av.id=avm.answer_variant_id WHERE av.question_item_id=?";
    private static String SQL_ANSWER_VARIANT_MARK = "INSERT INTO answer_variant_mark (mark,answer_variant_id) VALUES(?,?)";
    private static String SQL_ANSWER_GIVEN_READ = "SELECT COUNT(us.id) AS people FROM user us JOIN (SELECT umr.user_id AS results_id FROM user_answer ua JOIN user_main_result umr ON ua.user_main_result_id=umr.id WHERE ua.answer_variant_id=?) AS T1 ON us.id=T1.results_id";
    private static String SQL_ANSWER_MARK = "SELECT mark FROM answer_variant_mark WHERE answer_variant_id=?";
    private static String SQL_USERS_RESULT_COUNT = "SELECT COUNT(id) AS results FROM user_main_result WHERE user_id=? AND quest_list_id=?";

    private static QuestDao instance = new QuestDao();
    private static Logger logger = LogManager.getLogger();

    private QuestDao() {
    }

    public static QuestDao getInstance() {
        return instance;
    }

    public List<QuestBean> showAllQuest() throws PoolConnectionException, GetEntityException {
        PreparedStatement statement=null;
        List<QuestBean> result = null;
        ConnectionUsable connection=null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUEST_ALL);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                result = new ArrayList<QuestBean>();
                while (resultSet.next()) {
                    QuestBean tmpQuest = new QuestBean();
                    tmpQuest.setTitle(resultSet.getString("name"));
                    tmpQuest.setLanguage(resultSet.getString("language"));
                    tmpQuest.setId(resultSet.getInt("id"));
                    tmpQuest.setCategory(resultSet.getString("field"));
                    tmpQuest.setTypeResult(resultSet.getString("type_result"));
                    tmpQuest.setAuthor(resultSet.getString("author"));
                    tmpQuest.setPassed(resultSet.getInt("passed"));
                    Timestamp timestamp = resultSet.getTimestamp("creation_date");
                    tmpQuest.setDate(new Date(timestamp.getTime()));
                    result.add(tmpQuest);
                }
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public List<QuestBean> showAllQuest(UserBean userBean) throws PoolConnectionException, GetEntityException {
        PreparedStatement statement=null;
        List<QuestBean> result = null;
        ConnectionUsable connection=null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUEST_ALL_AUTHOR);
            statement.setString(1,userBean.getNick());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                result = new ArrayList<QuestBean>();
                while (resultSet.next()) {
                    QuestBean tmpQuest = new QuestBean();
                    tmpQuest.setTitle(resultSet.getString("name"));
                    tmpQuest.setLanguage(resultSet.getString("language"));
                    tmpQuest.setId(resultSet.getInt("id"));
                    tmpQuest.setCategory(resultSet.getString("field"));
                    tmpQuest.setTypeResult(resultSet.getString("type_result"));
                    tmpQuest.setAuthor(resultSet.getString("author"));
                    tmpQuest.setPassed(resultSet.getInt("passed"));
                    Timestamp timestamp = resultSet.getTimestamp("creation_date");
                    tmpQuest.setDate(new Date(timestamp.getTime()));
                    result.add(tmpQuest);
                }
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public List<QuestBean> showAllParticipatedQuest(UserBean userBean) throws PoolConnectionException, GetEntityException {
        PreparedStatement statement=null;
        List<QuestBean> result = null;
        ConnectionUsable connection=null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUEST_PARTICIPATED);
            statement.setInt(1,userBean.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                result = new ArrayList<QuestBean>();
                while (resultSet.next()) {
                    QuestBean tmpQuest = new QuestBean();
                    tmpQuest.setTitle(resultSet.getString("name"));
                    tmpQuest.setLanguage(resultSet.getString("language"));
                    tmpQuest.setId(resultSet.getInt("id"));
                    tmpQuest.setCategory(resultSet.getString("field"));
                    tmpQuest.setTypeResult(resultSet.getString("type_result"));
                    tmpQuest.setAuthor(resultSet.getString("author"));
                    Timestamp timestamp = resultSet.getTimestamp("creation_date");
                    tmpQuest.setDate(new Date(timestamp.getTime()));
                    result.add(tmpQuest);
                }
            }
            resultSet.close();
            statement.close();
            if (result!=null) {
                statement = connection.prepareStatement(SQL_PASSED_QUEST_ID);
                for (QuestBean quest:result) {
                    statement.setInt(1,quest.getId());
                    ResultSet passedRS = statement.executeQuery();
                    if (passedRS.next()) {
                        quest.setPassed(passedRS.getInt(1));
                    }
                    passedRS.close();
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

    public Set<String> showAllCategories(String language) throws PoolConnectionException, GetEntityException {
        PreparedStatement statement=null;
        Set<String> result = null;
        ConnectionUsable connection=null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            if (UtilString.isNullEmpty(language)) {
                statement = connection.prepareStatement(SQL_CATEGORIES_ALL);
            } else {
                statement = connection.prepareStatement(SQL_CATEGORIES_LANG);
                statement.setString(1,language);
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                result = new HashSet<>();
                while (resultSet.next()) {
                    result.add(resultSet.getString("field"));
                }
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public List<QuestBean> showQuests(List<String> languages, List<String> types, List<String> categories) throws PoolConnectionException, GetEntityException {
        String sqlString="";
        if ((languages!=null)&&(!languages.isEmpty())) {
            sqlString = " WHERE(";
            Iterator<String> iterator = languages.iterator();
            sqlString = "language='"+iterator.next()+"'";
            while (iterator.hasNext()) {
                sqlString = " OR language='"+iterator.next()+"'";
            }
            sqlString=sqlString+")";
        }

        if ((types!=null)&&(!types.isEmpty())) {
            sqlString = sqlString+"AND(";
            Iterator<String> iterator = types.iterator();
            sqlString = "type_result='"+iterator.next()+"'";
            while (iterator.hasNext()) {
                sqlString = " OR type_result='"+iterator.next()+"'";
            }
            sqlString=sqlString+")";
        }

        if ((categories!=null)&&(!categories.isEmpty())) {
            sqlString = sqlString+"AND(";
            Iterator<String> iterator = categories.iterator();
            sqlString = "field='"+iterator.next()+"'";
            while (iterator.hasNext()) {
                sqlString = " OR field='"+iterator.next()+"'";
            }
            sqlString=sqlString+")";
        }
        sqlString = SQL_QUEST_TEMPLATE_BEGIN+sqlString+SQL_QUEST_TEMPLATE_END;
        PreparedStatement statement=null;
        List<QuestBean> result = null;
        ConnectionUsable connection=null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(sqlString);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                result = new ArrayList<QuestBean>();
                while (resultSet.next()) {
                    QuestBean tmpQuest = new QuestBean();
                    tmpQuest.setTitle(resultSet.getString("name"));
                    tmpQuest.setLanguage(resultSet.getString("language"));
                    tmpQuest.setId(resultSet.getInt("id"));
                    tmpQuest.setCategory(resultSet.getString("field"));
                    tmpQuest.setTypeResult(resultSet.getString("type_result"));
                    tmpQuest.setAuthor(resultSet.getString("author"));
                    tmpQuest.setPassed(resultSet.getInt("passed"));
                    Timestamp timestamp = resultSet.getTimestamp("creation_date");
                    tmpQuest.setDate(new Date(timestamp.getTime()));
                    result.add(tmpQuest);
                }
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }


    public void saveQuest(QuestBean questBean) throws PoolConnectionException,AlterEntityException {
        PreparedStatement statement=null;
        ConnectionUsable connection=null;
        if (questBean==null) {
            throw new AlterEntityException("Null entity.");
        }
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUEST_NAME_CATEGORY_COUNT);
            statement.setString(1,questBean.getTitle());
            statement.setString(2,questBean.getTypeResult());
            ResultSet existSet = statement.executeQuery();
            int numQuests=-1;
            if (existSet.next()) {
                numQuests = existSet.getInt(1);
            }
            statement.close();
            if (numQuests==0) {
                statement = connection.prepareStatement(SQL_QUEST_SAVE,PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setString(1,questBean.getTitle());
                statement.setString(2,questBean.getLanguage());
                statement.setString(3,questBean.getCategory());
                statement.setString(4,questBean.getTypeResult());
                statement.setString(5,questBean.getAuthor());
                statement.setString(6,questBean.getDescription());
                statement.executeUpdate();
                ResultSet questResultSet = statement.getGeneratedKeys();
                int questId = 0;
                if (questResultSet.next()) {
                    questId = questResultSet.getInt(1);
                }
                questResultSet.close();
                statement.close();
                if ((questId>0)&&(questBean.getQuestions()!=null)&&(!questBean.getQuestions().isEmpty())) {
                    statement = connection.prepareStatement(SQL_QUESTION_SAVE,PreparedStatement.RETURN_GENERATED_KEYS);
                    for (QuestionBean question:questBean.getQuestions()) {
                        statement.setString(1, question.getFormulation());
                        statement.setInt(2, questId);
                        statement.executeUpdate();
                        ResultSet questionRS = statement.getGeneratedKeys();
                        if (questionRS.next()) {
                            question.setId(questionRS.getInt(1));
                        }
                        questionRS.close();
                    }
                    statement.close();
                    for (QuestionBean question:questBean.getQuestions()) {
                        if ((question.getVariants() != null)&&(!question.getVariants().isEmpty())) {
                            statement = connection.prepareStatement(SQL_ANSWER_VARIANT_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
                            for (AnswerVariantBasic answer:question.getVariants()) {
                                statement.setString(1,answer.getFormulation());
                                statement.setInt(2,question.getId());
                                statement.setString(3,answer.getType().name().toLowerCase());
                                statement.executeUpdate();
                                ResultSet answerRS = statement.getGeneratedKeys();
                                if (answerRS.next()) {
                                    answer.setId(answerRS.getInt(1));
                                }
                                answerRS.close();
                            }
                            statement.close();
                            statement = connection.prepareStatement(SQL_ANSWER_VARIANT_MARK);
                            for (AnswerVariantBasic answer:question.getVariants()) {
                                if (answer.getType()== AnswerTypeEnum.CASE_MARK) {
                                    AnswerVariantCaseMark caseAnswer = (AnswerVariantCaseMark) answer;
                                    statement.setDouble(1,caseAnswer.getMark());
                                    statement.setInt(2,answer.getId());
                                    statement.executeUpdate();
                                }
                            }
                            statement.close();
                        }
                    }
                }
            } else {
                throw new AlterEntityException("Entity already exists.");
            }
        } catch (SQLException e ) {
            throw new AlterEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
    }

    public QuestBean findQuestById(int id) throws GetEntityException,PoolConnectionException {
        PreparedStatement statement=null;
        QuestBean result = null;
        ConnectionUsable connection=null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUEST_ID);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result= new QuestBean();
                result.setTitle(resultSet.getString("name"));
                result.setLanguage(resultSet.getString("language"));
                result.setId(resultSet.getInt("id"));
                result.setCategory(resultSet.getString("field"));
                result.setTypeResult(resultSet.getString("type_result"));
                result.setAuthor(resultSet.getString("author"));
                result.setPassed(resultSet.getInt("passed"));
                Timestamp timestamp = resultSet.getTimestamp("creation_date");
                result.setDate(new Date(timestamp.getTime()));
            }
            resultSet.close();
            statement.close();
            if ((result!=null)) {
                List<QuestionBean> questions = null;
                statement = connection.prepareStatement(SQL_QUESTION_READ);
                statement.setInt(1,id);
                ResultSet questionRS = statement.executeQuery();
                questions = new ArrayList<>();
                while (questionRS.next()) {
                    QuestionBean questionBean = new QuestionBean();
                    questionBean.setQuestId(id);
                    questionBean.setFormulation(questionRS.getString("formulation"));
                    questionBean.setId(questionRS.getInt("id"));
                    questions.add(questionBean);
               }
               statement.close();
                if (!questions.isEmpty()) {
                    statement = connection.prepareStatement(SQL_ANSWER_BASIC_READ);
                    for (QuestionBean question:questions) {
                        List<AnswerVariantBasic> answers = new ArrayList<>();
                        statement.setInt(1,question.getId());
                        ResultSet answerRS = statement.executeQuery();
                        while (answerRS.next()) {
                            String typeString = answerRS.getString("type");
                            AnswerVariantBasic tmpAnswer = null;
                            AnswerTypeEnum currentType = AnswerTypeEnum.getByNameLower(typeString);
                            switch (currentType) {
                                case CASE_MARK:
                                    AnswerVariantCaseMark tmpAnswer2 = new AnswerVariantCaseMark();
                                    tmpAnswer2.setMark(answerRS.getDouble("mark"));
                                    tmpAnswer=tmpAnswer2;
                                    break;
                                case CASE_TEXT:
                                    tmpAnswer = new AnswerVariantBasic();
                                    break;
                                case USER_TEXT:
                                    tmpAnswer = new AnswerVariantUserText();
                                    break;
                            }
                            if (tmpAnswer!=null) {
                                tmpAnswer.setQuestionId(question.getId());
                                tmpAnswer.setId(answerRS.getInt("id"));
                                tmpAnswer.setFormulation(answerRS.getString("formulation"));
                                answers.add(tmpAnswer);
                            }
                        }
                        answerRS.close();
                        question.setVariants(answers);
                    }
                    statement.close();
                }
                result.setQuestions(questions);
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public int calculateUsersForAnswer(AnswerVariantBasic answer) throws PoolConnectionException, GetEntityException {
        PreparedStatement statement=null;
        ConnectionUsable connection=null;
        int result=0;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_ANSWER_GIVEN_READ);
            statement.setInt(1,answer.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public double returnMarkForAnswer(int answerId) throws PoolConnectionException, GetEntityException {
        PreparedStatement statement=null;
        ConnectionUsable connection=null;
        double result = 0;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_ANSWER_MARK);
            statement.setInt(1,answerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }

    public int calculateUserAttempts(QuestBeanJS quest,UserBean user) throws PoolConnectionException,GetEntityException {
        PreparedStatement statement=null;
        ConnectionUsable connection=null;
        int result=0;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_USERS_RESULT_COUNT);
            statement.setInt(1,user.getId());
            statement.setInt(2,quest.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new GetEntityException(e);
        } finally {
            connection.closeResources(statement);
        }
        return result;
    }
}
