<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<div class="custom-error">
    <c:out value="${error_message}"/>
</div>
<form action="${last_action}">
    <fmt:message key="message.button.tolast-action" bundle="${msg}" var="msg_last"/>
    <input type="submit" value="${msg_last}">
</form>