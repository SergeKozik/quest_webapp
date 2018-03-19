package by.kozik.quest.tag;

import by.kozik.quest.service.RoleEnum;
import by.kozik.quest.entity.UserBean;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by Serge on 10.12.2016.
 */
public abstract class AbstractRoleTag extends TagSupport {
    protected RoleEnum role;
    private boolean authorized = false;

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    @Override
    public int doStartTag() throws JspException {
        int result = SKIP_BODY;
        HttpSession session = pageContext.getSession();
        Object tmpUser = session.getAttribute("user");
        if ((tmpUser!=null)&&(tmpUser instanceof UserBean)) {
            UserBean userBean = (UserBean)tmpUser;
            if (((userBean.getRole()>=role.getPriority())&&(authorized==true))
                    || ((userBean.getRole()<role.getPriority())&&(authorized==false))) {
                result = EVAL_BODY_INCLUDE;
            }
        } else {
            if (authorized==false) {
                result = EVAL_BODY_INCLUDE;
            }
        }
        return result;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
