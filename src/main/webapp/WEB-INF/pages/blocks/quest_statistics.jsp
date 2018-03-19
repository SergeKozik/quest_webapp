<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="kozik" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<kozik:user authorized="true">
    <form action="/user/user-result.html">
        <table>
            <thead>
            <tr>
                <td>
                    <fmt:message key="message.label.user" bundle="${msg}"/>
                </td>
                <td>
                    <fmt:message key="message.label.user-score" bundle="${msg}"/>
                </td>
                <td>
                    <fmt:message key="message.label.user-result-date" bundle="${msg}"/>
                </td>
                <td>

                </td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="entry" items="${users_results}">
                <tr>
                    <td>
                        <c:out value="${entry.user.nick}"/>
                    </td>
                    <td>
                        <c:out value="${entry.quantityResult}"/>
                    </td>
                    <td>
                        <c:out value="${entry.date}"/>
                    </td>
                    <td>
                        <input class="finish-button-check" type="checkbox" name="result_id" value="${entry.id}" id="check${entry.id}" onclick="this.form.submit();"/>
                        <label for="check${entry.id}"><fmt:message key="message.label.user-result-show" bundle="${msg}"/></label>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>
</kozik:user>