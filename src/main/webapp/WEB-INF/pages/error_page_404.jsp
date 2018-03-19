<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isErrorPage="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/default_design.css'/>">
    <title><fmt:message key="message.application.title" bundle="${msg}"/></title>
</head>
<body>
<div class="wrapper">
    <div class="header">
        <fmt:message key="message.application.head-error" bundle="${msg}"/>${pageContext.errorData.statusCode}
        <jsp:include page="blocks/login_menu.jsp"/>
        <jsp:include page="blocks/lang_menu.jsp"/>
    </div>
    <jsp:include page="blocks/main_menu.jsp"/>
    <div class="main">
        <div class="box content">
            <fmt:message key="message.error.greeting-404" bundle="${msg}"/>
        </div>
    </div>
    <jsp:include page="blocks/footer.jsp"/>
</div>
</body>
</html>
