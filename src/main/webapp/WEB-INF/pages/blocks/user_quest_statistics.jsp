<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="kozik" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<kozik:user authorized="true">
    <fmt:message key="message.label.result-user" bundle="${msg}"/>
    <br>
    <c:out value="${users_result.user.nick}"/>
    <br>
    <fmt:message key="message.label.result-date" bundle="${msg}"/>
    <br>
    <c:out value="${users_result.date}"/>
    <br>
    <br>
    <c:forEach var="question" items="${users_result.questions}">
        <fmt:message key="message.label.question-title" bundle="${msg}"/>
        <br>
        <c:out value="${question.formulation}"/>
        <br>
        <fmt:message key="message.label.answers-given" bundle="${msg}"/>
        <br>
        <c:forEach var="answer" items="${question.answers}">
            <c:out value="${answer.userAnswer}"/>
            <br>
        </c:forEach>
        <br>
    </c:forEach>
</kozik:user>
