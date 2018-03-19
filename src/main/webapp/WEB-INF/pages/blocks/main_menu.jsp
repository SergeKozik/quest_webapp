<%--
  Created by IntelliJ IDEA.
  User: Serge
  Date: 09.12.2016
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="kozik" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<nav class="main-menu">
    <li><a href="/main.html"><fmt:message key="message.menu.tomain" bundle="${msg}"/></a></li>
    <li><a href="/quests.html"><fmt:message key="message.menu.toquest" bundle="${msg}"/></a></li>
    <kozik:user authorized="true">
        <li class="mainnav-wrapper">
            <a href="#"><fmt:message key="message.menu.user" bundle="${msg}"/></a>
            <ul class="mainnav-submenu">
                <li>
                    <a href="/user/quest-own.html"><fmt:message key="message.menu.toauthor" bundle="${msg}"/></a>
                </li>
            </ul>
        </li>
    </kozik:user>
    <kozik:author authorized="true">
        <li class="mainnav-wrapper">
            <a href="#"><fmt:message key="message.menu.author" bundle="${msg}"/></a>
            <ul class="mainnav-submenu">
                <li>
                    <a href="/author/quest-create.html"><fmt:message key="message.menu.toauthor-create-quest" bundle="${msg}"/></a>
                </li>
                <li>
                    <a href="/author/quest-own.html"><fmt:message key="message.menu.toauthor" bundle="${msg}"/></a>
                </li>
            </ul>
        </li>
    </kozik:author>
    <kozik:admin authorized="true">
        <li class="mainnav-wrapper">
            <a href="#"><fmt:message key="message.menu.admin" bundle="${msg}"/></a>
            <ul class="mainnav-submenu">
                <li>
                    <a href="/admin/users.html"><fmt:message key="message.menu.admin-users" bundle="${msg}"/></a>
                </li>
            </ul>
        </li>
    </kozik:admin>
</nav>
