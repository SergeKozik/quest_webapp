<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="kozik" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<div class="headmenu-wrapper">
    <kozik:user authorized="true">
        <fmt:message key="message.user.greeting" bundle="${msg}"/> <kozik:name/>
        <ul class="headmenu">
            <li><a href="/logout.html"><fmt:message key="message.menu.user-logout" bundle="${msg}"/></a></li>
        </ul>
    </kozik:user>
    <kozik:user authorized="false">
        <a href="/login.page"><fmt:message key="message.button.user-login" bundle="${msg}"/></a>
        <a href="/register.html"><fmt:message key="message.button.user-register" bundle="${msg}"/></a>
    </kozik:user>
</div>

