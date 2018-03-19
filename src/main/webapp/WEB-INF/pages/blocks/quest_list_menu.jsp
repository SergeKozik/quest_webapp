<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="kozik" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<div class="box sidebar">
<c:url value="/rest/quests" var="rest_url"/>
<form name="quest_menu_form" id="quest_menu_form" method="post" enctype="application/x-www-form-urlencoded">
    <div class="quest-menu">
        <h3><fmt:message key="message.label.lang-title" bundle="${msg}"/></h3>
        <ul>
            <c:forEach var="entry" items="${languages}">
                <li>
                    <input type="checkbox" name="quest_lang" id="${entry}" checked value="${entry}" onchange="quest_list_service('${rest_url}');"/>
                    <label for="${entry}">${entry}</label>
                </li>
            </c:forEach>

        </ul>
    </div>

    <div class="quest-menu">
        <h3><fmt:message key="message.label.quest-type" bundle="${msg}"/></h3>
        <ul>
            <c:forEach var="entry" items="${types}">
                <li>
                    <input type="checkbox" name="quest_type" id="${entry.name}" checked value="${entry.name}" onchange="quest_list_service('${rest_url}');"/>
                    <label for="${entry.name}">${entry.description}</label>
                </li>
            </c:forEach>
        </ul>
    </div>

    <div class="quest-menu">
        <h3><fmt:message key="message.label.quest-category" bundle="${msg}"/></h3>
        <ul>
            <c:forEach var="entry" items="${categories}">
                <li>
                    <input type="checkbox" name="quest_categ" id="${entry}" checked value="${entry}" onchange="quest_list_service('${rest_url}');"/>
                    <label for="${entry}">${entry}</label>
                </li>
            </c:forEach>
        </ul>
    </div>
</form>
</div>