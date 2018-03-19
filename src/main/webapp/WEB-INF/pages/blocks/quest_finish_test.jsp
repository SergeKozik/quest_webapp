<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="kozik" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<div>
    <fmt:message key="message.label.report.quest-completed" bundle="${msg}" var="msg_thanks"/>
    <c:out value="${msg_thanks}"/>
</div>
<div>
    <fmt:message key="message.label.report.quest-score" bundle="${msg}" var="msg_score"/>
    <fmt:message key="message.label.report.quest-score-from" bundle="${msg}" var="msg_from"/>
    <c:out value="${msg_thanks} ${test_score} ${msg_from} ${test_total}"/>
</div>
