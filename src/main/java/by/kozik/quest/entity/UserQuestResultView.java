package by.kozik.quest.entity;

import by.kozik.quest.utility.UtilString;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Serge on 25.01.2017.
 */
public class UserQuestResultView implements Serializable {
    private int id;
    private UserBean user;
    private Date date;
    private SimpleDateFormat dateFormat;
    private int questId;
    private List<UserQuestionView> questions;
    private double quantityResult;

    public UserQuestResultView() {
        dateFormat = new SimpleDateFormat("dd.MM.YYYY");
        dateFormat.setTimeZone(TimeZone.getDefault());
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getDate() {
        if (date!=null) {
            return dateFormat.format(date);
        } else {
            return "--";
        }

    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public List<UserQuestionView> getQuestions() {
        return questions;
    }

    public void setQuestions(List<UserQuestionView> questions) {
        this.questions = questions;
    }

    public void setDate(String input) {
        if (!UtilString.isNullEmpty(input)) {
            try {
                date = dateFormat.parse(input);
            } catch (ParseException e) {
                date = null;
            }
        }
    }

    public double getQuantityResult() {
        return quantityResult;
    }

    public void setQuantityResult(double quantityResult) {
        this.quantityResult = quantityResult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
