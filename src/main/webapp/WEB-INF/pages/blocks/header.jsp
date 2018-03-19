<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<div class="header">
    <fmt:message key="message.application.head-greeting" bundle="${msg}"/>
    <tiles:insertAttribute name="login-menu"/>
    <tiles:insertAttribute name="lang-menu"/>
</div>

