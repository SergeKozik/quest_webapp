package by.kozik.quest.entity;

import by.kozik.quest.service.AnswerTypeEnum;

/**
 * Created by Serge on 08.01.2017.
 */
public class AnswerVariantCaseMark extends AnswerVariantBasic {
    private double mark;

    public AnswerVariantCaseMark() {
        setType(AnswerTypeEnum.CASE_TEXT);
        mark=0;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }
}
