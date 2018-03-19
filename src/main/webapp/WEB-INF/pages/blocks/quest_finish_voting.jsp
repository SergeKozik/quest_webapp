<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="kozik" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<div>
    <c:out value="${question_title}"/>
</div>
<table>
    <c:forEach var="entry" items="${answer_results}">
        <tr>
            <td>
                <c:out value="${entry.name}"/>
            </td>
            <td>
                <c:out value="${entry.description}"/>
            </td>
        </tr>
    </c:forEach>
</table>