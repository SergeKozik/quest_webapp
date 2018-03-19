package by.kozik.quest.entity;

import by.kozik.quest.service.AnswerTypeEnum;

/**
 * Created by Serge on 08.01.2017.
 */
public class AnswerVariantUserText extends AnswerVariantBasic {
    private String userText;

    public AnswerVariantUserText() {
        setType(AnswerTypeEnum.USER_TEXT);
    }

    public AnswerVariantUserText(String userText) {
        setType(AnswerTypeEnum.USER_TEXT);
        this.userText = userText;
    }

    public AnswerVariantUserText(String userText, AnswerVariantBasic basic) {
        setId(basic.getId());
        setFormulation(basic.getFormulation());
        setQuestionId(basic.getQuestionId());
        setType(AnswerTypeEnum.USER_TEXT);
        this.userText = userText;
    }


    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }
}
