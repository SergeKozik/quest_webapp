package by.kozik.quest.entity;

import by.kozik.quest.utility.UtilString;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Serge on 14.12.2016.
 */
public class QuestBean implements Serializable {
    private String title;
    private String language;
    private String category;
    private String typeResult;
    private String author;
    private Date date;
    private SimpleDateFormat dateFormat;
    private int id;
    private int passed;
    private String description;
    private List<QuestionBean> questions;

    public QuestBean() {
        dateFormat = new SimpleDateFormat("dd.MM.YYYY");
        dateFormat.setTimeZone(TimeZone.getDefault());
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

    public Date getDateObject(){
        return date;
    }

    public Long getDateLong() {
        return date.getTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTypeResult() {
        return typeResult;
    }

    public void setTypeResult(String typeResult) {
        this.typeResult = typeResult;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionBean> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionBean> questions) {
        this.questions = questions;
    }
}
