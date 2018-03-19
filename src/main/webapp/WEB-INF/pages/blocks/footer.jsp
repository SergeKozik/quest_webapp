<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<div class="footer">
    <div>
        <fmt:message key="message.application.foot-text" bundle="${msg}"/>
    </div>
</div>
