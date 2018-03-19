<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="kozik" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="by.kozik.quest.i18n.messages" var="msg"/>
<fmt:message key="message.button.delete" bundle="${msg}" var="msg_del"/>
<fmt:message key="message.button.edit" bundle="${msg}" var="msg_edit"/>
<fmt:message key="message.button.restore" bundle="${msg}" var="msg_restore"/>
<div class="custom-error">
    <c:out value="${error_admin_message}"/>
</div>
<kozik:admin authorized="true">
    <div class="scroll-users">
        <table>
            <thead>
            <tr>
                <td>
                    <fmt:message key="message.label.user-nick" bundle="${msg}"/>
                </td>
                <td>
                    <fmt:message key="message.label.user-email" bundle="${msg}"/>
                </td>
                <td>
                    <fmt:message key="message.label.user-role" bundle="${msg}"/>
                </td>
                <td>
                </td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="entry" items="${user_list}">
                <tr>
                    <td>
                        <c:out value="${entry.nick}"/>
                    </td>
                    <td>
                        <c:out value="${entry.email}"/>
                    </td>
                    <td>
                        <kozik:rolename code="${entry.role}"/>
                    </td>
                    <td>
                        <form>
                            <input type="hidden" name="user_id" value="${entry.id}">
                            <button formaction="/admin/user-edit.html"><c:out value="${msg_edit}"/></button>
                            <button formaction="/admin/user-delete.html"><c:out value="${msg_del}"/></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <form action="/admin/user-add.html">
        <fmt:message key="message.button.user-add" bundle="${msg}" var="msg_add"/>
        <input type="submit" value="${msg_add}">
    </form>
    <br>
    <fmt:message key="message.label.bin-users" bundle="${msg}"/>
    <form action="/admin/user-bin-clear.html">
        <div class="scroll-users">
            <table>
                <thead>
                <tr>
                    <td>
                        <fmt:message key="message.label.user-nick" bundle="${msg}"/>
                    </td>
                    <td>
                        <fmt:message key="message.label.user-email" bundle="${msg}"/>
                    </td>
                    <td>
                        <fmt:message key="message.label.user-role" bundle="${msg}"/>
                    </td>
                    <td>
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${user_list_bin}">
                    <input type="hidden" name="list_user_bin" value="${entry.id}"/>
                    <tr>
                        <td>
                            <c:out value="${entry.nick}"/>
                        </td>
                        <td>
                            <c:out value="${entry.email}"/>
                        </td>
                        <td>
                            <kozik:rolename code="${entry.role}"/>
                        </td>
                        <td>
                            <input type="hidden" name="user_id" value="${entry.id}">
                            <button formaction="/admin/user-restore.html"><c:out value="${msg_restore}"/></button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <fmt:message key="message.button.clear-bin" bundle="${msg}" var="msg_clear"/>
        <input type="submit" value="${msg_clear}">
    </form>
</kozik:admin>