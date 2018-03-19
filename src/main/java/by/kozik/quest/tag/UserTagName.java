package by.kozik.quest.tag;

import by.kozik.quest.entity.UserBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Serge on 10.12.2016.
 */
public class UserTagName extends TagSupport {
    private static Logger logger = LogManager.getLogger();

    @Override
    public int doStartTag() throws JspException {
        int result = SKIP_BODY;
        HttpSession session = pageContext.getSession();
        Object tmpUser = session.getAttribute("user");
        if ((tmpUser!=null)&&(tmpUser instanceof UserBean)) {
            UserBean userBean = (UserBean)tmpUser;
            try {
                JspWriter out = pageContext.getOut();
                out.write(userBean.getNick());
            } catch (IOException e) {
                logger.error("Error in UserTagName, JspWriter.");
            }
        }
        return result;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
