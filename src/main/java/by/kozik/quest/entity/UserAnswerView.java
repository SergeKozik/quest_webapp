package by.kozik.quest.entity;

import java.io.Serializable;

/**
 * Created by Serge on 25.01.2017.
 */
public class UserAnswerView implements Serializable {
    private String userAnswer;

    public UserAnswerView() {
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
