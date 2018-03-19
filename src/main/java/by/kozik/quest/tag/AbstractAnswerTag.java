package by.kozik.quest.tag;

import by.kozik.quest.entity.AnswerVariantBasic;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by Serge on 14.01.2017.
 */
public abstract class AbstractAnswerTag extends TagSupport {
    protected AnswerVariantBasic answer;

    @Override
    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public void setAnswer(AnswerVariantBasic answer) {
        this.answer = answer;
    }
}
