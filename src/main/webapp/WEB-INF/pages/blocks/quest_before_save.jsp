<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="kozik" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<jsp:useBean id="newQuest" scope="session" class="by.kozik.quest.entity.QuestBean"/>
<div class="custom-error">
    <c:out value="${error_quest_message}"/>
</div>
<form action="/author/quest-save.html">
    <table>
        <tr>
            <td><fmt:message key="message.label.quest-title" bundle="${msg}"/></td>
            <td><c:out value="${newQuest.title}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="message.label.quest-language" bundle="${msg}"/></td>
            <td><c:out value="${newQuest.language}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="message.label.quest-category" bundle="${msg}"/></td>
            <td><c:out value="${newQuest.category}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="message.label.quest-type" bundle="${msg}"/></td>
            <td><c:out value="${quest_type}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="message.label.quest-description" bundle="${msg}"/></td>
            <td><textarea rows="10" cols="40" disabled><c:out value="${newQuest.description}"/></textarea></td>
        </tr>
        <tr>
            <td><fmt:message key="message.label.quest-questions" bundle="${msg}"/></td>
            <td><c:out value="${quest_questions}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="message.label.quest-marks" bundle="${msg}"/></td>
            <td><c:out value="${quest_marks}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="message.label.quest-author" bundle="${msg}"/></td>
            <td><c:out value="${newQuest.author}"/></td>
        </tr>
    </table>
    <fmt:message key="message.button.quest-create" bundle="${msg}" var="msg_create"/>
    <input type="submit" value="${msg_create}"/>
</form>
<form action="/author/quest-save-cancel.html">
    <fmt:message key="message.button.quest-create-cancel" bundle="${msg}" var="msg_cancel"/>
    <input type="submit" value="${msg_cancel}"/>
</form>
