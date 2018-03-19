package by.kozik.quest.entity;

import by.kozik.quest.utility.UtilString;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Serge on 23.01.2017.
 */
public class UserAnswerResult implements Serializable {
    private int id;
    private Date date;
    private SimpleDateFormat dateFormat;
    private int userMainResultId;
    private int answerVariantId;
    private String text = null;

    public UserAnswerResult() {
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

    public int getUserMainResultId() {
        return userMainResultId;
    }

    public void setUserMainResultId(int userMainResultId) {
        this.userMainResultId = userMainResultId;
    }

    public int getAnswerVariantId() {
        return answerVariantId;
    }

    public void setAnswerVariantId(int answerVariantId) {
        this.answerVariantId = answerVariantId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
