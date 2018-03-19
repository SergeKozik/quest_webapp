package by.kozik.quest.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Serge on 25.01.2017.
 */
public class UserQuestionView implements Serializable {
    private String formulation;
    private List<UserAnswerView> answers;

    public UserQuestionView() {
    }

    public String getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {
        this.formulation = formulation;
    }

    public List<UserAnswerView> getAnswers() {
        return answers;
    }

    public void setAnswers(List<UserAnswerView> answers) {
        this.answers = answers;
    }
}
