<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<fmt:setBundle basename="by.kozik.quest.i18n.form_regexp" var="reg"/>
<div class="custom-error" id = "divErrorId">
    <c:out value="${error_register_message}"/>
</div>
<fmt:message key="message.label.error.fields-empty" bundle="${msg}" var="msg_js"/>
<fmt:message key="message.label.mark-necessary" bundle="${msg}" var="msg_asterisk"/>
<form class="login-form" name="form_register" action="${action_link}" method="post" onsubmit="return validate_register('divErrorId','${msg_js}');">
    <table>
        <tr>
            <td><fmt:message key="message.label.register-nick" bundle="${msg}"/> </td>
            <td>
                <fmt:message key="regexp.login" bundle="${reg}" var="login_reg"/>
                <input name="user_name" type="text" value="" required pattern="${login_reg}"/>
                <div class="hint"><fmt:message key="message.hint.register-nick" bundle="${msg}"/></div>
            </td>
            <td>
                <c:out value="${msg_asterisk}"/>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="message.label.register-password" bundle="${msg}"/></td>
            <td>
                <fmt:message key="regexp.password" bundle="${reg}" var="login_pass"/>
                <input name="passw" type="password" value="" required pattern="${login_pass}"/>
                <div class="hint"><fmt:message key="message.hint.register-password" bundle="${msg}"/> </div>
            </td>
            <td>
                <c:out value="${msg_asterisk}"/>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="message.label.register-password-double" bundle="${msg}"/></td>
            <td>
                <input name="passw_double" type="password" value="" required pattern="${login_pass}"/>
            </td>
            <td>
                <c:out value="${msg_asterisk}"/>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="message.label.register-email" bundle="${msg}"/></td>
            <td>
                <fmt:message key="regexp.email" bundle="${reg}" var="login_email"/>
                <input name="email" type="email" value="" pattern="${login_email}"/>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="message.label.user-role" bundle="${msg}"/>
            </td>
            <td>
                <select name="select_role">
                    <c:forEach var="entry" items="${roles}">
                        <option value="${entry.name}">${entry.description}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <c:out value="${msg_asterisk}"/>
            </td>
        </tr>
    </table>
    <fmt:message key="message.button.user-register" bundle="${msg}" var="msg_login"/>
    <input class="button" type="submit" value="${msg_login}">
</form>
