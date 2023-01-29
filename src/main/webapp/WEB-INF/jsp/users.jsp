<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://company.com/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="container mt-3 ml-3">
    <p class="h3 mb-4 text-center"><fmt:message key="user.title" bundle="${lang}"/></p>
    <c:choose>
        <c:when test="${not empty errorCode}">
            <div class="alert alert-warning" role="alert">
                <fmt:message key="${errorCode}" bundle="${lang}"/>
            </div>
        </c:when>
    </c:choose>
    <table class="table" aria-describedby="users-table">
        <thead class="thead-light">
        <tr>
            <th scope="col"><fmt:message key="user.name" bundle="${lang}"/></th>
            <th scope="col"><fmt:message key="user.email" bundle="${lang}"/></th>
            <th scope="col"><fmt:message key="user.role" bundle="${lang}"/></th>
            <th scope="col"><fmt:message key="user.registered" bundle="${lang}"/></th>
            <c:choose>
                <c:when test="${ADMINISTRATOR == SESSION_ROLE}">
                    <th scope="col"><fmt:message key="app.actions" bundle="${lang}"/></th>
                </c:when>
            </c:choose>
        </tr>
        </thead>
        <c:forEach items="${users}" var="user">
            <jsp:useBean id="user" type="com.company.model.User"/>
            <tr>
                <td><c:out value="${user.name}"/></td>
                <td><a href="mailto:${user.email}">${user.email}</a></td>
                <td>
                    <c:choose>
                        <c:when test="${user.role eq ADMINISTRATOR}">
                            <button class="btm-xsm-red" disabled>
                                <fmt:message key="user.role.administrator" bundle="${lang}"/>
                            </button>
                        </c:when>
                        <c:when test="${user.role eq LIBRARIAN}">
                            <button class="btm-xsm-green" disabled>
                                <fmt:message key="user.role.librarian" bundle="${lang}"/>
                            </button>
                        </c:when>
                        <c:otherwise>
                            <button class="btm-xsm-blue" disabled>
                                <fmt:message key="user.role.reader" bundle="${lang}"/>
                            </button>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${fn:formatDateTime(user.registered)}</td>
                <td>
                    <c:choose>
                    <c:when test="${ADMINISTRATOR == SESSION_ROLE}">
                        <a class="btn btn-info btn-sm"
                           href="users/update/${user.id}">
                            <fmt:message key="app.button.update" bundle="${lang}"/>
                        </a>

                        <a class="btn btn-danger btn-sm"
                           href="${user.id eq sessionScope.userId ? "users" : "users/delete/".concat(user.id)}" >
                            <fmt:message key="app.button.delete" bundle="${lang}"/>
                        </a>
                    </c:when>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
