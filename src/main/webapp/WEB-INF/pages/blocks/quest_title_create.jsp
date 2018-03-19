<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="kozik" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<fmt:setBundle basename="by.kozik.quest.i18n.form_regexp" var="reg"/>
<div class="custom-error">
    <c:out value="${error_quest_message}"/>
</div>
<form class="quest-create-form" name="form_new_quest" action="/author/quest-title-create.html">
    <div>
        <c:url value="/rest/quest/categories" var="rest_category"/>
        <label for="quest_lang"><fmt:message key="message.label.quest-lang-create" bundle="${msg}"/></label>
        <select name="quest_lang" id="quest_lang" onchange="quest_category_service('${rest_category}','quest_categ')">
            <c:forEach var="entry" items="${languages}">
                <option value="${entry}">${entry}</option>
            </c:forEach>
        </select>
    </div>
    <div class="quest-category-choice">
        <label for="quest_categ"><fmt:message key="message.label.quest-categ-сreate" bundle="${msg}"/></label>
        <select name="quest_categ" id="quest_categ">
            <c:forEach var="entry" items="${categories}">
                <option value="${entry}">${entry}</option>
            </c:forEach>
        </select>
        <input type="checkbox" name="own_categ" id="own_categ" value="own"><label for="own_categ"><fmt:message key="message.label.categ-сreate" bundle="${msg}"/></label>
        <fmt:message key="regexp.quest.category" bundle="${reg}" var="reg_category"/>
        <input type="text" name="own_categ_text" pattern="${reg_category}">
        <div class="hint"><fmt:message key="message.hint.quest-category" bundle="${msg}"/></div>
    </div>
    <div>
        <label for="quest_title"><fmt:message key="message.label.quest-title" bundle="${msg}"/></label>
        <textarea rows="2" cols="30" name="quest_title" id="quest_title"></textarea>
        <div class="hint"><fmt:message key="message.hint.quest-title" bundle="${msg}"/></div>
    </div>
    <div>
        <textarea rows="20" cols="100" name="quest_descr"><fmt:message key="message.text.quest-description" bundle="${msg}"/></textarea>
        <div class="hint"><fmt:message key="message.hint.quest-description" bundle="${msg}"/></div>
    </div>
    <div>
        <c:forEach var="entry" items="${type_beans}">
            <input type="radio" name="quest_type" value="${entry.name}" id="${entry.name}"><label for="${entry.name}">${entry.description}</label>
            <br>
        </c:forEach>
    </div>
    <fmt:message key="message.button.create-questions" bundle="${msg}" var="msg_toquest"/>
    <fmt:message key="message.button.cancel" bundle="${msg}" var="msg_cancel"/>
    <input type="submit" class="button-quest" value="${msg_toquest}">
    <input type="submit" class="button-quest" value="${msg_cancel}" formaction="/main.html">
</form>


