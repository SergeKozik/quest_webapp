package by.kozik.quest.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serge on 01.01.2017.
 */
public class QuestBeanJS implements Serializable {
    private String title;
    private String titleName;
    private String language;
    private String languageName;
    private String category;
    private String categoryName;
    private String typeResult;
    private String typeResultName;
    private String typeResultNative;
    private String author;
    private String authorName;
    private String date;
    private Long dateLong;
    private String dateName;
    private int id;
    private int listId;
    private int passed;
    private String passedName;
    private String startName;
    private String deleteName;
    private List<FormActionBean> actionList;

    public QuestBeanJS(QuestBean sample) {
        this.title = sample.getTitle();
        this.language = sample.getLanguage();
        this.category = sample.getCategory();
        this.typeResult = sample.getTypeResult();
        this.author = sample.getAuthor();
        this.date = sample.getDate();
        this.dateLong = sample.getDateLong();
        this.id = sample.getId();
        this.passed = sample.getPassed();
        actionList = new ArrayList<>();
    }

    public QuestBeanJS() {
        actionList = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTypeResult() {
        return typeResult;
    }

    public void setTypeResult(String typeResult) {
        this.typeResult = typeResult;
    }

    public String getTypeResultName() {
        return typeResultName;
    }

    public void setTypeResultName(String typeResultName) {
        this.typeResultName = typeResultName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public String getPassedName() {
        return passedName;
    }

    public void setPassedName(String passedName) {
        this.passedName = passedName;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public String getDeleteName() {
        return deleteName;
    }

    public void setDeleteName(String deleteName) {
        this.deleteName = deleteName;
    }

    public Long getDateLong() {
        return dateLong;
    }

    public void setDateLong(Long dateLong) {
        this.dateLong = dateLong;
    }

    public List<FormActionBean> getActionList() {
        return actionList;
    }

    public void setActionList(List<FormActionBean> actionList) {
        this.actionList = actionList;
    }

    public String getTypeResultNative() {
        return typeResultNative;
    }

    public void setTypeResultNative(String typeResultNative) {
        this.typeResultNative = typeResultNative;
    }
}
