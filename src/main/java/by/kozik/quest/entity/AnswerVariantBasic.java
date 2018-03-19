package by.kozik.quest.entity;

import by.kozik.quest.service.AnswerTypeEnum;

import java.io.Serializable;

/**
 * Created by Serge on 08.01.2017.
 */
public class AnswerVariantBasic implements Serializable {
    private String formulation;
    private int id;
    private int questionId;
    private AnswerTypeEnum type;

    public AnswerVariantBasic() {
        type = AnswerTypeEnum.CASE_TEXT;
    }

    public String getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {
        this.formulation = formulation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public AnswerTypeEnum getType() {
        return type;
    }

    public void setType(AnswerTypeEnum type) {
        this.type = type;
    }
}
