package by.kozik.quest.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Serge on 01.12.2016.
 */
public class FilterEncoding implements Filter {
    private String code;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        code = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String codeRequest = servletRequest.getCharacterEncoding();
        if ((code!=null)&&(!code.equalsIgnoreCase(codeRequest))) {
            servletRequest.setCharacterEncoding(code);
            servletResponse.setCharacterEncoding(code);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        code = null;
    }
}
