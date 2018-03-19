<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<fmt:setBundle basename="by.kozik.quest.i18n.form_regexp" var="reg"/>
<fmt:message key="message.label.error.login-js-validation" bundle="${msg}" var="msg_error"/>
<form class="login-form" name="form_login" action="/login-user.html" method="post" onsubmit="return validate_login('login_error','${msg_error}');">
    <div id="login_error" class="custom-error">
        <c:out value="${error_login_message}"/>
    </div>
    <table>
        <tr>
            <td><fmt:message key="message.label.login-nick" bundle="${msg}"/> </td>
            <td>
                <input name="user_name" type="text" value=""/>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="message.label.login-password" bundle="${msg}"/></td>
            <td>
                <input name="passw" type="password" value=""/>
            </td>
        </tr>
    </table>
    <fmt:message key="message.button.user-login" bundle="${msg}" var="msg_login"/>
    <input class="button" type="submit" value="${msg_login}">
</form>
</div>
