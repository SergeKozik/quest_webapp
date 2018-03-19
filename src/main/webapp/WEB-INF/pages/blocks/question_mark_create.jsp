<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="kozik" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<div class="custom-error">
    <c:out value="${error_question_message}"/>
</div>
<form class="question-create-form" action="/author/next-question-mark.html">
    <label for="question_title"><fmt:message key="message.label.question-сreate" bundle="${msg}"/></label>
    <textarea rows="2" cols="30" name="question_title" id="question_title"></textarea>
    <div class="hint"><fmt:message key="message.hint.question-title" bundle="${msg}"/></div>
    <br>
    <div class="answer-multiple">
        <table id="table_answers">
            <thead>
            <tr>
                <td><fmt:message key="message.label.answers-title" bundle="${msg}"/></td>
                <td><fmt:message key="message.label.answers-mark" bundle="${msg}"/></td>
                <td></td>
            </tr>
            </thead>
        </table>
        <fmt:message key="message.button.answer-del" bundle="${msg}" var="msg_del"/>
        <fmt:message key="message.label.answer-yes" bundle="${msg}" var="msg_yes"/>
        <fmt:message key="message.label.answer-no" bundle="${msg}" var="msg_no"/>
        <fmt:message key="message.label.answers-text-hint" bundle="${msg}" var="msg_text"/>
        <fmt:message key="message.label.answers-text-ask" bundle="${msg}" var="msg_text_placeholder"/>
        <input type="button" class="button-answer add" id="answer_custom" onclick="add_answer_custom_mark('table_answers','${msg_del}')"/>
        <label for="answer_custom"><fmt:message key="message.button.answer-custom-add" bundle="${msg}"/></label>
        <input type="button" class="button-answer add" id="answer_yesno" onclick="add_answer_yesno_mark('table_answers','${msg_del}','${msg_yes}','${msg_no}')"/>
        <label for="answer_yesno"><fmt:message key="message.button.answer-yesno-add" bundle="${msg}"/></label>
        <input type="button" class="button-answer add" id="answer_user" onclick="add_answer_user('table_answers','${msg_text_placeholder}','${msg_text}','${msg_del}')"/>
        <label for="answer_user"><fmt:message key="message.button.answer-user-add" bundle="${msg}"/></label>
    </div>
    <fmt:message key="message.button.next-question" bundle="${msg}" var="msg_tonext"/>
    <input type="submit" class="button-quest" value="${msg_tonext}"/>
    <input class="finish-button-check" type="checkbox" name="typeLink" value="toFinish" id="finishLink" onclick="this.form.submit();"/><label for="finishLink"><fmt:message key="message.button.question-finish" bundle="${msg}"/></label>
</form>
<form action="/author/next-question-mark.html">
    <fmt:message key="message.button.rewrite-question" bundle="${msg}" var="msg_cancel"/>
    <input type="hidden" name="typeLink" value="toNew"/>
    <input type="submit" class="button-quest" value="${msg_cancel}"/>
</form>