package by.kozik.quest.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Serge on 08.01.2017.
 */
public class QuestionBean implements Serializable{
    private String formulation;
    private List<AnswerVariantBasic> variants;
    private int questId;
    private int id;

    public QuestionBean() {
    }

    public String getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {
        this.formulation = formulation;
    }

    public List<AnswerVariantBasic> getVariants() {
        return variants;
    }

    public void setVariants(List<AnswerVariantBasic> variants) {
        this.variants = variants;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
