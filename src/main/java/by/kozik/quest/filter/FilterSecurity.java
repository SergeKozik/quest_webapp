package by.kozik.quest.filter;

import by.kozik.quest.entity.UserBean;
import by.kozik.quest.service.RoleEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Serge on 20.12.2016.
 */
public class FilterSecurity implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uriString = request.getRequestURI();
        HttpSession session = request.getSession();
        Object userObject= session.getAttribute("user");
        UserBean user;
        boolean flagAutorized = true;
        int role=0;
        if ((userObject!=null)&&(userObject instanceof UserBean)) {
            user = (UserBean)userObject;
            role = user.getRole();
        }
        if ((uriString.startsWith("/admin"))&&(role<RoleEnum.ADMIN.getPriority())) {
                flagAutorized = false;
        }
        if ((uriString.startsWith("/user"))&&(role<RoleEnum.USER.getPriority())) {
            flagAutorized = false;
        }
        if ((uriString.startsWith("/author"))&&(role<RoleEnum.AUTHOR.getPriority())) {
            flagAutorized = false;
        }
        if (!flagAutorized) {
            response.sendRedirect("/main.html");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}