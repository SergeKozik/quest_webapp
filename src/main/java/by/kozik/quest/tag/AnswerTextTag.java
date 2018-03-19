package by.kozik.quest.tag;

import by.kozik.quest.service.AnswerTypeEnum;

import javax.servlet.jsp.JspException;

/**
 * Created by Serge on 14.01.2017.
 */
public class AnswerTextTag extends AbstractAnswerTag {
    @Override
    public int doStartTag() throws JspException {
        int result = SKIP_BODY;
        if (answer==null) {
            return result;
        }
        if ((answer.getType()== AnswerTypeEnum.USER_TEXT)) {
            result = EVAL_BODY_INCLUDE;
        }
        return result;
    }
}
