<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<html>
<head>
    <tiles:insertAttribute name="include"/>
    <title>
        <fmt:message key="message.application.title" bundle="${msg}"/>
    </title>
</head>
<body>
    <div class="wrapper">
        <tiles:insertAttribute name="header"/>
        <tiles:insertAttribute name="main-menu"/>
        <div class="main">
            <tiles:insertAttribute name="side-bar"/>
            <div class="box content">
                <tiles:insertAttribute name="main-content"/>
            </div>
        </div>
        <tiles:insertAttribute name="footer"/>
    </div>
</body>
</html>
