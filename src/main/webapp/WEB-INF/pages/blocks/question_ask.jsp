<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="kozik" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<div class="custom-error">
    <c:out value="${error_question_message}"/>
</div>
<div>
    <form action="/user/quest-next-question.html">
        <input type="hidden" name="question_title" value="${current_question.formulation}">
        <textarea rows="2" cols="50" disabled><c:out value="${current_question.formulation}"/></textarea>
        <br>
        <c:forEach var="answer" items="${current_question.variants}">
            <input type="radio" name="current_answer" value="${answer.id}" id="answ${answer.id}"/><label for="answ${answer.id}"><c:out value="${answer.formulation}"/></label>
            <kozik:answer-text answer="${answer}">
                <textarea rows="3" cols="30" name="current_answer_text"></textarea>
            </kozik:answer-text>
            <br>
        </c:forEach>
        <fmt:message key="message.button.question-next" bundle="${msg}" var="msg_next"/>
        <fmt:message key="message.button.question-finish" bundle="${msg}" var="msg_finish"/>
        <fmt:message key="message.button.question-skip" bundle="${msg}" var="msg_skip"/>
        <input class="finish-button-check" type="checkbox" name="typeLink" value="toFinish" id="finishLink" onclick="this.form.submit();"/><label for="finishLink"><c:out value="${msg_finish}"/></label>
        <input type="submit" class="button-quest" formaction="/user/quest-skip-question.html" value="${msg_skip}">
        <input type="submit" class="button-quest" value="${msg_next}">
    </form>
</div>
