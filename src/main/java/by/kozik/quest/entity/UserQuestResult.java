package by.kozik.quest.entity;

import by.kozik.quest.utility.UtilString;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Serge on 23.01.2017.
 */
public class UserQuestResult implements Serializable {
    private int id;
    private Date date;
    private SimpleDateFormat dateFormat;
    private int questListId;
    private int userId;
    List<UserAnswerResult> answers;

    public UserQuestResult() {
        answers = new ArrayList<>();
        dateFormat = new SimpleDateFormat("dd.MM.YYYY");
        dateFormat.setTimeZone(TimeZone.getDefault());
    }

    public UserQuestResult(int questListId, int userId) {
        this.answers = new ArrayList<>();
        this.questListId = questListId;
        this.userId = userId;
    }

    public Date getDateObject() {
        return date;
    }

    public String getDate() {
        if (date!=null) {
            return dateFormat.format(date);
        } else {
            return "--";
        }
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

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestListId() {
        return questListId;
    }

    public void setQuestListId(int questListId) {
        this.questListId = questListId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<UserAnswerResult> getAnswers() {
        return answers;
    }

    public void setAnswers(List<UserAnswerResult> answers) {
        this.answers = answers;
    }
}
