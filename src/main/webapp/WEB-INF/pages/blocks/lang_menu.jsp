<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<div class="headmenu-wrapper">
    <fmt:message key="message.label.lang-title" bundle="${msg}"/>
    <ul class="headmenu">
        <li><a href="/language.html?lang=ru_RU"><img src="/img/russia_640.png" width="20" class="lang-image"><fmt:message key="message.label.lang-title-ru" bundle="${msg}"/></a></li>
        <li><a href="/language.html?lang=en_GB"><img src="/img/united_kingdom_640.png" width="20" class="lang-image"><fmt:message key="message.label.lang-title-en" bundle="${msg}"/></a></li>
    </ul>
</div>