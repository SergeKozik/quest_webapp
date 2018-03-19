<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="kozik" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<jsp:useBean id="startQuest" scope="session" class="by.kozik.quest.entity.QuestBean"/>
<div>
    <c:out value="${startQuest.title}"/>
</div>
<div>
    <c:out value="${startQuest.description}"/>
</div>
<fmt:message key="message.button.quest-start" bundle="${msg}" var="msg_start"/>
<fmt:message key="message.button.cancel" bundle="${msg}" var="msg_cancel"/>
<form action="/user/quest-run.html">
    <input type="submit" value="${msg_start}">
    <input type="submit" formaction="/quests.html" value="${msg_cancel}">
</form>
