<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="kozik" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<div class="custom-error">
    <c:out value="${error_question_message}"/>
</div>
<form class="question-create-form">
    <label for="question_title"><fmt:message key="message.label.question-сreate" bundle="${msg}"/></label>
    <textarea rows="2" cols="30" name="question_title" id="question_title"><c:out value="${newQuest.title}"/></textarea>
    <div class="hint"><fmt:message key="message.hint.question-title" bundle="${msg}"/></div>
    <br>
    <div class="answer-multiple">
        <table id="table_answers">
        </table>
        <fmt:message key="message.button.answer-del" bundle="${msg}" var="msg_del"/>
        <fmt:message key="message.label.answer-yes" bundle="${msg}" var="msg_yes"/>
        <fmt:message key="message.label.answer-no" bundle="${msg}" var="msg_no"/>
        <input type="button" class="button-answer add" id="answer_custom" onclick="add_answer_custom_nomark('table_answers','${msg_del}')"/>
        <label for="answer_custom"><fmt:message key="message.button.answer-custom-add" bundle="${msg}"/></label>
        <input type="button" class="button-answer add" id="answer_yesno" onclick="add_answer_yesno_nomark('table_answers','${msg_del}','${msg_yes}','${msg_no}')"/>
        <label for="answer_yesno"><fmt:message key="message.button.answer-yesno-add" bundle="${msg}"/></label>
    </div>
    <fmt:message key="message.button.finish-quest-create" bundle="${msg}" var="msg_tofinish"/>
    <fmt:message key="message.button.rewrite-question" bundle="${msg}" var="msg_cancel"/>
    <input type="submit" class="button-quest" value="${msg_tofinish}" formaction="/author/next-question-vote.html">
    <input type="submit" class="button-quest" value="${msg_cancel}" formaction="/author/cancel-question.html">
</form>