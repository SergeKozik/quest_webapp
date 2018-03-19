package by.kozik.quest.tag;

import by.kozik.quest.service.RoleEnum;
import by.kozik.quest.entity.UserBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Serge on 13.12.2016.
 */
public class UserTagRoleString extends TagSupport {
    private int code;
    private static Logger logger = LogManager.getLogger();

    public void setCode(int code) {
        this.code = code;
    }

    public UserTagRoleString() {
    }

    @Override
    public int doStartTag() throws JspException {
        int result = SKIP_BODY;
        if (code!=0) {
            try {
                JspWriter out = pageContext.getOut();
                out.write(RoleEnum.findByPriority(code).name().toLowerCase());
            } catch (IOException e) {
                logger.error("Error in UserTagRoleString, JspWriter.");
            }
        }
        return result;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
