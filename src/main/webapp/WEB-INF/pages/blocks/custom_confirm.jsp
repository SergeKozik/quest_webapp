<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<div class="custom-error">
    <c:out value="${ask_message}"/>
</div>
<form action="${proceed_link}">
    <fmt:message key="message.button.ok" bundle="${msg}" var="msg_ok"/>
    <fmt:message key="message.button.cancel" bundle="${msg}" var="msg_cancel"/>
    <input type="submit" value="${msg_ok}">
    <input type="submit" value="${msg_cancel}" formaction="${cancel_link}">
</form>
